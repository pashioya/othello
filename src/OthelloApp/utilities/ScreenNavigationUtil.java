package OthelloApp.utilities;

import OthelloApp.model.Game;
import OthelloApp.model.GameSession;
import OthelloApp.model.GameSessionStatistics;
import OthelloApp.model.GameStatistics;
import OthelloApp.view.gameStatistics.GameStatisticsScreenPresenter;
import OthelloApp.view.gameStatistics.GameStatisticsScreenView;
import OthelloApp.view.chooseColor.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColor.ChooseColorScreenView;
import OthelloApp.view.gameSessionStatistics.GameSessionStatisticsPresenter;
import OthelloApp.view.gameSessionStatistics.GameSessionStatisticsView;
import OthelloApp.view.gameSession.GameSessionScreenPresenter;
import OthelloApp.view.gameSession.GameSessionScreenView;
import OthelloApp.view.rules.RulesScreenPresenter;
import OthelloApp.view.rules.RulesScreenView;
import OthelloApp.view.welcome.WelcomeScreenPresenter;
import OthelloApp.view.welcome.WelcomeScreenView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class ScreenNavigationUtil {

    public static void showWelcomeScreen(Pane view){
        WelcomeScreenView welcomeScreenView = new WelcomeScreenView();
        WelcomeScreenPresenter welcomeScreenPresenter = new WelcomeScreenPresenter(welcomeScreenView);
        view.getScene().setRoot(welcomeScreenView);
        welcomeScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) welcomeScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    public static void showAllGameStatisticsScreen(Pane view, boolean isCreatedFromWelcomeScreen) {
        GameStatisticsScreenView gameStatisticsScreenView = new GameStatisticsScreenView();
        GameStatisticsScreenPresenter gameStatisticsScreenPresenter = new GameStatisticsScreenPresenter(new GameStatistics(), gameStatisticsScreenView, isCreatedFromWelcomeScreen);
        view.getScene().setRoot(gameStatisticsScreenView);
        gameStatisticsScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) gameStatisticsScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    public static void showChooseColorScreen(Pane view, String creationScreen){
        ChooseColorScreenView chooseColorScreenView = new ChooseColorScreenView();
        ChooseColorScreenPresenter ChooseColorScreenPresenter = new ChooseColorScreenPresenter(chooseColorScreenView, creationScreen);
        view.getScene().setRoot(chooseColorScreenView);
        chooseColorScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) chooseColorScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    public static void showRulesScreen(Pane view){
        RulesScreenView rulesScreenView = new RulesScreenView();
        RulesScreenPresenter rulesScreenPresenter = new RulesScreenPresenter(Game.getInstance(), rulesScreenView);
        view.getScene().setRoot(rulesScreenView);
        rulesScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) rulesScreenView.getScene().getWindow();
        stage.setMinWidth(1000);
        stage.setMinHeight(1000);
        stage.centerOnScreen();
    }

    public static void showGameSessionStatisticsScreen(Pane view){
        GameSessionStatisticsView endScreenView = new GameSessionStatisticsView();
        GameSessionStatistics gameSessionStatistics = new GameStatistics().getLatestGameSessionStatistics();
        GameSessionStatisticsPresenter endScreenPresenter = new GameSessionStatisticsPresenter(gameSessionStatistics, endScreenView);
        view.getScene().setRoot(endScreenView);
        endScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) endScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }

    public static void showGameScreen(Pane view, boolean userGoesFirst, String userName, String difficultyMode, boolean replay) {
        GameSessionScreenView gameSessionScreenView = new GameSessionScreenView(replay);
        GameSession model = Game.createNewGameSession(userGoesFirst, userName, difficultyMode);
        GameSessionScreenPresenter gameSessionScreenPresenter = new GameSessionScreenPresenter(model, gameSessionScreenView);
        view.getScene().setRoot(gameSessionScreenView);
        gameSessionScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) gameSessionScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }
}
