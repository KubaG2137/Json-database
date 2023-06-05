package server;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Database {
    private static Map<String, String> database = new HashMap<>();

    public static void get(String key, Response response) {

        if (database.get(key) == null) {
            response.setResponse("ERROR");
            response.setReason("No such key");
        } else {
            response.setResponse("OK");
            response.setValue(database.get(key));
        }
    }

    public static void set(String key, String text, Response response) {

        database.put(key, text);
        response.setResponse("OK");
    }

    public static void delete(String key, Response response) {

        if (database.get(key) == null) {
            response.setResponse("ERROR");
            response.setReason("No such key");
        } else {
            database.remove(key);
            response.setResponse("OK");
        }
    }

    public static void storeDatabase() {
        String path = System.getProperty("user.dir") + "/server/data/db.json";

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(database);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
