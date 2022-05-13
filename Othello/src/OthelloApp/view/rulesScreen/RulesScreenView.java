package OthelloApp.view.rulesScreen;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RulesScreenView extends GridPane {
    private Text title;
    private Text setupHeader;
    private Text setup;
    private Image setupImage;
    private Text gamePlayHeader;
    private Text gamePlay;
    private Image gamePlayImage;
    private Text gameEndHeader;
    private Text gameEnd;
    private Image gameEndImage;
    private Button backButton;

    private static final Font TITLE_FONT = new Font("Consolas", 30);
    private static final Font HEADER_FONT = new Font("Consolas", 20);
    private static final Font BODY_FONT = new Font("Consolas", 15);
    private static final double WRAPPING_WIDTH = 600;

    public RulesScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes(){
        this.title = new Text("Othello Rules");
        this.setupHeader = new Text("Game Setup");
        this.setup = new Text();
        this.setupImage = new Image("gameStart.jpg");
        this.gamePlayImage = new Image("othelloMove.gif");
        this.gamePlayHeader = new Text("Gameplay");
        this.gamePlay = new Text();
        this.gameEndHeader = new Text("Game End");
        this.gameEnd = new Text();
        this.gameEndImage = new Image("gameEnd.jpg");
        this.backButton = new Button("Back");
    }

    private void layoutNodes(){
        VBox vBoxSetupImageCaption = createImageViewCaptionPair(new ImageView(setupImage),
                "Board at startup.", 150);
        VBox vBoxGamePlayImageCaption = createImageViewCaptionPair(new ImageView(gamePlayImage),
                "Example of a valid move wherein black flips white stones in two directions.", 200);
        VBox vBoxGameEndImageCaption = createImageViewCaptionPair(new ImageView(gameEndImage),
                "Example of a finished game where white wins with 35 stones.", 150);
//        this.setGridLinesVisible(true);
        GridPane.setConstraints(title, 0, 0, 2, 1, HPos.CENTER, VPos.BASELINE, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(setupHeader, 0, 1, 1, 1, HPos.CENTER, VPos.BOTTOM, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(setup, 0, 2, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(vBoxSetupImageCaption, 1, 1, 1, 2, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);

        GridPane.setConstraints(gamePlayHeader, 0, 3, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(gamePlay, 0, 4, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(vBoxGamePlayImageCaption, 1, 3, 1, 2, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);

        GridPane.setConstraints(gameEndHeader, 0, 5, 1, 1, HPos.CENTER, VPos.BOTTOM, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(gameEnd, 0, 6, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(vBoxGameEndImageCaption, 1, 5, 1, 2, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(backButton, 0, 7, 2, 1, HPos.CENTER, VPos.BASELINE, Priority.ALWAYS, Priority.ALWAYS);
        this.getChildren().addAll(title, setupHeader, setup, vBoxSetupImageCaption, gamePlayHeader, gamePlay, vBoxGamePlayImageCaption,
                gameEndHeader, gameEnd, vBoxGameEndImageCaption, backButton);
        this.setVgap(10);
        this.setHgap(25);
        this.setPadding(new Insets(20));
        setWrappingWidths();
        setFonts();
    }

    private VBox createImageViewCaptionPair(ImageView imageView, String caption, double imageDimension){
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(imageDimension);
        VBox vBoxImageViewCaption = new VBox();
        Label captionLabel = new Label(caption);
        captionLabel.setWrapText(true);
        captionLabel.setMaxWidth(imageDimension);
        captionLabel.setFont(BODY_FONT);
        vBoxImageViewCaption.getChildren().addAll(imageView, captionLabel);
        vBoxImageViewCaption.setSpacing(5);
        vBoxImageViewCaption.setAlignment(Pos.CENTER);
        vBoxImageViewCaption.setPadding(new Insets(10));
        return vBoxImageViewCaption;
    }

    private void setWrappingWidths(){
        for (Node child : this.getChildren()) {
            if (child instanceof Text){
                Text text = (Text) child;
                text.setWrappingWidth(WRAPPING_WIDTH);
            }
        }
    }

    private void setFonts(){
        this.title.setFont(TITLE_FONT);
        this.setupHeader.setFont(HEADER_FONT);
        this.setup.setFont(BODY_FONT);
        this.gamePlayHeader.setFont(HEADER_FONT);
        this.gamePlay.setFont(BODY_FONT);
        this.gameEndHeader.setFont(HEADER_FONT);
        this.gameEnd.setFont(BODY_FONT);
        this.backButton.setFont(BODY_FONT);
    }
    public Button getBackButton() {
        return backButton;
    }


    public Text getSetup() {
        return setup;
    }

    public Text getGamePlay() {
        return gamePlay;
    }

    public Text getGameEnd() {
        return gameEnd;
    }
}
