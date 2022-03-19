package OthelloApp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GameSession {

    private Board board;
    private ArrayList<Turn> turns;
    private Player[] players;
    private String sessionStartTime;
    private Player activePlayer;

//    private int sessionEndTime;
//    private boolean isWon;

    public GameSession(boolean userGoesFirst) {
        this.board = new Board();
        this.players = new Player[2];
        if (userGoesFirst){
            this.players[0] = new User(StoneColor.BLACK);
            this.players[1] = new Computer(StoneColor.WHITE);
        } else{
            this.players[0] = new Computer(StoneColor.BLACK);
            this.players[1] = new User(StoneColor.WHITE);
        }
        this.activePlayer = this.players[0];
        this.sessionStartTime = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss:SS").format(new Date());
        this.turns = new ArrayList<Turn>();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean isOver() {
        return board.isFull();
    }

    public boolean userWon(StoneColor stoneColor) {
        return board.userWon(stoneColor);
    }


    public void updateBoard(int row, int column, StoneColor playerColor) {
        board.update(row, column, playerColor);
    }

    public Board getBoard() {
        return board;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }
}
