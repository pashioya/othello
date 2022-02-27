package domain;

import java.util.ArrayList;

public class GameSession {

    private Board board;
    private User user;
    private Computer computer;
//    private int sessionStartTime;
//    private int sessionEndTime;
//    private boolean isOver;
//    private boolean isWon;

    public GameSession() {
        this.board = new Board();
        this.user = new User(StoneColor.BLACK);
        this.computer = new Computer(StoneColor.WHITE);

        // testing
//        board.placeStone(2, 3, StoneColor.WHITE);
//        board.placeStone(2, 4, StoneColor.WHITE);
//        board.placeStone(3, 3, StoneColor.WHITE);
//        board.placeStone(3, 4, StoneColor.WHITE);
//        board.placeStone(3, 5, StoneColor.BLACK);
//        board.placeStone(4, 3, StoneColor.WHITE);
//        board.placeStone(4, 4, StoneColor.BLACK);
//        board.placeStone(5, 4, StoneColor.WHITE);
//        board.placeStone(6, 5, StoneColor.BLACK);
        //
    }

    public void playRound() {
        showBoard();
        boolean isValidMove = false;
        while (!isValidMove) {
            int[] coordinates = user.move();
            isValidMove = board.isValidMove(coordinates[0], coordinates[1], user.getPlayerColor());
            if (isValidMove){
                updateBoard(coordinates[0], coordinates[1], user.getPlayerColor());
            }
        }
        showBoard();
//        computer.chooseMostProfitableMove(board);
    }

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

}
