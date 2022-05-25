package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

import static OthelloApp.model.GameSession.setMostProfitableMove;

public class SafeSideMovesAvailableAction implements Action {

    public void execute(Facts facts) throws Exception {
        System.out.println("Safe side moves are available");
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        StoneColor playerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        builder.append("Computer could not take a corner or protect its existing side stones.\n");
        builder.append("Computer found the following safe (not corner-adjacent) side move(s).\n");
        ArrayList<int[]> filteredMoves = filterSafeSideMoves(board, possibleMoves);
        setMostProfitableMove(filteredMoves, board, playerColor, activeTurn, builder);
    }

    private ArrayList<int[]> filterSafeSideMoves(Board board, ArrayList<int[]> possibleMoves){
        ArrayList<int[]> filteredMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsSide(possibleMove, true)){
                filteredMoves.add(possibleMove);
            }
        }
        return filteredMoves;
    }

    public static SafeSideMovesAvailableAction takeSafeSideSquare() {
        return new SafeSideMovesAvailableAction();
    }
}

