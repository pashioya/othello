package OthelloApp.model.rules;

import OthelloApp.model.Board;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class SafeSideMovesAvailableCondition implements Condition {

    public boolean evaluate(Facts facts) {
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        return canTakeSafeSide(board, possibleMoves);
    }

    private boolean canTakeSafeSide(Board board, ArrayList<int[]> possibleMoves){
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsSide(possibleMove, true)){
                return true;
            };
        };
        System.out.println("Safe side moves not available");
        return false;
    }



    public static SafeSideMovesAvailableCondition computerCanTakeSafeSide(){
        return new SafeSideMovesAvailableCondition();
    }
}
