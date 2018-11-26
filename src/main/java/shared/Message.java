package shared;

public class Message {
    private String name;
    private Object[] data;

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
}
