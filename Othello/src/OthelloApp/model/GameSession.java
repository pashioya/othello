package OthelloApp.model;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameSession {

    private Board board;
    public User user;
    public Computer computer;
//    private int sessionStartTime;
//    private int sessionEndTime;
//    private boolean isWon;

    public GameSession() {
        this.board = new Board();
        this.user = new User(StoneColor.BLACK);
        this.computer = new Computer(StoneColor.WHITE);
    }

    public boolean isOver() {
        int stoneCounter = 0;
        int numberSquares = 64;
        for (Square[] row : board.getGRID()) {
            for (Square square : row) {
                if (square.hasStone()) {
                    stoneCounter++;
                }
            }
        }
        return (stoneCounter == numberSquares);
    }

    public boolean userWon() {
        int userStoneCount = 0;
        for (Square[] row : board.getGRID()) {
            for (Square square : row) {
                if (square.getStone().isPlayerColor(user.getPlayerColor())) {
                    userStoneCount++;
                }
            }
        }
        return (userStoneCount > 32);
    }

//    public void playRound() {
//        System.out.println("--USER'S TURN--");
//        delayOneSecond();
//        showBoard();
//        if (user.hasValidMoves(board)) {
//            boolean isValidMove = false;
//            while (!isValidMove) {
//                int[] coordinates = user.move();
//                isValidMove = board.isValidMove(coordinates[0], coordinates[1], user.getPlayerColor());
//                if (isValidMove) {
//                    updateBoard(coordinates[0], coordinates[1], user.getPlayerColor());
//                    showBoard();
//                } else {
//                    System.out.println("Please input a valid row and column");
//                }
//            }
//        } else {
//            System.out.println("User cannot place a stone - turn forfeited");
//        }
//        delayOneSecond();
//        System.out.println("--COMPUTER'S TURN--");
//        delayOneSecond();
//        if (computer.hasValidMoves(board)) {
//            ArrayList<int[]> possibleMoves = computer.findAllPossibleMoves(board);
//            int[] mostProfitableMove = computer.findMostProfitableMove(possibleMoves, board);
//            delayOneSecond();
//            updateBoard(mostProfitableMove[0], mostProfitableMove[1], computer.getPlayerColor());
//            System.out.println("Computer placed stone at row " + mostProfitableMove[0] + ", column " + mostProfitableMove[1]);
//            delayOneSecond();
//        } else {
//            System.out.println("Computer cannot place a stone - turn forfeited");
//        }
//    }


    public void updateBoard(int row, int column, StoneColor playerColor) {
        ArrayList<int[]> flippableStoneCoordinates = board.findFlippableStones(row, column, playerColor);
        board.placeStone(row, column, playerColor);
        for (int[] flippableStoneCoordinate : flippableStoneCoordinates) {
            int stoneRow = flippableStoneCoordinate[0];
            int stoneColumn = flippableStoneCoordinate[1];
            board.getGRID()[stoneRow][stoneColumn].getStone().flip();
        }
    }

    public Board getBoard() {
        return board;
    }

    public void showBoard() {
        System.out.println(board);
    }

    public void delayOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
