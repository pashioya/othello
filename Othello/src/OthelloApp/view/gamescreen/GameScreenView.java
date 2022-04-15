package OthelloApp.view.gamescreen;


import OthelloApp.model.StoneColor;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameScreenView extends BorderPane {
    private VBox vBoxTopTexts;
    private HBox hBoxBottomButtons;
    private Text playerScore;
    private Text computerScore;
    private Text turnInstruction;
    private Button rulesButton;
    private Button quitButton;
    private Button computerTurnButton;
    private Label clock;
    private GridPane grid;
    private Button[][] gridButtons;
    private final static Font TITLE_FONT = new Font("Consolas", 40);
    private final static Font BODY_FONT = new Font("Consolas", 15);

    public GameScreenView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        // create and configure controls
        this.vBoxTopTexts = new VBox();
        this.hBoxBottomButtons = new HBox();
        initializeGrid();
        this.playerScore = new Text("Player: ");
        this.computerScore = new Text("Computer: ");
        this.turnInstruction = new Text();
        this.rulesButton = new Button("Rules");
        this.computerTurnButton = new Button("Play Computer Turn");
        this.quitButton = new Button("Quit");
    }

    private void layoutNodes() {
        layoutVboxTop();
        this.grid.setPadding(new Insets(20));
        layoutHboxBottom();
        styleNodes();
    }

    public void layoutVboxTop() {
        HBox hBoxPlayerScores = new HBox();
        hBoxPlayerScores.getChildren().addAll(playerScore, computerScore);
        hBoxPlayerScores.setAlignment(Pos.CENTER);
        hBoxPlayerScores.setPadding(new Insets(10, 10, 10, 10));
        hBoxPlayerScores.setSpacing(100);
        vBoxTopTexts.getChildren().addAll(hBoxPlayerScores, turnInstruction);
        this.setTop(vBoxTopTexts);
        this.setMargin(vBoxTopTexts, new Insets(10, 10, 10, 10));
        vBoxTopTexts.setAlignment(Pos.CENTER);
    }

    public void layoutHboxBottom() {
        this.setBottom(hBoxBottomButtons);
        this.hBoxBottomButtons.setAlignment(Pos.CENTER);
        this.hBoxBottomButtons.getChildren().addAll(rulesButton, computerTurnButton, quitButton);
        this.hBoxBottomButtons.setPadding(new Insets(10, 10, 10, 10));
        this.setMargin(hBoxBottomButtons, new Insets(10, 10, 10, 10));
        this.hBoxBottomButtons.setSpacing(100);
    }

    public void styleNodes(){
        playerScore.setFont(BODY_FONT);
        computerScore.setFont(BODY_FONT);
        turnInstruction.setFont(BODY_FONT);
        rulesButton.setFont(BODY_FONT);
        computerTurnButton.setFont(BODY_FONT);
        quitButton.setFont(BODY_FONT);
        Font font = new Font("Consolas", 10);
    }

    Button setButtonBackgroundImage(Button button, String imageUrl) {
        int buttonWidth = 80;
        int buttonHeight = 80;
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
        // change so view doesn't see Board
        this.gridButtons = new Button[8][8];
        for (int row = 0; row < this.gridButtons.length; row++) {
            for (int column = 0; column < this.gridButtons[row].length; column++) {
                Button button = new Button();
                GridPane.setConstraints(button, column, row, 1, 1, HPos.CENTER, VPos.BASELINE, Priority.ALWAYS, Priority.ALWAYS);
                setButtonBackgroundImage(button, "empty.png");
                this.gridButtons[row][column] = button;
                grid.add(button, column, row);
            }
        }

    }

    void setClickableComputerTurnButton(boolean activePlayerIsComputer) {
        this.computerTurnButton.setDisable(!activePlayerIsComputer);
    }

    void disableAllGridButtons() {
        for (int row = 0; row < getGridButtons().length; row++) {
            for (int column = 0; column < getGridButtons()[row].length; column++) {
                Button button = getGridButtons()[row][column];
                button.setDisable(true);

            }
        }
    }


    public Text getPlayerScoreLabel() {
        return playerScore;
    }

    public Text getComputerScore() {
        return computerScore;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button[][] getGridButtons() {
        return gridButtons;
    }

    public Button getComputerTurnButton() {
        return computerTurnButton;
    }

    public Text getTurnInstruction() {
        return turnInstruction;
    }


}





