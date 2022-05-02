package OthelloApp.view.gameSessionScreen;


import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameSessionScreenView extends BorderPane {
    private VBox vBoxTopTexts;
    private HBox hBoxBottomButtons;
    private Text playerScore;
    private Text computerScore;
    private Text turnInstruction;
    private Button rulesButton;
    private Button quitButton;
    private Button computerTurnButton;
    private Text timer;
    private GridPane grid;
    private Button[][] gridButtons;
    private final static Font BODY_FONT = new Font("Consolas", 15);
    private final long startMilliseconds = System.currentTimeMillis();

    public GameSessionScreenView() {
        initialiseNodes();
        layoutNodes();
        updateTimer();
    }

    private void initialiseNodes() {
        // create and configure controls
        this.vBoxTopTexts = new VBox();
        this.hBoxBottomButtons = new HBox();
        initializeGrid();
        this.timer = new Text();
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
        hBoxPlayerScores.getChildren().addAll(timer, playerScore, computerScore);
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

    public void styleNodes() {
        grid.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));
        timer.setFont(BODY_FONT);
        playerScore.setFont(BODY_FONT);
        computerScore.setFont(BODY_FONT);
        turnInstruction.setFont(BODY_FONT);
        rulesButton.setFont(BODY_FONT);
        computerTurnButton.setFont(BODY_FONT);
        quitButton.setFont(BODY_FONT);
//        for (int row = 0; row < this.gridButtons.length; row++) {
//            for (int column = 0; column < this.gridButtons[row].length; column++) {
//                Button button = this.gridButtons[row][column];
//                button.setStyle(";");
//            }
//        }
    }

    public void setButtonOpacity(int row, int column, boolean hasStone, boolean isValidMove) {
        Button button = getGridButtons()[row][column];
        if (hasStone) {
            button.setStyle("-fx-opacity: 1;");
        } else if (isValidMove) {
            button.setStyle("-fx-opacity: 0.8;");
        } else {
            button.setStyle("-fx-opacity: 0.1;");
        }
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


    void disableButton(int row, int column) {
        Button button = getGridButtons()[row][column];
        button.setDisable(true);
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

    private void setTimerText() {
        long millisecondsElapsed = System.currentTimeMillis() - startMilliseconds;
        String hours = (TimeUnit.MILLISECONDS.toHours(millisecondsElapsed) < 10) ? String.format("0%d:", TimeUnit.MILLISECONDS.toHours(millisecondsElapsed)) : String.format("%d:", TimeUnit.MILLISECONDS.toHours(millisecondsElapsed));
        String minutes = ((TimeUnit.MILLISECONDS.toMinutes(millisecondsElapsed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecondsElapsed))) < 10) ? String.format("0%d:", TimeUnit.MILLISECONDS.toMinutes(millisecondsElapsed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecondsElapsed))) : String.format("%d:", TimeUnit.MILLISECONDS.toMinutes(millisecondsElapsed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecondsElapsed)));
        String seconds = ((TimeUnit.MILLISECONDS.toSeconds(millisecondsElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecondsElapsed)) < 10)) ? String.format("0%d", TimeUnit.MILLISECONDS.toSeconds(millisecondsElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecondsElapsed))) : String.format("%d", TimeUnit.MILLISECONDS.toSeconds(millisecondsElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecondsElapsed)));
        StringBuilder timerText = new StringBuilder();
        timerText.append(hours);
        timerText.append(minutes);
        timerText.append(seconds);
        timer.setText(timerText.toString());
    }

    private void updateTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setTimerText();
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void fadeBetweenImages(Button button, String URL, double duration) {
        fadeOut(button, duration);
        String newURL = null;
        if (URL == "black.png") {
            newURL = "white.png";
        } else {
            newURL = "black.png";
        }
        setButtonBackgroundImage(button, URL);
        fadeIn(button, duration);
    }

    private void fadeOut(Button button, double duration) {
        FadeTransition fadeTransitionOut = new FadeTransition();
        fadeTransitionOut.setNode(button);
        fadeTransitionOut.setDuration(new Duration(duration / 2));
        fadeTransitionOut.setFromValue(1.0);
        fadeTransitionOut.setToValue(0);
        fadeTransitionOut.play();
    }

    private void fadeIn(Button button, double duration) {
        FadeTransition fadeTransitionIn = new FadeTransition();
        fadeTransitionIn.setNode(button);
        fadeTransitionIn.setDuration(new Duration(duration / 2));
        fadeTransitionIn.setFromValue(0.0);
        fadeTransitionIn.setToValue(1.0);
        fadeTransitionIn.play();
    }
}





