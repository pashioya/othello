package domain;

import java.util.ArrayList;

public class Computer extends Player{

    public Computer(StoneColor stoneColor) {
        super(stoneColor);
    }




    public int[] findMostProfitableMove(ArrayList<int[]> possibleMoves, Board board){
        int[] mostProfitableMove = null;
        int highestProfitability = 0;
        for (int[] possibleMove : possibleMoves) {
            ArrayList<int[]>flippableStoneList = board.findFlippableStones(possibleMove[0], possibleMove[1], getPlayerColor());
            int profitability =  flippableStoneList.size();
            System.out.println("Move at row " + possibleMove[0] + ", column " + possibleMove[1] + " yields " + profitability);
            if (profitability > highestProfitability){
                mostProfitableMove = possibleMove;
                highestProfitability = profitability;
            }
        }
        return mostProfitableMove;
    }
}
