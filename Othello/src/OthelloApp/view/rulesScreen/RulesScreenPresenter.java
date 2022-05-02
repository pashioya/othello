package OthelloApp.view.rulesScreen;

import static OthelloApp.screen_navigation_util.SCREEN_NAVIGATION_UTIL.showWelcomeScreen;

public class RulesScreenPresenter {
    private final RulesScreenView view;

    public RulesScreenPresenter(RulesScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getBackButton().setOnAction(event -> {
            showWelcomeScreen(view);
        });
    }


}
