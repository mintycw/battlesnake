package nl.hu.bep.battlesnake.models.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Coord {
    private int x;
    private int y;

    public Coord() {}

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //    X
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    //    Y
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCoordString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public String toString() {
        return getCoordString();
    }
}
