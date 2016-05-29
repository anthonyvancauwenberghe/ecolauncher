package com.updater;

/**
 *
 * @author Daniel
 */
public class Version {

    private final String name, jar, file, display;
    private final double version;

    public Version(final String name, final double version, final String jar) {
        this.name = name;
        this.version = version;
        this.jar = jar;
        this.file = String.format("%s-%s.jar", jar, Double.toString(version).replace(".", "_"));
        this.display = String.format("%s %.1f", jar, version);
    }
    
    public String getName() {
        return name;
    }
    
    public String getJar() {
        return jar;
    }
    
    public String getFile() {
        return file;
    }
    
    public String getDisplay() {
        return display;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
