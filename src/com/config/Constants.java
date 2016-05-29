package com.config;

/**
 *
 * @author Daniel
 */
public class Constants {

    public static final String NAME = "DeviousPs";

    public static final String TITLE = String.format("%s Launcher", Constants.NAME);

    public static final String REMOTE_ROOT_PATH = "http://cache.deviousps.com/";

    public static final String REMOTE_CLIENTS_PATH = String.format("%sclients.json", REMOTE_ROOT_PATH);

    public static final String HOME = System.getProperty("user.home");

    public static final String SEPARATOR = System.getProperty("file.separator");

}
