package OthelloApp.view.gamescreen;

import OthelloApp.model.GameSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import OthelloApp.model.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameScreenPresenter {

    private final GameSession model;
    private final GameScreenView view;

    public GameScreenPresenter(GameSession model, GameScreenView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
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
            ArrayList<int[]> flippableStoneCoordinates = model.getBoard().findFlippableStones(row, column, model.user.getPlayerColor());
            model.updateBoard(row, column, model.user.getPlayerColor());
            System.out.println(model.getBoard());
            setButtonImage(row, column);
            setClickableButtons();
            updateViewFlippedPieces(row, column, flippableStoneCoordinates);

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

    private String getButtonImageURL(Square square) {
        if (!square.hasStone()){
            return "empty.png";
        }
        else return switch (square.getStone().getColor()) {
            case WHITE -> "white.png";
            case BLACK -> "black.png";

        };
    }

    private void setClickableButtons(){
        ArrayList<int[]> findAllPossibleMoves = this.model.user.findAllPossibleMoves(this.model.getBoard());
        System.out.println(findAllPossibleMoves.size());
        for (int[] possibleMove : findAllPossibleMoves) {
            Button button = this.view.getGridButtons()[possibleMove[0]][possibleMove[1]];
            button.setDisable(false);
        }
    }

    private void updateViewFlippedPieces(int row, int column, ArrayList<int[]> flippableStoneCoordinates){
        for (int[] flippableStoneCoordinate : flippableStoneCoordinates) {
            setButtonImage(flippableStoneCoordinate[0], flippableStoneCoordinate[1]);
        }
    }

}


