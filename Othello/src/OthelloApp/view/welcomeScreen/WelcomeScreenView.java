package OthelloApp.view.welcomeScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WelcomeScreenView extends VBox{
    private final Image othelloImage = new Image("othelloImage.png");
    private Button newGameButton;
    private Button rulesButton;
    private Button gameStatisticsButton;
    private final static Font BODY_FONT = new Font("Consolas", 20);

    public WelcomeScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes(){
        this.newGameButton = new Button("New Game");
        this.rulesButton = new Button("Rules");
        this.gameStatisticsButton = new Button("Game Statistics");
    }

    private void layoutNodes(){
        ImageView othelloImageView = new ImageView(othelloImage);
        othelloImageView.setPreserveRatio(true);
        othelloImageView.setFitWidth(500);
        newGameButton.setPrefWidth(200);
        gameStatisticsButton.setPrefWidth(200);
        rulesButton.setPrefWidth(200);
        this.getChildren().addAll(othelloImageView, newGameButton, gameStatisticsButton, rulesButton);
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        setFonts();
    }

    public void setFonts(){
        newGameButton.setFont(BODY_FONT);
        gameStatisticsButton.setFont(BODY_FONT);
        rulesButton.setFont(BODY_FONT);
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getGameStatisticsButton() {
        return gameStatisticsButton;
    }
}
