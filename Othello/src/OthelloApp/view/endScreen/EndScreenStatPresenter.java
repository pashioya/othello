package OthelloApp.view.endScreen;


import OthelloApp.model.GameSession;
import OthelloApp.model.GameSession.*;
import OthelloApp.model.GameStatistics;
import OthelloApp.model.Turn;
import OthelloApp.view.allGameStatisticsScreen.AllGameStatisticsScreenPresenter;
import OthelloApp.view.allGameStatisticsScreen.AllGameStatisticsScreenView;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.*;

public class EndScreenStatPresenter {
    private final GameSession model;
    private final EndScreenStatView view;

    public EndScreenStatPresenter(GameSession model, EndScreenStatView view) {
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
            showChooseColorScreen();
        });

        this.view.getAllGameStatisticsButton().setOnAction(event -> {
            showAllGameStatisticsScreen();
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
        XYChart.Series<Number, Number> playerMoves = new XYChart.Series<>();
        playerMoves.setName("Flipped Stones by Move Number");

        for (Turn turn : model.getTurns()) {
            if (turn.getName().equals(model.getUserName())) {
                playerMoves.getData().add(new XYChart.Data<Number, Number>(turn.getTurnId(), turn.getFlippedStoneCoordinates().size()));
            }
        }
        view.getProfitabilitiesPerMoveChart().getData().addAll(playerMoves);
        view.setCenter(view.getProfitabilitiesPerMoveChart());
    }

    private void displayMoveDurationsChart() {
        view.getDurationsPerMoveChart().getData().clear();
        XYChart.Series<Number, Number> playerMoves = new XYChart.Series<>();
        playerMoves.setName("Move Durations in Seconds by Move Number");
        for (Turn turn : model.getTurns()) {
            if (turn.getName().equals(model.getUserName())) {
                playerMoves.getData().add(new XYChart.Data<Number, Number>(turn.getTurnId(), turn.getTimeElapsed()));
            }
        }
        view.getDurationsPerMoveChart().getData().addAll(playerMoves);
        view.setCenter(view.getDurationsPerMoveChart());
    }

    private void displayMoveProfitabilitiesHistogram() {
        ArrayList<Integer> userMoveProfitabilities = model.getUserMoveProfitabilitiesList();
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
        int userScore = this.model.getUserScore();
        this.view.getScore().setText(String.format("Your score: %d stones", userScore));
    }

    private void displayMostProfitableMove() {
        Turn mostProfitableTurn = this.model.getMostProfitableUserTurn();
        this.view.getMostProfitableMove().setText(String.format("Your most profitable move: #%d with %d flipped stones",
                mostProfitableTurn.getTurnId(),
                mostProfitableTurn.getFlippedStoneCoordinates().size()));
    }

    private void displayAverageMoveDuration() {
        double totalUserMoveDuration = 0;
        double userMovesCount = 0;
        for (Turn turn : model.getTurns()) {
            if (turn.getName().equals(model.getUserName())) {
                totalUserMoveDuration += turn.getTimeElapsed();
                userMovesCount += 1;
            }
        }
        double averageMoveDuration = totalUserMoveDuration / userMovesCount;
        this.view.getAverageDuration().setText(String.format("Your average move duration: %.2f seconds",
                averageMoveDuration));
    }

    private void displayOutlierMoves() {
        Map<Integer, Integer> outlierMovesMap = getOutlierMovesMap();
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


    private void showChooseColorScreen() {
        ChooseColorScreenView chooseColorScreenView = new ChooseColorScreenView();
        ChooseColorScreenPresenter ChooseColorScreenPresenter = new ChooseColorScreenPresenter(chooseColorScreenView);
        view.getScene().setRoot(chooseColorScreenView);
        chooseColorScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) chooseColorScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    private HashMap<Integer, Integer> getOutlierMovesMap() {
        HashMap<Integer, Integer> outlierMovesMap = new HashMap<>();
        ArrayList<Integer> userMoveProfitabilities = model.getUserMoveProfitabilitiesList();
        Collections.sort(userMoveProfitabilities);
        for (Turn turn : model.getTurns()) {
            if (turn.getName().equals(model.getUserName())) {
                if (isOutlier(turn.getFlippedStoneCoordinates().size(), userMoveProfitabilities)) {
                    outlierMovesMap.put(turn.getTurnId(), turn.getFlippedStoneCoordinates().size());
                }
            }
        }
        return outlierMovesMap;
    }

    private boolean isOutlier(int value, List<Integer> dataList) {
        double q3 = getThirdQuartile(dataList);
        double q1 = getFirstQuartile(dataList);
        double lowerBound = q1 - 1.5 * getIQR(dataList);
        double upperBound = q3 + 1.5 * getIQR(dataList);
        if (value > upperBound || value < lowerBound) {
            return true;
        }
        return false;
    }

    private double getThirdQuartile(List<Integer> dataList) {
        if (dataList.size() % 2 == 0) {
            List<Integer> dataAboveMedian = dataList.subList(dataList.size() / 2, dataList.size());
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile;
        } else {
            List<Integer> dataAboveMedian = dataList.subList((dataList.size() + 1) / 2, dataList.size());
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile;
        }
    }

    private double getFirstQuartile(List<Integer> dataList) {
        if (dataList.size() % 2 == 0) {
            List<Integer> dataBelowMedian = dataList.subList(0, dataList.size() / 2);
            double firstQuartile = getMedian(dataBelowMedian);
            return firstQuartile;
        } else {
            List<Integer> dataBelowMedian = dataList.subList(0, (dataList.size() - 1) / 2);
            double firstQuartile = getMedian(dataBelowMedian);
            return firstQuartile;
        }
    }

    private double getIQR(List<Integer> dataList) {
        if (dataList.size() % 2 == 0) {
            List<Integer> dataBelowMedian = dataList.subList(0, dataList.size() / 2);
            List<Integer> dataAboveMedian = dataList.subList(dataList.size() / 2, dataList.size());
            double firstQuartile = getMedian(dataBelowMedian);
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile - firstQuartile;
        } else {
            List<Integer> dataBelowMedian = dataList.subList(0, (dataList.size() - 1) / 2);
            List<Integer> dataAboveMedian = dataList.subList((dataList.size() + 1) / 2, dataList.size());
            double firstQuartile = getMedian(dataBelowMedian);
            double thirdQuartile = getMedian(dataAboveMedian);
            return thirdQuartile - firstQuartile;
        }

    }

    private double getMedian(List<Integer> dataList) {
        Collections.sort(dataList);
        // if list contains even number of items
        if (dataList.size() % 2 == 0) {
            double n2 = dataList.get(dataList.size() / 2);
            double n1 = dataList.get(dataList.size() / 2 - 1);
            double median = (n1 + n2) / 2;
            return median;
        } else {
            double median = dataList.get((dataList.size() - 1) / 2);
            return median;
        }
    }

        private void showAllGameStatisticsScreen(){
        AllGameStatisticsScreenView allGameStatisticsScreenView = new AllGameStatisticsScreenView();
        AllGameStatisticsScreenPresenter allGameStatisticsScreenPresenter = new AllGameStatisticsScreenPresenter(allGameStatisticsScreenView, new GameStatistics());
        view.getScene().setRoot(allGameStatisticsScreenView);
        allGameStatisticsScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) allGameStatisticsScreenView.getScene().getWindow();
        stage.centerOnScreen();

    }
}
