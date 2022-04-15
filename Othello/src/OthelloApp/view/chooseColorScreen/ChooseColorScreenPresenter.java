package OthelloApp.view.chooseColorScreen;

import OthelloApp.model.Game;
import OthelloApp.model.GameSession;
import OthelloApp.view.gamescreen.GameScreenPresenter;
import OthelloApp.view.gamescreen.GameScreenView;
import OthelloApp.view.welcomeScreen.WelcomeScreenPresenter;
import OthelloApp.view.welcomeScreen.WelcomeScreenView;
import javafx.stage.Stage;


public class ChooseColorScreenPresenter {
    private final ChooseColorScreenView view;


    public ChooseColorScreenPresenter(ChooseColorScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartButton().setOnAction(event -> {
            boolean userGoesFirst = view.getSelectedColorButtonText().toLowerCase().equals("black") ? true : false;
            String userName = view.getNameField().getText();
            showGameScreen(userGoesFirst, userName);
        });

        view.getBackButton().setOnAction(event -> {
            showWelcomeScreen();
        });
    }

    private void showGameScreen(boolean userGoesFirst, String userName) {
        GameScreenView gameScreenView = new GameScreenView();
        Game model = new Game(userGoesFirst, userName);
        GameScreenPresenter gameScreenPresenter = new GameScreenPresenter(model, gameScreenView);
        view.getScene().setRoot(gameScreenView);
        gameScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) gameScreenView.getScene().getWindow();
        stage.centerOnScreen();
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
