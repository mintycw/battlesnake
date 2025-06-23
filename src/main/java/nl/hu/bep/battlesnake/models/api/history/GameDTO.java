package nl.hu.bep.battlesnake.models.api.history;

public class GameDTO {
    private String gameId;
    private int totalTurns;
    private int startingSnakes;
    private int endingSnakes;
    private String yourSnakeName;
    private int startLength;
    private int endLength;
    private int startHealth;
    private int endHealth;
    private boolean survived;

    public GameDTO() {}

    public GameDTO(String gameId, int totalTurns, int startingSnakes, int endingSnakes,
                   String yourSnakeName, int startLength, int endLength,
                   int startHealth, int endHealth, boolean survived) {
        this.gameId = gameId;
        this.totalTurns = totalTurns;
        this.startingSnakes = startingSnakes;
        this.endingSnakes = endingSnakes;
        this.yourSnakeName = yourSnakeName;
        this.startLength = startLength;
        this.endLength = endLength;
        this.startHealth = startHealth;
        this.endHealth = endHealth;
        this.survived = survived;
    }

    public String getGameId() {
        return gameId;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public int getStartingSnakes() {
        return startingSnakes;
    }

    public int getEndingSnakes() {
        return endingSnakes;
    }

    public String getYourSnakeName() {
        return yourSnakeName;
    }

    public int getStartLength() {
        return startLength;
    }

    public int getEndLength() {
        return endLength;
    }

    public int getStartHealth() {
        return startHealth;
    }

    public int getEndHealth() {
        return endHealth;
    }

    public boolean isSurvived() {
        return survived;
    }
}
