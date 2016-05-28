package devious.loader.updater;

public class ClientVersion {

    public final String name;
    public final double version;
    public final String jarName;

    public final String fileName;

    public ClientVersion(final String name, final double version, final String jarName) {
        this.name = name;
        this.version = version;
        this.jarName = jarName;

        fileName = String.format("%s-%s.jar", jarName, Double.toString(version).replace(".", "_"));
        System.out.println(fileName);
    }

}