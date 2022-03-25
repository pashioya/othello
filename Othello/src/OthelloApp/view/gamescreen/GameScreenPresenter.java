package OthelloApp.view.gamescreen;

import OthelloApp.model.GameSession;
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
        this.model.getActivePlayer();
        System.out.println(this.model.getBoard());
        updatePlayerScores();
        this.view.setClickableComputerTurnButton(this.model.activePlayerIsComputer());

        addEventHandlers();
        StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
        setClickableGridButtons(activePlayerColor);
        drawBoard();
    }

    class ComputerTurnHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            ArrayList<int[]> possibleMoves = model.getBoard().findAllPossibleMoves(activePlayerColor);
            int[] mostProfitableMove = model.getBoard().findMostProfitableMove(possibleMoves, activePlayerColor);
            ArrayList<int[]> flippableStoneCoordinates = model.getBoard().findFlippableStones(mostProfitableMove[0], mostProfitableMove[1], activePlayerColor);
            model.updateBoard(mostProfitableMove[0], mostProfitableMove[1], activePlayerColor);
            setButtonImage(mostProfitableMove[0], mostProfitableMove[1]);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.switchActivePlayer();
            activePlayerColor = model.getActivePlayer().getPlayerColor();
            setClickableGridButtons(activePlayerColor);
            view.setClickableComputerTurnButton(model.activePlayerIsComputer());
        }
    }


    class StoneHandler implements EventHandler<ActionEvent> {
        private int row;
        private int column;

        public StoneHandler(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public void handle(ActionEvent event) {
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            ArrayList<int[]> flippableStoneCoordinates = model.getBoard().findFlippableStones(row, column, activePlayerColor);
            model.updateBoard(row, column, activePlayerColor);
            System.out.println(model.getBoard());
            setButtonImage(row, column);
            updateView(flippableStoneCoordinates);
            updatePlayerScores();
            model.switchActivePlayer();
            // If the Computer can play a turn, then disable the buttons so the user can't click on them, and enable the button that allows the computer to take a turn
            if (model.getBoard().hasValidMoves(model.getActivePlayer().getPlayerColor())) {
                view.disableAllGridButtons();
                view.setClickableComputerTurnButton(model.activePlayerIsComputer());
                // If Computer can't, then switch back to user and let user pick another move
            } else {
                model.switchActivePlayer();
                activePlayerColor = model.getActivePlayer().getPlayerColor();
                setClickableGridButtons(activePlayerColor);
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
                setButtonImage(row, column);
            }
        }
    }

    private void setButtonImage(int row, int column) {
        Square square = this.model.getBoard().getGRID()[row][column];
        Button button = this.view.getGridButtons()[row][column];
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
                if (buttonIsValidMove(row, column, possibleMoves)) {
                    button.setDisable(false);
                } else {
                    button.setDisable(true);
                }
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

    private void updateView(ArrayList<int[]> flippableStoneCoordinates) {
        for (int[] flippableStoneCoordinate : flippableStoneCoordinates) {
            setButtonImage(flippableStoneCoordinate[0], flippableStoneCoordinate[1]);
        }
    }

    public void updatePlayerScores(){
        Integer[] playerScores = this.model.getPlayerScores();
        for (int i = 0; i < playerScores.length; i++){
            if (this.model.getPlayers()[i] instanceof User) {
                this.view.getPlayerScoreLabel().setText(String.format("Player: %d", playerScores[i]));
            }
            else if (this.model.getPlayers()[i] instanceof Computer){
                this.view.getComputerScoreLabel().setText(String.format("Computer: %d", playerScores[i]));
            }
        }
    }


}


