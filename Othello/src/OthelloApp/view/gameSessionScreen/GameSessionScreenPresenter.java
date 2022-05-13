package OthelloApp.view.gameSessionScreen;

import OthelloApp.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static OthelloApp.alert_creation.AlertCreation.*;
import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.showChooseColorScreen;
import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.showGameSessionStatisticsScreen;

public class GameSessionScreenPresenter {
    private final GameSession model;
    private final GameSessionScreenView view;

    public GameSessionScreenPresenter(GameSession model, GameSessionScreenView view) {
        this.model = model;
        this.view = view;
        System.out.println(this.model.getBoard());
        this.view.setClickableExplainComputerMoveButton(false);
        updatePlayerScores();
        addEventHandlers();
        if (view.isReplay()) {
            this.view.setClickableTurnButton(true);
            disableAllGridButtons();
            view.getTurnInstruction().setText("Click \"View Next Turn\" to replay the next move in the last game session.");
        } else {
            System.out.println(this.model.activePlayerIsComputer());
            if (this.model.activePlayerIsComputer()) {
                this.view.setClickableTurnButton(true);
                disableAllGridButtons();
                view.getTurnInstruction().setText("Click \"Play Computer Turn\" to make the computer place a stone.");
            } else {
                StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
                this.view.setClickableTurnButton(false);
                setClickableGridButtons(activePlayerColor);
                view.getTurnInstruction().setText("Click a highlighted square to place a stone.");
            }
        }
        drawBoard();
    }

