package OthelloApp.view.endScreen;


import OthelloApp.model.GameSessionStatistics;
import OthelloApp.model.GameStatistics;
import OthelloApp.view.allGameStatisticsScreen.GameStatisticsScreenPresenter;
import OthelloApp.view.allGameStatisticsScreen.GameStatisticsScreenView;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.*;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.*;

public class GameSessionStatisticsPresenter {
    private final GameSessionStatistics model;
    private final GameSessionStatisticsView view;

    public GameSessionStatisticsPresenter(GameSessionStatistics model, GameSessionStatisticsView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        this.view.getMoveProfitabilityRadioButton().setOnAction(event -> {
            displayMoveProfitabilitiesChart();
        });
        this.view.getMoveDurationRadioButton().setOnAction(event -> {
            displayMoveDurationsChart();
        });
        this.view.getMoveProfitabilityHistogramRadioButton().setOnAction(event -> {
            displayMoveProfitabilitiesHistogram();
        });
        this.view.getPlayAgainButton().setOnAction(event -> {
            showChooseColorScreen(view);
        });

        this.view.getAllGameStatisticsButton().setOnAction(event -> {
            showAllGameStatisticsScreen(view, false);
        });

    }

    private void updateView() {
        if (model.isTied()) {
            view.getGameOutcome().setText("It's a tie!");
        } else {
            if (model.userWon()) {
                view.getGameOutcome().setText("Congratulations! You win!");
            } else {
                view.getGameOutcome().setText("You lose! Better luck next time.");
            }
        }
        displayUserScore();
        displayMoveProfitabilitiesChart();
        displayMostProfitableMove();
        displayAverageMoveDuration();
        displayOutlierMoves();
    }

    private void displayMoveProfitabilitiesChart() {
        view.getProfitabilitiesPerMoveChart().getData().clear();
        XYChart.Series<Number, Number> userMoves = new XYChart.Series<>();
        userMoves.setName("User Move");
        for (Map.Entry<Integer, Integer> userMoveProfitability : model.getUserMoveProfitabilitiesMap().entrySet()) {
            userMoves.getData().add(new XYChart.Data<Number, Number>(userMoveProfitability.getKey(), userMoveProfitability.getValue()));
        };
        XYChart.Series<Number, Number> computerMoves = new XYChart.Series<>();
        computerMoves.setName("Computer Move");
        for (Map.Entry<Integer, Integer> computerMoveProfitability : model.getComputerMoveProfitabilitiesMap().entrySet()) {
            computerMoves.getData().add(new XYChart.Data<Number, Number>(computerMoveProfitability.getKey(), computerMoveProfitability.getValue()));
        };
        view.getProfitabilitiesPerMoveChart().getData().addAll(userMoves, computerMoves);
        view.setCenter(view.getProfitabilitiesPerMoveChart());
    }

    private void displayMoveDurationsChart() {
        view.getDurationsPerMoveChart().getData().clear();
        XYChart.Series<Number, Number> playerMoveDurations = new XYChart.Series<>();
        playerMoveDurations.setName("Move Durations in Seconds by Move Number");
        for (Map.Entry<Integer, Double> userMoveDurationsEntry : model.getUserMoveDurationsMap().entrySet()) {
            playerMoveDurations.getData().add(new XYChart.Data<Number, Number>(userMoveDurationsEntry.getKey(), userMoveDurationsEntry.getValue()));
        }
        view.getDurationsPerMoveChart().getData().addAll(playerMoveDurations);
        view.setCenter(view.getDurationsPerMoveChart());
    }

    private void displayMoveProfitabilitiesHistogram() {
        ArrayList<Integer> userMoveProfitabilities = new ArrayList(model.getUserMoveProfitabilitiesMap().values());
        HashMap<Integer, Integer> profitabilitiesFrequencies = getFrequencyDictionary(userMoveProfitabilities);
        fillHistogram(profitabilitiesFrequencies);
        view.setCenter(view.getProfitabilitiesHistogram());
    }


    private HashMap<Integer, Integer> getFrequencyDictionary(ArrayList<Integer> values) {
        int maximum = Collections.max(values);
        int minimum = Collections.min(values);
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        // Initialize all values in frequency map to 0,
        for (Integer i = minimum; i < maximum + 1; i++) {
            frequencyMap.put(i, 0);
        }
        // Count the frequency of each move profitability
        for (Integer value : values) {
            frequencyMap.put(value,
                    frequencyMap.get(value) + 1);
        }
        return frequencyMap;
    }

    private void fillHistogram(HashMap<Integer, Integer> profitabilitiesFrequencies) {
        view.getProfitabilitiesHistogram().getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Move Profitability (Number of Flipped Stones)");
        this.view.getProfitabilitiesHistogram().getYAxis().setLabel("Frequency");
        for (Map.Entry<Integer, Integer> profitabilityFrequency : profitabilitiesFrequencies.entrySet()) {
            series.getData().add(new XYChart.Data<String, Number>(
                    String.valueOf(profitabilityFrequency.getKey()),
                    profitabilityFrequency.getValue())
            );
        }
        this.view.getProfitabilitiesHistogram().getData().add(series);

    }

    private void displayUserScore() {
        int userScore = this.model.getScore();
        this.view.getScore().setText(String.format("Your score: %d stones", userScore));
    }

    private void displayMostProfitableMove() {
        Map.Entry mostProfitableTurn = this.model.getMostProfitableUserTurn();
        this.view.getMostProfitableMove().setText(String.format("Your most profitable move: #%d with %d flipped stones",
                mostProfitableTurn.getKey(),
                mostProfitableTurn.getValue()));
    }

    private void displayAverageMoveDuration() {
        this.view.getAverageDuration().setText(
                String.format("Your average move duration: %.2f seconds",
                this.model.getAverageMoveDuration()));
    }

    private void displayOutlierMoves() {
        Map<Integer, Integer> outlierMovesMap = model.getOutlierMovesMap();
        if (outlierMovesMap.isEmpty()) {
            view.getOutlierMoves().setText("There were no outlier moves in this game.");
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("List of outlier moves and their profitabilities:\n");
            for (Map.Entry<Integer, Integer> outlierMove : outlierMovesMap.entrySet()) {
                builder.append(String.format("Move #%d with %d flipped stones\n", outlierMove.getKey(), outlierMove.getValue()));
            }
            view.getOutlierMoves().setText(builder.toString());
        }
    }

}
