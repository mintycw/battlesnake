package nl.hu.bep.battlesnake.models.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class BattlesnakeCustomizations {
    public String color;
    public String head;
    public String tail;

    public BattlesnakeCustomizations() {}

    public BattlesnakeCustomizations(String color, String head, String tail) {
     this.color = color;
     this.head = head;
     this.tail = tail;
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
}
