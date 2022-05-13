package OthelloApp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import static OthelloApp.DBUtil.DBUtil.closeDbConnection;
import static OthelloApp.DBUtil.DBUtil.getStatement;

public class GameStatistics {
    private ArrayList<GameSessionStatistics> gameSessionStatisticsList;


    public GameStatistics() {
        gameSessionStatisticsList = new ArrayList<>();
        fillGameSessionStatisticsList();
    }

    public ArrayList<GameSessionStatistics> getGameSessionStatisticsList() {
        return gameSessionStatisticsList;
    }

    private void fillGameSessionStatisticsList() {
        for (Integer gameSessionID : getFinishedGameSessionIDList()) {
            gameSessionStatisticsList.add(new GameSessionStatistics(gameSessionID));
        }
    }

    private ArrayList<Integer> getFinishedGameSessionIDList() {
        ArrayList<Integer> finishedGameSessionsIDList = new ArrayList<>();
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT gamesession_id FROM gamesessions WHERE is_over = True ORDER BY gamesession_id;");
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


    public ArrayList<Double> getSessionDurationsList() {
        ArrayList<Double> sessionDurationsList = new ArrayList<>();
        if (!gameSessionStatisticsList.isEmpty()) {
            for (GameSessionStatistics gameSessionStatistics : gameSessionStatisticsList) {
                sessionDurationsList.add(gameSessionStatistics.getDuration());
            }
        }
        return sessionDurationsList;
    }

    public double getLastSessionDurationPercentile() {
        double lastSessionDuration = getGameSessionStatisticsList().get(getGameSessionStatisticsList().size()-1).getDuration();
        ArrayList<Double> sessionDurationsList = getSessionDurationsList();
        Collections.sort(sessionDurationsList);
        double nBelow = 0.0;
        for (Double sessionDuration : sessionDurationsList) {
            if (sessionDuration < lastSessionDuration) {
                nBelow++;
            }
        }
        return (nBelow / Double.valueOf(sessionDurationsList.size()) * 100);
    }


    public GameSessionStatistics getLatestGameSessionStatistics() {
        for (GameSessionStatistics gameSessionStatistics : gameSessionStatisticsList) {
            if (gameSessionStatistics.getGameSessionID() == getLastSessionID()) {
                return gameSessionStatistics;
            }
        }
        return null;
    }

    public Double getAverageScore() {
        Double averageScore;
        double sum = 0;
        for (GameSessionStatistics gameSessionStatistics : gameSessionStatisticsList) {
            sum += gameSessionStatistics.getScore();
        }
        averageScore = Double.valueOf(sum/gameSessionStatisticsList.size());
        return averageScore;
    }

    public boolean lastSessionScoreGreaterThanAverage() {
        return gameSessionStatisticsList.get(gameSessionStatisticsList.size() - 1).getScore() > getAverageScore();
    }

    public int getLastSessionID(){
        return gameSessionStatisticsList.get(gameSessionStatisticsList.size() - 1).getGameSessionID();
    }

}

