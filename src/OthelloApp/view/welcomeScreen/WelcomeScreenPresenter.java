package OthelloApp.view.welcomeScreen;

import static OthelloApp.alertCreation.AlertCreation.showResetDatabaseAlert;
import static OthelloApp.screenNavigationUtil.ScreenNavigationUtil.*;

public class WelcomeScreenPresenter {
    private final WelcomeScreenView view;

    public WelcomeScreenPresenter(WelcomeScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getNewGameButton().setOnAction(event ->{
            showChooseColorScreen(view);
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






}
