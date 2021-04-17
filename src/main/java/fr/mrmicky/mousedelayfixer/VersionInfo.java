package fr.mrmicky.mousedelayfixer;

public enum VersionInfo {

    DEFAULT("Entity", "EntityLivingBase", "EntityPlayerSP", "getLook"),
    V1_8_0("wv", "xm", "cio", "d"),
    V1_8_8("pk", "pr", "bew", "d"),
    V1_9_0("rr", "sa", "bmt", "f"),
    V1_9_4("rr", "sa", "bmr", "f"),
    V1_10_2("rw", "sf", "bnn", "f");

    private final String entityClass;
    private final String entityLivingBaseClass;
    private final String entityPlayerSPClass;
    private final String getLookMethod;

    VersionInfo(String entityClass, String entityLivingBaseClass, String entityPlayerSPClass, String getLookMethod) {
        this.entityClass = entityClass;
        this.entityLivingBaseClass = entityLivingBaseClass;
        this.entityPlayerSPClass = entityPlayerSPClass;
        this.getLookMethod = getLookMethod;
    }

    public String getEntityClass() {
        return this.entityClass;
    }

    public boolean isEntityClass(String name) {
        return this.entityClass.equals(name) || DEFAULT.getEntityClass().equals(name);
    }

    public String getEntityLivingBaseClass() {
        return this.entityLivingBaseClass;
    }

    public boolean isLivingEntityBaseClass(String name) {
        return this.entityLivingBaseClass.equals(name) || DEFAULT.getEntityLivingBaseClass().equals(name);
    }

    public String getEntityPlayerSPClass() {
        return this.entityPlayerSPClass;
    }

    public boolean isEntityPlayerSPClass(String name) {
        return this.entityPlayerSPClass.equals(name) || DEFAULT.getEntityPlayerSPClass().equals(name);
    }

    public String getGetLookMethod() {
        return this.getLookMethod;
    }

    public boolean isGetLookMethod(String name) {
        return this.getLookMethod.equals(name) || DEFAULT.getLookMethod.equals(name);
    }

    public static VersionInfo detectVersion() {
        try {
            Class<?> realmsConstants = Class.forName("net.minecraft.realms.RealmsSharedConstants");
            String version = (String) realmsConstants.getDeclaredField("VERSION_STRING").get(null);

            switch (version) {
                case "1.8":
                case "1.8.1":
                case "1.8.2":
                case "1.8.3":
                case "1.8.4":
                case "1.8.5":
                case "1.8.6":
                case "1.8.7":
                    return V1_8_0;
                case "1.8.8":
                case "1.8.9":
                    return V1_8_8;
                case "1.9":
                case "1.9.1":
                case "1.9.2":
                case "1.9.3":
                    return V1_9_0;
                case "1.9.4":
                    return V1_9_4;
                case "1.10":
                case "1.10.1":
                case "1.10.2":
                    return V1_10_2;
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to detect Minecraft version", e);
        }
    }
}
