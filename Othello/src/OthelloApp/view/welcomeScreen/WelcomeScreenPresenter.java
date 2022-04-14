package OthelloApp.view.welcomeScreen;

import OthelloApp.model.GameSession;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import OthelloApp.view.gamescreen.GameScreenPresenter;
import OthelloApp.view.gamescreen.GameScreenView;

public class WelcomeScreenPresenter {
    private final WelcomeScreenView view;

    public WelcomeScreenPresenter(WelcomeScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getNewGameButton().setOnAction(event ->{
            showChooseColorScreen();
        });
    }

    private void showChooseColorScreen(){
        ChooseColorScreenView chooseColorScreenView = new ChooseColorScreenView();
        ChooseColorScreenPresenter ChooseColorScreenPresenter = new ChooseColorScreenPresenter(chooseColorScreenView);
        view.getScene().setRoot(chooseColorScreenView);
        chooseColorScreenView.getScene().getWindow().sizeToScene();
    }
}
