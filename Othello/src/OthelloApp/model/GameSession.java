package OthelloApp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static OthelloApp.db_util.DB_UTIL.closeDbConnection;
import static OthelloApp.db_util.DB_UTIL.getStatement;

public class GameSession {
    private int idNo;
    private final Board board;
    private final Player[] players;
    private final String startDateTime;

    private Player activePlayer;
    private Turn activeTurn;
    private ArrayList<Turn> turns;

    private final long startTimeMilisec;
    private long endTimeMilisec;

    private boolean isWon;
    private boolean isOver;

    public GameSession(boolean userGoesFirst, String userName) {
        this.board = new Board();
        this.players = new Player[2];
        initializePlayers(userGoesFirst, userName);
        this.activePlayer = this.players[0];
        this.startDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(new Date());
        this.startTimeMilisec = System.currentTimeMillis();
        this.endTimeMilisec = 0;
        this.isOver = false;
        this.isWon = false;
        this.turns = new ArrayList<Turn>();
        createGameSessionsTable();
        setIdNo();
        this.activeTurn = new Turn(this.activePlayer.getName());

    }
    // --------------------GETTERS AND SETTERS----------------------------

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

    private double getTimeElapsed() {
        return (double) (endTimeMilisec - startTimeMilisec) / 1000;
    }

    public Board getBoard() {
        return board;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getIdNo() {
        return idNo;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setEndTimeMilisec(long endTimeMilisec) {
        this.endTimeMilisec = endTimeMilisec;
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public void setIdNo() {
        try {
            Statement statement = getStatement();
            ResultSet resultset = statement.executeQuery("SELECT nextval('seq_gamesession_id')");
            while (resultset.next()) {
                int idNo = resultset.getInt(1);
                this.idNo = idNo;
            }
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }




    // -----------------------------------------------------------------

    public boolean userWon() {
        for (Player player : players) {
            if (player instanceof User) {
                return board.userWon(player.getPlayerColor());
            }
        }
        return false;
    }



    public boolean isTied(){
        for (Player player : players) {
            if (player instanceof User) {
                return board.isTied(player.getPlayerColor());
            }
        }
        return false;
    }

    public void initializePlayers(boolean userGoesFirst, String userName) {
        if (userGoesFirst) {
            this.players[0] = new User(StoneColor.BLACK, userName);
            this.players[1] = new Computer(StoneColor.WHITE, "basicAI");
        } else {
            this.players[0] = new Computer(StoneColor.BLACK, "basicAI");
            this.players[1] = new User(StoneColor.WHITE, userName);
        }
    }

    public void updateBoard(int[] coordinates, StoneColor playerColor) {
        activeTurn.setPlacedCoordinate(coordinates);
        activeTurn.setFlippedStoneCoordinates(board.findFlippableStones(coordinates, playerColor));
        board.update(coordinates, playerColor);
    }

    private void createGameSessionsTable() {
        try {
            Statement statement = getStatement();
            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS seq_gamesession_id INCREMENT BY 1 MINVALUE 0 START WITH 0;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS gamesessions (" +
                    "gamesession_id NUMERIC(20) CONSTRAINT pk_gamesession_id PRIMARY KEY, " +
                    "start_date_time VARCHAR(50) CONSTRAINT nn_gs_start_date_time NOT NULL, " +
                    "time_elapsed NUMERIC(10,2) DEFAULT 0, " +
                    "is_over BOOLEAN DEFAULT FALSE, " +
                    "user_won BOOLEAN DEFAULT FALSE, " +
                    "username VARCHAR(25) CONSTRAINT nn_username NOT NULL, " +
                    "computer_name VARCHAR(25) CONSTRAINT nn_computer_name NOT NULL);");
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

    public String getUserName() {
        for (Player player : players) {
            if (player instanceof User) {
                return player.getName();
            }
        }
        return null;
    }

    public String getComputerName() {
        for (Player player : players) {
            if (player instanceof Computer) {
                return player.getName();
            }
        }
        return null;
    }

    public void switchActivePlayer() {
        this.activeTurn.setEndTime();
        if (this.activeTurn.getPlacedCoordinate() != null) {
            this.activeTurn.save(getIdNo());
        }
        if (getActivePlayer().equals(getPlayers()[0])) {
            setActivePlayer(getPlayers()[1]);
        } else {
            setActivePlayer(getPlayers()[0]);
        }
        createNewTurn(getActivePlayer());
    }


    public void createNewTurn(Player player) {
        this.turns.add(this.activeTurn);
        this.activeTurn = new Turn(player.getName());
    }

    public int[] findMostProfitableMove() {
        StoneColor activePlayerColor = getActivePlayer().getPlayerColor();
        ArrayList<int[]> possibleMoves = getBoard().findAllPossibleMoves(activePlayerColor);
        int[] mostProfitableMove = getBoard().findMostProfitableMove(possibleMoves, activePlayerColor);
        return mostProfitableMove;
    }

    public ArrayList<int[]> playTurn(int[] moveCoordinates) {
        StoneColor activePlayerColor = getActivePlayer().getPlayerColor();
        ArrayList<int[]> flippableStoneCoordinates = getBoard().findFlippableStones(moveCoordinates, activePlayerColor);
        updateBoard(moveCoordinates, activePlayerColor);
        System.out.println(getBoard());
        updateGameSessionsTable();
        return flippableStoneCoordinates;
    }

    public void updateGameSessionsTable() {
        setEndTimeMilisec(System.currentTimeMillis());
        try {
            System.out.println("Opening connection to 'gamesessions' table on turn" + activeTurn.getTurnId());
            Statement statement = getStatement();
            statement.executeUpdate("INSERT INTO gamesessions (gamesession_id, start_date_time, time_elapsed, is_over, user_won, username, computer_name) " +
                    "VALUES ("
                    + getIdNo() + ", '"
                    + getStartDateTime() + "', "
                    + getTimeElapsed() + ", "
                    + isOver() + ", "
                    + userWon() + ", '"
                    + getUserName() + "', '"
                    + getComputerName() + "') " +
                    "ON CONFLICT (gamesession_id) DO UPDATE " +
                    "SET  time_elapsed= EXCLUDED.time_elapsed, " +
                    "       is_over = EXCLUDED.is_over, " +
                    "       user_won = EXCLUDED.user_won ");
            closeDbConnection();
            System.out.println("Closed connection to 'gamesessions' table ");
        } catch (SQLException e) {
            e.printStackTrace(); // error handling
        }
    }

    public Turn getMostProfitableUserTurn(){
        int maxFlipped = 0;
        Turn mostProfitableTurn = null;
        for (Turn turn : turns) {
            if (turn.getName().equals(getUserName())){
                if (turn.getFlippedStoneCoordinates().size() > maxFlipped){
                    maxFlipped = turn.getFlippedStoneCoordinates().size();
                    mostProfitableTurn = turn;
                }
            }
        }
        return mostProfitableTurn;
    }

}
