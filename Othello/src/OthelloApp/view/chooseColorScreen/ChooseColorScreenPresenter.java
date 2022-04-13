package OthelloApp.view.chooseColorScreen;

import OthelloApp.model.GameSession;
import OthelloApp.view.gamescreen.GameScreenPresenter;
import OthelloApp.view.gamescreen.GameScreenView;


public class ChooseColorScreenPresenter {
    private final ChooseColorScreenView view;


    public ChooseColorScreenPresenter(ChooseColorScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartButton().setOnAction(e -> {
            boolean userGoesFirst = view.getSelectedColorButtonText().toLowerCase().equals("black") ? true : false;
            String userName = view.getNameField().getText();
            showGameScreen(userGoesFirst, userName);
        });
    }

    private void showGameScreen(boolean userGoesFirst, String userName) {
        GameScreenView gameScreenView = new GameScreenView();
        GameSession model = new GameSession(userGoesFirst, userName);
        GameScreenPresenter gameScreenPresenter = new GameScreenPresenter(model, gameScreenView);
        view.getScene().setRoot(gameScreenView);
        gameScreenView.getScene().getWindow().sizeToScene();
    }
}
