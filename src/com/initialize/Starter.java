package com.initialize;

import com.logging.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;

/**
 *
 * @author Daniel
 */
public class Starter {

    private static Starter instance;

    public static Starter getInstance() {
        return instance != null ? instance : (instance = new Starter());
    }

    private Starter() {
    }

    public void start(final File file) {
        try {
            new URLClassLoader(new URL[]{file.toURI().toURL()}).loadClass("Client").getDeclaredMethod("main", String[].class).invoke(null, (Object) new String[0]);
        } catch (Exception ex) {
            Logger.log(Starter.class, Level.SEVERE, String.format("Error Invoking %s", file.getName()), ex);
            System.exit(0);
        }
    }

}
