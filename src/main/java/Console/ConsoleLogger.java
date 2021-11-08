package Console;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogger {
    private static boolean debug;
    private static Logger logger;

    public static void setLogger(Logger logger) { ConsoleLogger.logger = logger; }
    public static void setDebugEnabled(boolean enabled) { debug = enabled; }

    public static void log(Level level, String msg, Throwable thrown){
        if(logger != null) logger.log(level, msg, thrown);
    }

    public static void log(Level level, String msg) { log(level, msg,null); }
    public static void logDebug(Level level,String msg, Throwable thrown){
        if(debug) log(level, "[Debug] "+msg, thrown);
    }
    public static void logDebug(Level level, String msg) {
        logDebug(level, msg, null);
    }
}
