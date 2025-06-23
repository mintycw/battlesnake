package nl.hu.bep.battlesnake.webservices.game.moveservice;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;

public interface Strategy {
    String findBestMove(GameRequest state);
}