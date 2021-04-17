package fr.mrmicky.mousedelayfixer;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.List;

public class Tweaker implements ITweaker {

    private static final String[] LAUNCH_ARGUMENTS = new String[0];

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        //
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        try {
            classLoader.registerTransformer("fr.mrmicky.mousedelayfixer.ClassTransformer");
        } catch (Exception e) {
            MouseDelayFixer.logError("Unable to inject into LaunchClassLoader", e);
        }
    }

    @Override
    public String getLaunchTarget() {
        return null;
    }

    @Override
    public String[] getLaunchArguments() {
        return LAUNCH_ARGUMENTS;
    }
}
