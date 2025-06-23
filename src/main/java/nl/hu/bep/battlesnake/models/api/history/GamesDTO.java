package nl.hu.bep.battlesnake.models.api.history;

public class GamesDTO {
    private String gameId;

    public GamesDTO() {}

    public GamesDTO(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}