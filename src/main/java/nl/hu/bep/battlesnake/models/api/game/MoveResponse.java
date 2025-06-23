package nl.hu.bep.battlesnake.models.api.game;

public class MoveResponse {
    private String move;
    private String shout;

    public MoveResponse() {}

    public MoveResponse(String move) {
        this.move = move;
    }

    public MoveResponse(String move, String shout) {
        this(move);
        this.shout = shout;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getShout() {
        return shout;
    }

    public void setShout(String shout) {
        this.shout = shout;
    }
}
