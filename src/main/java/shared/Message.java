package shared;

import com.google.gson.Gson;

public class Message {
    private String name;
    private Object[] data;
    private static Gson gson = new Gson();

    public Message(String name, Object... data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Object[] getData() {
        return data;
    }

    public static Message fromJSON(String json){

        return gson.fromJson(json,Message.class);
    }

    public String toJson(){

        return gson.toJson(this);
    }
}
