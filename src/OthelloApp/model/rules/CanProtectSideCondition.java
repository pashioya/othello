package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class CanProtectSideCondition implements Condition {

    public boolean evaluate(Facts facts) {
        Board board = facts.get("board");
        StoneColor computerColor = facts.get("playerColor");
        if (board.userCanTakeSide(computerColor)) {
            ArrayList<int[]> dangerousTopBottomCoordinates = new ArrayList<>();
            dangerousTopBottomCoordinates.addAll(board.findDangerousTopBottomCoordinates(computerColor));
            for (int[] dangerousCoordinate : dangerousTopBottomCoordinates) {
                if (board.findCoordinateToProtectSide(dangerousCoordinate[0], dangerousCoordinate[1], computerColor, true) != null) {
                    return true;
                }
            }
            ArrayList<int[]> dangerousLeftRightCoordinates = new ArrayList<>();
            dangerousLeftRightCoordinates.addAll(board.findDangerousLeftRightCoordinates(computerColor));
            for (int[] dangerousCoordinate : dangerousLeftRightCoordinates) {
                if (board.findCoordinateToProtectSide(dangerousCoordinate[0], dangerousCoordinate[1], computerColor, false) != null) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }




    public static CanProtectSideCondition computerCanProtectSide() {
        return new CanProtectSideCondition();
    }
}
