package OthelloApp.view.gameStatistics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameStatisticsScreenView extends BorderPane {
    private Text title;
    private Text activeSessionScoreVSAverage;
    private Text activeSessionDurationPercentile;
    private Text fastestWinTime;
    private Text fastestLoseTime;
    private ScatterChart<Number, Number> sessionDurationsChart;
    private Button newGameButton;
    private Button backButton;
    private Button quitButton;

    private static final Font TITLE_FONT = new Font("Consolas", 30);
    private static final Font SUBTITLE_FONT = new Font("Consolas", 18);
    private static final Font BODY_FONT = new Font("Consolas", 15);
    private static final double MAX_WIDTH = 200;

    public GameStatisticsScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes(){
        this.title = new Text("Statistics For All Finished Games");
        this.activeSessionScoreVSAverage  = new Text();
        this.activeSessionDurationPercentile = new Text();
        this.fastestWinTime = new Text();
        this.fastestLoseTime = new Text();

        NumberAxis xAxisGameSessionNo = new NumberAxis();
        xAxisGameSessionNo.setLabel("Game Number");
        NumberAxis yAxisSessionDurations = new  NumberAxis();
        yAxisSessionDurations.setAutoRanging(true);
        yAxisSessionDurations.setLabel("Session Durations (Seconds)");
        this.sessionDurationsChart = new ScatterChart<Number,Number>(xAxisGameSessionNo, yAxisSessionDurations);

        this.newGameButton = new Button ("New Game");
        this.backButton = new Button("Back");
        this.quitButton = new Button ("Quit");
    }

    private void layoutNodes(){
        this.setTop(this.title);
        VBox leftAverageScoreDurationPercentile = new VBox();
        leftAverageScoreDurationPercentile.getChildren().addAll(activeSessionScoreVSAverage, activeSessionDurationPercentile);
        leftAverageScoreDurationPercentile.setSpacing(50);
        leftAverageScoreDurationPercentile.setAlignment(Pos.CENTER);
        this.setLeft(leftAverageScoreDurationPercentile);

        this.setCenter(this.sessionDurationsChart);
        VBox rightWinLoseDurationsInformation = new VBox();
        rightWinLoseDurationsInformation.getChildren().addAll(fastestWinTime, fastestLoseTime);
        rightWinLoseDurationsInformation.setSpacing(50);
        rightWinLoseDurationsInformation.setAlignment(Pos.CENTER);
        this.setRight(rightWinLoseDurationsInformation);

        HBox bottomButtons = new HBox();
        bottomButtons.getChildren().addAll(backButton, newGameButton, quitButton);
        bottomButtons.setSpacing(200);
        bottomButtons.setAlignment(Pos.CENTER);
        this.setBottom(bottomButtons);

        this.setAlignment(title, Pos.CENTER);
        this.setAlignment(this.activeSessionScoreVSAverage, Pos.CENTER);
        this.setAlignment(rightWinLoseDurationsInformation, Pos.CENTER);

        this.getActiveSessionScoreVSAverage().setWrappingWidth(MAX_WIDTH);
        this.getActiveSessionDurationPercentile().setWrappingWidth(MAX_WIDTH);
        this.getFastestWinTime().setWrappingWidth(MAX_WIDTH);
        this.getFastestLoseTime().setWrappingWidth(MAX_WIDTH);

        this.setPadding(new Insets(20));
        this.setMargin(sessionDurationsChart, new Insets(10));
        setFonts();
    }

    private void setFonts(){
        this.title.setFont(TITLE_FONT);
        this.activeSessionDurationPercentile.setFont(SUBTITLE_FONT);
        this.activeSessionScoreVSAverage.setFont(SUBTITLE_FONT);
        this.fastestWinTime.setFont(SUBTITLE_FONT);
        this.fastestLoseTime.setFont(SUBTITLE_FONT);
        this.newGameButton.setFont(BODY_FONT);
        this.quitButton.setFont(BODY_FONT);
        this.backButton.setFont(BODY_FONT);
    }

    public Text getActiveSessionScoreVSAverage() {
        return activeSessionScoreVSAverage;
    }

    public Text getActiveSessionDurationPercentile() {
        return activeSessionDurationPercentile;
    }

    public ScatterChart<Number, Number> getSessionDurationsChart() {
        return sessionDurationsChart;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Text getTitle() {
        return title;
    }

    public Text getFastestWinTime() {
        return fastestWinTime;
    }

    public Text getFastestLoseTime() {
        return fastestLoseTime;
    }
}
