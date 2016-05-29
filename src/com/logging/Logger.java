package com.logging;

/**
 *
 * @author Daniel
 */
public class Logger {

    public static void log(final Class<?> clazz, final java.util.logging.Level level, final String message, final Throwable thrown) {
        java.util.logging.Logger.getLogger(clazz.getName()).log(level, message, thrown);
    }

}
