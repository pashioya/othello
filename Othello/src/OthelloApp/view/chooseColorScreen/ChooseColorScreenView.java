package OthelloApp.view.chooseColorScreen;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class ChooseColorScreenView extends BorderPane {
    private Label chooseAColor;
    private GridPane colorChoicesGrid;
    private Label userName;
    private TextField nameField;
    private RadioButton whiteButton;
    private RadioButton blackButton;
    private ToggleGroup colorToggleGroup;
    private Button startButton;
    private Button backButton;
    private final static Font TITLE_FONT = new Font("Consolas", 40);
    private final static Font BODY_FONT = new Font("Consolas", 15);
    private final double IMAGE_DIMENSION = 200;

    public ChooseColorScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.chooseAColor = new Label("Choose a color");

        this.blackButton = new RadioButton("Black");
        this.whiteButton = new RadioButton("White");
        this.colorToggleGroup = new ToggleGroup();
        this.blackButton.setToggleGroup(colorToggleGroup);
        this.whiteButton.setToggleGroup(colorToggleGroup);
        colorToggleGroup.selectToggle(blackButton);

        this.userName = new Label("Please enter your name:");
        this.nameField = new TextField();
        this.startButton = new Button("Start Game");
        startButton.disableProperty().bind(Bindings.isEmpty(nameField.textProperty()));

        this.backButton = new Button("Back");
        this.colorChoicesGrid = new GridPane();
    }


    private void layoutNodes() {
        this.setPadding(new Insets(10));
        this.setTop(chooseAColor);
        this.setCenter(colorChoicesGrid);
        layoutColorChoiceUserNameGrid();
        layoutBottomGrid();
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(chooseAColor, new Insets(10)) ;
        BorderPane.setAlignment(chooseAColor, Pos.CENTER);
        BorderPane.setMargin(colorChoicesGrid, new Insets(20));
        this.setPadding(new Insets(20));
        setFonts();
    }

    private void setFonts(){
        this.chooseAColor.setFont(TITLE_FONT);
        this.userName.setFont(BODY_FONT);
        this.blackButton.setFont(BODY_FONT);
        this.whiteButton.setFont(BODY_FONT);
        this.backButton.setFont(BODY_FONT);
        this.startButton.setFont(BODY_FONT);

    }

    private void layoutColorChoiceUserNameGrid() {
//        colorChoicesGrid.setGridLinesVisible(true);
        ImageView blackStoneImage = new ImageView(new Image("black.png"));
        ImageView whiteStoneImage = new ImageView(new Image("white.png"));
        blackStoneImage.setFitWidth(IMAGE_DIMENSION);
        blackStoneImage.setFitHeight(IMAGE_DIMENSION);
        whiteStoneImage.setFitWidth(IMAGE_DIMENSION);
        whiteStoneImage.setFitHeight(IMAGE_DIMENSION);
        GridPane.setConstraints(blackStoneImage, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(whiteStoneImage, 1, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(blackButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(whiteButton, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        colorChoicesGrid.getChildren().addAll(blackStoneImage, whiteStoneImage, blackButton, whiteButton);
        colorChoicesGrid.setHgap(10);
        colorChoicesGrid.setVgap(10);
        setRowAndColumnConstraints();
    }

    private void layoutBottomGrid(){
        GridPane gridNameInputButtons = new GridPane();
//        gridNameInputButtons.setGridLinesVisible(true);
        nameField.setPrefWidth(250);
        GridPane.setConstraints(userName, 0,0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
        GridPane.setConstraints(nameField, 1,0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(backButton, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(startButton, 1, 1, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
//        gridNameInputButtons.setPadding(new Insets(10));
        gridNameInputButtons.setVgap(10);
        gridNameInputButtons.getChildren().addAll(userName, nameField, backButton, startButton);
        this.setBottom(gridNameInputButtons);
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



    public Button getBackButton() {
        return backButton;
    }

    public TextField getNameField() {
        return nameField;
    }

    public Button getStartButton() {
        return startButton;
    }

    public String getSelectedColorButtonText() {
        RadioButton button = (RadioButton) colorToggleGroup.getSelectedToggle();
        return button.getText();
    }
}
