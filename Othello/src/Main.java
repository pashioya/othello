import domain.Board;
import domain.Stone;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.initializeBoard();

        board.placeStone(2, 3, Stone.Color.WHITE);
        board.placeStone(3, 3, Stone.Color.WHITE);
        board.placeStone(3, 4, Stone.Color.WHITE);
        board.placeStone(3, 5, Stone.Color.BLACK);
        board.placeStone(4, 3, Stone.Color.WHITE);
        board.placeStone(4, 4, Stone.Color.BLACK);
        board.placeStone(5, 4, Stone.Color.WHITE);
        board.placeStone(6,5,Stone.Color.BLACK);

        System.out.println(board.toString());

        if (board.isValidMove(3,2)){
            System.out.println("3,2 is a valid move");
        } else{
            System.out.println("3,2 is not a valid move");
        }



    }
}
