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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EndScreenStatView extends BorderPane {
    private Label gameOutcomeLabel;
    private Label scoreLabel;
    private Label averageDurationLabel;
    private Label mostProfitableMoveLabel;

    private ScatterChart<Number, Number> profitabilitiesPerMoveChart;
    private ScatterChart<Number, Number> durationsPerMoveChart;
    private BarChart<String, Number> profitabilitiesHistogram;

    private RadioButton moveProfitabilityRadioButton;
    private RadioButton moveDurationRadioButton;
    private RadioButton moveProfitabilityHistogramRadioButton;

    private Button playAgainButton;
    private final String FONT_FAMILY = "Consolas";

    public EndScreenStatView() {
        initializeNodes();
        layoutNodes();
        styleNodes();
    }

    private void initializeNodes(){
        this.gameOutcomeLabel = new Label();
        this.scoreLabel = new Label();

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
    }

    private void layoutNodes(){
        this.setPadding(new Insets(10));
        VBox topLabels = new VBox();
        topLabels.getChildren().addAll(gameOutcomeLabel, scoreLabel);
        this.setTop(topLabels);
        topLabels.setAlignment(Pos.CENTER);

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
    }

    private void styleNodes(){
        Font fontTitle = new Font(FONT_FAMILY, 30);
        Font fontSubtitle = new Font(FONT_FAMILY, 20);
        Font fontBody = new Font(FONT_FAMILY, 15);
        final double MAX_WIDTH = 200;
        gameOutcomeLabel.setFont(fontTitle);
        scoreLabel.setFont(fontSubtitle);
        moveProfitabilityRadioButton.setFont(fontBody);
        moveProfitabilityRadioButton.setMaxWidth(MAX_WIDTH);
        moveProfitabilityRadioButton.setWrapText(true);

        moveDurationRadioButton.setFont(fontBody);
        moveDurationRadioButton.setMaxWidth(MAX_WIDTH);
        moveDurationRadioButton.setWrapText(true);

        moveProfitabilityHistogramRadioButton.setFont(fontBody);
        moveProfitabilityHistogramRadioButton.setMaxWidth(MAX_WIDTH);
        moveProfitabilityHistogramRadioButton.setWrapText(true);

        mostProfitableMoveLabel.setFont(fontBody);
        mostProfitableMoveLabel.setMaxWidth(MAX_WIDTH);
        mostProfitableMoveLabel.setWrapText(true);

        averageDurationLabel.setFont(fontBody);
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

    public Label getGameOutcomeLabel() {
        return gameOutcomeLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
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
