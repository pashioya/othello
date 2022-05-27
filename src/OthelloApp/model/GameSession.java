package OthelloApp.model;

import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import java.text.SimpleDateFormat;
import java.util.*;

import static OthelloApp.model.rules.CanProtectSideAction.takeSideProtectingSquare;
import static OthelloApp.model.rules.CanProtectSideCondition.computerCanProtectSide;
import static OthelloApp.model.rules.CanReinforceCornerAction.takeCornerReinforcingSquare;
import static OthelloApp.model.rules.CanReinforceCornerCondition.computerCanReinforceCorner;
import static OthelloApp.model.rules.CenterMovesAvailableAction.takeCenterSquare;
import static OthelloApp.model.rules.CenterMovesAvailableCondition.computerCanTakeCenter;
import static OthelloApp.model.rules.CornerMovesAvailableAction.takeCornerSquare;
import static OthelloApp.model.rules.CornerMovesAvailableCondition.computerCanTakeCorner;
import static OthelloApp.model.rules.MaximizeProfitabilityAction.chooseMoveProfitableMove;
import static OthelloApp.model.rules.MinimizeProfitabilityAction.chooseLeastProfitableMove;
import static OthelloApp.model.rules.MovesAvailableCondition.computerHasAvailableMoves;
import static OthelloApp.model.rules.NotCornerAdjacentMovesAvailableAction.takeNonCornerAdjacentSquare;
import static OthelloApp.model.rules.NotCornerAdjacentMovesAvailableCondition.computerCanAvoidCornerAdjacent;
import static OthelloApp.model.rules.SafeSideMovesAvailableAction.takeSafeSideSquare;
import static OthelloApp.model.rules.SafeSideMovesAvailableCondition.computerCanTakeSafeSide;
import static OthelloApp.dataManager.DataManager.*;

public class GameSession {
    private final int idNo;
    private final Board board;
    private final Player[] players;
    private final String startDateTime;

    private Player activePlayer;
    private Turn activeTurn;
    private ArrayList<Turn> turns;

    private final long startTimeMilisec;
    private long endTimeMilisec;

    private final RulesEngine rulesEngine;
    private final Rules rules;

    public GameSession(boolean userGoesFirst, String userName, String difficultyMode) {
        this.board = new Board();
        this.players = new Player[2];
        initializePlayers(userGoesFirst, userName, difficultyMode);
        this.activePlayer = this.players[0];
        this.startDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(new Date());
        this.startTimeMilisec = System.currentTimeMillis();
        this.endTimeMilisec = 0;
        this.turns = new ArrayList<Turn>();
        this.rules = createRules();
        this.rulesEngine = createRulesEngine();
        createGameSessionsTable();
        this.idNo = createIdNo();
        this.activeTurn = new Turn(this.activePlayer.getName(), true);

    }

