package OthelloApp.view.chooseColorScreen;


import static OthelloApp.screenNavigationUtil.ScreenNavigationUtil.showGameScreen;
import static OthelloApp.screenNavigationUtil.ScreenNavigationUtil.showWelcomeScreen;


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
