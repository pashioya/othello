package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

import static OthelloApp.model.GameSession.setMostProfitableMove;

public class CornerMovesAvailableAction implements Action {

    public void execute (Facts facts) throws Exception {
        System.out.println("Corner move(s) are available");
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        StoneColor playerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        builder.append("Computer found the following center move(s): \n");
        ArrayList<int[]> filteredMoves = filterCornerMoves(board, possibleMoves);
        setMostProfitableMove(filteredMoves, board, playerColor, activeTurn, builder);
    }

    private ArrayList<int[]> filterCornerMoves(Board board, ArrayList<int[]> possibleMoves){
        ArrayList<int[]> filteredMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsCorner(possibleMove)){
                filteredMoves.add(possibleMove);
            }
        }
        return filteredMoves;
    }

    public static CornerMovesAvailableAction takeCornerSquare(){
        return new CornerMovesAvailableAction();
    }
}
