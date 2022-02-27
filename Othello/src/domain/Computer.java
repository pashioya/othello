package domain;

import java.util.ArrayList;

public class Computer extends Player{

    public Computer(StoneColor stoneColor) {
        super(stoneColor);
    }

    public void chooseMostProfitableMove(Board board){
        ArrayList<int[]> possibleMovesCoordinates = findAllPossibleMoves(board);
        ArrayList<int[]> flippableStones = board.findFlippableStones(4, 1, this.getPlayerColor());
        System.out.println(flippableStones.size());


    }

    public ArrayList<int[]> findAllPossibleMoves(Board board){
        ArrayList<int[]> possibleMovesCoordinates = new ArrayList<int[]>();
        for (int row = 0; row < board.getGRID().length; row++){
            for (int column = 0; column < board.getGRID()[row].length; column++ ){
                if (board.isValidMove(row, column, getPlayerColor())){
                    possibleMovesCoordinates.add(new int[] {row, column});
                }
            }
        }
        return possibleMovesCoordinates;
    }

    public int getMoveProfitability(int[] coordinatePair, Board board){
        ArrayList<int[]>flippableStoneList = board.findFlippableStones(coordinatePair[0], coordinatePair[1], getPlayerColor());
        return flippableStoneList.size();
    }


}
