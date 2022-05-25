package OthelloApp.view.gameStatistics;

import OthelloApp.model.GameStatistics;
import javafx.scene.chart.XYChart;

import java.util.Map;

import static OthelloApp.utilities.AlertCreationUtil.showExitAlert;
import static OthelloApp.utilities.ScreenNavigationUtil.*;

public class GameStatisticsScreenPresenter {
    private final GameStatisticsScreenView view;
    private final GameStatistics model;
    private final boolean isCreatedFromWelcomeScreen;

    public GameStatisticsScreenPresenter(GameStatistics model, GameStatisticsScreenView view, boolean isCreatedFromWelcomeScreen) {
        this.view = view;
        this.model = model;
        this.isCreatedFromWelcomeScreen = isCreatedFromWelcomeScreen;
        addEventHandlers();
        if (model.getSessionDurationsMap().isEmpty()) {
            view.getTitle().setText("You have no previous game sessions.");
        } else{
            updateView();
        };
    }

    private void addEventHandlers() {
        view.getNewGameButton().setOnAction(event -> {
            showChooseColorScreen(view, "gameStatisticsScreen");
        });

        view.getQuitButton().setOnAction(event -> {
            showExitAlert(event, view);
        });

        if (isCreatedFromWelcomeScreen) {
            view.getBackButton().setOnAction(event -> {
                showWelcomeScreen(view);
            });
        } else {
            view.getBackButton().setOnAction(event -> {
                showGameSessionStatisticsScreen(view);
            });
        }
    }

    private void updateView() {
        setActiveSessionScoreVSAverage();
        fillSessionDurationsChart();
        setSessionDurationsPercentile();
    }

    private void setActiveSessionScoreVSAverage() {
        int lastSessionScore = model.getGameSessionStatisticsList().get(model.getGameSessionStatisticsList().size() - 1).getScore();
        if (model.lastSessionScoreGreaterThanAverage()) {
            this.view.getActiveSessionScoreVSAverage().setText(
                    String.format("The last game's score (%d) was %.2f stones higher than the average score (%.2f)",
                            lastSessionScore,
                            Double.valueOf(Double.valueOf(lastSessionScore) - model.getAverageScore()),
                            model.getAverageScore()));
        } else {
            this.view.getActiveSessionScoreVSAverage().setText(
                    String.format("The last game's score (%d) was %.2f stones lower than the average score (%.2f)",
                            lastSessionScore,
                            Double.valueOf(model.getAverageScore() - Double.valueOf(lastSessionScore)),
                            model.getAverageScore()));
        }
    }


    private void fillSessionDurationsChart() {
        this.view.getSessionDurationsChart().getData().clear();
        XYChart.Series<Number, Number> sessionDurations = new XYChart.Series<>();
        sessionDurations.setName("Game Durations in Seconds");
        for (Map.Entry<Integer, Double> sessionDuration : model.getSessionDurationsMap().entrySet()) {
            sessionDurations.getData().add(new XYChart.Data<Number, Number>(sessionDuration.getKey(), sessionDuration.getValue()));
        };
        this.view.getSessionDurationsChart().getData().addAll(sessionDurations);
    }

    private void setSessionDurationsPercentile() {
        view.getActiveSessionDurationPercentile().setText(
                String.format("Your game duration (%.2f seconds) is in the %.1f percentile of game durations",
                        model.getLatestGameSessionStatistics().getDuration(),
                        model.getLastSessionDurationPercentile()));
    }

}