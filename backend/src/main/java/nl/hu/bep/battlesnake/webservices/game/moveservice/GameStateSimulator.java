package nl.hu.bep.battlesnake.webservices.game.moveservice;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;
import nl.hu.bep.battlesnake.models.components.Coord;
import nl.hu.bep.battlesnake.models.game.Battlesnake;
import nl.hu.bep.battlesnake.models.game.Board;

import java.util.ArrayList;
import java.util.List;

public class GameStateSimulator {

    public GameRequest simulateMove(GameRequest state, String move, Battlesnake snake) {
        // Calculate the new head position based on the move
        Coord newHead = CoordUtils.getNextCoord(snake.getHead(), move);

        // Create a new body with the new head at the front
        List<Coord> newBody = new ArrayList<>();
        newBody.add(newHead);
        newBody.addAll(snake.getBody());

        // Check if the snake eats food at the new head position
        boolean ateFood = false;
        List<Coord> newFood = new ArrayList<>(state.getBoard().getFood());
        for (Coord food : newFood) {
            if (food.getX() == newHead.getX() && food.getY() == newHead.getY()) {
                ateFood = true;
                newFood.remove(food);
                break;
            }
        }

        // If the snake did not eat, remove the tail to simulate movement
        if (!ateFood && !newBody.isEmpty()) {
            newBody.remove(newBody.size() - 1);
        }

        int newHealth = ateFood ? 100 : snake.getHealth() - 1;

        Battlesnake movedSnake = new Battlesnake(
                snake.getId(),
                snake.getName(),
                newHealth,
                newBody,
                snake.getLatency(),
                newHead,
                newBody.size(),
                snake.getShout(),
                snake.getSquad(),
                snake.getCustomizations()
        );

        // Replace the moved snake in the list of snakes on the board
        List<Battlesnake> newSnakes = new ArrayList<>();
        for (Battlesnake s : state.getBoard().getSnakes()) {
            if (s.getId().equals(snake.getId())) {
                newSnakes.add(movedSnake);
            } else {
                newSnakes.add(s);
            }
        }

        var newBoard = new Board(
                state.getBoard().getHeight(),
                state.getBoard().getWidth(),
                newFood,
                state.getBoard().getHazards(),
                newSnakes
        );

        return new GameRequest(
                state.getGame(),
                state.getTurn() + 1,
                newBoard,
                state.getYou().getId().equals(snake.getId()) ? movedSnake : state.getYou()
        );
    }
}