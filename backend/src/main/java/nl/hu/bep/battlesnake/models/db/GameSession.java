package nl.hu.bep.battlesnake.models.db;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;

public class GameSession {
    private String gameId;
    private GameRequest start;
    private GameRequest end;

    public GameSession() {}

    public GameSession(String gameId, GameRequest start) {
        this.gameId = gameId;
        this.start = start;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public GameRequest getStart() {
        return start;
    }

    public void setStart(GameRequest start) {
        this.start = start;
    }

    public GameRequest getEnd() {
        return end;
    }

    public void setEnd(GameRequest end) {
        this.end = end;
    }
}