package OthelloApp.model.rules;

import OthelloApp.model.Board;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class CenterMovesAvailableCondition implements Condition {

    public boolean evaluate(Facts facts) {
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        return canTakeCenter(board, possibleMoves);
    }

    private boolean canTakeCenter(Board board, ArrayList<int[]> possibleMoves){
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsCenter(possibleMove)){
                return true;
            };
        };
        System.out.println("No centers available");
        return false;
    }

    public static CenterMovesAvailableCondition computerCanTakeCenter(){
        return new CenterMovesAvailableCondition();
    }
}
