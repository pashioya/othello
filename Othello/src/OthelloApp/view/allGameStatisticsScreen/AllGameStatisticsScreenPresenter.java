package OthelloApp.view.allGameStatisticsScreen;

import OthelloApp.model.Game;
import OthelloApp.model.Turn;
import javafx.scene.chart.XYChart;

import java.util.List;

public class AllGameStatisticsScreenPresenter {
    private final AllGameStatisticsScreenView view;
    private final Game model;

    public AllGameStatisticsScreenPresenter(AllGameStatisticsScreenView view, Game model) {
        this.view = view;
        this.model = model;
        addEventHandlers();
        updateView();
    }


    public void addEventHandlers() {

    }

    public void updateView() {
        setActiveSessionScoreVSAverage();
        fillSessionDurationsChart();
        setSessionDurationsPercentile();
    }

    private void setActiveSessionScoreVSAverage() {
        if (model.getActiveSession().getUserScore() > model.getAverageScore()){
            this.view.getActiveSessionScoreVSAverage().setText(
                    String.format("Your score (%d) was %.2f stones higher than the average score (%.2f)",
                            model.getActiveSession().getUserScore(),
                            Double.valueOf(model.getActiveSession().getUserScore()) - model.getAverageScore(),
                            model.getAverageScore()));
        } else {
            this.view.getActiveSessionScoreVSAverage().setText(
                    String.format("Your score (%d) was %.2f stones lower than the average score (%.2f)",
                            model.getActiveSession().getUserScore(),
                            model.getAverageScore() - Double.valueOf(model.getActiveSession().getUserScore()),
                            model.getAverageScore()));
        }

    }

    private void fillSessionDurationsChart() {
        List<Double> durations = model.getSessionDurations();
        for (Double duration : durations) {
            System.out.println(duration);
        }
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
                model.getActiveSession().getTimeElapsed(),
                model.getActiveSessionDurationPercentile()));
    }
}
