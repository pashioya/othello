package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class CanReinforceCornerCondition implements Condition {

    public boolean evaluate(Facts facts) {
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        StoneColor computerColor = facts.get("playerColor");
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsSide(possibleMove, false)){
                if (possibleMove[0] == 0 || possibleMove[0] == 7){ // if move is on the top or bottom side
                    if (board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, true, true, false) ||
                            board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, true, false, false)){
                        System.out.println("Row: " + possibleMove[0] + ", column: " + possibleMove[1] + " reinforces corner" );
                        return true;
                    };
                } else if (possibleMove[1] == 0 || possibleMove[1] == 7){ // if move is on the left or right side
                    if (board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, false, false, true) ||
                            board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, false, false, false)){
                        System.out.println("Row: " + possibleMove[0] + ", column: " + possibleMove[1] + " reinforces corner" );
                        return true;
                    };
                }
            }
        }
        System.out.println("No reinforcing corners found" );
        return false;
    }

    public static CanReinforceCornerCondition computerCanReinforceCorner() {
        return new CanReinforceCornerCondition();
    }
}

