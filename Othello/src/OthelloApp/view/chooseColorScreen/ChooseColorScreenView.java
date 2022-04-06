package OthelloApp.view.chooseColorScreen;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class ChooseColorScreenView extends BorderPane {
    private Label chooseAColor;
    private GridPane colorChoicesGrid;
    private Button whiteButton;
    private Button blackButton;
    private Button backButton;
//    private final String BUTTON_STYLE = "-fx-border-color:black; -fx-background-radius: 0; -fx-border-width: 2px";
    private final String FONT_FAMILY = "Consolas";
    private final double IMAGE_DIMENSION = 200;

    public ChooseColorScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.chooseAColor = new Label("Choose a color");
        this.blackButton = new Button("Black");
        this.whiteButton = new Button("White");
        this.backButton = new Button("Back");
        this.colorChoicesGrid = new GridPane();
//        this.colorChoicesGrid.setGridLinesVisible(true);
    }

    private void layoutNodes() {
        this.setPadding(new Insets(10));
        this.setTop(chooseAColor);
        this.setCenter(colorChoicesGrid);
        this.setBottom(backButton);
        layoutColorChoiceGrid();
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(chooseAColor, new Insets(10)) ;
        BorderPane.setAlignment(chooseAColor, Pos.CENTER);
        styleNodes();
    }

    private void styleNodes(){
        this.chooseAColor.setFont(new Font(FONT_FAMILY, 40));
        Font buttonFont = new Font(FONT_FAMILY, 15);
        this.blackButton.setFont(buttonFont);
        //this.blackButton.setStyle(BUTTON_STYLE);
        this.whiteButton.setFont(buttonFont);
        //this.whiteButton.setStyle(BUTTON_STYLE);
        this.backButton.setFont(buttonFont);
        //this.backButton.setStyle(BUTTON_STYLE);

    }

    private void layoutColorChoiceGrid() {
        ImageView blackStoneImage = new ImageView(new Image("black.png"));
        ImageView whiteStoneImage = new ImageView(new Image("white.png"));
        blackStoneImage.setFitWidth(IMAGE_DIMENSION);
        blackStoneImage.setFitHeight(IMAGE_DIMENSION);
        whiteStoneImage.setFitWidth(IMAGE_DIMENSION);
        whiteStoneImage.setFitHeight(IMAGE_DIMENSION);
        setRowAndColumnConstraints();
        GridPane.setConstraints(blackStoneImage, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(whiteStoneImage, 1, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(blackButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(whiteButton, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        colorChoicesGrid.getChildren().addAll(blackStoneImage, whiteStoneImage, blackButton, whiteButton);
    }

    private void setRowAndColumnConstraints(){
        int gridDimension = 2 ;
        for (int row = 0 ; row < gridDimension ; row++ ){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.ALWAYS);
            colorChoicesGrid.getRowConstraints().add(rowConstraints);
        }
        for (int col = 0 ; col < gridDimension; col++ ) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setHgrow(Priority.ALWAYS);
            colorChoicesGrid.getColumnConstraints().add(columnConstraints);
        }
    }


    public GridPane getColorChoicesGrid() {
        return colorChoicesGrid;
    }

    public Button getWhiteButton() {
        return whiteButton;
    }

    public Button getBlackButton() {
        return blackButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
