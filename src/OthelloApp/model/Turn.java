package OthelloApp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static OthelloApp.dataManager.DataManager.*;
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
    private String explanation;

    public Turn(String name, boolean isFirstTurn) {
        if (isFirstTurn){
            turnCount = 0;
            createTurnsTable();
            createFlippedPiecesTable();
        }
        this.turnId = turnCount;
        this.name = name;
        this.startDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(new Date());
        this.startTimeMilisec = currentTimeMillis();
        this.flippedStoneCoordinates = new ArrayList<>();
        turnCount ++;
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
        saveTurn(gameSessionID, getTurnId(), getName(), getStartDateTime(), getTimeElapsed(), getPlacedCoordinate());
        saveFlippedPieces(gameSessionID, getTurnId(), getFlippedStoneCoordinates());
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
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

