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

public final class AlertCreationUtil {

    public static void showExitAlert(ActionEvent event, Pane view) {
        Alert quitGameAlert = createWarningAlert("Confirm quit application", "Are you sure you want to quit the application?", "Click \"Continue\" to quit.");
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

    public static Alert createInformationAlert(String title, String header, String context){
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(title);
        informationAlert.setHeaderText(header);
        informationAlert.setContentText(context);
        informationAlert.showAndWait();
        return informationAlert;
    }
    public static Alert createWarningAlert(String title, String header, String context){
        Alert warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setTitle(title);
        warningAlert.setHeaderText(header);
        warningAlert.setContentText(context);
        warningAlert.getButtonTypes().clear();
        return warningAlert;
    }
}







