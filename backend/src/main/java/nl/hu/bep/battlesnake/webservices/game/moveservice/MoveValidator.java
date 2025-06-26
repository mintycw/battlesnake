package nl.hu.bep.battlesnake.webservices.game.moveservice;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;
import nl.hu.bep.battlesnake.models.components.Coord;
import nl.hu.bep.battlesnake.models.game.Battlesnake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveValidator {

    public List<String> getSafeMoves(Battlesnake snake, GameRequest state) {
        List<String> moves = new ArrayList<>(List.of("up", "down", "left", "right"));
        Coord head = snake.getHead();
        int maxX = state.getBoard().getWidth() - 1;
        int maxY = state.getBoard().getHeight() - 1;

        // Remove moves going off board
        moves.removeIf(move -> {
            Coord next = CoordUtils.getNextCoord(head, move);
            return next.getX() < 0 || next.getX() > maxX || next.getY() < 0 || next.getY() > maxY;
        });

        // Prevent 180 turn into neck
        if (snake.getBody().size() >= 2) {
            Coord neck = snake.getBody().get(1);
            if (head.getX() > neck.getX()) moves.remove("left");
            else if (head.getX() < neck.getX()) moves.remove("right");
            else if (head.getY() > neck.getY()) moves.remove("down");
            else if (head.getY() < neck.getY()) moves.remove("up");
        }

        // Collect all snake body coords except tail (tail moves forward)
        Set<String> occupied = new HashSet<>();
        for (Battlesnake s : state.getBoard().getSnakes()) {
            List<Coord> body = s.getBody();
            // Exclude tail for safety (tail moves away next turn)
            int limit = body.size() - 1;
            for (int i = 0; i < limit; i++) {
                Coord part = body.get(i);
                occupied.add(part.getCoordString());
            }
        }

        moves.removeIf(move -> {
            Coord next = CoordUtils.getNextCoord(head, move);
            return occupied.contains(next.getCoordString());
        });

        // Head-to-head collision avoidance
        int yourLength = snake.getLength();
        for (Battlesnake opponent : state.getBoard().getSnakes()) {
            if (opponent.getId().equals(snake.getId())) continue;

            Coord oppHead = opponent.getHead();
            int oppLength = opponent.getLength();

            for (String dir : List.copyOf(moves)) {
                Coord yourNext = CoordUtils.getNextCoord(head, dir);
                List<Coord> oppNexts = List.of(
                        CoordUtils.getNextCoord(oppHead, "up"),
                        CoordUtils.getNextCoord(oppHead, "down"),
                        CoordUtils.getNextCoord(oppHead, "left"),
                        CoordUtils.getNextCoord(oppHead, "right")
                );

                for (Coord c : oppNexts) {
                    if (c.getX() == yourNext.getX() && c.getY() == yourNext.getY() && oppLength >= yourLength) {
                        moves.remove(dir);
                        break;
                    }
                }
            }
        }

        return moves;
    }

    public List<Battlesnake> getOpponentSnakes(GameRequest state) {
        List<Battlesnake> opponents = new ArrayList<>();
        for (Battlesnake snake : state.getBoard().getSnakes()) {
            if (!snake.getId().equals(state.getYou().getId())) {
                opponents.add(snake);
            }
        }
        return opponents;
    }

    public boolean isGameOver(GameRequest state) {
        return state.getYou().getHealth() <= 0;
    }
}