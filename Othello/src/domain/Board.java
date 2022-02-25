package domain;

public class Board {
    private final Square[][] GRID;
    private final int SIDE_LENGTH = 8;

    public Board() {
        this.GRID = new Square[SIDE_LENGTH][SIDE_LENGTH];
    }

    public void initializeBoard() {
        // Create empty board
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < GRID[row].length; col++) {
                GRID[row][col] = new Square();
            }
        }
        // Initialize first 4 squares in center
        GRID[3][3] = new Square(new Stone(Stone.Color.BLACK));
        GRID[3][4] = new Square(new Stone(Stone.Color.WHITE));
        GRID[4][3] = new Square(new Stone(Stone.Color.WHITE));
        GRID[4][4] = new Square(new Stone(Stone.Color.BLACK));
    }

    //---------------TO STRING METHODS---------------
    public String toString() {
        /*
        Creates a visual representation of the user's progress on a Othello board.
        The board is shown with a border and numbers along each of the board's axes
        to allow the user to choose the coordinates of the square he/she wants to put a stone in .


         */
        StringBuilder builder = new StringBuilder();
        int countRow = 0;
        for (Square[] row : GRID) {
            if (countRow == 0) {
                buildBoardHeader(builder);
            }
            buildBoardRow(builder, row, countRow);
            countRow++;
        }
        buildBoardFooter(builder);
        return builder.toString();
    }


    private void buildBoardHeader(StringBuilder builder) {
        /*
        Used in the toString() method.

        This method first creates the top border of the visual representation of the
        semester1.Minesweeper board. Next, it adds a blank row.
        After the blank row, it adds numbers indicating the index of each column of the
        semester1.Minesweeper board. The column indexes allow the user to easily
        choose which column coordinate of the Square he/she wants to check.
         */

        for (int i = 0; i < 11 + 3 * SIDE_LENGTH; i++) {
            builder.append("_");
        }
        builder.append("\n");
        String header = "|%" + (11 + 3 * SIDE_LENGTH) + "s";
        builder.append(String.format(header, "|\n"));
        builder.append(String.format("%-7s", "|"));
        for (int i = 0; i < SIDE_LENGTH ; i++) {
            builder.append(String.format("%2d ", i));
        }
        builder.append(String.format("%4s", "|") + "\n");
    }

    private void buildBoardVerticalAxis(StringBuilder builder, int countRow) {
        /*
        Used in the buildBoardRow() method.

        This method creates a margin. It prints the row's index in this margin to allow the user
        to easily choose a Square's coordinates on the Board.
         */
        builder.append(String.format("|%5d%s", countRow, "|"));
    }

    private void buildBoardSquares(StringBuilder builder, Square[] row) {
        /*
        Used in the buildBoardRow() method.

        This method iterates over all Squares in a row of the Board's array of Squares.

         */
        for (Square square : row) {

            if (!square.hasStone()) {
                builder.append(String.format("%2s ", "□"));
            } else {
                if (square.getStone().getColor() == Stone.Color.BLACK) {
                    builder.append(String.format("%2s ", "B"));
                }
                else {
                    builder.append(String.format("%2s ", "W"));
                }
            }
        }
    }

    private void buildBoardRow(StringBuilder builder, Square[] row, int countRow) {
        /*
        Used in the toString() method.

        This method makes a left margin that starts with the row's count (1 for the first row, 2, for the second, etc) and adds it
        to the string.
        Next, this method adds a representation of all Squares in the row to the string.
        Next, this method adds a right margin to the row.
        Lastly, the method adds a new line character.
         */
        buildBoardVerticalAxis(builder, countRow); // put a number in front of each row to help the user choose coordinates
        buildBoardSquares(builder, row); // create String representation of each square in a row
        builder.append(String.format("%4s", "|")); // Add a margin at the end of the row with a border
        // at the end of each row, make a new line
        builder.append("\n");
    }

    private void buildBoardFooter(StringBuilder builder) {
        /*
        Used in the toString() method.

        Formats the bottom of the grid that is displayed to the user.
         */
        String footer = "|%" + (11 + 3 * SIDE_LENGTH) + "s";
        builder.append(String.format(footer, "|\n"));
        for (int i = 0; i < 11 + 3 * SIDE_LENGTH; i++) {
            builder.append("-");
        }
    }

    //---------------TO STRING METHODS---------------



    public void isValidMove(int row, int column){
        for (int vertical = row-1; vertical <= row+1; vertical++){
            for (int horizontal = column-1; horizontal <= column+1; horizontal++){
                if (horizontal < 0 || horizontal > SIDE_LENGTH - 1 || vertical < 0 || vertical > SIDE_LENGTH - 1) {
                    continue;
                }
                else if (GRID[vertical][horizontal].hasStone()){
                    if (GRID[vertical][horizontal].getStone().isPlayerColor()){
                        System.out.println("this is your color" + vertical + " " + horizontal);
                    }
                }
            }
        }
    }

    public void placeStone(int row, int column){
        this.GRID[row][column].setStone(new Stone(Stone.Color.BLACK));
    }
}
