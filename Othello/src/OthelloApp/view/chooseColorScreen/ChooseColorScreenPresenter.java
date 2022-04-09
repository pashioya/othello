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
        view.getBlackButton().setOnAction(e -> {
            showGameScreen(true);
        });
        view.getWhiteButton().setOnAction(e -> {
            showGameScreen(false);
        });
    }

    private void showGameScreen(boolean userGoesFirst) {
        GameScreenView gameScreenView = new GameScreenView();
        GameSession model = new GameSession(userGoesFirst, "Dora");
        GameScreenPresenter gameScreenPresenter = new GameScreenPresenter(model, gameScreenView);
        view.getScene().setRoot(gameScreenView);
        gameScreenView.getScene().getWindow().sizeToScene();
    }
}
