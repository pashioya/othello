package OthelloApp.view.welcome;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static OthelloApp.utilities.AlertCreationUtil.createWarningAlert;
import static OthelloApp.dataManager.DataManager.clearAllData;
import static OthelloApp.utilities.ScreenNavigationUtil.*;

public class WelcomeScreenPresenter {
    private final WelcomeScreenView view;

    public WelcomeScreenPresenter(WelcomeScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getNewGameButton().setOnAction(event ->{
            showChooseColorScreen(view, "welcomeScreen");
        });
        view.getRulesButton().setOnAction(event -> {
            showRulesScreen(view);
        });
        view.getGameStatisticsButton().setOnAction(event ->{
            showAllGameStatisticsScreen(view, true);
        });
        view.getResetDataBaseButton().setOnAction(event -> {
            showResetDatabaseAlert();
        });
    }

    private void showResetDatabaseAlert() {
        Alert resetDatabaseAlert = createWarningAlert("Confirm clear database", "Are you sure you want to clear the contents of the database?", "Click \"Continue\" to delete all data from previous game sessions.");
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType cancelButton = new ButtonType("Cancel");
        resetDatabaseAlert.getButtonTypes().addAll(continueButton, cancelButton);
        Optional<ButtonType> result = resetDatabaseAlert.showAndWait();
        if (result.get() == continueButton) {
            clearAllData();
        }
    }

}
