package OthelloApp.screenNavigationUtil;

import OthelloApp.model.Game;
import OthelloApp.model.GameSession;
import OthelloApp.model.GameSessionStatistics;
import OthelloApp.model.GameStatistics;
import OthelloApp.view.gameStatisticsScreen.GameStatisticsScreenPresenter;
import OthelloApp.view.gameStatisticsScreen.GameStatisticsScreenView;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;
import OthelloApp.view.gameSessionStatisticsScreen.GameSessionStatisticsPresenter;
import OthelloApp.view.gameSessionStatisticsScreen.GameSessionStatisticsView;
import OthelloApp.view.gameSessionScreen.GameSessionScreenPresenter;
import OthelloApp.view.gameSessionScreen.GameSessionScreenView;
import OthelloApp.view.rulesScreen.RulesScreenPresenter;
import OthelloApp.view.rulesScreen.RulesScreenView;
import OthelloApp.view.welcomeScreen.WelcomeScreenPresenter;
import OthelloApp.view.welcomeScreen.WelcomeScreenView;
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
        Game model = new Game();
        RulesScreenPresenter rulesScreenPresenter = new RulesScreenPresenter(model, rulesScreenView);
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
        Game game = new Game();
        GameSession model = game.createNewGameSession(userGoesFirst, userName, difficultyMode);
        GameSessionScreenPresenter gameSessionScreenPresenter = new GameSessionScreenPresenter(model, gameSessionScreenView);
        view.getScene().setRoot(gameSessionScreenView);
        gameSessionScreenView.getScene().getWindow().sizeToScene();
        Stage stage = (Stage) gameSessionScreenView.getScene().getWindow();
        stage.centerOnScreen();
    }
}
