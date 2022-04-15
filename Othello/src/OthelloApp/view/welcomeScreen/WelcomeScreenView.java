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
    private final static Font BODY_FONT = new Font("Consolas", 20);
    private final static double IMAGE_WIDTH = 500;
    private final static double BUTTON_WIDTH = 200;

    public WelcomeScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes(){
        this.newGameButton = new Button("New Game");
        this.rulesButton = new Button("Rules");
    }

    private void layoutNodes(){
        ImageView othelloImageView = new ImageView(othelloImage);
        othelloImageView.setPreserveRatio(true);
        othelloImageView.setFitWidth(IMAGE_WIDTH);
        newGameButton.setPrefWidth(BUTTON_WIDTH);
        rulesButton.setPrefWidth(BUTTON_WIDTH);
        this.getChildren().addAll(othelloImageView, newGameButton, rulesButton);
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(rulesButton, new Insets(0, 0, 50, 0));
        setFonts();
    }

    public void setFonts(){
        newGameButton.setFont(BODY_FONT);
        rulesButton.setFont(BODY_FONT);
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }
}
