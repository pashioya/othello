package OthelloApp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import static OthelloApp.DBUtil.DBUtil.closeDbConnection;
import static OthelloApp.DBUtil.DBUtil.getStatement;
import static OthelloApp.dataManager.DataManager.createGameSessionsTable;

public class GameSession {
    private int idNo;
    private final Board board;
    private final Player[] players;
    private final String startDateTime;

    private Player activePlayer;
    private Turn activeTurn;
    private ArrayList<Turn> turns;

    private final long startTimeMilisec;
    private long endTimeMilisec;

    public GameSession(boolean userGoesFirst, String userName, String difficultyMode) {
        this.board = new Board();
        this.players = new Player[2];
        initializePlayers(userGoesFirst, userName, difficultyMode);
        this.activePlayer = this.players[0];
        this.startDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(new Date());
        this.startTimeMilisec = System.currentTimeMillis();
        this.endTimeMilisec = 0;
        this.turns = new ArrayList<Turn>();
        createGameSessionsTable();
        setIdNo();
        this.activeTurn = new Turn(this.activePlayer.getName(), true);

    }
    // --------------------GETTERS AND SETTERS----------------------------

    public Player getActivePlayer() {
        return activePlayer;
    }

    public boolean activePlayerIsComputer() {
        return (activePlayer.isComputer());
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean isOver() {
        return neitherPlayerHasValidMoves() ;
    }

    public double getTimeElapsed() {
        return (double) (endTimeMilisec - startTimeMilisec) / 1000;
    }

    public Board getBoard() {
        return board;
    }

    public Square[][] getGrid(){return board.getGRID();}

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getIdNo() {
        return idNo;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setEndTimeMilisec(long endTimeMilisec) {
        this.endTimeMilisec = endTimeMilisec;
    }

    private void setIdNo() {
        try {
            Statement statement = getStatement();
            ResultSet resultset = statement.executeQuery("SELECT nextval('seq_gamesession_id')");
            while (resultset.next()) {
                int idNo = resultset.getInt(1);
                this.idNo = idNo;
            }
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    // -----------------------------------------------------------------

    public boolean userWon() {
        for (Player player : players) {
            if (!player.isComputer()) {
                return board.userWon(player.getPlayerColor());
            }
        }
        return false;
    }

    public boolean neitherPlayerHasValidMoves(){
        return (!board.hasValidMoves(players[0].getPlayerColor())) && (!board.hasValidMoves(players[1].getPlayerColor()));
    }


    private void initializePlayers(boolean userGoesFirst, String userName, String difficultyMode) {
        if (userGoesFirst) {
            this.players[0] = new Player(StoneColor.BLACK, userName, "user");
            this.players[1] = new Player(StoneColor.WHITE, difficultyMode, "computer");
        } else {
            this.players[0] = new Player(StoneColor.BLACK, difficultyMode, "computer");
            this.players[1] = new Player(StoneColor.WHITE, userName, "user");
        }
    }

    private void updateBoard(int[] coordinates, StoneColor playerColor) {
        activeTurn.setPlacedCoordinate(coordinates);
        activeTurn.setFlippedStoneCoordinates(board.findFlippableStones(coordinates, playerColor));
        board.update(coordinates, playerColor);
    }



    public HashMap<Player, Integer> getPlayerScores() {
        HashMap<Player, Integer> playersScores = new HashMap<>();
        for (int i = 0; i < getPlayers().length; i++) {
            Player player = getPlayers()[i];
            int playerPoints = getBoard().countStones(player.getPlayerColor());
            playersScores.put(player, playerPoints);
        }
        return playersScores;
    }

    public int getUserScore() {
        HashMap<Player, Integer> playerScores = getPlayerScores();
        for (Map.Entry<Player, Integer> playerScore : playerScores.entrySet()) {
            if (!playerScore.getKey().isComputer()) {
                return playerScore.getValue();
            }
        }
        return 0;
    }

    public String getUserName() {
        for (Player player : players) {
            if (!player.isComputer()) {
                return player.getName();
            }
        }
        return null;
    }

    private String getComputerName() {
        for (Player player : players) {
            if (player.isComputer()) {
                return player.getName();
            }
        }
        return null;
    }

    public void switchActivePlayer() {
        this.activeTurn.setEndTime();
        this.activeTurn.save(getIdNo());
        if (getActivePlayer().equals(getPlayers()[0])) {
            setActivePlayer(getPlayers()[1]);
        } else {
            setActivePlayer(getPlayers()[0]);
        }
        createNewTurn(getActivePlayer());
    }


    private void createNewTurn(Player player) {
        this.turns.add(this.activeTurn);
        this.activeTurn = new Turn(player.getName(), false);
    }

    public int[] chooseMove() {
        int[] chosenMove = null;
        StoneColor activePlayerColor = getActivePlayer().getPlayerColor();
        ArrayList<int[]> possibleMoves = getBoard().findAllPossibleMoves(activePlayerColor);
        switch (getActivePlayer().getName()) {
            case "medium":
                chosenMove = getBoard().findMostProfitableMove(possibleMoves, activePlayerColor);
                break;
            case "hard":
                ArrayList<int[]> filteredMoves = filterPossibleMoves(possibleMoves);
                chosenMove = getBoard().findMostProfitableMove(filteredMoves, activePlayerColor);
                System.out.println("Chosen move: " + chosenMove[0] + " " + chosenMove[1]);
                break;
            default:
                Random random = new Random();
                chosenMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        }
        return chosenMove;
    }

    private boolean coordinateIsCorner(int[] coordinates) {
        int[] cornerTopLeft = {0, 0};
        int[] cornerTopRight = {0, 7};
        int[] cornerBottomLeft = {7, 0};
        int[] cornerBottomRight = {7, 7};
        return (Arrays.equals(cornerTopLeft, coordinates) || Arrays.equals(cornerTopRight, coordinates) || Arrays.equals(cornerBottomLeft, coordinates) || Arrays.equals(cornerBottomRight, coordinates));
    }

    private boolean coordinateIsSafeSide(int[] coordinates) {
        return (
                ((coordinates[0] == 0) && (coordinates[1] > 1) && (coordinates[1] < 6)) ||
                        ((coordinates[0] == 7) && (coordinates[1] > 1) && (coordinates[1] < 6)) ||
                        ((coordinates[1] == 0) && (coordinates[0] > 1) && (coordinates[0] < 6)) ||
                        ((coordinates[1] == 7) && (coordinates[0] > 1) && (coordinates[0] < 6))
        );
    }

    private boolean coordinateIsCenter(int[] coordinates) {
        return (
                (coordinates[0] > 1) && (coordinates[0] < 6) && (coordinates[1] > 1) && (coordinates[1] < 6)
        );
    }

    private boolean coordinateIsNotDangerZone(int[] coordinates){
        List<int[]> dangerZoneTopLeft = List.of(new int[]{1,0}, new int[]{1,1}, new int[]{0,1});
        List<int[]> dangerZoneTopRight = List.of(new int[]{1,6}, new int[]{0,6}, new int[]{1,7});
        List<int[]> dangerZoneBottomLeft = List.of(new int[]{6,1}, new int[]{6,1}, new int[]{7,1});
        List<int[]> dangerZoneBottomRight = List.of(new int[]{7,6}, new int[]{6,7}, new int[]{6,6});
        List<List<int[]>> dangerZones = new ArrayList<List<int[]>>();
        dangerZones.add(dangerZoneTopLeft);
        dangerZones.add(dangerZoneTopRight);
        dangerZones.add(dangerZoneBottomLeft);
        dangerZones.add(dangerZoneBottomRight);
        for (List<int[]> dangerZone : dangerZones){
            for (int[] dangerousCoordinate : dangerZone){
                if (Arrays.equals(dangerousCoordinate, coordinates)){
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<int[]> filterPossibleMoves(ArrayList<int[]> possibleMoves) {
        ArrayList<int[]> filteredMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsCorner(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            System.out.println("Corners: ");
            for (int[] filteredMove : filteredMoves) {
                System.out.println(filteredMove[0] + " " + filteredMove[1]);
            }
            return filteredMoves;
        }
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsSafeSide(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            System.out.println("Sides: ");
            for (int[] filteredMove : filteredMoves) {
                System.out.println(filteredMove[0] + " " + filteredMove[1]);
            }
            return filteredMoves;
        }
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsCenter(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            System.out.println("Center: ");
            for (int[] filteredMove : filteredMoves) {
                System.out.println(filteredMove[0] + " " + filteredMove[1]);
            }
            return filteredMoves;
        }
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsNotDangerZone(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            System.out.println("Not safe zone: ");
            for (int[] filteredMove : filteredMoves) {
                System.out.println(filteredMove[0] + " " + filteredMove[1]);
            }
            return filteredMoves;
        }
        return possibleMoves;
    }
    public String explainComputerMoveDecision(ArrayList<int[]> possibleMoves) {
        ArrayList<int[]> filteredMoves = new ArrayList<>();
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsCorner(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            return createComputerMoveExplanation("Computer found available corner move(s): ", filteredMoves);
        }
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsSafeSide(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            return createComputerMoveExplanation("Computer could not place a stone in a corner, but found available side move(s): ", filteredMoves);
        }
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsCenter(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            return createComputerMoveExplanation("Computer could not place a stone in a corner or side, but found available center move(s): ", filteredMoves);
        }
        for (int[] possibleMove : possibleMoves) {
            if (coordinateIsNotDangerZone(possibleMove)) {
                filteredMoves.add(possibleMove);
            }
        }
        if (!filteredMoves.isEmpty()) {
            return createComputerMoveExplanation("Computer could not place a stone in a corner, side, or center, but found available non-corner-adjacent move(s): ", filteredMoves);
        }
        return createComputerMoveExplanation("Computer only found corner-adjacent move(s): ", possibleMoves);
    }

    private String createComputerMoveExplanation(String header, ArrayList<int[]> filteredMoves){
        StringBuilder builder = new StringBuilder();
        builder.append(header);
        for (int[] filteredMove : filteredMoves) {
            int profitability = board.getMoveProfitability(filteredMove, getActivePlayer().getPlayerColor());
            builder.append("\nRow " + filteredMove[0] + ", column " + filteredMove[1] + " flips " + profitability + " stone(s).");
        }
        int[] chosenMove = getBoard().findMostProfitableMove(filteredMoves, getActivePlayer().getPlayerColor());
        builder.append("\nComputer played a stone at row " + chosenMove[0] + ", column " + chosenMove[1] + ".");
        return builder.toString();
    }

    public ArrayList<int[]> updateStones(int[] moveCoordinates) {
        StoneColor activePlayerColor = getActivePlayer().getPlayerColor();
        ArrayList<int[]> flippableStoneCoordinates = getBoard().findFlippableStones(moveCoordinates, activePlayerColor);
        updateBoard(moveCoordinates, activePlayerColor);
        System.out.println(getBoard());
        updateGameSessionsTable();
        return flippableStoneCoordinates;
    }

    private void updateGameSessionsTable() {
        setEndTimeMilisec(System.currentTimeMillis());
        try {
            Statement statement = getStatement();
            statement.executeUpdate("INSERT INTO gamesessions (gamesession_id, start_date_time, time_elapsed, is_over, user_won, username, computer_name, number_stones_user) " +
                    "VALUES ("
                    + getIdNo() + ", '"
                    + getStartDateTime() + "', "
                    + getTimeElapsed() + ", "
                    + isOver() + ", "
                    + userWon() + ", '"
                    + getUserName() + "', '"
                    + getComputerName() + "', "
                    + getUserScore() + " " +
                    ") " +
                    "ON CONFLICT (gamesession_id) DO UPDATE " +
                    "SET  time_elapsed= EXCLUDED.time_elapsed, " +
                    "       is_over = EXCLUDED.is_over, " +
                    "       user_won = EXCLUDED.user_won, " +
                    "       number_stones_user = EXCLUDED.number_stones_user ");
            closeDbConnection();
        } catch (SQLException e) {
            e.printStackTrace(); // error handling
        }
    }

    public int[] getTurnCoordinates(int turnID) {
        int[] coordinates = new int[2];
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT placed_stone_row, placed_stone_column " +
                    "FROM turns " +
                    "WHERE gamesession_id = (" +
                    "                       SELECT MAX(gamesession_id) " +
                    "                       FROM gamesessions " +
                    "                       WHERE is_over = True) " +
                    "AND turn_id = " + turnID + ";");
            while (resultSet.next()) {
                int placedStoneRow = resultSet.getInt(1);
                int placedStoneColumn = resultSet.getInt(2);
                if (!resultSet.wasNull()) {
                    coordinates[0] = placedStoneRow;
                    coordinates[1] = placedStoneColumn;
                } else {
                    coordinates = null;
                }
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return coordinates;
    }

    public int getLastSessionNumberOfTurns() {
        int maxNumberOfTurns = 0;
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) " +
                    "FROM turns " +
                    "WHERE gamesession_id = (" +
                    "                       SELECT MAX(gamesession_id) " +
                    "                       FROM gamesessions " +
                    "                       WHERE is_over = True) " +
                    ";");
            while (resultSet.next()) {
                maxNumberOfTurns = resultSet.getInt(1);
            }
            closeDbConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return maxNumberOfTurns;
    }
}
