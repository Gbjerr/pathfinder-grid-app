package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of the view containing all visual modules, screens and buttons
 */
public class View extends Pane {

    private final Screen screen;
    private final Stage parentStage;

    private TextField startCoordinateField;
    private TextField endCoordinateField;
    private ComboBox<String> algoMenu;

    private Button startButton;
    private Button clearButton;
    private VBox dialogPopup;

    public View(Stage parentStage) {
        this.parentStage = parentStage;

        setPrefSize(650, 450);

        screen = new Screen();
        initContent();
    }

    /**
     * method which positions and creates all the UI elements
     */
    private void initContent() {

        startCoordinateField = new TextField("(0, 0)");
        startCoordinateField.setLayoutY(120);
        startCoordinateField.setLayoutX(10);

        endCoordinateField = new TextField("(29, 29)");
        endCoordinateField.setLayoutY(170);
        endCoordinateField.setLayoutX(10);

        screen.setLayoutX(200);

        algoMenu = new ComboBox<>();
        algoMenu.getItems().add("Dijkstra's algorithm");
        algoMenu.getItems().add("A* algorithm");
        algoMenu.getItems().add("Bidirectional Dijkstra");
        algoMenu.getItems().add("Breadth First Search");
        algoMenu.setValue("Bidirectional Dijkstra");
        algoMenu.setLayoutY(240);
        algoMenu.setLayoutX(10);

        startButton = new Button("Visualize Path");
        startButton.setPrefSize(150, 40);
        startButton.setLayoutY(50);
        startButton.setLayoutX(10);

        clearButton = new Button("Clear");
        clearButton.setPrefSize(90, 30);
        clearButton.setLayoutY(290);
        clearButton.setLayoutX(10);

        getChildren().addAll(
                startButton,
                clearButton,
                screen,
                startCoordinateField,
                endCoordinateField,
                algoMenu
        );

    }

    public void activateDialogPopup(String msg) {

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        dialogPopup = new VBox();
        dialogPopup.setPrefSize(200, 130);
        dialogPopup.setAlignment(Pos.CENTER);

        Scene scene = new Scene(dialogPopup);
        Text text = new Text(msg);
        text.setWrappingWidth(150);
        dialogPopup.getChildren().add(text);

        stage.setScene(scene);
        stage.show();

    }

    //-------------------------- Bunch of setters and getters below

    public Screen getScreen() {
        return screen;
    }

    public TextField getStartCoordinateField() {
        return startCoordinateField;
    }

    public TextField getEndCoordinateField() {
        return endCoordinateField;
    }

    public ComboBox<String> getAlgoMenu() {
        return algoMenu;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

}
