import OthelloApp.view.welcome.WelcomeScreenPresenter;
import OthelloApp.view.welcome.WelcomeScreenView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    public void start(Stage primaryStage) {
        final WelcomeScreenView viewWelcome = new WelcomeScreenView();
        new WelcomeScreenPresenter(viewWelcome);
        final Scene scene = new Scene(viewWelcome);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

