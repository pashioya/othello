package OthelloApp.view.rules;

import OthelloApp.model.Game;

import static OthelloApp.utilities.ScreenNavigationUtil.showWelcomeScreen;

public class RulesScreenPresenter {
    private final Game model;
    private final RulesScreenView view;

    public RulesScreenPresenter(Game model, RulesScreenView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers(){
        view.getBackButton().setOnAction(event -> {
            showWelcomeScreen(view);
        });
    }

    private void updateView(){
        fillGameSetupRules();
        fillGamePlayRules();
        fillGameEndRules();
    }

    private void fillGameSetupRules(){
        view.getSetup().setText(model.getGameSetupRules());
    }

    private void fillGamePlayRules(){
        view.getGamePlay().setText(model.getGamePlayRules());
    }

    private void fillGameEndRules(){
        view.getGameEnd().setText(model.getGameEndRules());
    }

}
