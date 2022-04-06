import OthelloApp.model.GameSession;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenPresenter;
import OthelloApp.view.chooseColorScreen.ChooseColorScreenView;

import OthelloApp.view.gamescreen.GameScreenPresenter;
import OthelloApp.view.gamescreen.GameScreenView;
import javafx.application.Application;
import javafx.scene.Scene;
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
        final ChooseColorScreenView viewChoose = new ChooseColorScreenView();
        new ChooseColorScreenPresenter(viewChoose);
        final Scene scene = new Scene(viewChoose);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

