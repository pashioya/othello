package OthelloApp.view.chooseColorScreen;


import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.showGameScreen;
import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.showWelcomeScreen;


public class ChooseColorScreenPresenter {
    private final ChooseColorScreenView view;


    public ChooseColorScreenPresenter(ChooseColorScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartButton().setOnAction(event -> {
            boolean userGoesFirst = view.getSelectedRadioButtonText(view.getColorToggleGroup()).toLowerCase().equals("black") ? true : false;
            String userName = view.getNameField().getText();
            String difficultyMode = view.getSelectedRadioButtonText(view.getDifficultyToggleGroup()).toLowerCase();
            showGameScreen(view, userGoesFirst, userName, difficultyMode, false);
        });

        view.getBackButton().setOnAction(event -> {
            showWelcomeScreen(view);
        });
    }
}
