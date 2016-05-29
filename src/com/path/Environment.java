package com.path;

import com.config.Configuration;

/**
 *
 * @author Daniel
 */
public class Environment {

    private static Environment instance;

    public static Environment getInstance() {
        return instance != null ? instance : (instance = new Environment());
    }

    private Environment() {
    }

    public void create() {
        if (!Configuration.CACHE_DIRECTORY.isDirectory()) {
            Configuration.CACHE_DIRECTORY.mkdir();
        }
        if (!Configuration.CLIENT_DIRECTORY.isDirectory()) {
            Configuration.CLIENT_DIRECTORY.mkdir();
        }
    }

}
