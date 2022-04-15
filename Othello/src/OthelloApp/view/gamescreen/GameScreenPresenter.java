package OthelloApp.view.gamescreen;

import OthelloApp.model.*;
import OthelloApp.view.endScreen.EndScreenStatPresenter;
import OthelloApp.view.endScreen.EndScreenStatView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import OthelloApp.model.GameSession.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameScreenPresenter {

    private final Game model;
    private final GameScreenView view;

    public GameScreenPresenter(Game model, GameScreenView view) {
        this.model = model;
        this.view = view;
        System.out.println(this.model.getActiveSession().getActivePlayer());
        System.out.println(this.model.getActiveSession().getBoard());
        updatePlayerScores();
        addEventHandlers();
        if (this.model.getActiveSession().activePlayerIsComputer()) {
            this.view.setClickableComputerTurnButton(true);
            view.disableAllGridButtons();
            view.getTurnInstruction().setText("Click \"Play Computer Turn\" to make the computer place a stone.");
        } else {
            StoneColor activePlayerColor = model.getActiveSession().getActivePlayer().getPlayerColor();
            this.view.setClickableComputerTurnButton(false);
            setClickableGridButtons(activePlayerColor);
            view.getTurnInstruction().setText("Click a highlighted square to place a stone.");
        }
        drawBoard();
    }

    class ComputerTurnHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            int[] mostProfitableMove = model.getActiveSession().findMostProfitableMove();
            ArrayList<int[]> flippableStoneCoordinates = model.getActiveSession().playTurn(mostProfitableMove);
            setButtonImage(mostProfitableMove);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.getActiveSession().switchActivePlayer();
            StoneColor activePlayerColor = model.getActiveSession().getActivePlayer().getPlayerColor();
            if (model.getActiveSession().isOver()) {
                view.disableAllGridButtons();
                showGameOverAlert();
            } else {
                if (model.getActiveSession().getBoard().hasValidMoves(activePlayerColor)) {
                    view.getTurnInstruction().setText("Click a highlighted square to place a stone.");
                    setClickableGridButtons(activePlayerColor);
                } else {
                    view.getTurnInstruction().setText("You cannot place a stone - Click \"Play Computer Move\" to make the Computer place a stone.");
                    model.getActiveSession().switchActivePlayer();
                }
                view.setClickableComputerTurnButton(model.getActiveSession().activePlayerIsComputer());
            }
        }
    }


    class StoneHandler implements EventHandler<ActionEvent> {
        private final int[] coordinates = new int[2];

        public StoneHandler(int row, int column) {
            this.coordinates[0] = row;
            this.coordinates[1] = column;
        }

        public void handle(ActionEvent event) {
            ArrayList<int[]> flippableStoneCoordinates = model.getActiveSession().playTurn(coordinates);
            setButtonImage(coordinates);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.getActiveSession().switchActivePlayer();
            if (model.getActiveSession().isOver()) {
                view.disableAllGridButtons();
                showGameOverAlert();
            } else {
                // If the Computer can play a turn, then disable the buttons so the user can't click on them, and enable the button that allows the computer to take a turn
                if (model.getActiveSession().getBoard().hasValidMoves(model.getActiveSession().getActivePlayer().getPlayerColor())) {
                    view.getTurnInstruction().setText("Click \"Play Computer Turn\" to make the computer place a stone.");
                    view.disableAllGridButtons();
                    // If Computer can't, then switch back to user and let user pick another move
                } else {
                    view.getTurnInstruction().setText("Computer unable to place a stone - Click another square to place a stone.");
                    model.getActiveSession().switchActivePlayer();
                    StoneColor activePlayerColor = model.getActiveSession().getActivePlayer().getPlayerColor();
                    setClickableGridButtons(activePlayerColor);
                }
                view.setClickableComputerTurnButton(model.getActiveSession().activePlayerIsComputer());
            }
        }

    }


    private void showGameOverAlert(){
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game over!");
        gameOverAlert.setHeaderText("Neither player can play any more turns");
        gameOverAlert.setContentText("Click \"Continue\" to view the results of your game.");
        gameOverAlert.getButtonTypes().clear();
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");
        gameOverAlert.getButtonTypes().addAll(continueButton, cancelButton);
        Optional<ButtonType> result = gameOverAlert.showAndWait();
        if (result.get() == continueButton){
            showEndScreen();
        } else {
            gameOverAlert.close();
        }
    }

    private void addEventHandlers() {
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int col = 0; col < view.getGridButtons()[row].length; col++) {
                view.getGridButtons()[row][col].setOnAction(new StoneHandler(row, col));
            }
        }
        view.getComputerTurnButton().setOnAction(new ComputerTurnHandler());
        view.getRulesButton().setOnAction(event -> {
            Alert rulesInfoAlert = createRulesInfoAlert();
            rulesInfoAlert.showAndWait();
        });
        view.getQuitButton().setOnAction(event -> {
            Alert quitGameAlert = new Alert(Alert.AlertType.WARNING);
            quitGameAlert.setTitle("Confirm quit game");
            quitGameAlert.setHeaderText("All progress in this game will be lost.");
            quitGameAlert.setContentText("Quit anyway?");
            quitGameAlert.getButtonTypes().clear();
            ButtonType continueButton = new ButtonType("Continue");
            ButtonType cancelButton = new ButtonType("Cancel");
            quitGameAlert.getButtonTypes().addAll(continueButton, cancelButton);
            Optional<ButtonType> result = quitGameAlert.showAndWait();
            if (result.get() == continueButton){
                Stage stage = (Stage)view.getScene().getWindow();
                stage.close();
            } else {
                event.consume();
            }
        });
    }
    public Alert createRulesInfoAlert(){
        Alert rulesInfoAlert = new Alert(Alert.AlertType.INFORMATION);
        rulesInfoAlert.setTitle("Othello Rules");
        rulesInfoAlert.setHeaderText("Rules");
        rulesInfoAlert.setContentText("Othello is a two-player game. One player plays black stones and the other player plays white stones. " +
                "The game begins with four stones (two white and two black) in the center of the board. The player that plays black stones makes the first move.\n\n" +
                "Players make moves by placing stone of their respective colors on the board. " +
                "A move is valid only if the placed stone outflanks an opposite-colored stone (or row of opposite-colored stones). " +
                "A stone or row of stones is outflanked when it is bordered by opposite-colored stones at each end. " +
                "Each player must outflank opposite-colored stones and flip them so they have the player's color. \n\n" +
                "If a player is not able to flip any stones, the player forfeits his/her turn and the other player plays again. " +
                "Players may not voluntarily forfeit a turn if a move is available.\n\n" +
                "The game is over when neither player can make a move. The player with the most stones of his/her color wins the game."
        );
        return rulesInfoAlert;
    }


    private void drawBoard() {
        for (int row = 0; row < model.getActiveSession().getBoard().getGRID().length; row++) {
            for (int column = 0; column < model.getActiveSession().getBoard().getGRID()[row].length; column++) {
                setButtonImage(new int[]{row, column});
            }
        }
    }

    private void setButtonImage(int[] coordinates) {
        Square square = this.model.getActiveSession().getBoard().getGRID()[coordinates[0]][coordinates[1]];
        Button button = this.view.getGridButtons()[coordinates[0]][coordinates[1]];
        String imageURL = getButtonImageURL(square);
        this.view.setButtonBackgroundImage(button, imageURL);
    }


    // PUT IN VIEW!
    private String getButtonImageURL(Square square) {
        if (!square.hasStone()) {
            return "empty.png";
        } else return switch (square.getStone().getColor()) {
            case WHITE -> "white.png";
            case BLACK -> "black.png";

        };
    }

    private void setClickableGridButtons(StoneColor stoneColor) {
        ArrayList<int[]> possibleMoves = this.model.getActiveSession().getBoard().findAllPossibleMoves(stoneColor);
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int column = 0; column < view.getGridButtons()[row].length; column++) {
                Button button = this.view.getGridButtons()[row][column];
                button.setDisable(!(buttonIsValidMove(row, column, possibleMoves)));
            }
        }
    }


    public boolean buttonIsValidMove(int buttonRow, int buttonColumn, ArrayList<int[]> possibleMoves) {
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
        for (int[] flippableStoneCoordinates : flippableStoneCoordinatesList) {
            setButtonImage(flippableStoneCoordinates);
        }
    }

    private void updatePlayerScores() {
        HashMap<Player, Integer> playerScores = this.model.getActiveSession().getPlayerScores();
        for (Map.Entry<Player, Integer> playerScore : playerScores.entrySet()) {
            if (playerScore.getKey() instanceof User) {
                this.view.getPlayerScoreLabel().setText(String.format("Player: %d", playerScore.getValue()));
            } else {
                this.view.getComputerScore().setText(String.format("Computer: %d", playerScore.getValue()));
            }
        }

    }

    private void showEndScreen() {
        EndScreenStatView endScreenView = new EndScreenStatView();
        EndScreenStatPresenter endScreenPresenter = new EndScreenStatPresenter(model, endScreenView);
        view.getScene().setRoot(endScreenView);
        endScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) endScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }


}


