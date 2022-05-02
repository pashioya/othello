package OthelloApp.view.welcomeScreen;

import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import OthelloApp.view.rulesScreen.RulesScreenPresenter;
import OthelloApp.view.rulesScreen.RulesScreenView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    }






}
