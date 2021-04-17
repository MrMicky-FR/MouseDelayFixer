package fr.mrmicky.mousedelayfixer;

public final class MouseDelayFixer {

    private static final String LOG_PREFIX = "[MouseDelayFixer] ";

    public static void log(String message) {
        System.out.println(LOG_PREFIX + message);
    }

    public static void logError(String message) {
        System.err.println(LOG_PREFIX + message);
    }

    public static void logError(String message, Throwable t) {
        logError(message);
        t.printStackTrace();
    }
}
