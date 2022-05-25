package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

import static OthelloApp.model.GameSession.setMostProfitableMove;

public class CanReinforceCornerAction implements Action {
    @Override
    public void execute(Facts facts) throws Exception {
        System.out.println("Corner-reinforcing move(s) are available");
        ArrayList<int[]> possibleMoves = facts.get("possibleMoveList");
        Board board = facts.get("board");
        StoneColor playerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        ArrayList<int[]> filteredMoves = filterCornerReinforcingMoves(board, possibleMoves, playerColor);
        builder.append("Computer could not take a corner or protect existing side stones.\n");
        builder.append("Computer found the following moves(s) that reinforce its corners: \n");
        setMostProfitableMove(filteredMoves, board, playerColor, activeTurn, builder);
    }

    ArrayList<int[]> filterCornerReinforcingMoves(Board board, ArrayList<int[]> possibleMoves, StoneColor computerColor){
        ArrayList<int[]> cornerReinforcingMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (board.coordinateIsSide(possibleMove, false)){ // look at all moves on the side
                if (possibleMove[0] == 0 || possibleMove[0] == 7){ // if move is on the top or bottom side
                    if (board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, true, true, false) ||
                            board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, true, false, false)){
                        cornerReinforcingMoves.add(possibleMove);
                    };
                } else if (possibleMove[1] == 0 || possibleMove[1] == 7){ // if move is on the left or right side
                    if (board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, false, false, true) ||
                            board.coordinateCanReinforceCorner(possibleMove[0], possibleMove[1], computerColor, false, false, false)){
                        cornerReinforcingMoves.add(possibleMove);
                    };
                }
            }
        }
        return cornerReinforcingMoves;
    }


    public static CanReinforceCornerAction takeCornerReinforcingSquare() {
        return new CanReinforceCornerAction();
    }
}
