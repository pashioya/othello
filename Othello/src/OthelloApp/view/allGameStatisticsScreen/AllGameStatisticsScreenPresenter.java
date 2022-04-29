package OthelloApp.view.allGameStatisticsScreen;

import OthelloApp.model.GameSession;
import OthelloApp.model.GameStatistics;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import OthelloApp.view.endScreen.EndScreenStatPresenter;
import OthelloApp.view.endScreen.EndScreenStatView;
import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class AllGameStatisticsScreenPresenter {
    private final AllGameStatisticsScreenView view;
    private final GameStatistics model;

    public AllGameStatisticsScreenPresenter(AllGameStatisticsScreenView view, GameStatistics model) {
        this.view = view;
        this.model = model;
        addEventHandlers();
        updateView();
    }


    private void addEventHandlers() {
        view.getPlayAgainButton().setOnAction(event -> {
            showChooseColorScreen();
        });
        view.getBackButton().setOnAction(event -> {
            showEndScreen();
        });
        view.getQuitButton().setOnAction(event -> {
            showQuitAlert(event);
        });

    }

    private void updateView() {
        setActiveSessionScoreVSAverage();
        fillSessionDurationsChart();
        setSessionDurationsPercentile();
    }

    private void setActiveSessionScoreVSAverage() {
        if (model.getLastSessionScore() > model.getAverageScore()){
            this.view.getActiveSessionScoreVSAverage().setText(
                    String.format("The last game's score (%d) was %.2f stones higher than the average score (%.2f)",
                            model.getLastSessionScore(),
                            Double.valueOf(model.getLastSessionScore()) - model.getAverageScore(),
                            model.getAverageScore()));
        } else {
            this.view.getActiveSessionScoreVSAverage().setText(
                    String.format("The last game's socre (%d) was %.2f stones lower than the average score (%.2f)",
                            model.getLastSessionScore(),
                            model.getAverageScore() - Double.valueOf(model.getLastSessionScore()),
                            model.getAverageScore()));
        }
    }



    private void fillSessionDurationsChart() {
        List<Double> durations = model.getSessionDurations();
        this.view.getSessionDurationsChart().getData().clear();
        XYChart.Series<Number, Number> sessionDurations = new XYChart.Series<>();
        sessionDurations.setName("Game Durations in Seconds");
        for (int i = 0; i < model.getSessionDurations().size(); i++) {
            sessionDurations.getData().add(new XYChart.Data<Number, Number>(i, model.getSessionDurations().get(i)));
        }
        this.view.getSessionDurationsChart().getData().addAll(sessionDurations);
    }

    private void setSessionDurationsPercentile() {
        view.getActiveSessionDurationPercentile().setText(
        String.format("Your game duration (%.2f seconds) is in the %.1f percentile of game durations",
                model.getLastSessionDuration(),
                model.getLastSessionDurationPercentile()));
    }

    private void showChooseColorScreen(){
        ChooseColorScreenView chooseColorScreenView = new ChooseColorScreenView();
        ChooseColorScreenPresenter ChooseColorScreenPresenter = new ChooseColorScreenPresenter(chooseColorScreenView);
        view.getScene().setRoot(chooseColorScreenView);
        chooseColorScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) chooseColorScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    private void showEndScreen() {
        EndScreenStatView endScreenView = new EndScreenStatView();
        EndScreenStatPresenter endScreenPresenter = new EndScreenStatPresenter(new GameSession(true, "test"), endScreenView);
        view.getScene().setRoot(endScreenView);
        endScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) endScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    private void showQuitAlert(ActionEvent event){
        Alert quitGameAlert = new Alert(Alert.AlertType.WARNING);
        quitGameAlert.setTitle("Confirm quit");
        quitGameAlert.setHeaderText("Quit game and exit?");
        quitGameAlert.setContentText("Click \"Continue\" to exit");
        quitGameAlert.getButtonTypes().clear();
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");
        quitGameAlert.getButtonTypes().addAll(continueButton, cancelButton);
        Optional<ButtonType> result = quitGameAlert.showAndWait();
        if (result.get() == continueButton){
            Stage stage = (Stage)view.getScene().getWindow();
            stage.close();
        } else {
            event.consume();
        }
    }
}
