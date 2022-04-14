package OthelloApp.view.rulesScreen;

import OthelloApp.view.welcomeScreen.WelcomeScreenPresenter;
import OthelloApp.view.welcomeScreen.WelcomeScreenView;
import javafx.stage.Stage;

public class RulesScreenPresenter {
    private final RulesScreenView view;

    public RulesScreenPresenter(RulesScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getBackButton().setOnAction(event -> {
            showWelcomeScreen();
        });
    }

    private void showWelcomeScreen(){
        WelcomeScreenView welcomeScreenView = new WelcomeScreenView();
        WelcomeScreenPresenter welcomeScreenPresenter = new WelcomeScreenPresenter(welcomeScreenView);
        view.getScene().setRoot(welcomeScreenView);
        welcomeScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) welcomeScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }
}
