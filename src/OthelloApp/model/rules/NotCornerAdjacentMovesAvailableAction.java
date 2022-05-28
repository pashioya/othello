package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

import static OthelloApp.model.GameSession.setMostProfitableMove;

public class NotCornerAdjacentMovesAvailableAction implements Action {

    public void execute (Facts facts) throws Exception {
        System.out.println("Non dangerzone moves are available");
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        StoneColor playerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        builder.append("Computer could not take a corner, protect its existing side stones, or place a stone on a side of the board.\n");
        builder.append("Computer found the following move(s) that are not next to a corner.\n");
        ArrayList<int[]> filteredMoves = filterCornerAdjacentMoves(board, possibleMoves);
        setMostProfitableMove(filteredMoves, board, playerColor, activeTurn, builder);
    }

    private ArrayList<int[]> filterCornerAdjacentMoves(Board board, ArrayList<int[]> possibleMoves){
        ArrayList<int[]> filteredMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsNotCornerAdjacent(possibleMove)){
                filteredMoves.add(possibleMove);
            }
        }
        return filteredMoves;
    }

    public static NotCornerAdjacentMovesAvailableAction takeNonCornerAdjacentSquare(){
        return new NotCornerAdjacentMovesAvailableAction();
    }
}
