package nl.hu.bep.battlesnake.models.api.history;

public class StatsDTO {
    private long winRate;
    private long averageLength;
    private int gamesPlayed;

    public StatsDTO() {}

    public StatsDTO(long winRate, long averageLength, int gamesPlayed) {
        this.winRate = winRate;
        this.averageLength = averageLength;
        this.gamesPlayed = gamesPlayed;
    }

    public long getWinRate() {
        return winRate;
    }

    public void setWinRate(long winRate) {
        this.winRate = winRate;
    }

    public long getAverageLength() {
        return averageLength;
    }

    public void setAverageLength(long averageLength) {
        this.averageLength = averageLength;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

}

