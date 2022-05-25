package OthelloApp.model.rules;



import OthelloApp.model.Board;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class CornerMovesAvailableCondition implements Condition {

    @Override
    public boolean evaluate(Facts facts) {
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        return canTakeCorner(board, possibleMoves);
    }

    private boolean canTakeCorner(Board board, ArrayList<int[]> possibleMoves){
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsCorner(possibleMove)){
                return true;
            };
        };
        System.out.println("No corners available");
        return false;
    }



    public static CornerMovesAvailableCondition computerCanTakeCorner(){
        return new CornerMovesAvailableCondition();
    }
}
