package OthelloApp.view.gamescreen;

import OthelloApp.model.GameSession;
import OthelloApp.view.endScreen.EndScreenStatPresenter;
import OthelloApp.view.endScreen.EndScreenStatView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import OthelloApp.model.*;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class GameScreenPresenter {

    private final GameSession model;
    private final GameScreenView view;

    public GameScreenPresenter(GameSession model, GameScreenView view) {
        this.model = model;
        this.view = view;
        System.out.println(this.model.getActivePlayer());
        System.out.println(this.model.getBoard());
        updatePlayerScores();
        addEventHandlers();
        if (this.model.activePlayerIsComputer()) {
            this.view.setClickableComputerTurnButton(true);
            view.disableAllGridButtons();
            view.getTurnInstructionLabel().setText("Click \"Play Computer Turn\" to make the computer place a stone.");
        } else {
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            this.view.setClickableComputerTurnButton(false);
            setClickableGridButtons(activePlayerColor);
            view.getTurnInstructionLabel().setText("Click a highlighted square to place a stone.");
        }
        drawBoard();
    }

    class ComputerTurnHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            int[] mostProfitableMove = model.findMostProfitableMove();
            ArrayList<int[]> flippableStoneCoordinates = model.playTurn(mostProfitableMove);
            setButtonImage(mostProfitableMove);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.switchActivePlayer();
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            if (model.isOver()) {
                showEndScreen();
            } else {
                if (model.getBoard().hasValidMoves(model.getActivePlayer().getPlayerColor())) {
                    view.getTurnInstructionLabel().setText("Click a highlighted square to place a stone");
                    setClickableGridButtons(activePlayerColor);
                } else {
                    view.getTurnInstructionLabel().setText("You cannot place a stone - Click \"Play Computer Move\" to make the Computer place a stone.");
                    model.switchActivePlayer();
                }
                view.setClickableComputerTurnButton(model.activePlayerIsComputer());
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
            ArrayList<int[]> flippableStoneCoordinates = model.playTurn(coordinates);
            setButtonImage(coordinates);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.switchActivePlayer();
            if (model.isOver()) {
                showEndScreen();
            } else {
                // If the Computer can play a turn, then disable the buttons so the user can't click on them, and enable the button that allows the computer to take a turn
                if (model.getBoard().hasValidMoves(model.getActivePlayer().getPlayerColor())) {
                    view.getTurnInstructionLabel().setText("Click \"Play Computer Turn\" to make the computer place a stone.");
                    view.disableAllGridButtons();
                    // If Computer can't, then switch back to user and let user pick another move
                } else {
                    view.getTurnInstructionLabel().setText("Computer unable to place a stone - Click another square to place a stone.");
                    model.switchActivePlayer();
                    StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
                    setClickableGridButtons(activePlayerColor);
                }
                view.setClickableComputerTurnButton(model.activePlayerIsComputer());
            }
        }

    }


    private void addEventHandlers() {
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int col = 0; col < view.getGridButtons()[row].length; col++) {
                view.getGridButtons()[row][col].setOnAction(new StoneHandler(row, col));
            }
        }
        view.getComputerTurnButton().setOnAction(new ComputerTurnHandler());
    }

    private void drawBoard() {
        for (int row = 0; row < model.getBoard().getGRID().length; row++) {
            for (int column = 0; column < model.getBoard().getGRID()[row].length; column++) {
                setButtonImage(new int[]{row, column});
            }
        }
    }

    private void setButtonImage(int[] coordinates) {
        Square square = this.model.getBoard().getGRID()[coordinates[0]][coordinates[1]];
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
        ArrayList<int[]> possibleMoves = this.model.getBoard().findAllPossibleMoves(stoneColor);
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

    public void updatePlayerScores() {
        Integer[] playerScores = this.model.getPlayerScores();
        for (int i = 0; i < playerScores.length; i++) {
            if (this.model.getPlayers()[i] instanceof User) {
                this.view.getPlayerScoreLabel().setText(String.format("Player: %d", playerScores[i]));
            } else if (this.model.getPlayers()[i] instanceof Computer) {
                this.view.getComputerScoreLabel().setText(String.format("Computer: %d", playerScores[i]));
            }
        }
    }

    private void showEndScreen() {
        EndScreenStatView endScreenView = new EndScreenStatView();
        EndScreenStatPresenter endScreenPresenter = new EndScreenStatPresenter(model, endScreenView);
        view.getScene().setRoot(endScreenView);
        endScreenView.getScene().getWindow().sizeToScene();
    }
}


