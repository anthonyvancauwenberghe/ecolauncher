package com.config;

import java.io.File;

/**
 *
 * @author Daniel
 */
public class Configuration {

    public static final File CACHE_DIRECTORY = new File(Constants.HOME, Constants.NAME);

    public static final File CLIENT_DIRECTORY = new File(CACHE_DIRECTORY, "clients");

}
