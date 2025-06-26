package nl.hu.bep.battlesnake.models.api.snake;

import org.bson.types.ObjectId;

import java.util.Date;

public class SnakeDTO {
    private String color;
    private String head;
    private String tail;
    private String author;

    private Date lastUpdated;
    private String updatedBy;

    public SnakeDTO() {}

    public SnakeDTO(String color, String head, String tail, String author, Date lastUpdated, String updatedBy) {
        this.color = color;
        this.head = head;
        this.tail = tail;
        this.author = author;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
