package nl.hu.bep.battlesnake.models.game;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nl.hu.bep.battlesnake.models.components.Coord;

import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {
    private int height;
    private int width;
    private List<Coord> food;
    private List<Coord> hazards;
    private List<Battlesnake> snakes;

    public Board() {}

    public Board(int height, int width, List<Coord> food, List<Coord> hazards, List<Battlesnake> snakes) {
        this.height = height;
        this.width = width;
        this.food = food;
        this.hazards = hazards;
        this.snakes = snakes;
    }

    //    Height
    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    //    Width
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    //    Snakes
    public List<Battlesnake> getSnakes() {
        return snakes;
    }

    public void setSnakes(List<Battlesnake> snakes) {
        this.snakes = snakes;
    }

    //    Hazards
    public List<Coord> getHazards() {
        return hazards;
    }

    public void setHazards(List<Coord> hazards) {
        this.hazards = hazards;
    }

    //    Food
    public List<Coord> getFood() {
        return food;
    }

    public void setFood(List<Coord> food) {
        this.food = food;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board (").append(width).append("x").append(height).append(")\n");

        sb.append("Food:\n");
        for (Coord c : food) {
            sb.append("  ").append(c).append("\n");
        }

        sb.append("Hazards:\n");
        for (Coord c : hazards) {
            sb.append("  ").append(c).append("\n");
        }

        sb.append("Snakes:\n");
        for (Battlesnake s : snakes) {
            sb.append("  ").append(s).append("\n");
        }

        return sb.toString();
    }
}