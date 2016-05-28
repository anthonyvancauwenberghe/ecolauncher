package devious.loader.updater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class ClientUpdater {

    public static final String REMOTE_ROOT_PATH = "http://cache.deviousps.com/";
    public static final String REMOTE_CLIENTS_PATH = REMOTE_ROOT_PATH + "clients.json";

    public static final File CACHE_DIR = new File(System.getProperty("user.home"), "DeviousPs");
    public static final File CLIENTS_DIR = new File(CACHE_DIR, "clients");

    static {
        if(!CACHE_DIR.exists())
            CACHE_DIR.mkdir();
        if(!CLIENTS_DIR.exists())
            CLIENTS_DIR.mkdir();
    }

    public static void download(final ClientVersion cv) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try{
            in = new URL(REMOTE_ROOT_PATH + cv.fileName).openStream();
            out = new FileOutputStream(new File(CLIENTS_DIR, cv.fileName));
            final byte[] buffer = new byte[4096];
            int len;
            while((len = in.read(buffer)) > 0)
                out.write(buffer, 0, len);
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
            if(in != null)
                in.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static List<ClientVersion> loadRemoteVersions() throws IOException {
        final List<ClientVersion> versions = new ArrayList<ClientVersion>();

        InputStream is = new URL(REMOTE_CLIENTS_PATH).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray jsonArray = new JSONArray(jsonText);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.optString("name", "Unknown");
                double currentVersion = jsonObject.optDouble("currentVersion", 0.1);
                String jarName = jsonObject.optString("jarName", "DeviousPs");
                versions.add(new ClientVersion(name, currentVersion, jarName));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return versions;
    }

    public static List<ClientVersion> tryLoadRemoteVersions() {
        try{
            return loadRemoteVersions();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
