package OthelloApp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import static OthelloApp.db_util.DB_UTIL.closeDbConnection;
import static OthelloApp.db_util.DB_UTIL.getStatement;

public class Game {
    private ArrayList<Integer> sessionsUserScores;
    private ArrayList<Double> sessionDurations;
    private GameSession activeSession;


    public Game(boolean userGoesFirst, String userName) {
        activeSession = new GameSession(userGoesFirst, userName);
        sessionsUserScores = new ArrayList<>();
        sessionDurations = new ArrayList<>();
        fillSessionsUserScores();
        fillSessionDurations();
    }

    private void fillSessionsUserScores() {
        this.sessionsUserScores.clear();
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT number_stones_user FROM gamesessions WHERE is_over = true;");
            while (resultSet.next()) {
                int userScore = resultSet.getInt(1); // gets the result of name
                this.sessionsUserScores.add(userScore);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillSessionDurations() {
        this.sessionDurations.clear();
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT time_elapsed FROM gamesessions WHERE is_over = true ORDER BY gamesession_id;");
            while (resultSet.next()) {
                double timeElapsed = resultSet.getDouble(1); // gets the result of name
                this.sessionDurations.add(timeElapsed);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Double> getSessionDurations() {
        fillSessionDurations();
        return sessionDurations;
    }

    public GameSession getActiveSession() {
        return activeSession;
    }

    public double getAverageScore() {
        fillSessionsUserScores();
        Double sum = 0.0;
        if (!sessionsUserScores.isEmpty()) {
            for (Integer sessionsUserScore : sessionsUserScores) {
                sum += Double.valueOf(sessionsUserScore);
            }
            return sum / sessionsUserScores.size();
        }
        return sum;
    }

    public double getActiveSessionDurationPercentile() {
        fillSessionDurations();
        Collections.sort(sessionDurations);
        double nBelow = 0.0;
        for (Double sessionDuration : sessionDurations) {
            if (sessionDuration < activeSession.getTimeElapsed()) {
                nBelow++;
            }
        }
        return (nBelow / Double.valueOf(sessionDurations.size()) * 100);
    }
}

