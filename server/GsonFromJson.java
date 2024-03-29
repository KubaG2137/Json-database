package server;

import com.google.gson.Gson;

class Parameters {
    private String type;
    private String key;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

public class GsonFromJson {

    public static Parameters gsonFromJson (String msg) {
        Gson gson = new Gson();
        return gson.fromJson(msg, Parameters.class);
    }
}
