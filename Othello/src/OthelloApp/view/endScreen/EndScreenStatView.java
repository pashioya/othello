package OthelloApp.view.endScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EndScreenStatView extends BorderPane {
    private Text gameOutcome;
    private Text score;
    private Label averageDurationLabel;
    private Label mostProfitableMoveLabel;

    private ScatterChart<Number, Number> profitabilitiesPerMoveChart;
    private ScatterChart<Number, Number> durationsPerMoveChart;
    private BarChart<String, Number> profitabilitiesHistogram;

    private RadioButton moveProfitabilityRadioButton;
    private RadioButton moveDurationRadioButton;
    private RadioButton moveProfitabilityHistogramRadioButton;

    private Button playAgainButton;
    private Button allGameStatisticsButton;
    private static final Font TITLE_FONT = new Font("Consolas", 30);
    private static final Font SUBTITLE_FONT = new Font("Consolas", 20);
    private static final Font BODY_FONT = new Font("Consolas", 15);


    public EndScreenStatView() {
        initializeNodes();
        layoutNodes();
        styleNodes();
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
        yAxisProfitabilities.setLabel("Number of Flipped Pieces");
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
        NumberAxis yAxisFrequency = new  NumberAxis();
        this.profitabilitiesHistogram = new BarChart<String, Number>(xAxisMoveProfitabilityBins, yAxisFrequency);

        this.averageDurationLabel = new Label();
        this.mostProfitableMoveLabel = new Label();

        this.playAgainButton = new Button("Play Again");
        this.allGameStatisticsButton = new Button("View All Games Statistics");
    }

    private void layoutNodes(){
        this.setPadding(new Insets(10));
        VBox topTexts = new VBox();
        topTexts.getChildren().addAll(gameOutcome, score);
        this.setTop(topTexts);
        topTexts.setAlignment(Pos.CENTER);

        this.setCenter(profitabilitiesPerMoveChart);

        VBox leftRadioButtons = new VBox();
        leftRadioButtons.getChildren().addAll(moveProfitabilityRadioButton, moveDurationRadioButton, moveProfitabilityHistogramRadioButton);
        this.setLeft(leftRadioButtons);
        BorderPane.setAlignment(leftRadioButtons, Pos.CENTER);
        BorderPane.setMargin(leftRadioButtons, new Insets(200, 10, 200, 10));
        leftRadioButtons.setSpacing(15);

        VBox rightStatisticsLabels = new VBox();
        rightStatisticsLabels.getChildren().addAll(mostProfitableMoveLabel, averageDurationLabel);
        this.setRight(rightStatisticsLabels);
        BorderPane.setMargin(rightStatisticsLabels, new Insets(200, 10, 200, 10));
        rightStatisticsLabels.setSpacing(15);

        HBox bottomButtons = new HBox();
        bottomButtons.getChildren().addAll(playAgainButton, allGameStatisticsButton);

    }

    private void styleNodes(){
        final double MAX_WIDTH = 200;
        gameOutcome.setFont(TITLE_FONT);
        score.setFont(SUBTITLE_FONT);
        moveProfitabilityRadioButton.setFont(BODY_FONT);
        moveProfitabilityRadioButton.setMaxWidth(MAX_WIDTH);
        moveProfitabilityRadioButton.setWrapText(true);

        moveDurationRadioButton.setFont(BODY_FONT);
        moveDurationRadioButton.setMaxWidth(MAX_WIDTH);
        moveDurationRadioButton.setWrapText(true);

        moveProfitabilityHistogramRadioButton.setFont(BODY_FONT);
        moveProfitabilityHistogramRadioButton.setMaxWidth(MAX_WIDTH);
        moveProfitabilityHistogramRadioButton.setWrapText(true);

        mostProfitableMoveLabel.setFont(BODY_FONT);
        mostProfitableMoveLabel.setMaxWidth(MAX_WIDTH);
        mostProfitableMoveLabel.setWrapText(true);

        averageDurationLabel.setFont(BODY_FONT);
        averageDurationLabel.setMaxWidth(MAX_WIDTH);
        averageDurationLabel.setWrapText(true);

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


    public Label getMostProfitableMoveLabel() {
        return mostProfitableMoveLabel;
    }

    public Label getAverageDurationLabel() {
        return averageDurationLabel;
    }
}
