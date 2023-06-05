package server;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GsonToJson {
    public static Map<String, String> response = new HashMap<>();

    public static String gsonToJson(String resp, String value, String reason){
        response.put("response", resp);
        response.put("value", value);
        response.put("reason", reason);
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}
