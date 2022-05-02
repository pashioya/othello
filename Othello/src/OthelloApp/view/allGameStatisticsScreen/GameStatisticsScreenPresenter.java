package OthelloApp.view.allGameStatisticsScreen;

import OthelloApp.model.GameSessionStatistics;
import OthelloApp.model.GameStatistics;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import OthelloApp.view.endScreen.GameSessionStatisticsPresenter;
import OthelloApp.view.endScreen.GameSessionStatisticsView;
import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.*;

public class GameStatisticsScreenPresenter {
    private final GameStatisticsScreenView view;
    private final GameStatistics model;
    private final boolean isCreatedFromWelcomeScreen;

    public GameStatisticsScreenPresenter(GameStatistics model, GameStatisticsScreenView view, boolean isCreatedFromWelcomeScreen) {
        this.view = view;
        this.model = model;
        this.isCreatedFromWelcomeScreen = isCreatedFromWelcomeScreen;
        addEventHandlers();
        updateView();
    }


    private void addEventHandlers() {
        view.getNewGameButton().setOnAction(event -> {
            showChooseColorScreen(view);
        });

        view.getQuitButton().setOnAction(event -> {
            showQuitAlert(event);
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
        for (int i = 0; i < model.getSessionDurationsList().size(); i++) {
            sessionDurations.getData().add(new XYChart.Data<Number, Number>(i, model.getSessionDurationsList().get(i)));
        }
        this.view.getSessionDurationsChart().getData().addAll(sessionDurations);
    }

    private void setSessionDurationsPercentile() {
        view.getActiveSessionDurationPercentile().setText(
                String.format("Your game duration (%.2f seconds) is in the %.1f percentile of game durations",
                        model.getLatestGameSessionStatistics().getDuration(),
                        model.getLastSessionDurationPercentile()));
    }

    private void showQuitAlert(ActionEvent event) {
        Alert quitGameAlert = new Alert(Alert.AlertType.WARNING);
        quitGameAlert.setTitle("Confirm quit");
        quitGameAlert.setHeaderText("Quit game and exit?");
        quitGameAlert.setContentText("Click \"Continue\" to exit");
        quitGameAlert.getButtonTypes().clear();
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");
        quitGameAlert.getButtonTypes().addAll(continueButton, cancelButton);
        Optional<ButtonType> result = quitGameAlert.showAndWait();
        if (result.get() == continueButton) {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        } else {
            event.consume();
        }
    }
}
