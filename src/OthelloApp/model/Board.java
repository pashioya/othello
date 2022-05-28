package OthelloApp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static OthelloApp.model.StoneColor.BLACK;
import static OthelloApp.model.StoneColor.WHITE;

public class Board {
    private final Square[][] GRID;
    private final static int SIDE_LENGTH = 8;
    private final static int SPACING = 11;
    private final List<int[]> CORNERS;


    public Board() {
        this.GRID = new Square[SIDE_LENGTH][SIDE_LENGTH];
        this.CORNERS = defineCorners();
        initializeBoard();
    }

    private void initializeBoard() {
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

        for (int i = 0; i < SPACING + 3 * SIDE_LENGTH; i++) {
            builder.append("_");
        }
        builder.append("\n");
        String header = "|%" + (SPACING + 3 * SIDE_LENGTH) + "s";
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
        String footer = "|%" + (SPACING + 3 * SIDE_LENGTH) + "s";
        builder.append(String.format(footer, "|\n"));
        for (int i = 0; i < SPACING + 3 * SIDE_LENGTH; i++) {
            builder.append("-");
        }
    }

    //---------------TO STRING METHODS---------------


    private List<int[]> defineCorners(){
        int[] cornerTopLeft = {0, 0};
        int[] cornerTopRight = {0, 7};
        int[] cornerBottomLeft = {7, 0};
        int[] cornerBottomRight = {7, 7};
        List<int[]> corners = new ArrayList<>();
        corners.add(cornerTopLeft);
        corners.add(cornerTopRight);
        corners.add(cornerBottomLeft);
        corners.add(cornerBottomRight);
        return corners;
    }

    public Square[][] getGRID() {
        return GRID;
    }

