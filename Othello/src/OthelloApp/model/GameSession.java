package OthelloApp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static OthelloApp.db_util.DB_UTIL.closeDbConnection;
import static OthelloApp.db_util.DB_UTIL.getStatement;

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
        if (userGoesFirst) {
            this.players[0] = new User(StoneColor.BLACK);
            this.players[1] = new Computer(StoneColor.WHITE);
        } else {
            this.players[0] = new Computer(StoneColor.BLACK);
            this.players[1] = new User(StoneColor.WHITE);
        }
        this.activePlayer = this.players[0];
        this.sessionStartTime = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss:SS").format(new Date());
        this.turns = new ArrayList<Turn>();
        createGameSessionsTable();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public boolean activePlayerIsComputer() {
        return (activePlayer instanceof Computer);
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

    private void createGameSessionsTable() {
        try {

            Statement statement = getStatement();
            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS seq_gamesession_id INCREMENT BY 1 MINVALUE 0 START WITH 0;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sessions (" +
                    "session_id NUMERIC(20) CONSTRAINT pk_gamesession_id PRIMARY KEY, " +
                    "start_time VARCHAR(25), " +
                    "end_time VARCHAR(25), " +
                    "is_over BOOLEAN DEFAULT FALSE, " +
                    "user_won BOOLEAN DEFAULT FALSE, " +
                    "username VARCHAR(25), " +
                    "computer_username VARCHAR(25));");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Integer[] getPlayerScores() {
        Integer[] playersPoints = new Integer[2];
        for (int i = 0; i < getPlayers().length; i++) {
            Player player = getPlayers()[i];
            StoneColor playerColor = player.getPlayerColor();
            int playerPoints = getBoard().countStones(playerColor);
            System.out.println(playerPoints);
            playersPoints[i] = playerPoints;
        }
        return playersPoints;
    }

    public void switchActivePlayer() {
        if (getActivePlayer().equals(getPlayers()[0])) {
            setActivePlayer(getPlayers()[1]);
        } else {
            setActivePlayer(getPlayers()[0]);
        }

    }
}
