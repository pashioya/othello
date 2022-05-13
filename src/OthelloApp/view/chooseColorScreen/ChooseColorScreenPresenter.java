package OthelloApp.view.chooseColorScreen;


import static OthelloApp.screenNavigationUtil.ScreenNavigationUtil.*;

public class ChooseColorScreenPresenter {
    private final ChooseColorScreenView view;
    private final String creationScreen;


    public ChooseColorScreenPresenter(ChooseColorScreenView view, String creationScreen) {
        this.view = view;
        this.creationScreen = creationScreen;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartButton().setOnAction(event -> {
            boolean userGoesFirst = view.getSelectedRadioButtonText(view.getColorToggleGroup()).equalsIgnoreCase("black") ? true : false;
            String userName = view.getNameField().getText();
            String difficultyMode = view.getSelectedRadioButtonText(view.getDifficultyToggleGroup()).toLowerCase();
            showGameScreen(view, userGoesFirst, userName, difficultyMode, false);
        });

        switch (creationScreen) {
            case "welcomeScreen", "gameSessionScreen"  -> {
                view.getBackButton().setOnAction(event -> {
                    showWelcomeScreen(view);
                });
            }
            case "gameSessionStatisticsScreen" -> {
                view.getBackButton().setOnAction(event -> {
                    showGameSessionStatisticsScreen(view);
                });
            }
            case ("gameStatisticsScreen") -> {
                view.getBackButton().setOnAction(event -> {
                    showAllGameStatisticsScreen(view, false);
                });
            }

        }
    }
}
