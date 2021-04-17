package fr.mrmicky.mousedelayfixer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Based on https://github.com/prplz/MouseDelayFix
 */
public class ClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        try {
            VersionInfo version = VersionInfo.detectVersion();

            if (version == null) {
                MouseDelayFixer.logError("Unsupported Minecraft version");

                return basicClass;
            }

            if (!version.isLivingEntityBaseClass(transformedName)) {
                return basicClass;
            }

            MouseDelayFixer.log("Found EntityLivingBase class: " + name);

            ClassReader classReader = new ClassReader(basicClass);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);

            for (MethodNode method : classNode.methods) {
                if (version.isGetLookMethod(method.name) && method.desc.startsWith("(F)")) {
                    MouseDelayFixer.log("Found getLook method: " + method.name);

                    String entity = version.getEntityClass();
                    String entityPlayerSP = version.getEntityPlayerSPClass();

                    // if (this instanceof EntityPlayerSP) {
                    //     return super.getLook(f);
                    // }

                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    insnList.add(new TypeInsnNode(Opcodes.INSTANCEOF, entityPlayerSP));
                    LabelNode label = new LabelNode();
                    insnList.add(new JumpInsnNode(Opcodes.IFEQ, label));
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    insnList.add(new VarInsnNode(Opcodes.FLOAD, 1));
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, entity, method.name, method.desc));
                    insnList.add(new InsnNode(Opcodes.ARETURN));
                    insnList.add(label);
                    method.instructions.insertBefore(method.instructions.getFirst(), insnList);

                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                    classNode.accept(classWriter);

                    MouseDelayFixer.log("EntityLivingBase successfully transformed !");

                    return classWriter.toByteArray();
                }
            }

            throw new RuntimeException("Can't find getLook() method in EntityLivingBase");

        } catch (Exception e) {
            MouseDelayFixer.logError("Error while transforming EntityLivingBase", e);
        }

        return basicClass;
    }
}
