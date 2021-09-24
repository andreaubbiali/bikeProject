package bikeProject.controller;

public class KeyValuePair {
    private final Object key;
    private final String value;

    public KeyValuePair(Object key, String value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public String toString() {
        return value;
    }
}