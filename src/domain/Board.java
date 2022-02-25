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



    public String toString() {
        /*
        Creates a visual representation of the user's progress on a semester1.Minesweeper board.
        The board is shown with a border and numbers along each of the board's axes
        to allow the user to choose the coordinates of the square he/she wants to check.

        Uncovered squares with value "0" are displayed as □.
        Uncovered squares with any value over 0 are displayed as their number (e.g. a square
        with 2 bombs in its radius will be displayed as "2").
        Uncovered squares with a bomb (value = -1) are displayed as a *.
        All covered squares are displayed as ■.
         */
        StringBuilder builder = new StringBuilder();
        int countRow = 1;
        for (Square[] row : GRID) {
            if (countRow == 1) {
                buildBoardHeader(builder);
            }
//            buildBoardRow(builder, row, countRow);
//            countRow++;
        }
//        buildBoardFooter(builder);
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
        for (int i = 1; i < SIDE_LENGTH + 1; i++) {
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

//    private void buildBoardSquares(StringBuilder builder, Square[] row) {
//        /*
//        Used in the buildBoardRow() method.
//
//        This method iterates over all Squares in a row of the Board's array of Squares.
//        A representation of every Square's value is added to the String used to
//        display the user's progress.
//        Uncovered squares with value "0" are displayed as □.
//        Uncovered squares with any value over 0 are displayed as their number (e.g. a square
//        with 2 bombs in its radius will be displayed as "2").
//        Uncovered squares with a bomb (value = -1) are displayed as a *.
//        All covered squares are displayed as ■.
//         */
//        for (Square col : row) {
//            //if uncovered
//            if (!col.isCovered()) {
//                if (col.isBomb()) {
//                    builder.append(String.format("%2s ", "*"));
//                } else if (col.getSquareValue() > 0) {
//                    // if the square is not a bomb, print the square's value instead
//                    builder.append(String.format("%2d ", col.getSquareValue()));
//                } else {
//                    //if the square is a 0 and uncovered, display an empty box
//                    builder.append(String.format("%2s ", "□"));
//                }
//            } else {
//                //if covered
//                builder.append(String.format("%2s ", "■"));
//            }
//        }
//    }

//    private void buildBoardRow(StringBuilder builder, Square[] row, int countRow) {
//        /*
//        Used in the toString() method.
//
//        This method makes a left margin that starts with the row's count (1 for the first row, 2, for the second, etc) and adds it
//        to the string.
//        Next, this method adds a representation of all Squares in the row to the string.
//        Next, this method adds a right margin to the row.
//        Lastly, the method adds a new line character.
//         */
//        buildBoardVerticalAxis(builder, countRow); // put a number in front of each row to help the user choose coordinates
//        buildBoardSquares(builder, row); // create String representation of each square in a row
//        builder.append(String.format("%4s", "|")); // Add a margin at the end of the row with a border
//        // at the end of each row, make a new line
//        builder.append("\n");
//    }
//
//    private void buildBoardFooter(StringBuilder builder) {
//        /*
//        Used in the toString() method.
//
//        Formats the bottom of the grid that is displayed to the user.
//         */
//        String footer = "|%" + (11 + 3 * WIDTH) + "s";
//        builder.append(String.format(footer, "|\n"));
//        for (int i = 0; i < 11 + 3 * WIDTH; i++) {
//            builder.append("-");
//        }
//    }

}
