package client;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class GsonToJson {
    public static Map<String, String> parameters = new HashMap<>();
    public static String gsonToJson(String[] args) {
        Gson gson = new Gson();
        if (args[0].equals("-in")) {
            String path = System.getProperty("user.dir") + "/src/client/data/" + args[1];
            try {
                Reader reader = Files.newBufferedReader(Paths.get(path));
                parameters = gson.fromJson(reader, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (args.length == 2){
                parameters.put("type", args[1]);
            } else if (args.length == 4) {
                parameters.put("type", args[1]);
                parameters.put("key", args[3]);
            } else if (args.length == 6) {
                parameters.put("type", args[1]);
                parameters.put("key", args[3]);
                parameters.put("value", args[5]);
            }
        }
        return gson.toJson(parameters);
    }
}
