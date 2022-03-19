package domain;

import java.util.ArrayList;

public abstract class Player {
    private StoneColor stoneColor;

    public Player(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor getPlayerColor() {
        return stoneColor;
    }

    public ArrayList<int[]> findAllPossibleMoves(Board board){
        ArrayList<int[]> possibleMovesCoordinates = new ArrayList<int[]>();
        for (int row = 0; row < board.getGRID().length; row++){
            for (int column = 0; column < board.getGRID()[row].length; column++ ){
                if (board.isValidMove(row, column, this.stoneColor)){
                    possibleMovesCoordinates.add(new int[] {row, column});
                }
            }
        }
        return possibleMovesCoordinates;
    }

    public boolean hasValidMoves(Board board){
        ArrayList<int[]> validMoves = findAllPossibleMoves(board);
        if (validMoves.size() == 0){
            return false;
        }
        return true;
    }
}
