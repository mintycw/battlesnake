package nl.hu.bep.battlesnake.webservices.game.moveservice;
import nl.hu.bep.battlesnake.models.components.Coord;

public class CoordUtils {
    public static Coord getNextCoord(Coord current, String move) {
        return switch (move) {
            case "up" -> new Coord(current.getX(), current.getY() + 1);
            case "down" -> new Coord(current.getX(), current.getY() - 1);
            case "left" -> new Coord(current.getX() - 1, current.getY());
            case "right" -> new Coord(current.getX() + 1, current.getY());
            default -> current;
        };
    }

    public static int manhattanDistance(Coord a, Coord b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
