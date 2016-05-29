package com.updater;

import com.config.Configuration;
import com.config.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.logging.Logger;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Daniel
 */
public class Updater {

    private static Updater instance;

    public static Updater getInstance() {
        return instance != null ? instance : (instance = new Updater());
    }

    private Updater() {
    }

    public void download(final Version version) {
        final String value = version.getFile();
        try {
            final InputStream input = new URL(String.format("%s%s", Constants.REMOTE_ROOT_PATH, value)).openStream();
            final OutputStream output = new FileOutputStream(new File(Configuration.CLIENT_DIRECTORY, value));
            final byte[] buffer = new byte[4096];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception ex) {
            Logger.log(Updater.class, Level.SEVERE, "Error getting Input Stream", ex);
        }
    }

    public List<Version> loadClientList() {
        final List<Version> list = new ArrayList<Version>();
        try {
            URL url = new URL(Constants.REMOTE_CLIENTS_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
            final JsonParser parser = new JsonParser();
            final JsonArray array = (JsonArray) parser.parse(reader);
            for (JsonElement element : array) {
                final JsonObject object = element.getAsJsonObject();
                list.add(new Version(object.has("name") ? object.get("name").getAsString() : "Unknown", object.has("currentVersion") ? object.get("currentVersion").getAsDouble() : 0.1, object.has("jarName") ? object.get("jarName").getAsString() : "DeviousPs"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