    class ComputerTurnHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            int[] mostProfitableMove = model.chooseMove();
            if (mostProfitableMove != null) {
                setComputerTurnExplanationAlert();
                ArrayList<int[]> flippableStoneCoordinates = model.updateStones(mostProfitableMove);
                view.setClickableTurnButton(!model.activePlayerIsComputer());
                setButtonImage(mostProfitableMove);
                updateView(flippableStoneCoordinates);
                updatePlayerScores();
            };
            model.switchActivePlayer();
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            if (model.isOver()) {
                disableAllGridButtons();
                showGameOverAlert(view);
            } else {
                if (model.getBoard().hasValidMoves(activePlayerColor)) {
                    view.getTurnInstruction().setText("Click a highlighted square to place a stone.");
                    setClickableGridButtons(activePlayerColor);
                } else {
                    view.getTurnInstruction().setText("You cannot place a stone - Click \"Play Computer Move\" to make the Computer place a stone.");
                    disableAllGridButtons();
                    model.switchActivePlayer();
                }
                view.setClickableTurnButton(model.activePlayerIsComputer());
                view.setClickableExplainComputerMoveButton(true);
            }
        }
    }


    class StoneHandler implements EventHandler<ActionEvent> {
        private final int[] coordinates = new int[2];

        private StoneHandler(int row, int column) {
            this.coordinates[0] = row;
            this.coordinates[1] = column;
        }

        public void handle(ActionEvent event) {
            ArrayList<int[]> flippableStoneCoordinates = model.updateStones(coordinates);
            setButtonImage(coordinates);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.switchActivePlayer();
            if (model.isOver()) {
                disableAllGridButtons();
                showGameOverAlert(view);
            } else {
                beginComputerTurn();
                view.setClickableTurnButton(model.activePlayerIsComputer());
                view.setClickableExplainComputerMoveButton(!model.activePlayerIsComputer());
            }
        }
    }

    private void beginComputerTurn(){
        // If the Computer can play a turn, then disable the buttons so the user can't click on them, and enable the button that allows the computer to take a turn
        if (model.getBoard().hasValidMoves(model.getActivePlayer().getPlayerColor())) {
            view.getTurnInstruction().setText("Click \"Play Computer Turn\" to make the computer place a stone.");
            disableAllGridButtons();
            // If Computer can't, then switch back to user and let user pick another move
        } else {
            view.getTurnInstruction().setText("Computer unable to place a stone - Click another square to place a stone.");
            model.switchActivePlayer();
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            setClickableGridButtons(activePlayerColor);
        }
    }

    private void setComputerTurnExplanationAlert(){
        StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
        ArrayList<int[]> possibleMoves = model.getBoard().findAllPossibleMoves(activePlayerColor);
        String explanation = model.explainComputerMoveDecision(possibleMoves);
        view.getExplainComputerMoveButton().setOnAction(innerEvent -> {
            createComputerMoveExplanationAlert(explanation);
        });
    }

    class ReplayHandler implements EventHandler<ActionEvent> {
        private static int turnCount = 0;

        public void handle(ActionEvent event) {
            if (turnCount == model.getLastSessionNumberOfTurns()) {
                turnCount = 0;
            }
            int[] coordinates = model.getTurnCoordinates(turnCount);
            StoneColor stoneColor = (turnCount % 2 == 0) ? StoneColor.BLACK : StoneColor.WHITE;
            System.out.println(turnCount);
            if (coordinates != null) {
                ArrayList<int[]> flippableStoneCoordinates = model.getBoard().findFlippableStones(coordinates, stoneColor);
                model.getBoard().update(coordinates, stoneColor);
                setButtonImage(coordinates);
                updateView(flippableStoneCoordinates);
                updatePlayerScores();
                System.out.println(model.getBoard());
                view.setButtonOpacity(coordinates[0], coordinates[1], model.getGrid()[coordinates[0]][coordinates[1]].hasStone(), false);
            }
            turnCount++;
            if (turnCount == model.getLastSessionNumberOfTurns()) {
                view.getTurnButton().setDisable(true);
            }
        }
    }

    void disableAllGridButtons() {
        for (int row = 0; row < model.getGrid().length; row++) {
            for (int column = 0; column < model.getGrid()[row].length; column++) {
                view.disableButton(row, column);
                view.setButtonOpacity(row, column, model.getGrid()[row][column].hasStone(), false);
            }
        }
    }

    private void addEventHandlers() {
        if (!view.isReplay()) {
            for (int row = 0; row < view.getGridButtons().length; row++) {
                for (int col = 0; col < view.getGridButtons()[row].length; col++) {
                    view.getGridButtons()[row][col].setOnAction(new StoneHandler(row, col));
                }
            }
            view.getTurnButton().setOnAction(new ComputerTurnHandler());
            view.getBackButton().setOnAction(event -> {
                showChooseColorScreen(view);
            });
            view.getQuitButton().setOnAction(event -> {
                showQuitGameAlert(event, view);
            });
        } else {
            view.getTurnButton().setOnAction(new ReplayHandler());
            view.getBackButton().setOnAction(event -> {
                showGameSessionStatisticsScreen(view);
            });
            view.getQuitButton().setOnAction(event -> {
                showExitAlert(event, view);
            });
        }
        view.getRulesButton().setOnAction(event -> {
            Alert rulesInfoAlert = createRulesInfoAlert();
            rulesInfoAlert.showAndWait();
        });

    }




    private void drawBoard() {
        for (int row = 0; row < model.getGrid().length; row++) {
            for (int column = 0; column < model.getGrid()[row].length; column++) {
                setButtonImage(new int[]{row, column});
            }
        }
    }

    private void setButtonImage(int[] coordinates) {
        Square square = this.model.getGrid()[coordinates[0]][coordinates[1]];
        Button button = this.view.getGridButtons()[coordinates[0]][coordinates[1]];
        String imageURL = getButtonImageURL(square);
        this.view.setButtonBackgroundImage(button, imageURL);
    }


    private String getButtonImageURL(Square square) {
        if (!square.hasStone()) {
            return "empty.png";
        } else return switch (square.getStone().getColor()) {
            case WHITE -> "white.png";
            case BLACK -> "black.png";
        };
    }

    private void setClickableGridButtons(StoneColor stoneColor) {
        ArrayList<int[]> possibleMoves = this.model.getBoard().findAllPossibleMoves(stoneColor);
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int column = 0; column < view.getGridButtons()[row].length; column++) {
                Button button = this.view.getGridButtons()[row][column];
                button.setDisable(!(buttonIsValidMove(row, column, possibleMoves)));
                view.setButtonOpacity(row, column, model.getGrid()[row][column].hasStone(), buttonIsValidMove(row, column, possibleMoves));
            }
        }
    }


    private boolean buttonIsValidMove(int buttonRow, int buttonColumn, ArrayList<int[]> possibleMoves) {
        for (int[] possibleMove : possibleMoves) {
            int possibleMoveRow = possibleMove[0];
            int possibleMoveColumn = possibleMove[1];
            if ((buttonRow == possibleMoveRow) & (buttonColumn == possibleMoveColumn)) {
                return true;
            }
        }
        return false;
    }

    private void updateView(ArrayList<int[]> flippableStoneCoordinatesList) {
        final double imageTransitionDuration = 2000;
        for (int[] flippableStoneCoordinates : flippableStoneCoordinatesList) {
            fadeTransition(flippableStoneCoordinates, imageTransitionDuration);
        }
    }

    private void fadeTransition(int[] flippableStoneCoordinates, double imageTransitionDuration) {
        Square square = model.getGrid()[flippableStoneCoordinates[0]][flippableStoneCoordinates[1]];
        String URL = getButtonImageURL(square);
        Button button = view.getGridButtons()[flippableStoneCoordinates[0]][flippableStoneCoordinates[1]];
        view.fadeBetweenImages(button, URL, imageTransitionDuration);
    }

    private void updatePlayerScores() {
        HashMap<Player, Integer> playerScores = this.model.getPlayerScores();
        for (Map.Entry<Player, Integer> playerScore : playerScores.entrySet()) {
            if (playerScore.getKey().isComputer()) {
                this.view.getComputerScore().setText(String.format("Computer: %d", playerScore.getValue()));
            } else {
                this.view.getPlayerScoreLabel().setText(String.format("Player: %d", playerScore.getValue()));
            }
        }
    }
}


