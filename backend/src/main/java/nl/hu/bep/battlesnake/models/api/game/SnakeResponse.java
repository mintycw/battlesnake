package nl.hu.bep.battlesnake.models.api.game;

public class SnakeResponse {
    private String apiVersion;
    private String author;
    private String color;
    private String head;
    private String tail;
    private String version;

    public SnakeResponse() {
        this.apiVersion = "1";
        this.author = "mintycw";
        this.color = "#CEE5D5";
        this.head = "default";
        this.tail = "default";
        this.version = "1";
    }

    public SnakeResponse(String apiVersion, String author, String color, String head, String tail, String version) {
        this.apiVersion = apiVersion;
        this.author = author;
        this.color = color;
        this.head = head;
        this.tail = tail;
        this.version = version;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
