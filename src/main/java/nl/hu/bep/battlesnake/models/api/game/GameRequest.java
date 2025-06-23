package nl.hu.bep.battlesnake.models.api.game;

import nl.hu.bep.battlesnake.models.game.Battlesnake;
import nl.hu.bep.battlesnake.models.game.Board;
import nl.hu.bep.battlesnake.models.game.Game;

public class GameRequest {
    private Game game;
    private int turn;
    private Board board;
    private Battlesnake you;

    public GameRequest() {}

    public GameRequest(Game game, int turn, Board board, Battlesnake you) {
        this.game = game;
        this.turn = turn;
        this.board = board;
        this.you = you;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Battlesnake getYou() {
        return you;
    }

    public void setYou(Battlesnake you) {
        this.you = you;
    }
}