import OthelloApp.model.GameSession;
import OthelloApp.view.gamescreen.GameScreenPresenter;
import OthelloApp.view.gamescreen.GameScreenView;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application{

//    private void setGlobalEventHandler(GameScreenView view) {
//        view.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                view.fireComputerTurnButton(e);
//            }
//        });
//    }


    public void start(Stage primaryStage) {
        final GameSession model = new GameSession(true);
        final GameScreenView view = new GameScreenView();
//        setGlobalEventHandler(view);
        new GameScreenPresenter(model, view);
        final Scene scene = new Scene(view);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
//        primaryStage.setResizable(false);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

