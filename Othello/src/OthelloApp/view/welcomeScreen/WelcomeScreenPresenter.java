package OthelloApp.view.welcomeScreen;

import static OthelloApp.alert_creation.AlertCreation.showResetDatabaseAlert;
import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.*;

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
