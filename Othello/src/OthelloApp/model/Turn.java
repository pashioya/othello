package OthelloApp.model;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Turn {
    static int turnCount = 0;

    private int turnId;
    private String userType;
    private String startTime;
    private int[] placedCoordinate;
    private ArrayList<int[]> flippedStoneCoordinates;
    private String moveTimeStamp;

    public Turn(String userType) {
        this.turnId = turnCount;
        this.userType = userType;
        this.startTime = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss:SS").format(new Date());
        turnCount += 1;
    }

    public void setPlacedCoordinate(int[] placedCoordinate) {
        this.placedCoordinate = placedCoordinate;
    }

    public void setFlippedStoneCoordinates(ArrayList<int[]> flippedStoneCoordinates) {
        this.flippedStoneCoordinates = flippedStoneCoordinates;
    }

    public void setMoveTimeStamp(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss:SS").format(new Date());
        this.moveTimeStamp = timeStamp;
    }

    public void updateTurnTable(){
        // use turnID, placedCoordinate, userType, and startTime to update the SQL database

    }

    public void updateFlippedPiecesTable(){
        // use turnID and flippedStoneCoordinates to update the SQL database
    }

    @Override
    public String toString() {
        return "Turn{" +
                "turnId=" + turnId +
                ", userType='" + userType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", placedCoordinate=" + Arrays.toString(placedCoordinate) +
                ", flippedStoneCoordinates=" + flippedStoneCoordinates +
                ", moveTimeStamp='" + moveTimeStamp + '\'' +
                '}';
    }
}
