package OthelloApp.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static OthelloApp.db_util.DB_UTIL.*;
import static java.lang.System.currentTimeMillis;

public class Turn {
    static int turnCount = 0;

    private final int turnId;
    private final String name;
    private final String startDateTime;
    private final long startTimeMilisec;
    private long endTimeMilisec;
    private int[] placedCoordinate;
    private ArrayList<int[]> flippedStoneCoordinates;

    public Turn(String name) {
        if (turnCount == 0) {
            createTurnsTable();
            createFlippedPiecesTable();
        }
        this.turnId = turnCount;
        this.name = name;
        this.startDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(new Date());
        this.startTimeMilisec = currentTimeMillis();

        this.flippedStoneCoordinates = new ArrayList<>();
        turnCount += 1;
    }

    public void setPlacedCoordinate(int[] placedCoordinate) {
        this.placedCoordinate = placedCoordinate;
    }

    public void setFlippedStoneCoordinates(ArrayList<int[]> flippedStoneCoordinates) {
        this.flippedStoneCoordinates.addAll(flippedStoneCoordinates);
    }

    public void setEndTime() {
        this.endTimeMilisec = System.currentTimeMillis();
    }

    public double getTimeElapsed() {
        return (double) (endTimeMilisec - startTimeMilisec) / 1000;
    }

    public void save(int gameSessionID) {
        saveTurn(gameSessionID);
        saveFlippedPieces(gameSessionID);
    }

    public void saveTurn(int gameSessionID) {
        // use turnID, placedCoordinate, userType, and startTimeMilisec to update the SQL database
        try {
            Statement statement = getStatement();
            statement.executeUpdate("INSERT INTO turns (gamesession_id, turn_id, player_name, start_date_time, time_elapsed, placed_stone_row, placed_stone_column) " +
                    "VALUES ("
                    + gameSessionID + ", "
                    + getTurnId() + ", '"
                    + getName() + "', '"
                    + getStartDateTime() + "', "
                    + getTimeElapsed() + ", "
                    + getPlacedCoordinate()[0] + ", "
                    + getPlacedCoordinate()[1] + ")");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace(); // error handling
        }
    }


    private void createTurnsTable() {
        try {
            Statement statement = getStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS turns (" +
                    "gamesession_id  NUMERIC(8) CONSTRAINT fk_gamesession_id REFERENCES gamesessions(gamesession_id)," +
                    "turn_id NUMERIC(2) CONSTRAINT nn_turn_id NOT NULL, " +
                    "player_name VARCHAR(50) CONSTRAINT nn_player_name NOT NULL, " +
                    "start_date_time VARCHAR(50) CONSTRAINT nn_turn_start_date_time NOT NULL, " +
                    "time_elapsed NUMERIC(10,2) DEFAULT 0, " +
                    "placed_stone_row NUMERIC(1) CONSTRAINT ch_placed_stone_row CHECK(placed_stone_row BETWEEN 0 AND 7), " +
                    "placed_stone_column NUMERIC(1) CONSTRAINT ch_placed_stone_column CHECK(placed_stone_column BETWEEN 0 AND 7), " +
                    "CONSTRAINT pk_gs_id_turn_id PRIMARY KEY (gamesession_id, turn_id));");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createFlippedPiecesTable() {
        try {
            Statement statement = getStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS flipped_pieces (" +
                    "gamesession_id  NUMERIC(8), " +
                    "turn_id NUMERIC(2), " +
                    "flipped_stone_row NUMERIC(1) CONSTRAINT ch_flipped_stone_row CHECK(flipped_stone_row BETWEEN 0 AND 7), " +
                    "flipped_stone_column NUMERIC(1) CONSTRAINT ch_flipped_stone_column CHECK(flipped_stone_column BETWEEN 0 AND 7), " +
                    "CONSTRAINT fk_gs_id_turn_id FOREIGN KEY (gamesession_id, turn_id) REFERENCES turns(gamesession_id, turn_id), " +
                    "CONSTRAINT pk_gs_id_turn_id_row_column PRIMARY KEY (gamesession_id, turn_id, flipped_stone_row, flipped_stone_column));");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveFlippedPieces(int gameSessionID) {
        // use turnID and flippedStoneCoordinates to update the SQL database
        if (flippedStoneCoordinates.size() > 0) {
            try {
                Statement statement = getStatementAutoCommitFalse();
                statement.executeUpdate("BEGIN TRANSACTION;");
                for (int[] flippedStoneCoordinate : getFlippedStoneCoordinates()) {
                    statement.executeUpdate("INSERT INTO flipped_pieces (gamesession_id, turn_id, flipped_stone_row, flipped_stone_column) " +
                            "VALUES ("
                            + gameSessionID + ", "
                            + getTurnId() + ", "
                            + flippedStoneCoordinate[0] + ", "
                            + flippedStoneCoordinate[1] + ");");
                }
                statement.executeUpdate("COMMIT;");
                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace(); // error handling
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Turn{" +
                "turnId=" + turnId +
                ", name='" + name + '\'' +
                ", startTimeMilisec='" + startTimeMilisec + '\'' +
                ", placedCoordinate=" + Arrays.toString(placedCoordinate) +
                ", timeElapsed='" + getTimeElapsed() + '\'' +
                ", flippedStoneCoordinates=");
        for (int[] flippedStoneCoordinate : flippedStoneCoordinates) {
            builder.append(String.format("(%d, %d), ", flippedStoneCoordinate[0], flippedStoneCoordinate[1]));
        }
        builder.append("}");
        return builder.toString();
    }

    public int getTurnId() {
        return turnId;
    }


    public String getName() {
        return name;
    }


    public int[] getPlacedCoordinate() {
        return placedCoordinate;
    }

    public ArrayList<int[]> getFlippedStoneCoordinates() {
        return flippedStoneCoordinates;
    }

    public String getStartDateTime() {
        return startDateTime;
    }
}

