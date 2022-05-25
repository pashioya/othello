package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.*;

import java.util.ArrayList;


import static OthelloApp.model.GameSession.setLeastProfitableMove;

public class CenterMovesAvailableAction implements Action {

    public void execute (Facts facts) throws Exception {
        System.out.println("Center move(s) are available");
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        StoneColor playerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        ArrayList<int[]> filteredMoves = filterCenterMoves(board, possibleMoves);
        builder.append("Computer could not take a corner, side, or protect existing side stones.\n");
        builder.append("Computer found the following center move(s): \n");
        setLeastProfitableMove(filteredMoves, board, playerColor, activeTurn, builder);
    }

    private ArrayList<int[]> filterCenterMoves(Board board, ArrayList<int[]> possibleMoves){
        ArrayList<int[]> filteredMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsCenter(possibleMove)){
                filteredMoves.add(possibleMove);
            }
        }
        return filteredMoves;
    }


    
    public static CenterMovesAvailableAction takeCenterSquare(){
        return new CenterMovesAvailableAction();
    }
}
