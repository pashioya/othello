package OthelloApp.view.welcomeScreen;

import OthelloApp.model.Game;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import OthelloApp.view.rulesScreen.RulesScreenPresenter;
import OthelloApp.view.rulesScreen.RulesScreenView;
import javafx.stage.Stage;

public class WelcomeScreenPresenter {
    private final WelcomeScreenView view;

    public WelcomeScreenPresenter(WelcomeScreenView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getNewGameButton().setOnAction(event ->{
            showChooseColorScreen();
        });
        view.getRulesButton().setOnAction(event -> {
            showRulesScreen();
        });
        view.getGameStatisticsButton().setOnAction(event ->{
//            showAllGameStatisticsScreen();
        });
    }

    private void showChooseColorScreen(){
        ChooseColorScreenView chooseColorScreenView = new ChooseColorScreenView();
        ChooseColorScreenPresenter ChooseColorScreenPresenter = new ChooseColorScreenPresenter(chooseColorScreenView);
        view.getScene().setRoot(chooseColorScreenView);
        chooseColorScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) chooseColorScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    private void showRulesScreen(){
        RulesScreenView rulesScreenView = new RulesScreenView();
        RulesScreenPresenter rulesScreenPresenter = new RulesScreenPresenter(rulesScreenView);
        view.getScene().setRoot(rulesScreenView);
        rulesScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) rulesScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }


}
