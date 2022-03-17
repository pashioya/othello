import OthelloApp.model.GameSession;
import OthelloApp.view.gamescreen.GameScreenPresenter;
import OthelloApp.view.gamescreen.GameScreenView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{


    public void start(Stage primaryStage) {
        final GameSession model = new GameSession();
        final GameScreenView view = new GameScreenView();
        new GameScreenPresenter(model, view);
        final Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

