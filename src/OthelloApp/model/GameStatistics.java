package OthelloApp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static OthelloApp.dataManager.DataManager.databaseHasTables;
import static OthelloApp.dataManager.DataManager.getFinishedGameSessionIDList;

public class GameStatistics {
    private ArrayList<GameSessionStatistics> gameSessionStatisticsList;


    public GameStatistics() {
        gameSessionStatisticsList = new ArrayList<>();
        if (databaseHasTables()) {
            fillGameSessionStatisticsList();
        }
        ;
    }

    public ArrayList<GameSessionStatistics> getGameSessionStatisticsList() {
        return gameSessionStatisticsList;
    }

    private void fillGameSessionStatisticsList() {
        for (Integer gameSessionID : getFinishedGameSessionIDList()) {
            gameSessionStatisticsList.add(new GameSessionStatistics(gameSessionID));
        }
    }


    public HashMap<Integer, Double> getSessionDurationsMap() {
        HashMap<Integer, Double> sessionDurationsMap = new HashMap<Integer, Double>();
        if (!gameSessionStatisticsList.isEmpty()) {
            for (GameSessionStatistics gameSessionStatistics : gameSessionStatisticsList) {
                sessionDurationsMap.put(gameSessionStatistics.getGameSessionID(), gameSessionStatistics.getDuration());
            }
        }
        return sessionDurationsMap;
    }

    public double getLastSessionDurationPercentile() {
        double lastSessionDuration = getGameSessionStatisticsList().get(getGameSessionStatisticsList().size() - 1).getDuration();
        ArrayList<Double> sessionDurationsList = new ArrayList<Double>(getSessionDurationsMap().values());
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
        averageScore = Double.valueOf(sum / gameSessionStatisticsList.size());
        return averageScore;
    }

    public boolean lastSessionScoreGreaterThanAverage() {
        return gameSessionStatisticsList.get(gameSessionStatisticsList.size() - 1).getScore() > getAverageScore();
    }

    public int getLastSessionID() {
        return gameSessionStatisticsList.get(gameSessionStatisticsList.size() - 1).getGameSessionID();
    }


    public GameSessionStatistics getFastestGameSession(boolean sessionLost) {
        GameSessionStatistics fastestGameSession = gameSessionStatisticsList.get(0);
        double fastestTime = gameSessionStatisticsList.get(0).getDuration();
        for (GameSessionStatistics gameSessionStatistics : gameSessionStatisticsList) {
            if (sessionLost) {
                if (!gameSessionStatistics.userWon() && (gameSessionStatistics.getDuration() < fastestTime)) {
                    fastestTime = gameSessionStatistics.getDuration();
                    fastestGameSession = gameSessionStatistics;
                }
            } else {
                if (gameSessionStatistics.userWon() && (gameSessionStatistics.getDuration() < fastestTime)) {
                    fastestTime = gameSessionStatistics.getDuration();
                    fastestGameSession = gameSessionStatistics;
                }
            }
        }
        return fastestGameSession;
    }
}

