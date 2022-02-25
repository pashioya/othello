import domain.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.initializeBoard();
        System.out.println(board.toString());
        board.isValidMove(5,3);


    }
}
