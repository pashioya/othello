package OthelloApp.model;

import java.util.*;

import static OthelloApp.dataManager.DataManager.*;

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

    private void setScore() {
        this.score = getSessionScore(gameSessionID);
    }

    private void setDuration() {
        this.duration = getSessionDuration(gameSessionID);
    }

    private void setUserWon() {
        this.userWon = getSessionWon( gameSessionID);
    }

    private void setIsTied() {
        this.isTied = getSessionTied(gameSessionID);
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

    private void fillComputerMoveProfitabilitiesMap() {
        userMoveProfitabilitiesMap = getSessionComputerMoveProfitabilities(gameSessionID, userMoveProfitabilitiesMap);
    }

    private void fillUserMoveProfitabilitiesMap() {
        computerMoveProfitabilitiesMap = getSessionUserMoveProfitabilities(gameSessionID, computerMoveProfitabilitiesMap);
    }

    private void fillUserMoveDurationsMap() {
        userMoveDurationsMap = getSessionUserMoveDurations(gameSessionID, userMoveDurationsMap);
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
        return isTied;
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

    public boolean userWentFirst() {
        return getSessionUserWentFirst(gameSessionID);
    }

    public String getUserName() {
        return getSessionUserName(gameSessionID);
    }

    public String getComputerName() {
        return getSessionComputerName(gameSessionID);
    }
}