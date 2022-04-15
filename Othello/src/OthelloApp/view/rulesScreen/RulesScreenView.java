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
        this.setup = new Text("""
                The game is played on a 8 x 8 board of squares. The game begins with four stones (two white and two black) in four squares in the center of the board.
                """);
        this.setupImage = new Image("gameStart.jpg");
        this.gamePlayImage = new Image("othelloMove.gif");
        this.gamePlayHeader = new Text("Gameplay");
        this.gamePlay = new Text("""
                Othello is a two-player game. One player plays black stones and the other player plays white stones. The player that plays black stones makes the first move.
                
                Players make moves by placing stones of their respective colors on the board. A move is valid only if the placed stone outflanks an opposite-colored stone (or row of opposite-colored stones).
                A stone or row of stones is outflanked when it is bordered by opposite-colored stones at each end. Each player must outflank opposite-colored stones and flip them so they have the player's color.
                A player can flip stones horizontally, vertically, and diagonally.
       
                If a player is not able to flip any stones, the player forfeits his/her turn and the other player plays again. Players may not voluntarily forfeit a turn if a move is available.
                """);
        this.gameEndHeader = new Text("Game End");
        this.gameEnd = new Text("""
                The game is over when neither player can make a move. The player with the most stones of his/her color on the board wins the game. 
                The game may also end if a board is not full and the board only contains stones of one player's color.
                """);
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
        GridPane.setConstraints(setupHeader, 0, 1, 1, 1, HPos.CENTER, VPos.BASELINE, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(setup, 0, 2, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(vBoxSetupImageCaption, 1, 2, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);

        GridPane.setConstraints(gamePlayHeader, 0, 3, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(gamePlay, 0, 4, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(vBoxGamePlayImageCaption, 1, 4, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);

        GridPane.setConstraints(gameEndHeader, 0, 5, 1, 1, HPos.CENTER, VPos.BASELINE, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(gameEnd, 0, 6, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(vBoxGameEndImageCaption, 1, 6, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
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
}
