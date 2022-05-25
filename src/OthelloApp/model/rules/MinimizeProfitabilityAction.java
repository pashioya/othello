package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

public class MinimizeProfitabilityAction implements Action {

    public void execute (Facts facts) throws Exception {
        System.out.println("Finding least profitable move");
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        StoneColor playerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        Board board = facts.get("board");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        for (int[] possibleMove : possibleMoves) {
            builder.append("Row " + possibleMove[0] + ", column " + possibleMove[1] + " flips " + board.getMoveProfitability(possibleMove, playerColor) + " user stone(s).\n");
        }
        int[] coordinates = board.findLeastProfitableMove(possibleMoves, playerColor);
        builder.append("Computer placed stone at row " + coordinates[0] + ", column " + coordinates[1] + " because this move flipped the least user stone(s).");
        activeTurn.setPlacedCoordinate(coordinates);
        activeTurn.setFlippedStoneCoordinates(board.findFlippableStones(coordinates, playerColor));
        activeTurn.setExplanation(builder.toString());
    }

    public static MinimizeProfitabilityAction chooseLeastProfitableMove(){
        return new MinimizeProfitabilityAction();
    }
}
