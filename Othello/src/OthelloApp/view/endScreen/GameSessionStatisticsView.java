package OthelloApp.view.endScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameSessionStatisticsView extends BorderPane {
    private Text gameOutcome;
    private Text score;
    private Text averageDuration;
    private Text mostProfitableMove;
    private Text outlierMoves;

    private ScatterChart<Number, Number> profitabilitiesPerMoveChart;
    private ScatterChart<Number, Number> durationsPerMoveChart;
    private BarChart<String, Number> profitabilitiesHistogram;

    private RadioButton moveProfitabilityRadioButton;
    private RadioButton moveDurationRadioButton;
    private RadioButton moveProfitabilityHistogramRadioButton;

    private Button newGameButton;
    private Button replayLastGameSessionButton;
    private Button allGameStatisticsButton;
    private static final Font TITLE_FONT = new Font("Consolas", 30);
    private static final Font SUBTITLE_FONT = new Font("Consolas", 20);
    private static final Font BODY_FONT = new Font("Consolas", 15);
    private static final double MAX_WIDTH = 200;


    public GameSessionStatisticsView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes(){
        this.gameOutcome = new Text();
        this.score = new Text();

        ToggleGroup toggleGroup = new ToggleGroup();
        this.moveProfitabilityRadioButton = new RadioButton("Number of Stones Flipped per Move");
        this.moveDurationRadioButton = new RadioButton("Duration of Moves");
        this.moveProfitabilityHistogramRadioButton = new RadioButton("Number of Flipped Stones Histogram");
        moveProfitabilityRadioButton.setToggleGroup(toggleGroup);
        moveDurationRadioButton.setToggleGroup(toggleGroup);
        moveProfitabilityHistogramRadioButton.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(moveProfitabilityRadioButton);

        NumberAxis xAxisMoveNumberProfitabilities = new NumberAxis();
        xAxisMoveNumberProfitabilities.setLabel("Move Number");
        xAxisMoveNumberProfitabilities.setTickUnit(1);
        NumberAxis yAxisProfitabilities = new  NumberAxis();
        yAxisProfitabilities.setLabel("Number of Flipped Stones");
        yAxisProfitabilities.setTickUnit(1);
        yAxisProfitabilities.setMinorTickVisible(false);
        this.profitabilitiesPerMoveChart = new ScatterChart<Number,Number>(xAxisMoveNumberProfitabilities, yAxisProfitabilities);

        NumberAxis xAxisMoveNumberDurations = new NumberAxis();
        xAxisMoveNumberDurations.setLabel("Move Number");
        xAxisMoveNumberDurations.setTickUnit(1);
        NumberAxis yAxisDurations = new  NumberAxis();
        yAxisDurations.setLabel("Move Duration (Seconds)");
        this.durationsPerMoveChart = new ScatterChart<Number,Number>(xAxisMoveNumberDurations, yAxisDurations);

        CategoryAxis xAxisMoveProfitabilityBins = new CategoryAxis();
        xAxisMoveProfitabilityBins.setLabel("Number of Flipped Stones in Move");
        NumberAxis yAxisFrequency = new  NumberAxis();
        yAxisFrequency.setLabel("Frequency");
        this.profitabilitiesHistogram = new BarChart<String, Number>(xAxisMoveProfitabilityBins, yAxisFrequency);

        this.averageDuration = new Text();
        this.mostProfitableMove = new Text();
        this.outlierMoves = new Text();

        this.newGameButton = new Button("New Game");
        this.replayLastGameSessionButton = new Button ("Replay Last GameSession");
        this.allGameStatisticsButton = new Button("View All Games Statistics");
    }

    private void layoutNodes(){
        this.setPadding(new Insets(10));
        layoutTopTexts();
        this.setCenter(profitabilitiesPerMoveChart);
        layoutLeftRadioButtons();
        VBox rightStatisticsLabels = new VBox();
        rightStatisticsLabels.getChildren().addAll(mostProfitableMove, averageDuration, outlierMoves);
        BorderPane.setMargin(rightStatisticsLabels, new Insets(200, 10, 200, 10));
        rightStatisticsLabels.setSpacing(15);
        this.setRight(rightStatisticsLabels);

        layoutBottomButtons();
        setFonts();
        setWidthWrapNodes();
    }

    private void layoutTopTexts(){
        VBox topTexts = new VBox();
        topTexts.getChildren().addAll(gameOutcome, score);
        this.setTop(topTexts);
        topTexts.setAlignment(Pos.CENTER);
    }

    private void layoutLeftRadioButtons(){
        VBox leftRadioButtons = new VBox();
        leftRadioButtons.getChildren().addAll(moveProfitabilityRadioButton, moveDurationRadioButton, moveProfitabilityHistogramRadioButton);
        this.setLeft(leftRadioButtons);
        BorderPane.setAlignment(leftRadioButtons, Pos.CENTER);
        BorderPane.setMargin(leftRadioButtons, new Insets(200, 10, 200, 10));
        leftRadioButtons.setSpacing(15);
    }

    private void layoutBottomButtons(){
        HBox bottomButtons = new HBox();
        bottomButtons.getChildren().addAll(newGameButton, allGameStatisticsButton, replayLastGameSessionButton);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setSpacing(100);
        BorderPane.setMargin(bottomButtons, new Insets(20));
        this.setBottom(bottomButtons);
    }

    private void setFonts(){
        gameOutcome.setFont(TITLE_FONT);
        score.setFont(SUBTITLE_FONT);
        moveProfitabilityRadioButton.setFont(BODY_FONT);
        moveDurationRadioButton.setFont(BODY_FONT);
        moveProfitabilityHistogramRadioButton.setFont(BODY_FONT);
        mostProfitableMove.setFont(BODY_FONT);
        averageDuration.setFont(BODY_FONT);
        newGameButton.setFont(BODY_FONT);
        allGameStatisticsButton.setFont(BODY_FONT);
        outlierMoves.setFont(BODY_FONT);
        replayLastGameSessionButton.setFont(BODY_FONT);
    }

    private void setWidthWrapNodes(){
        moveProfitabilityRadioButton.setMaxWidth(MAX_WIDTH);
        moveProfitabilityRadioButton.setWrapText(true);
        moveDurationRadioButton.setMaxWidth(MAX_WIDTH);
        moveDurationRadioButton.setWrapText(true);
        moveProfitabilityHistogramRadioButton.setMaxWidth(MAX_WIDTH);
        moveProfitabilityHistogramRadioButton.setWrapText(true);
        mostProfitableMove.setWrappingWidth(MAX_WIDTH);
        averageDuration.setWrappingWidth(MAX_WIDTH);
        outlierMoves.setWrappingWidth(MAX_WIDTH);
    }

    public RadioButton getMoveProfitabilityRadioButton() {
        return moveProfitabilityRadioButton;
    }

    public RadioButton getMoveDurationRadioButton() {
        return moveDurationRadioButton;
    }

    public RadioButton getMoveProfitabilityHistogramRadioButton() {
        return moveProfitabilityHistogramRadioButton;
    }

    public Text getGameOutcome() {
        return gameOutcome;
    }

    public Text getScore() {
        return score;
    }

    public ScatterChart<Number, Number> getProfitabilitiesPerMoveChart() {
        return profitabilitiesPerMoveChart;
    }

    public ScatterChart<Number, Number> getDurationsPerMoveChart() {
        return durationsPerMoveChart;
    }

    public BarChart<String, Number> getProfitabilitiesHistogram() {
        return profitabilitiesHistogram;
    }


    public Text getMostProfitableMove() {
        return mostProfitableMove;
    }

    public Text getAverageDuration() {
        return averageDuration;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Text getOutlierMoves() {
        return outlierMoves;
    }

    public Button getAllGameStatisticsButton() {
        return allGameStatisticsButton;
    }

    public Button getReplayLastGameSessionButton() {
        return replayLastGameSessionButton;
    }
}
