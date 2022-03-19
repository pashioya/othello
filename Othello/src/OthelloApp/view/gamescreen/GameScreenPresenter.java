package OthelloApp.view.gamescreen;

import OthelloApp.model.GameSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import OthelloApp.model.*;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameScreenPresenter {

    private final GameSession model;
    private final GameScreenView view;

    public GameScreenPresenter(GameSession model, GameScreenView view) {
        this.model = model;
        this.view = view;
        System.out.println(this.model.getActivePlayer());
        addEventHandlers();

        //refactor as drawBoard()
        StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
        setClickableButtons(activePlayerColor);
        updateView();
    }

    class StoneHandler implements EventHandler<ActionEvent> {
        private int row;
        private int column;

        public StoneHandler(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public void handle(ActionEvent event) {
            System.out.println("Clicked " + row + " " + column);
            StoneColor activePlayerColor = model.getActivePlayer().getPlayerColor();
            ArrayList<int[]> flippableStoneCoordinates = model.getBoard().findFlippableStones(row, column, activePlayerColor);
            model.updateBoard(row, column, activePlayerColor);
            System.out.println(model.getBoard());
            setButtonImage(row, column);
            updateViewFlippedPieces(flippableStoneCoordinates);
            switchActivePlayer();
            activePlayerColor = model.getActivePlayer().getPlayerColor();
            if (model.getBoard().hasValidMoves(activePlayerColor)){
                ArrayList<int[]> possibleMoves = model.getBoard().findAllPossibleMoves(activePlayerColor);
                int[] mostProfitableMove = model.getBoard().findMostProfitableMove(possibleMoves, activePlayerColor);
                flippableStoneCoordinates = model.getBoard().findFlippableStones(mostProfitableMove[0], mostProfitableMove[1], activePlayerColor);
                model.updateBoard(mostProfitableMove[0], mostProfitableMove[1], activePlayerColor);
                System.out.println(model.getBoard());
                setButtonImage(mostProfitableMove[0], mostProfitableMove[1]);
                updateViewFlippedPieces(flippableStoneCoordinates);

            }
            else{
                System.out.println("Turn forfeited!");
            }
            switchActivePlayer();
            activePlayerColor = model.getActivePlayer().getPlayerColor();
            setClickableButtons(activePlayerColor);
        }
    }



    public void switchActivePlayer(){
        if (this.model.getActivePlayer().equals(this.model.getPlayers()[0])){
            this.model.setActivePlayer(this.model.getPlayers()[1]);
        } else {
            this.model.setActivePlayer(this.model.getPlayers()[0]);
        }

    }

    private void addEventHandlers() {
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int col = 0; col < view.getGridButtons()[row].length; col++) {
                view.getGridButtons()[row][col].setOnAction(new StoneHandler(row, col));
            }

        }
    }

    private void updateView() {
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

    private void setClickableButtons(StoneColor stoneColor) {
        ArrayList<int[]> possibleMoves = this.model.getBoard().findAllPossibleMoves(stoneColor);
        System.out.println(possibleMoves.size());
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int column = 0; column < view.getGridButtons()[row].length; column++) {
                Button button = this.view.getGridButtons()[row][column];
                if (buttonIsValidMove(row, column, possibleMoves)) {
                    button.setDisable(false);
                }
                else {
                    button.setDisable(true);
                }
            }
        }
    }

    public boolean buttonIsValidMove(int buttonRow, int buttonColumn, ArrayList<int[]> possibleMoves){
        for (int[] possibleMove : possibleMoves) {
            int possibleMoveRow = possibleMove[0];
            int possibleMoveColumn = possibleMove[1];
            if ((buttonRow == possibleMoveRow) & (buttonColumn == possibleMoveColumn)){
                return true;
            }
        }
        return false;
    }

    private void updateViewFlippedPieces(ArrayList<int[]> flippableStoneCoordinates) {
        for (int[] flippableStoneCoordinate : flippableStoneCoordinates) {
            setButtonImage(flippableStoneCoordinate[0], flippableStoneCoordinate[1]);
        }
    }

    public void delayOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}


