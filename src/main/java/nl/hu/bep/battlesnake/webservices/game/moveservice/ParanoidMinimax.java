package nl.hu.bep.battlesnake.webservices.game.moveservice;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;
import nl.hu.bep.battlesnake.models.components.Coord;
import nl.hu.bep.battlesnake.models.game.Battlesnake;
import nl.hu.bep.battlesnake.webservices.game.config.GameConfig;

import java.util.ArrayList;
import java.util.List;

public class ParanoidMinimax implements Strategy {
    private final GameStateSimulator simulator;
    private final Evaluator evaluator;
    private final MoveValidator validator;

    public ParanoidMinimax(GameStateSimulator simulator, Evaluator evaluator, MoveValidator validator) {
        this.simulator = simulator;
        this.evaluator = evaluator;
        this.validator = validator;
    }

    @Override
    public String findBestMove(GameRequest rootRequest) {
        List<Battlesnake> players = new ArrayList<>();
        players.add(rootRequest.getYou());
        players.addAll(validator.getOpponentSnakes(rootRequest));

        int maxDepth = determineMaxDepth(players.size());

        List<String> possibleMoves = validator.getSafeMoves(rootRequest.getYou(), rootRequest);
        if (possibleMoves.isEmpty()) return "up"; // fallback move

        Coord head = rootRequest.getYou().getHead();
        List<Coord> foodList = rootRequest.getBoard().getFood();

        int nextPlayerIndex = players.size() > 1 ? 1 : 0;
        double bestScore = Double.NEGATIVE_INFINITY;
        String bestMove = possibleMoves.get(0);

        for (String move : possibleMoves) {
            GameRequest nextState = simulator.simulateMove(rootRequest, move, rootRequest.getYou());
            double score = minimax(nextState, maxDepth, nextPlayerIndex, players, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

            Coord nextHead = CoordUtils.getNextCoord(head, move);
            if (!foodList.isEmpty()) {
                Coord closestFood = evaluator.getClosestFood(nextHead, foodList);
                score += 5.0 / (CoordUtils.manhattanDistance(nextHead, closestFood) + 1);
            }

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private double minimax(GameRequest state, int depth, int playerIndex, List<Battlesnake> players, double alpha, double beta) {
        if (depth == 0 || validator.isGameOver(state)) {
            return evaluator.evaluate(state);
        }

        Battlesnake currentPlayer = players.get(playerIndex);
        List<String> moves = validator.getSafeMoves(currentPlayer, state);

        if (moves.isEmpty()) {
            return evaluator.evaluate(state);
        }

        boolean isMaximizing = playerIndex == 0;

        int nextPlayerIndex = (playerIndex + 1) % players.size();
        int nextDepth = (nextPlayerIndex == 0) ? depth - 1 : depth;

        if (isMaximizing) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (String move : moves) {
                GameRequest nextState = simulator.simulateMove(state, move, currentPlayer);
                double eval = minimax(nextState, nextDepth, nextPlayerIndex, players, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Beta cutoff: prune remaining moves
                }
            }
            return maxEval;
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (String move : moves) {
                GameRequest nextState = simulator.simulateMove(state, move, currentPlayer);
                double eval = minimax(nextState, nextDepth, nextPlayerIndex, players, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha cutoff: prune remaining moves
                }
            }
            return minEval;
        }
    }

    private int determineMaxDepth(int numPlayers) {
        if (numPlayers >= 5) {
            return GameConfig.MAX_DEPTH_START;
        } else if (numPlayers == 4) {
            return GameConfig.MAX_DEPTH_EARLY;  // early game with many players: shallow search
        } else if (numPlayers == 3) {
            return GameConfig.MAX_DEPTH_MID;
        } else {
            return GameConfig.MAX_DEPTH_LATE;  // duel mode: deeper search
        }
    }
}