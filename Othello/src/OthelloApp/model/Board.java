package OthelloApp.model;

import java.util.ArrayList;

import static OthelloApp.model.StoneColor.BLACK;
import static OthelloApp.model.StoneColor.WHITE;

public class Board {
    private final Square[][] GRID;
    private final static int SIDE_LENGTH = 8;

    public Board() {
        this.GRID = new Square[SIDE_LENGTH][SIDE_LENGTH];
        initializeBoard();
    }

    public void initializeBoard() {
        // Create empty board
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < GRID[row].length; col++) {
                GRID[row][col] = new Square();
            }
        }
        // Initialize first 4 squares in center
        GRID[3][3].setStone(BLACK);
        GRID[3][4].setStone(WHITE);
        GRID[4][3].setStone(WHITE);
        GRID[4][4].setStone(BLACK);

//        // Test
//        GRID[3][3] = new Square(new Stone(StoneColor.WHITE));
//        GRID[3][4] = new Square(new Stone(StoneColor.WHITE));
//        GRID[4][3] = new Square(new Stone(StoneColor.WHITE));
//        GRID[4][4] = new Square(new Stone(StoneColor.BLACK));
//        GRID[5][4] = new Square(new Stone(StoneColor.WHITE));
//        GRID[6][5] = new Square(new Stone(StoneColor.BLACK));
//        GRID[3][5] = new Square(new Stone(StoneColor.BLACK));
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
        board. Next, it adds a blank row.
        After the blank row, it adds numbers indicating the index of each column of the
         board. The column indexes allow the user to easily
        choose which column coordinate of the Square he/she wants to check.
         */

        for (int i = 0; i < 11 + 3 * SIDE_LENGTH; i++) {
            builder.append("_");
        }
        builder.append("\n");
        String header = "|%" + (11 + 3 * SIDE_LENGTH) + "s";
        builder.append(String.format(header, "|\n"));
        builder.append(String.format("%-7s", "|"));
        for (int i = 0; i < SIDE_LENGTH; i++) {
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
            if (square.hasStone()) {
                if (square.getStoneColor() == BLACK) {
                    builder.append(String.format("%2s ", "B"));
                } else {
                    builder.append(String.format("%2s ", "W"));
                }
            } else {
                builder.append(String.format("%2s ", "□"));
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


    public Square[][] getGRID() {
        return GRID;
    }

    public boolean isValidMove(int row, int column, StoneColor stoneColor) {
        boolean isValid = false;

        // If the quare at the selected coordinate already contains a square, the move isn't valid -> return false
        if (GRID[row][column].hasStone()) {
            return isValid;
        }

        // Check every square surrounding the selected square
        for (int vertical = row - 1; vertical <= row + 1; vertical++) {
            for (int horizontal = column - 1; horizontal <= column + 1; horizontal++) {

                // Don't try to check square that would be out of bounds
                if (horizontal < 0 || horizontal > SIDE_LENGTH - 1 || vertical < 0 || vertical > SIDE_LENGTH - 1) {
                    continue;
                } else if (GRID[vertical][horizontal].hasStone()) {
                    // If a square that is touching the selected square contains a stone, check to see if the stone is the opposite color as the player's
                    if (GRID[vertical][horizontal].hasOppositeColorStone(stoneColor)) {
                        int verticalShift = vertical - row;
                        int horizontalShift = horizontal - column;
//                        System.out.println("Vertical shift: " + verticalShift + " Horizontal shift: " + horizontalShift + " Row: " + vertical + " Column: " + horizontal);
                        // Check to see if this stone can be surrounded
                        isValid = checkDirection(verticalShift, horizontalShift, vertical, horizontal, stoneColor);
                        if (isValid) {
                            return isValid;
                        }
                    }
                }
            }

        }
        return isValid;
    }

    public void placeStone(int row, int column, StoneColor stoneColor) {
        this.GRID[row][column].setStone(stoneColor);

    }

    public int[] getDirection(int vertical, int row, int horizontal, int column) {
        int verticalShift = vertical - row;
        int horizontalShift = horizontal - column;
        return new int[]{verticalShift, horizontalShift};
    }

    public ArrayList<int[]> findAllPossibleMoves(StoneColor stonecolor) {
        ArrayList<int[]> possibleMovesCoordinates = new ArrayList<int[]>();
        for (int row = 0; row < getGRID().length; row++) {
            for (int column = 0; column < getGRID()[row].length; column++) {
                if (isValidMove(row, column, stonecolor)) {
                    possibleMovesCoordinates.add(new int[]{row, column});
                }
            }
        }
        return possibleMovesCoordinates;
    }

    public boolean hasValidMoves(StoneColor stoneColor) {
        ArrayList<int[]> validMoves = findAllPossibleMoves(stoneColor);
        if (validMoves.size() == 0) {
            return false;
        }
        return true;
    }

    public int[] findMostProfitableMove(ArrayList<int[]> possibleMoves, StoneColor stoneColor) {
        int[] mostProfitableMove = null;
        int highestProfitability = 0;
        for (int[] possibleMove : possibleMoves) {
            ArrayList<int[]> flippableStoneList = findFlippableStones(possibleMove, stoneColor);
            int profitability = flippableStoneList.size();
            System.out.println("Move at row " + possibleMove[0] + ", column " + possibleMove[1] + " yields " + profitability);
            if (profitability > highestProfitability) {
                mostProfitableMove = possibleMove;
                highestProfitability = profitability;
            }
        }
        System.out.println("Placed stone at: row " + mostProfitableMove[0] + ", column " + mostProfitableMove[1]);
        return mostProfitableMove;
    }

    public boolean checkDirection(int verticalShift, int horizontalShift, int vertical, int horizontal, StoneColor stoneColor) {
        int newVertical = vertical + verticalShift;
        int newHorizontal = horizontal + horizontalShift;
        // check if not out of bounds:
        if (newHorizontal < 0 || newHorizontal > SIDE_LENGTH - 1 || newVertical < 0 || newVertical > SIDE_LENGTH - 1) {
            return false;
        }
        Square newSquare = GRID[newVertical][newHorizontal];
        if (newSquare.hasStone()) {
            if (newSquare.hasOppositeColorStone(stoneColor)) {
                return checkDirection(verticalShift, horizontalShift, newVertical, newHorizontal, stoneColor);
            } else {
                return true;
            }
        }
        return false;
    }

    public ArrayList<int[]> findFlippableStones(int[] coordinates, StoneColor stoneColor) {
        int row = coordinates[0];
        int column = coordinates[1];
        ArrayList<int[]> flippableStoneCoordinates = new ArrayList<int[]>();
        for (int vertical = row - 1; vertical <= row + 1; vertical++) {
            for (int horizontal = column - 1; horizontal <= column + 1; horizontal++) {
                if (horizontal < 0 || horizontal > SIDE_LENGTH - 1 || vertical < 0 || vertical > SIDE_LENGTH - 1) {
                    continue;
                } else if (GRID[vertical][horizontal].hasStone()) {
                    Square currentSquare = GRID[vertical][horizontal];
                    if (currentSquare.hasOppositeColorStone(stoneColor)) {
                        int[] direction = getDirection(vertical, row, horizontal, column);
                        boolean directionHasFlippableStones = checkDirection(direction[0], direction[1], vertical, horizontal, stoneColor);
                        if (directionHasFlippableStones) {
                            addFlippableStoneCoordinates(direction, vertical, horizontal, stoneColor, flippableStoneCoordinates);
                        }
                    }
                }
            }
        }
        return flippableStoneCoordinates;
    }

    public void addFlippableStoneCoordinates(int[] direction, int vertical, int horizontal, StoneColor stoneColor, ArrayList<int[]> flippableStoneCoordinates) {
        Square currentSquare = GRID[vertical][horizontal];
        if (currentSquare.hasOppositeColorStone(stoneColor)) {
            flippableStoneCoordinates.add(new int[]{vertical, horizontal});
            int newVertical = vertical + direction[0];
            int newHorizontal = horizontal + direction[1];
            addFlippableStoneCoordinates(direction, newVertical, newHorizontal, stoneColor, flippableStoneCoordinates);
        }
    }

    public static int getSIDE_LENGTH() {
        return SIDE_LENGTH;
    }


    public boolean userWon(StoneColor stoneColor) {
        int userStoneCount = countStones(stoneColor);
        return (userStoneCount > 32);
    }

    public boolean isTied(StoneColor stoneColor) {
        int userStoneCount = countStones(stoneColor);
        return (userStoneCount == 32);
    }

    public int countStones(StoneColor stoneColor) {
        int playerStoneCount = 0;
        for (Square[] row : getGRID()) {
            for (Square square : row) {
                if (square.hasStone()) {
                    if (!square.hasOppositeColorStone(stoneColor))
                        playerStoneCount++;
                }
            }
        }
        return playerStoneCount;
    }

    public boolean isFull() {
        int stoneCounter = 0;
        int numberOfSquares = SIDE_LENGTH * SIDE_LENGTH;
        for (Square[] row : getGRID()) {
            for (Square square : row) {
                if (square.hasStone()) {
                    stoneCounter++;
                }
            }
        }
        return stoneCounter == numberOfSquares;
    }

    public boolean containsOnlyOneColorStone(){
        int whiteStoneCounter = 0;
        int blackStoneCounter = 0;
        for (Square[] row: getGRID()){
            for (Square square: row){
                if (square.hasStone()){
                    switch(square.getStoneColor()){
                        case WHITE -> whiteStoneCounter++;
                        case BLACK -> blackStoneCounter++;
                    }
                }
            }
        }
        return (whiteStoneCounter == 0 || blackStoneCounter == 0);
    }

    public void update(int[] coordinates, StoneColor stoneColor) {
        ArrayList<int[]> flippableStoneCoordinates = findFlippableStones(coordinates, stoneColor);
        placeStone(coordinates[0], coordinates[1], stoneColor);
        for (int[] flippableStoneCoordinate : flippableStoneCoordinates) {
            int stoneRow = flippableStoneCoordinate[0];
            int stoneColumn = flippableStoneCoordinate[1];
            getGRID()[stoneRow][stoneColumn].flipStone();
        }
    }


}