package OthelloApp.dataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import static OthelloApp.DBUtil.DBUtil.closeDbConnection;
import static OthelloApp.DBUtil.DBUtil.getStatement;

public final class DataManager {

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
                lastSessionScore = resultSet.getInt(1); // gets the result of name
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
                lastSessionDuration = resultSet.getDouble(1); // gets the result of name
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
                userWon = resultSet.getBoolean(1); // gets the result of name
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
                isTied = (resultSet.getInt(1) == 32); // gets the result of name
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
                userName = resultSet.getString(1); // gets the result of name
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
                computerName = resultSet.getString(1); // gets the result of name
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
                playerName = resultSet.getString(1); // gets the result of name
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
}
