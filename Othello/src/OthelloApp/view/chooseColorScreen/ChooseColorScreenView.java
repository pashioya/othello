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
import javafx.scene.text.Text;

public class ChooseColorScreenView extends BorderPane {
    private Text chooseAColor;
    private GridPane colorChoicesGrid;
    private Label userName;
    private TextField nameField;
    private RadioButton whiteButton;
    private RadioButton blackButton;
    private ToggleGroup colorToggleGroup;
    private RadioButton easyDifficultyButton;
    private RadioButton mediumDifficultyButton;
    private RadioButton hardDifficultyButton;
    private ToggleGroup difficultyToggleGroup;
    private Label difficultyModeLabel;
    private Button startButton;
    private Button backButton;
    private final static Font TITLE_FONT = new Font("Consolas", 40);
    private static final Font HEADER_FONT = new Font("Consolas", 20);
    private final static Font BODY_FONT = new Font("Consolas", 15);
    private final double IMAGE_DIMENSION = 200;

    public ChooseColorScreenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.chooseAColor = new Text("Choose a color:");

        initializeColorChoiceRadioButtonGroup();
        initializeDifficultyRadioButtonGroup();
        this.difficultyModeLabel = new Label("Choose a difficulty mode:");

        this.userName = new Label("Please enter your name:");
        this.nameField = new TextField();
        this.startButton = new Button("Start Game");
        startButton.disableProperty().bind(Bindings.isEmpty(nameField.textProperty()));

        this.backButton = new Button("Back");
        this.colorChoicesGrid = new GridPane();
    }

    private void initializeColorChoiceRadioButtonGroup(){
        this.blackButton = new RadioButton("Black");
        this.whiteButton = new RadioButton("White");
        this.colorToggleGroup = new ToggleGroup();
        this.blackButton.setToggleGroup(colorToggleGroup);
        this.whiteButton.setToggleGroup(colorToggleGroup);
        this.colorToggleGroup.selectToggle(blackButton);
    }

    private void initializeDifficultyRadioButtonGroup(){
        this.easyDifficultyButton = new RadioButton("Easy");
        this.mediumDifficultyButton = new RadioButton("Medium");
        this.hardDifficultyButton = new RadioButton("Hard");
        this.difficultyToggleGroup = new ToggleGroup();
        this.easyDifficultyButton.setToggleGroup(difficultyToggleGroup);
        this.mediumDifficultyButton.setToggleGroup(difficultyToggleGroup);
        this.hardDifficultyButton.setToggleGroup(difficultyToggleGroup);
        this.difficultyToggleGroup.selectToggle(mediumDifficultyButton);
    }

    private void layoutNodes() {
        this.setPadding(new Insets(10));
        this.setTop(chooseAColor);
        this.setCenter(colorChoicesGrid);
        layoutColorChoiceUserNameGrid();
        layoutBottom();
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
        this.difficultyModeLabel.setFont(HEADER_FONT);
        this.easyDifficultyButton.setFont(BODY_FONT);
        this.mediumDifficultyButton.setFont(BODY_FONT);
        this.hardDifficultyButton.setFont(BODY_FONT);

    }

    private void layoutColorChoiceUserNameGrid() {
        ImageView blackStoneImage = new ImageView(new Image("black.png"));
        ImageView whiteStoneImage = new ImageView(new Image("white.png"));
        setImageDimensions(blackStoneImage, whiteStoneImage);
        GridPane.setConstraints(blackStoneImage, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(whiteStoneImage, 1, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(blackButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(whiteButton, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        colorChoicesGrid.getChildren().addAll(blackStoneImage, whiteStoneImage, blackButton, whiteButton);
        colorChoicesGrid.setHgap(10);
        colorChoicesGrid.setVgap(10);
        setRowAndColumnConstraints();
    }

    private void setImageDimensions(ImageView blackStoneImage, ImageView whiteStoneImage){
        blackStoneImage.setFitWidth(IMAGE_DIMENSION);
        blackStoneImage.setFitHeight(IMAGE_DIMENSION);
        whiteStoneImage.setFitWidth(IMAGE_DIMENSION);
        whiteStoneImage.setFitHeight(IMAGE_DIMENSION);
    }

    private void layoutBottom(){
        HBox radioButtonsHBox = new HBox();
        radioButtonsHBox.getChildren().addAll(easyDifficultyButton, mediumDifficultyButton, hardDifficultyButton);
        radioButtonsHBox.setAlignment(Pos.CENTER);
        radioButtonsHBox.setSpacing(35);
        GridPane gridNameInputButtons = new GridPane();
        nameField.setPrefWidth(250);
        GridPane.setConstraints(userName, 0,0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
        GridPane.setConstraints(nameField, 1,0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(backButton, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(startButton, 1, 1, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        gridNameInputButtons.setVgap(10);
        gridNameInputButtons.getChildren().addAll(userName, nameField, backButton, startButton);
        VBox bottomVBox = new VBox();
        bottomVBox.getChildren().addAll(difficultyModeLabel,radioButtonsHBox, gridNameInputButtons);
        bottomVBox.setSpacing(15);
        bottomVBox.setAlignment(Pos.CENTER);
        this.setBottom(bottomVBox);
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

    public String getSelectedRadioButtonText(ToggleGroup toggleGroup) {
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        return button.getText();
    }

    public ToggleGroup getColorToggleGroup() {
        return colorToggleGroup;
    }

    public ToggleGroup getDifficultyToggleGroup() {
        return difficultyToggleGroup;
    }
}
