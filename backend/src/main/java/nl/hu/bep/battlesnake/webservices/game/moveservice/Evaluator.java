package nl.hu.bep.battlesnake.webservices.game.moveservice;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;
import nl.hu.bep.battlesnake.models.components.Coord;
import nl.hu.bep.battlesnake.models.game.Battlesnake;
import nl.hu.bep.battlesnake.webservices.game.config.GameConfig;

import java.util.*;

public class Evaluator {

    public double evaluate(GameRequest state) {
        Battlesnake you = state.getYou();
        Coord head = you.getHead();
        int health = you.getHealth();
        int length = you.getLength();

        double areaControl = floodFillScore(head, state);
        double foodBonus = 0;

        List<Coord> foodList = state.getBoard().getFood();
        if (!foodList.isEmpty()) {
            Coord closestFood = getClosestFood(head, foodList);
            int dist = CoordUtils.manhattanDistance(head, closestFood);

            // Force prioritize food strongly if health low
            if (health < 30) {
                foodBonus = GameConfig.FOOD_PRIORITY_LOW_HP * Math.exp(-0.5 * dist);
            } else {
                foodBonus = GameConfig.FOOD_PRIORITY_HIGH_HP * Math.exp(-0.3 * dist);
            }
        }

        // Cap length reward to avoid reckless long growth
        double lengthReward = GameConfig.FOOD_REWARD_BASE_VALUE * Math.min(length, 20);

        // Penalize proximity to hazards and snake bodies near head
        double hazardPenalty = countHazardsNearHead(head, state) * -30;
        double bodyPenalty = countBodiesNearHead(head, state) * -20;

        // Combine all factors with weights (tweak as needed)
        double score = 0.05 * health
                + 0.3 * areaControl
                + foodBonus
                + lengthReward
                + hazardPenalty
                + bodyPenalty;

        // Debug print for evaluation
        // System.out.printf("Eval: health=%.2f area=%.2f food=%.2f len=%.2f hazard=%.2f body=%.2f total=%.2f\n",
        //    0.05 * health, 0.3 * areaControl, foodBonus, lengthReward, hazardPenalty, bodyPenalty, score);

        return score;
    }

    public Coord getClosestFood(Coord head, List<Coord> foodList) {
        Coord closest = foodList.get(0);
        int minDist = CoordUtils.manhattanDistance(head, closest);

        for (Coord food : foodList) {
            int dist = CoordUtils.manhattanDistance(head, food);
            if (dist < minDist) {
                minDist = dist;
                closest = food;
            }
        }
        return closest;
    }

    private double floodFillScore(Coord start, GameRequest state) {
        Set<String> visited = new HashSet<>();
        Queue<Coord> queue = new LinkedList<>();
        queue.add(start);

        int score = 0;
        int maxX = state.getBoard().getWidth();
        int maxY = state.getBoard().getHeight();

        Set<String> blocked = getBlockedCoordinates(state);

        while (!queue.isEmpty() && score < GameConfig.FOOD_PRIORITY_HIGH_HP) {
            Coord current = queue.poll();
            String key = current.getCoordString();
            if (visited.contains(key) || blocked.contains(key)) continue;

            visited.add(key);
            score++;

            for (String dir : List.of("up", "down", "left", "right")) {
                Coord next = CoordUtils.getNextCoord(current, dir);
                if (next.getX() >= 0 && next.getY() >= 0 && next.getX() < maxX && next.getY() < maxY) {
                    queue.add(next);
                }
            }
        }
        return score;
    }

    private Set<String> getBlockedCoordinates(GameRequest state) {
        Set<String> blocked = new HashSet<>();

        // Add snake bodies except tail (tail moves away next turn)
        for (Battlesnake snake : state.getBoard().getSnakes()) {
            List<Coord> body = snake.getBody();
            for (int i = 0; i < body.size() - 1; i++) {
                Coord part = body.get(i);
                blocked.add(part.getCoordString());
            }
        }

        // Add hazards
        for (Coord hazard : state.getBoard().getHazards()) {
            blocked.add(hazard.getCoordString());
        }

        return blocked;
    }

    private int countHazardsNearHead(Coord head, GameRequest state) {
        int count = 0;
        Set<String> hazardCoords = new HashSet<>();
        for (Coord h : state.getBoard().getHazards()) {
            hazardCoords.add(h.getCoordString());
        }
        for (String dir : List.of("up", "down", "left", "right")) {
            Coord next = CoordUtils.getNextCoord(head, dir);
            if (hazardCoords.contains(next.getCoordString())) {
                count++;
            }
        }
        return count;
    }

    private int countBodiesNearHead(Coord head, GameRequest state) {
        int count = 0;
        Set<String> bodyCoords = new HashSet<>();
        for (Battlesnake snake : state.getBoard().getSnakes()) {
            for (Coord part : snake.getBody()) {
                bodyCoords.add(part.getCoordString());
            }
        }
        for (String dir : List.of("up", "down", "left", "right")) {
            Coord next = CoordUtils.getNextCoord(head, dir);
            if (bodyCoords.contains(next.getCoordString())) {
                count++;
            }
        }
        return count;
    }
}