    public static void setMostProfitableMove(ArrayList<int[]> filteredMoves, Board board, StoneColor playerColor, Turn activeTurn, StringBuilder builder) {
        System.out.println("Items in moveslist:");
        for (int[] filteredMove : filteredMoves) {
            System.out.println("Row: " + filteredMove[0] + " column: " + filteredMove[1]);
        }
        Facts newFacts = new Facts();
        newFacts.put("possibleMoveList", filteredMoves);
        newFacts.put("board", board);
        newFacts.put("playerColor", playerColor);
        newFacts.put("turn", activeTurn);
        newFacts.put("moveExplanationBuilder", builder);
        Rules rules = new Rules();
        Rule mostProfitableMoveRule = new RuleBuilder()
                .name("profitable available moves rule")
                .when(computerHasAvailableMoves())
                .then(chooseMoveProfitableMove())
                .build();
        rules.register(mostProfitableMoveRule);
        RulesEngineParameters parameters = new RulesEngineParameters();
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);
        rulesEngine.fire(rules, newFacts);
    }

    public static void setLeastProfitableMove(ArrayList<int[]> filteredMoves, Board board, StoneColor playerColor, Turn activeTurn, StringBuilder builder) {
        Facts newFacts = new Facts();
        newFacts.put("possibleMoveList", filteredMoves);
        newFacts.put("board", board);
        newFacts.put("playerColor", playerColor);
        newFacts.put("turn", activeTurn);
        newFacts.put("moveExplanationBuilder", builder);
        Rules rules = new Rules();
        Rule mostProfitableMoveRule = new RuleBuilder()
                .name("unprofitable available moves rule")
                .when(computerHasAvailableMoves())
                .then(chooseLeastProfitableMove())
                .build();
        rules.register(mostProfitableMoveRule);
        RulesEngineParameters parameters = new RulesEngineParameters();
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);
        rulesEngine.fire(rules, newFacts);
    }

    // --------------------GETTERS AND SETTERS----------------------------


    public Turn getActiveTurn() {
        return activeTurn;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public StoneColor getActivePlayerColor(){
        return getActivePlayer().getPlayerColor();
    }

    //Add!
    public boolean activePlayerIsComputer() {
        return (activePlayer.isComputer());
    }

    public Player[] getPlayers() {
        return players;
    }
// add!
    public boolean isOver() {
        return neitherPlayerHasValidMoves();
    }

    public double getTimeElapsed() {
        return (double) (endTimeMilisec - startTimeMilisec) / 1000;
    }

    public Board getBoard() {
        return board;
    }

    public Square[][] getGrid() {
        return board.getGRID();
    }

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

    private int createIdNo() {
        return getNextGameSessionID();
    }

    private Rules createRules() {
        Rule cornerRule = new RuleBuilder()
                .name("corner rule")
                .priority(0)
                .when(computerCanTakeCorner())
                .then(takeCornerSquare())
                .build();
        Rule protectSideRule = new RuleBuilder()
                .name("protect side rule")
                .priority(1)
                .when(computerCanProtectSide())
                .then(takeSideProtectingSquare())
                .build();
        Rule reinforceCornerRule = new RuleBuilder()
                .name("reinforce corner rule")
                .priority(2)
                .when(computerCanReinforceCorner())
                .then(takeCornerReinforcingSquare())
                .build();
        Rule safeSideRule = new RuleBuilder()
                .name("safe side rule")
                .priority(3)
                .when(computerCanTakeSafeSide())
                .then(takeSafeSideSquare())
                .build();
        Rule centerRule = new RuleBuilder()
                .name("center rule")
                .priority(4)
                .when(computerCanTakeCenter())
                .then(takeCenterSquare())
                .build();
        Rule notCornerAdjacentRule = new RuleBuilder()
                .name("not corner adjacent rule")
                .priority(5)
                .when(computerCanAvoidCornerAdjacent())
                .then(takeNonCornerAdjacentSquare())
                .build();
        Rule mostProfitableMoveRule = new RuleBuilder()
                .name("available moves rule")
                .priority(6)
                .when(computerHasAvailableMoves())
                .then(chooseMoveProfitableMove())
                .build();
        Rules rules = new Rules();
        rules.register(cornerRule,protectSideRule, reinforceCornerRule, safeSideRule, centerRule, notCornerAdjacentRule, mostProfitableMoveRule);
        return rules;
    }

    private DefaultRulesEngine createRulesEngine() {
        RulesEngineParameters parameters = new RulesEngineParameters()
                .skipOnFirstAppliedRule(true);
        return new DefaultRulesEngine(parameters);
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

    public boolean neitherPlayerHasValidMoves() {
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
        System.out.println(activeTurn);
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
                setMostProfitableMove(possibleMoves, board, activePlayerColor, activeTurn, new StringBuilder());
                break;
            case "hard":
                Facts facts = new Facts();
                facts.put("possibleMoveList", possibleMoves);
                facts.put("board", board);
                facts.put("playerColor", activePlayer.getPlayerColor());
                facts.put("turn", activeTurn);
                facts.put("moveExplanationBuilder", new StringBuilder());
                rulesEngine.fire(rules, facts);
                break;
            default:
                Random random = new Random();
                chosenMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        }
        chosenMove = activeTurn.getPlacedCoordinate();
        return chosenMove;
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
        updateGameSessions(getIdNo(), getStartDateTime(), getTimeElapsed(), isOver(), userWon(), getUserName(), getComputerName(), getUserScore());
    }

    public int[] getTurnCoordinates(int turnID) {
        return getPlacedCoordinatesByTurnID(turnID);
    }

    public int getLastSessionNumberOfTurns() {
        return getNumberOfTurnsLastSession();
    }


}
