package nl.hu.bep.battlesnake.models.db;

import java.util.Date;

public class SnakeRequest {
    private String apiVersion;
    private String author;
    private String color;
    private String head;
    private String tail;
    private String version;

    private Date lastUpdated;
    private String updatedBy;

    public SnakeRequest() {}

    public SnakeRequest(String apiVersion, String author, String color, String head, String tail, String version, Date updated, String updatedBy) {
        this.apiVersion = apiVersion;
        this.author = author;
        this.color = color;
        this.head = head;
        this.tail = tail;
        this.version = version;
        this.lastUpdated = updated;
        this.updatedBy = updatedBy;
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
