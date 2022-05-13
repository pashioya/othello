package OthelloApp.alertCreation;

import OthelloApp.view.gameSessionScreen.GameSessionScreenView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

import static OthelloApp.dataManager.DataManager.clearAllData;
import static OthelloApp.screenNavigationUtil.ScreenNavigationUtil.showGameSessionStatisticsScreen;

public final class AlertCreation {
    public static Alert createRulesInfoAlert() {
        Alert rulesInfoAlert = new Alert(Alert.AlertType.INFORMATION);
        rulesInfoAlert.setTitle("Othello Rules");
        rulesInfoAlert.setHeaderText("Rules");
        rulesInfoAlert.setContentText("Othello is a two-player game. One player plays black stones and the other player plays white stones. " +
                "The game begins with four stones (two white and two black) in the center of the board. The player that plays black stones makes the first move.\n\n" +
                "Players make moves by placing stone of their respective colors on the board. " +
                "A move is valid only if the placed stone outflanks an opposite-colored stone (or row of opposite-colored stones). " +
                "A stone or row of stones is outflanked when it is bordered by opposite-colored stones at each end. " +
                "Each player must outflank opposite-colored stones and flip them so they have the player's color. \n\n" +
                "If a player is not able to flip any stones, the player forfeits his/her turn and the other player plays again. " +
                "Players may not voluntarily forfeit a turn if a move is available.\n\n" +
                "The game is over when neither player can make a move. The player with the most stones of his/her color on the board wins the game."
        );
        return rulesInfoAlert;
    }

    public static Alert createComputerMoveExplanationAlert(String explanation) {
        Alert computerMoveExplanationAlert = new Alert(Alert.AlertType.INFORMATION);
        computerMoveExplanationAlert.setTitle("Latest Computer Move Explanation");
        computerMoveExplanationAlert.setHeaderText("Explanation");
        computerMoveExplanationAlert.setContentText(explanation);
        computerMoveExplanationAlert.showAndWait();
        return computerMoveExplanationAlert;
    }

    public static void showQuitGameAlert(ActionEvent event, GameSessionScreenView view) {
        Alert quitGameAlert = new Alert(Alert.AlertType.WARNING);
        quitGameAlert.setTitle("Confirm quit game");
        quitGameAlert.setHeaderText("All progress in this game will be lost.");
        quitGameAlert.setContentText("Quit anyway?");
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

    public static void showGameOverAlert(GameSessionScreenView view) {
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game over!");
        gameOverAlert.setHeaderText("Neither player can play any more turns.");
        gameOverAlert.setContentText("Click \"Continue\" to view the results of your game.");
        gameOverAlert.getButtonTypes().clear();
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");
        gameOverAlert.getButtonTypes().addAll(continueButton, cancelButton);
        Optional<ButtonType> result = gameOverAlert.showAndWait();
        if (result.get() == continueButton) {
            showGameSessionStatisticsScreen(view);
        } else {
            gameOverAlert.close();
        }
    }

    public static void showExitAlert(ActionEvent event, Pane view) {
        Alert quitGameAlert = new Alert(Alert.AlertType.WARNING);
        quitGameAlert.setTitle("Confirm quit application");
        quitGameAlert.setHeaderText("Are you sure you want to quit the application?");
        quitGameAlert.setContentText("Click \"Continue\" to quit.");
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

    public static void showResetDatabaseAlert() {
        Alert resetDatabaseAlert = new Alert(Alert.AlertType.WARNING);
        resetDatabaseAlert.setTitle("Confirm clear database");
        resetDatabaseAlert.setHeaderText("Are you sure you want to clear the contents of the database?");
        resetDatabaseAlert.setContentText("Click \"Continue\" to delete all data from previous game sessions.");
        resetDatabaseAlert.getButtonTypes().clear();
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");
        resetDatabaseAlert.getButtonTypes().addAll(continueButton, cancelButton);
        Optional<ButtonType> result = resetDatabaseAlert.showAndWait();
        if (result.get() == continueButton) {
            clearAllData();
        }
    }
}
