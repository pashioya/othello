package OthelloApp.view.gamescreen;


import OthelloApp.model.Board;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GameScreenView extends BorderPane {
    // private Node attributes (controls)
    private HBox hBoxTopLabels;
    private HBox hBoxBottomButtons;
    private Label playerScoreLabel;
    private Label computerScoreLabel;
    private Button rulesButton;
    private Button quitButton;
    private Label clock;
    private GridPane grid;
    private Button[][] gridButtons;

    public GameScreenView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        // create and configure controls
        this.hBoxTopLabels = new HBox();
        this.hBoxBottomButtons = new HBox();
        initializeGrid();
        this.playerScoreLabel = new Label("Player: ");
        this.computerScoreLabel = new Label("Computer: ");
        this.rulesButton = new Button("Rules");
        this.quitButton = new Button("Quit");
    }

    private void layoutNodes() {

        this.setTop(hBoxTopLabels);
        this.hBoxTopLabels.getChildren().addAll(playerScoreLabel, computerScoreLabel);
        this.hBoxTopLabels.setAlignment(Pos.CENTER);
        this.hBoxTopLabels.setPadding(new Insets(10, 10, 10, 10));
        this.setMargin(hBoxTopLabels, new Insets(10, 10, 10, 10));
        this.hBoxTopLabels.setSpacing(20);


        this.setBottom(hBoxBottomButtons);
        this.hBoxBottomButtons.setAlignment(Pos.CENTER);
        this.hBoxBottomButtons.getChildren().addAll(rulesButton, quitButton);
    }

    public Button setButtonBackgroundImage(Button button, String imageUrl) {
        int buttonWidth = 100;
        int buttonHeight = 100;
        Image image = new Image(imageUrl, buttonWidth, buttonHeight, false, true, true);

        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(buttonWidth - 20,
                        buttonWidth - 20,
                        true,
                        true,
                        true, false));
        Background backGround = new Background(backgroundImage);
        button.setPrefWidth(buttonWidth);
        button.setPrefHeight(buttonHeight);
        button.setBackground(backGround);
        return button;
    }

    private void initializeGrid() {
        this.grid = new GridPane();
        this.setCenter(grid);
//        this.grid.setGridLinesVisible(true);
        this.grid.setVgap(10);
        this.grid.setHgap(10);
        this.gridButtons = new Button[Board.getSIDE_LENGTH()][Board.getSIDE_LENGTH()];
        for (int row = 0; row < this.gridButtons.length; row++) {
            for (int column = 0; column < this.gridButtons[row].length; column++) {
                Button button = new Button();
                setButtonBackgroundImage(button, "empty.png");
                this.gridButtons[row][column] = button;
                grid.add(button, column, row);
            }
        }

    }


    public HBox getHBoxTopLabels() {
        return hBoxTopLabels;
    }

    public HBox getHBoxBottomButtons() {
        return hBoxBottomButtons;
    }

    public Label getPlayerScoreLabel() {
        return playerScoreLabel;
    }

    public Label getComputerScoreLabel() {
        return computerScoreLabel;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Label getClock() {
        return clock;
    }

    public GridPane getGrid() {
        return grid;
    }

    public Button[][] getGridButtons() {
        return gridButtons;
    }
}