    private boolean isValidMove(int row, int column, StoneColor stoneColor) {
        boolean isValid;

        // If the square at the selected coordinate already contains a square, the move isn't valid -> return false
        if (GRID[row][column].hasStone()) {
            return false;
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
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    private void placeStone(int row, int column, StoneColor stoneColor) {
        this.GRID[row][column].setStone(stoneColor);

    }

    private int[] getDirection(int vertical, int row, int horizontal, int column) {
        int verticalShift = vertical - row;
        int horizontalShift = horizontal - column;
        return new int[]{verticalShift, horizontalShift};
    }

    public ArrayList<int[]> findAllPossibleMoves(StoneColor stonecolor) {
        ArrayList<int[]> possibleMovesCoordinates = new ArrayList<>();
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
        return validMoves.size() != 0;
    }

    public int[] findMostProfitableMove(ArrayList<int[]> possibleMoves, StoneColor stoneColor) {
        int[] mostProfitableMove = null;
        int highestProfitability = 0;
        for (int[] possibleMove : possibleMoves) {
            int profitability = getMoveProfitability(possibleMove, stoneColor);
            System.out.println("Row: " + possibleMove[0] + " Column: " + possibleMove[1] + " - profitability " + profitability);
            if (profitability > highestProfitability) {
                mostProfitableMove = possibleMove;
                highestProfitability = profitability;
            }
        }
        return mostProfitableMove;
    }

    public int[] findLeastProfitableMove(ArrayList<int[]> possibleMoves, StoneColor stoneColor) {
        int[] leastProfitableMove = null;
        double lowestProfitability = Double.POSITIVE_INFINITY;
        for (int[] possibleMove : possibleMoves) {
            int profitability = getMoveProfitability(possibleMove, stoneColor);
            System.out.println("Row: " + possibleMove[0] + " Column: " + possibleMove[1] + " - profitability " + profitability);
            if (profitability < lowestProfitability) {
                leastProfitableMove = possibleMove;
                lowestProfitability = profitability;
            }
        }
        return leastProfitableMove;
    }

    public int getMoveProfitability(int[] possibleMove, StoneColor stoneColor) {
        ArrayList<int[]> flippableStoneList = findFlippableStones(possibleMove, stoneColor);
        return flippableStoneList.size();
    }

    private boolean checkDirection(int verticalShift, int horizontalShift, int vertical, int horizontal, StoneColor stoneColor) {
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

    public boolean coordinateCanFlipASide(int row, int column, StoneColor activePlayerColor, boolean sideIsHorizontal) {
        if (sideIsHorizontal) {
            return (
                    checkSideCanBeFlipped(row, column, activePlayerColor, true, true, false) ||
                            checkSideCanBeFlipped(row, column, activePlayerColor, true, false, false)
            );
        } else {
            System.out.println("Checking the vertical sides");
            return (
                    checkSideCanBeFlipped(row, column, activePlayerColor, false, false, true) ||
                            checkSideCanBeFlipped(row, column, activePlayerColor, false, false, false)
            );
        }
    }

    public int[] findCoordinateToProtectSide(int dangerousCoordinateRow, int dangerousCoordinateColumn, StoneColor activePlayerColor, boolean sideIsHorizontal) {
        System.out.println("findcoordinatetoprotectside is executed");
        int[] counterMove = null;
        if (sideIsHorizontal) {
            counterMove = findAvailableSquare(dangerousCoordinateRow, dangerousCoordinateColumn, activePlayerColor, true, true, false);
            if (counterMove != null) {
                System.out.println("Solution: " + counterMove[0] + ", " + counterMove[1]);
                return counterMove;
            }
            counterMove = findAvailableSquare(dangerousCoordinateRow, dangerousCoordinateColumn, activePlayerColor, true, false, false);
            if (counterMove != null) {
                System.out.println("Solution: " + counterMove[0] + ", " + counterMove[1]);
                return counterMove;
            }
        } else {
            counterMove = findAvailableSquare(dangerousCoordinateRow, dangerousCoordinateColumn, activePlayerColor, false, false, true);
            if (counterMove != null) {
                System.out.println("Solution: " + counterMove[0] + ", " + counterMove[1]);
                return counterMove;
            }
            counterMove = findAvailableSquare(dangerousCoordinateRow, dangerousCoordinateColumn, activePlayerColor, false, false, false);
            if (counterMove != null) {
                System.out.println("Solution: " + counterMove[0] + ", " + counterMove[1]);
                return counterMove;
            }
        }
        return null;
    }

    private int[] shiftCoordinate(int row, int column, boolean horizontal, boolean left, boolean down){
        int newColumn;
        int newRow;
        if (horizontal) {
            if (left) {
                newColumn = column - 1;
            } else {
                newColumn = column + 1;
            }
            newRow = row;
        } else {
            if (down) {
                newRow = row + 1;
            } else {
                newRow = row - 1;
            }
            newColumn = column;
        }
        return new int[] {newRow, newColumn};
    }


    public int[] findAvailableSquare(int dangerousCoordinateRow, int dangerousCoordinateColumn, StoneColor activePlayerColor, boolean horizontal, boolean left, boolean down) {
        System.out.println("What's going into this function: Row: " + dangerousCoordinateRow + " Column: " + dangerousCoordinateColumn);
        int[] newCoordinates = shiftCoordinate(dangerousCoordinateRow, dangerousCoordinateColumn, horizontal, left, down);
        int newRow = newCoordinates[0];
        int newColumn = newCoordinates[1];
        boolean coordinateIsSide = (newColumn == 7 || newColumn == 0 || newRow == 7 || newRow == 0);
        if (newColumn < 0 || newColumn > SIDE_LENGTH - 1 || newRow < 0 || newRow > SIDE_LENGTH - 1) {
            return null;
        }
        if (!coordinateIsSide) {
            System.out.println("Found an inappropriate solution: " + newRow + " " + newColumn);
            return null;
        }
        System.out.println("Trying " + newRow + ", " + newColumn);
        Square newSquare = getGRID()[newRow][newColumn];
        if (newSquare.hasStone()) {
            if (newSquare.hasOppositeColorStone(activePlayerColor)) {
                return findAvailableSquare(newRow, newColumn, activePlayerColor, horizontal, left, down);
            } else {
                return null;
            }
        } else {
            return new int[]{newRow, newColumn};
        }
    }

    public boolean checkSideCanBeFlipped(int row, int column, StoneColor activePlayerColor, boolean horizontal, boolean left, boolean down) {
        int[] newCoordinates = shiftCoordinate(row, column, horizontal, left, down);
        int newRow = newCoordinates[0];
        int newColumn = newCoordinates[1];
        System.out.println("Looking at " + newRow + " : " + newColumn);
        if (newColumn < 0 || newColumn > SIDE_LENGTH - 1 || newRow < 0 || newRow > SIDE_LENGTH - 1) {
            System.out.println("Out of bounds");
            return false;
        }
        Square newSquare = getGRID()[newRow][newColumn];
        if (newSquare.hasStone()) { // if the new square has a stone
            if (newSquare.hasOppositeColorStone(activePlayerColor)) { // if that stone has the opposite color
                // if black
                return false;
            } else { // if square has stone of same color
                System.out.println(newRow + ", " + newColumn + " has same color stone as computer");
                return checkSideCanBeFlipped(newRow, newColumn, activePlayerColor, horizontal, left, down);
            }
        } else { // if square has no stone - square is free
            System.out.println("Square is empty and can be taken");
            return true;
        }
    }

    public boolean coordinateCanReinforceCorner(int row, int column, StoneColor activePlayerColor, boolean horizontal, boolean left, boolean down){
        int[] newCoordinates = shiftCoordinate(row, column, horizontal, left, down);
        int newRow = newCoordinates[0];
        int newColumn = newCoordinates[1];
        System.out.println("Looking at for reinforcing corner " + newRow + " : " + newColumn);
        // if program passes corner without running into any blank spaces or opposite-colored stones, means that computer has the corner, and coordinate reinforces it
        if (newColumn < 0 || newColumn > SIDE_LENGTH - 1 || newRow < 0 || newRow > SIDE_LENGTH - 1) {
            System.out.println("Computer has the corner, so this coordinate reinforces the corner");
            return true;
        }
        Square newSquare = getGRID()[newRow][newColumn];
        if (newSquare.hasStone()) { // if the new square has a stone
            if (newSquare.hasOppositeColorStone(activePlayerColor)) { // if that stone has the opposite color
                // if black
                System.out.println(newRow + ", " + newColumn + " has opposite color stone as computer, stop checking");
                return false;
            } else { // if square has stone of same color
                System.out.println(newRow + ", " + newColumn + " has same color stone as computer, keep checking");
                return coordinateCanReinforceCorner(newRow, newColumn, activePlayerColor, horizontal, left, down);
            }
        } else { // if square has no stone - square is free
            System.out.println("Ran into empty space, stop checking");
            return false;
        }
    }

    public ArrayList<int[]> findFlippableStones(int[] coordinates, StoneColor stoneColor) {
        int row = coordinates[0];
        int column = coordinates[1];
        ArrayList<int[]> flippableStoneCoordinates = new ArrayList<>();
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

    private void addFlippableStoneCoordinates(int[] direction, int vertical, int horizontal, StoneColor stoneColor, ArrayList<int[]> flippableStoneCoordinates) {
        Square currentSquare = GRID[vertical][horizontal];
        if (currentSquare.hasOppositeColorStone(stoneColor)) {
            flippableStoneCoordinates.add(new int[]{vertical, horizontal});
            int newVertical = vertical + direction[0];
            int newHorizontal = horizontal + direction[1];
            addFlippableStoneCoordinates(direction, newVertical, newHorizontal, stoneColor, flippableStoneCoordinates);
        }
    }


    public boolean userWon(StoneColor stoneColor) {
        int userStoneCount = countStones(stoneColor);
        StoneColor oppositeColor = null;
        if (stoneColor == WHITE) {
            oppositeColor = BLACK;
        } else {
            oppositeColor = WHITE;
        }
        // user wins if she/he has more than 32 stones or the opponent has no more stones on the board
        return (userStoneCount > 32) || (countStones(oppositeColor) == 0);
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

    public void update(int[] coordinates, StoneColor stoneColor) {
        ArrayList<int[]> flippableStoneCoordinates = findFlippableStones(coordinates, stoneColor);
        placeStone(coordinates[0], coordinates[1], stoneColor);
        for (int[] flippableStoneCoordinate : flippableStoneCoordinates) {
            int stoneRow = flippableStoneCoordinate[0];
            int stoneColumn = flippableStoneCoordinate[1];
            getGRID()[stoneRow][stoneColumn].flipStone();
        }
    }



    public boolean coordinateIsCenter(int[] coordinates) {
        return (
                (coordinates[0] > 1) && (coordinates[0] < SIDE_LENGTH-2) && (coordinates[1] > 1) && (coordinates[1] < SIDE_LENGTH-2)
        );
    }

    public boolean coordinateIsCorner(int[] coordinates) {
        for (int[] corner : CORNERS) {
            if (Arrays.equals(corner, coordinates)){
                return Arrays.equals(corner, coordinates);
            }
        }
        return false;
    }

    public boolean userCanTakeSide(StoneColor computerColor) {
        boolean userCanTakeTopOrBottomSide = findDangerousTopBottomCoordinates(computerColor) != null;
        boolean userCanTakeLeftOrRightSide = findDangerousLeftRightCoordinates(computerColor) != null;
        System.out.println("danger sides " + userCanTakeLeftOrRightSide);
        if (userCanTakeTopOrBottomSide || userCanTakeLeftOrRightSide) {
            return true;
        }
        return false;
    }
    public boolean coordinateIsNotCornerAdjacent(int[] coordinates) {
        for (int[] corner : CORNERS) {
            for (int row = corner[0]-1; row <= corner[0]+1; row ++){ // go through every row in the radius of a corner
                for (int column = corner[1]-1; column <= corner[1]+1; column++){ // go through every column in the radius of a corner
                    if (row < 0 || row > SIDE_LENGTH-1 || column < 0 || column > SIDE_LENGTH -1 ){ // if out of bounds, do nothing
                        continue;
                    } else if (row == corner[0] && column == corner[1]){ // if the row and column are those of a corner, do nothing
                        continue;
                    } else {
                        if (Arrays.equals(coordinates, new int[] {row, column})){ // compare
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean coordinateIsSide(int[] coordinates, boolean isSafe) {
        if (isSafe) {
            return (
                    ((coordinates[0] == 0) && (coordinates[1] > 1) && (coordinates[1] < SIDE_LENGTH - 2)) || // top
                            ((coordinates[0] == SIDE_LENGTH - 1) && (coordinates[1] > 1) && (coordinates[1] < SIDE_LENGTH - 2)) || // bottom
                            ((coordinates[1] == 0) && (coordinates[0] > 1) && (coordinates[0] < SIDE_LENGTH - 2)) || // left
                            ((coordinates[1] == SIDE_LENGTH - 1) && (coordinates[0] > 1) && (coordinates[0] < SIDE_LENGTH - 2)) // right
            );
        }
        return (
                ((coordinates[0] == 0) && (coordinates[1] > 0) && (coordinates[1] < SIDE_LENGTH - 1)) || // top
                        ((coordinates[0] == SIDE_LENGTH - 1) && (coordinates[1] > 0) && (coordinates[1] < SIDE_LENGTH - 1)) || // bottom
                        ((coordinates[1] == 0) && (coordinates[0] > 0) && (coordinates[0] < SIDE_LENGTH - 1)) || // left
                        ((coordinates[1] == SIDE_LENGTH - 1) && (coordinates[0] > 0) && (coordinates[0] < SIDE_LENGTH - 1)) // right
        );
    }

    public ArrayList<int[]> findDangerousTopBottomCoordinates(StoneColor computerColor) {
        System.out.println("testing top and bottom sides");
        ArrayList<int[]> dangerousStoneCoordinates = new ArrayList<int[]>();
        ArrayList<Integer> sideRows = new ArrayList<>();
        sideRows.add(0);
        sideRows.add(7);
        for (Integer sideRow : sideRows) {
            for (int sideColumn = 2; sideColumn < 5; sideColumn++) {
                if (GRID[sideRow][sideColumn].hasStone() && GRID[sideRow][sideColumn + 1].hasStone()) {
                    if (
                            GRID[sideRow][sideColumn].hasOppositeColorStone(computerColor) &&
                                    (GRID[sideRow][sideColumn + 1].getStoneColor() == computerColor)
                    ) {
                        if (coordinateCanFlipASide(sideRow, sideColumn, computerColor, true)) {
                            System.out.println("Dangerous stone to the left of your piece");
                            System.out.println("Coordinate " + sideRow + ", " + sideColumn + " can take your side");
                            dangerousStoneCoordinates.add(new int[]{sideRow, sideColumn});
                        }
                    } else if (
                            GRID[sideRow][sideColumn].getStoneColor() == computerColor &&
                                    (GRID[sideRow][sideColumn + 1].hasOppositeColorStone(computerColor))
                    ) {
                        //sideRow, sideColumn, getActivePlayer().getPlayerColor(), true
                        if (coordinateCanFlipASide(sideRow, sideColumn + 1, computerColor, true)) {
                            System.out.println("Dangerous stone to the right of your piece");
                            System.out.println("Coordinate " + sideRow + ", " + (sideColumn + 1) + " can take your side");
                            dangerousStoneCoordinates.add(new int[]{sideRow, sideColumn + 1});
                        }
                    }
                }
            }
        }
        return dangerousStoneCoordinates;
    }

    public ArrayList<int[]> findDangerousLeftRightCoordinates(StoneColor computerColor) {
        ArrayList<int[]> dangerousStoneCoordinates = new ArrayList<>();
        ArrayList<Integer> sideColumns = new ArrayList<>();
        sideColumns.add(0);
        sideColumns.add(7);
        for (Integer sideColumn : sideColumns) {
            for (int sideRow = 2; sideRow < 5; sideRow++) {
                System.out.println("testing side: " + sideRow + " : " + sideColumn);
                System.out.println("testing next: " + (sideRow + 1) + " : " + sideColumn);
                if (GRID[sideRow][sideColumn].hasStone() && GRID[sideRow + 1][sideColumn].hasStone()) {
                    if (
                            GRID[sideRow][sideColumn].hasOppositeColorStone(computerColor) &&
                                    (GRID[sideRow + 1][sideColumn].getStoneColor() == computerColor)
                    ) {
                        if (coordinateCanFlipASide(sideRow, sideColumn, computerColor, false)) {
                            System.out.println("Dangerous stone above your piece");
                            System.out.println("Coordinate " + sideRow + ", " + sideColumn + " can take your side");
                            dangerousStoneCoordinates.add(new int[]{sideRow, sideColumn});
                        }
                    } else if (
                            GRID[sideRow][sideColumn].getStoneColor() == computerColor &&
                                    (GRID[sideRow + 1][sideColumn].hasOppositeColorStone(computerColor))
                    ) {
                        if (coordinateCanFlipASide(sideRow + 1, sideColumn, computerColor, false)) {
                            System.out.println("Dangerous stone below your piece");
                            System.out.println("Coordinate " + (sideRow + 1) + ", " + sideColumn + " can take your side");
                            dangerousStoneCoordinates.add(new int[]{sideRow + 1, sideColumn});
                        }
                    }
                }
            }
        }
        System.out.println(dangerousStoneCoordinates.size());
        return dangerousStoneCoordinates;
    }
}