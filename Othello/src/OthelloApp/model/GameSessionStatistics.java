package OthelloApp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static OthelloApp.db_util.DB_UTIL.closeDbConnection;
import static OthelloApp.db_util.DB_UTIL.getStatement;

public class GameSessionStatistics {
    private int gameSessionID;
    private int score;
    private double duration;
    private boolean userWon;
    private boolean isTied;
    private HashMap<Integer, Integer> userMoveProfitabilitiesMap;
    private HashMap<Integer, Integer> computerMoveProfitabilitiesMap;
    private HashMap<Integer, Double> userMoveDurationsMap;

    public GameSessionStatistics(int gameSessionID) {
        this.gameSessionID = gameSessionID;
        this.userMoveProfitabilitiesMap = new HashMap<>();
        this.computerMoveProfitabilitiesMap = new HashMap<>();
        this.userMoveDurationsMap = new HashMap<>();
        setScore();
        setDuration();
        setUserWon();
        setIsTied();
        fillUserMoveProfitabilitiesMap();
        fillComputerMoveProfitabilitiesMap();
        fillUserMoveDurationsMap();
    }

    public void setScore() {
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
        this.score = lastSessionScore;
    }

    public void setDuration() {
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
        duration = lastSessionDuration;
    }

    public void setUserWon() {
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
    }

    public void setIsTied() {
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
    }

    public double getAverageUserMoveProfitability() {
        Double sum = 0.0;
        if (!userMoveProfitabilitiesMap.isEmpty()) {
            for (Integer userMoveProfitability : userMoveProfitabilitiesMap.values()) {
                sum += Double.valueOf(userMoveProfitability);
            }
            return sum / userMoveProfitabilitiesMap.values().size();
        }
        return sum;
    }

    private void fillComputerMoveProfitabilitiesMap(){
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

    }

    private void fillUserMoveProfitabilitiesMap() {
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
    }

    private void fillUserMoveDurationsMap() {
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
    }

    public int getGameSessionID() {
        return gameSessionID;
    }

    public int getScore() {
        return score;
    }

    public double getDuration() {
        return duration;
    }

    public boolean userWon() {
        return userWon;
    }

    public boolean isTied() {
        return score == 32;
    }



    public HashMap<Integer, Integer> getUserMoveProfitabilitiesMap() {
        return userMoveProfitabilitiesMap;
    }

    public HashMap<Integer, Integer> getOutlierMovesMap() {
        HashMap<Integer, Integer> outlierMovesMap = new HashMap<>();
        ArrayList<Integer> userMoveProfitabilitiesList = new ArrayList<Integer>(userMoveProfitabilitiesMap.values());
        Collections.sort(userMoveProfitabilitiesList);
        for (Map.Entry<Integer, Integer> userMoveEntry : userMoveProfitabilitiesMap.entrySet()) {
            if (isOutlier(userMoveEntry.getValue(), userMoveProfitabilitiesList)) {
                outlierMovesMap.put(userMoveEntry.getKey(), userMoveEntry.getValue());
            }
        }
        return outlierMovesMap;
    }

    private boolean isOutlier(int value, List<Integer> dataList) {
        double q3 = getThirdQuartile(dataList);
        double q1 = getFirstQuartile(dataList);
        double lowerBound = q1 - 1.5 * getIQR(dataList);
        double upperBound = q3 + 1.5 * getIQR(dataList);
        if (value > upperBound || value < lowerBound) {
            return true;
        }
        return false;
    }

    private double getThirdQuartile(List<Integer> dataList) {
        if (dataList.size() % 2 == 0) {
            List<Integer> dataAboveMedian = dataList.subList(dataList.size() / 2, dataList.size());
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile;
        } else {
            List<Integer> dataAboveMedian = dataList.subList((dataList.size() + 1) / 2, dataList.size());
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile;
        }
    }

    private double getFirstQuartile(List<Integer> dataList) {
        if (dataList.size() % 2 == 0) {
            List<Integer> dataBelowMedian = dataList.subList(0, dataList.size() / 2);
            double firstQuartile = getMedian(dataBelowMedian);
            return firstQuartile;
        } else {
            List<Integer> dataBelowMedian = dataList.subList(0, (dataList.size() - 1) / 2);
            double firstQuartile = getMedian(dataBelowMedian);
            return firstQuartile;
        }
    }

    private double getIQR(List<Integer> dataList) {
        if (dataList.size() % 2 == 0) {
            List<Integer> dataBelowMedian = dataList.subList(0, dataList.size() / 2);
            List<Integer> dataAboveMedian = dataList.subList(dataList.size() / 2, dataList.size());
            double firstQuartile = getMedian(dataBelowMedian);
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile - firstQuartile;
        } else {
            List<Integer> dataBelowMedian = dataList.subList(0, (dataList.size() - 1) / 2);
            List<Integer> dataAboveMedian = dataList.subList((dataList.size() + 1) / 2, dataList.size());
            double firstQuartile = getMedian(dataBelowMedian);
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile - firstQuartile;
        }

    }

    private double getMedian(List<Integer> dataList) {
        Collections.sort(dataList);
        // if list contains even number of items
        if (dataList.size() % 2 == 0) {
            double n2 = dataList.get(dataList.size() / 2);
            double n1 = dataList.get(dataList.size() / 2 - 1);
            double median = (n1 + n2) / 2;
            return median;
        } else {
            double median = dataList.get((dataList.size() - 1) / 2);
            return median;
        }
    }

    public Map.Entry getMostProfitableUserTurn() {
        int maxFlipped = 0;
        Map.Entry<Integer, Integer> mostProfitableUserTurn = null;
        for (Map.Entry<Integer, Integer> userMoveProfitabilityEntry : userMoveProfitabilitiesMap.entrySet()) {
            if (userMoveProfitabilityEntry.getValue() > maxFlipped) {
                mostProfitableUserTurn = userMoveProfitabilityEntry;
                maxFlipped = userMoveProfitabilityEntry.getValue();
            }
        }

        return mostProfitableUserTurn;
    }

    public double getAverageMoveDuration() {
        double totalUserMoveDuration = 0;
        for (Map.Entry<Integer, Double> userMoveDurationsEntry : userMoveDurationsMap.entrySet()) {
            totalUserMoveDuration += userMoveDurationsEntry.getValue();
        }
        double averageMoveDuration = totalUserMoveDuration / userMoveDurationsMap.entrySet().size();
        return averageMoveDuration;
    }

    public HashMap<Integer, Double> getUserMoveDurationsMap() {
        return userMoveDurationsMap;
    }

    public HashMap<Integer, Integer> getComputerMoveProfitabilitiesMap() {
        return computerMoveProfitabilitiesMap;
    }
}