package OthelloApp.model.rules;

import OthelloApp.model.Board;
import OthelloApp.model.StoneColor;
import OthelloApp.model.Turn;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.ArrayList;

import static OthelloApp.model.GameSession.setMostProfitableMove;

public class CanProtectSideAction implements Action {

    public void execute(Facts facts) throws Exception {
        Board board = facts.get("board");
        StoneColor computerColor = facts.get("playerColor");
        Turn activeTurn = facts.get("turn");
        StringBuilder builder = facts.get("moveExplanationBuilder");
        builder.append("Computer found the following user stone(s) that could flip the computer's side stone(s): \n");
        ArrayList<int[]> dangerousTopBottomCoordinates = new ArrayList<>();
        dangerousTopBottomCoordinates.addAll(board.findDangerousTopBottomCoordinates(computerColor));
        ArrayList<int[]> counterMoves = new ArrayList<>();
        System.out.println("looking for solutions to horizontal dangerous coordinates");
        for (int[] dangerousCoordinate : dangerousTopBottomCoordinates) {
            builder.append("Row " + dangerousCoordinate[0] + ", column " + dangerousCoordinate[1] + "\n");
            int[] counterMove = null;
            counterMove = board.findCoordinateToProtectSide(dangerousCoordinate[0], dangerousCoordinate[1], computerColor, true);
            if (counterMove != null) {
                System.out.println(counterMove[0] + " " + counterMove[1]);
                counterMoves.add(counterMove);
            }
        }
        ArrayList<int[]> dangerousLeftRightCoordinates = new ArrayList<>();
        dangerousLeftRightCoordinates.addAll(board.findDangerousLeftRightCoordinates(computerColor));
        System.out.println("looking for solutions to vertical dangerous coordinates");
        for (int[] dangerousCoordinate : dangerousLeftRightCoordinates) {
            builder.append("Row " + dangerousCoordinate[0] + ", column " + dangerousCoordinate[1] + "\n");
            int[] counterMove = null;
            System.out.println("Vertical solution: ");
            counterMove = board.findCoordinateToProtectSide(dangerousCoordinate[0], dangerousCoordinate[1], computerColor, false);
            if (counterMove != null) {
                System.out.println(counterMove[0] + " " + counterMove[1]);
                counterMoves.add(counterMove);
            }
        }
        builder.append("Computer found moves to protect its side stones: \n");
        System.out.println("start finding profitable moves: ");
        setMostProfitableMove(counterMoves, board, computerColor, activeTurn, builder);
    }

    public static CanProtectSideAction takeSideProtectingSquare() {
        return new CanProtectSideAction();
    }
}
