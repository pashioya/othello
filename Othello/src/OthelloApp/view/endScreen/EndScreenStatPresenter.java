package OthelloApp.view.endScreen;

import OthelloApp.model.Computer;
import OthelloApp.model.GameSession;
import OthelloApp.model.Turn;
import OthelloApp.model.User;
import javafx.scene.chart.XYChart;

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
            for (Turn turn : model.getTurns()) {
                System.out.println(turn.getTurnId() + " " + turn.getFlippedStoneCoordinates().size());
            }
        });

    }

    private void updateView() {
        if (model.isTied()) {
            view.getGameOutcomeLabel().setText("It's a tie!");
        } else {
            if (model.userWon()) {
                view.getGameOutcomeLabel().setText("Congratulations! You win!");
            } else {
                view.getGameOutcomeLabel().setText("You lose! Better luck next time.");
            }
        }
        displayUserScore();
        displayMoveProfitabilitiesChart();
        displayMostProfitableMove();
        displayAverageMoveDuration();
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


    public void displayUserScore() {
        Integer[] playerScores = this.model.getPlayerScores();
        for (int i = 0; i < playerScores.length; i++) {
            if (this.model.getPlayers()[i] instanceof User) {
                this.view.getScoreLabel().setText(String.format("Your score: %d stones", playerScores[i]));
            }
        }
    }

    public void displayMostProfitableMove(){
        Turn mostProfitableTurn = this.model.getMostProfitableUserTurn();
        this.view.getMostProfitableMoveLabel().setText(String.format("Your most profitable move: #%d with %d flipped stones",
                mostProfitableTurn.getTurnId(),
                mostProfitableTurn.getFlippedStoneCoordinates().size()));
    }

    public void displayAverageMoveDuration(){
        double totalUserMoveDuration = 0;
        double userMovesCount = 0;
        for (Turn turn : model.getTurns()) {
            if (turn.getName().equals(model.getUserName())) {
                totalUserMoveDuration += turn.getTimeElapsed();
                userMovesCount += 1;
            }
        }
        double averageMoveDuration = totalUserMoveDuration/userMovesCount;
        this.view.getAverageDurationLabel().setText(String.format("Your average move duration: %.2f seconds",
                averageMoveDuration));
    }
}
