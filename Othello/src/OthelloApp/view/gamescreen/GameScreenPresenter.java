package OthelloApp.view.gamescreen;

import OthelloApp.model.GameSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import OthelloApp.model.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;

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
            model.updateBoard(row, column, model.user.getPlayerColor());
            setButtonImage(row, column);

        }
    }

    private void addEventHandlers() {
        for (int row = 0; row < view.getGridButtons().length; row++) {
            for (int col = 0; col < view.getGridButtons()[row].length; col++) {
                view.getGridButtons()[row][col].setOnAction(new StoneHandler(row, col));
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
        return switch (square.getStone().getColor()) {
            case WHITE -> "white.png";
            case BLACK -> "black.png";
            default -> "empty.png";
        };
    }

    private void updateView() {
    }

}
