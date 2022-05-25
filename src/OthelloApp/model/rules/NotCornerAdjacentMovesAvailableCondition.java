package OthelloApp.model.rules;

import OthelloApp.model.Board;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class NotCornerAdjacentMovesAvailableCondition implements Condition {

    public boolean evaluate(Facts facts) {
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        return canAvoidCornerAdjacentMoves(board, possibleMoves);
    }

    private boolean canAvoidCornerAdjacentMoves(Board board, ArrayList<int[]> possibleMoves){
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsNotCornerAdjacent(possibleMove)){
                return true;
            };
        };
        System.out.println("Only danger zone moves available: " + possibleMoves.size());
        return false;
    }



    public static NotCornerAdjacentMovesAvailableCondition computerCanAvoidCornerAdjacent(){
        return new NotCornerAdjacentMovesAvailableCondition();
    }
}
