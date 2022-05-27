package OthelloApp.dataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static OthelloApp.DBUtil.DBUtil.*;

public final class DataManager {

    public static void createGameSessionsTable() {
        try {
            Statement statement = getStatement();
            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS seq_gamesession_id INCREMENT BY 1 MINVALUE 0 START WITH 0;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS gamesessions (" +
                    "gamesession_id NUMERIC(20) CONSTRAINT pk_gamesession_id PRIMARY KEY, " +
                    "start_date_time VARCHAR(50) CONSTRAINT nn_gs_start_date_time NOT NULL, " +
                    "time_elapsed NUMERIC(10,3) DEFAULT 0, " +
                    "is_over BOOLEAN DEFAULT FALSE, " +
                    "user_won BOOLEAN DEFAULT FALSE, " +
                    "username VARCHAR(25) CONSTRAINT nn_username NOT NULL, " +
                    "computer_name VARCHAR(25) CONSTRAINT nn_computer_name NOT NULL, " +
                    "number_stones_user NUMERIC(2) DEFAULT 0);");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTurnsTable() {
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

    public static void createFlippedPiecesTable() {
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

    public static void saveTurn(int gameSessionID, int turnId, String playerName, String startDateTime, double timeElapsed, int[] placedCoordinate) {
        // use turnID, placedCoordinate, userType, and startTimeMilisec to update the SQL database
        if (placedCoordinate != null) {
            try {
                Statement statement = getStatement();
                statement.executeUpdate("INSERT INTO turns (gamesession_id, turn_id, player_name, start_date_time, time_elapsed, placed_stone_row, placed_stone_column) " +
                        "VALUES ("
                        + gameSessionID + ", "
                        + turnId + ", '"
                        + playerName + "', '"
                        + startDateTime + "', "
                        + timeElapsed + ", "
                        + placedCoordinate[0] + ", "
                        + placedCoordinate[1] + ")");
                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace(); // error handling
            }
        } else {
            try {
                Statement statement = getStatement();
                statement.executeUpdate("INSERT INTO turns (gamesession_id, turn_id, player_name, start_date_time, time_elapsed, placed_stone_row, placed_stone_column) " +
                        "VALUES ("
                        + gameSessionID + ", "
                        + turnId + ", '"
                        + playerName + "', '"
                        + startDateTime + "', "
                        + timeElapsed + ", "
                        + null + ", "
                        + null + ")");
                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace(); // error handling
            }
        }
    }




    public static void saveFlippedPieces(int gameSessionID, int turnID, ArrayList<int[]> flippedStoneCoordinates) {
        // use turnID and flippedStoneCoordinates to update the SQL database
        if (flippedStoneCoordinates.size() > 0) {
            try {
                Statement statement = getStatementAutoCommitFalse();
                statement.executeUpdate("BEGIN TRANSACTION;");
                for (int[] flippedStoneCoordinate : flippedStoneCoordinates) {
                    statement.executeUpdate("INSERT INTO flipped_pieces (gamesession_id, turn_id, flipped_stone_row, flipped_stone_column) " +
                            "VALUES ("
                            + gameSessionID + ", "
                            + turnID + ", "
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

    public static void clearAllData(){
        try {
            Statement statement = getStatement();
            statement.executeUpdate("DROP SEQUENCE IF EXISTS seq_gamesession_id CASCADE;");
            statement.executeUpdate("DROP TABLE IF EXISTS gamesessions CASCADE;");
            statement.executeUpdate("DROP TABLE IF EXISTS turns CASCADE;");
            statement.executeUpdate("DROP TABLE IF EXISTS flipped_pieces CASCADE;");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getSessionScore(int gameSessionID) {
        int lastSessionScore = 0;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT number_stones_user FROM gamesessions " +
                    "WHERE gamesession_id =  " + gameSessionID + "; ");
            while (resultSet.next()) {
                lastSessionScore = resultSet.getInt(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return lastSessionScore;
    }


    public static double getSessionDuration(int gameSessionID){
        double lastSessionDuration = 0;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT time_elapsed FROM gamesessions " +
                    "WHERE gamesession_id =  " + gameSessionID + "; ");
            while (resultSet.next()) {
                lastSessionDuration = resultSet.getDouble(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return lastSessionDuration;
    }

    public static boolean getSessionWon(int gameSessionID){
        boolean userWon = false;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user_won FROM gamesessions " +
                    "WHERE gamesession_id =  " + gameSessionID + "; ");
            while (resultSet.next()) {
                userWon = resultSet.getBoolean(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return userWon;
    }

    public static boolean getSessionTied(int gameSessionID){
        boolean isTied = false;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT number_stones_user FROM gamesessions " +
                    "WHERE gamesession_id =  " + gameSessionID + "; ");
            while (resultSet.next()) {
                isTied = (resultSet.getInt(1) == 32);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return isTied;
    }

    public static HashMap<Integer, Integer> getSessionComputerMoveProfitabilities(int gameSessionID, HashMap<Integer, Integer> computerMoveProfitabilitiesMap) {
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT t.turn_id, COUNT(*) " +
                    "        FROM turns t JOIN gamesessions g ON g.gamesession_id = t.gamesession_id " +
                    "        JOIN flipped_pieces f ON (t.gamesession_id = f.gamesession_id AND t.turn_id=f.turn_id) " +
                    "        WHERE f.gamesession_id=" + gameSessionID + " AND player_name IN ('easy', 'medium', 'hard') " +
                    "        GROUP BY (t.gamesession_id, t.turn_id) " +
                    "        ORDER BY t.gamesession_id, t.turn_id;");
            while (resultSet.next()) {
                int computerMoveTurnID = resultSet.getInt(1);
                int computerMoveProfitability = resultSet.getInt(2);
                computerMoveProfitabilitiesMap.put(computerMoveTurnID, computerMoveProfitability);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return computerMoveProfitabilitiesMap;
    }


    public static HashMap<Integer, Integer> getSessionUserMoveProfitabilities(int gameSessionID, HashMap<Integer, Integer> userMoveProfitabilitiesMap)  {
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT t.turn_id, COUNT(*) " +
                    "        FROM turns t JOIN gamesessions g ON g.gamesession_id = t.gamesession_id " +
                    "        JOIN flipped_pieces f ON (t.gamesession_id = f.gamesession_id AND t.turn_id=f.turn_id) " +
                    "        WHERE f.gamesession_id=" + gameSessionID + " AND player_name NOT IN ('easy', 'medium', 'hard') " +
                    "        GROUP BY (t.gamesession_id, t.turn_id) " +
                    "        ORDER BY t.gamesession_id, t.turn_id;");
            while (resultSet.next()) {
                int userMoveTurnID = resultSet.getInt(1);
                int userMoveProfitability = resultSet.getInt(2);
                userMoveProfitabilitiesMap.put(userMoveTurnID, userMoveProfitability);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return userMoveProfitabilitiesMap;
    }

    public static HashMap<Integer, Double> getSessionUserMoveDurations(int gameSessionID, HashMap<Integer, Double> userMoveDurationsMap)  {
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT turn_id, time_elapsed FROM turns " +
                    " WHERE gamesession_id = " + gameSessionID +
                    " AND player_name NOT IN ('easy', 'medium', 'hard') " +
                    " ORDER BY turn_id;");
            while (resultSet.next()) {
                int userMoveTurnID = resultSet.getInt(1);
                double userMoveDuration = resultSet.getDouble(2);
                userMoveDurationsMap.put(userMoveTurnID, userMoveDuration);
            }
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userMoveDurationsMap;
    }

    public static String getSessionUserName(int gameSessionID) {
        String userName = null;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT username from gamesessions WHERE gamesession_id=" + gameSessionID +
                    ";");
            while (resultSet.next()) {
                userName = resultSet.getString(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }

    public static String getSessionComputerName(int gameSessionID){
        String computerName = null;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT computer_name from gamesessions WHERE gamesession_id=" + gameSessionID +
                    ";");
            while (resultSet.next()) {
                computerName = resultSet.getString(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return computerName;
    }


    public static boolean getSessionUserWentFirst(int gameSessionID) {
        List<String> computerModes = List.of("easy", "medium", "hard");
        String playerName = null;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT player_name from turns WHERE gamesession_id=" + gameSessionID +
                    " FETCH NEXT 1 ROWS ONLY;");
            while (resultSet.next()) {
                playerName = resultSet.getString(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        if (!computerModes.contains(playerName)) {
            return true;
        }
        return false;
    }

    public static boolean databaseHasTables(){
        boolean databaseHasData = false;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS (" +
                    "    SELECT FROM " +
                    "        information_schema.tables " +
                    "    WHERE " +
                    "        table_name = 'gamesessions' " +
                    "    );");
            while (resultSet.next()) {
                databaseHasData = resultSet.getBoolean(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return databaseHasData;
    }

    public static ArrayList<Integer> getFinishedGameSessionIDList() {
        ArrayList<Integer> finishedGameSessionsIDList = new ArrayList<>();
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT gamesession_id FROM gamesessions WHERE is_over = true ORDER BY gamesession_id;");
            while (resultSet.next()) {
                int gamesessionID = resultSet.getInt(1);
                finishedGameSessionsIDList.add(gamesessionID);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return finishedGameSessionsIDList;
    }
}
