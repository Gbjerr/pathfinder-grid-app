package view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Label nodesExpanded;
    private Label pathLength;

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

        Pane statBox = new Pane();
        statBox.setPrefSize(197, 70);
        statBox.setLayoutX(2);
        statBox.setLayoutY(370);


        statBox.setStyle("-fx-opacity: 0.5; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 10 10; -fx-background-color: coral;");
        nodesExpanded = new Label("Nodes expanded: -- ");
        nodesExpanded.setStyle("-fx-font-weight: bold;");
        nodesExpanded.setLayoutX(7);
        nodesExpanded.setLayoutY(385);
        pathLength = new Label("Path length: -- ");
        pathLength.setStyle("-fx-font-weight: bold;");
        pathLength.setLayoutX(7);
        pathLength.setLayoutY(410);

        getChildren().addAll(
                startButton,
                clearButton,
                screen,
                startCoordinateField,
                endCoordinateField,
                algoMenu,
                statBox,
                nodesExpanded,
                pathLength
        );

    }

    public void activateDialogPopup(String msg) {

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        VBox dialogPopup = new VBox();
        dialogPopup.setPrefSize(200, 130);
        dialogPopup.setAlignment(Pos.CENTER);

        Scene scene = new Scene(dialogPopup);
        Text text = new Text(msg);
        text.setWrappingWidth(150);
        dialogPopup.getChildren().add(text);

        stage.setScene(scene);
        stage.show();

    }

    // show blank stats
    public void resetStats() {
        Platform.runLater(() -> {
            nodesExpanded.setText("Nodes expanded: -- ");
            pathLength.setText("Path length: --");
        });
    }

    // update stats with the number of nodes and length of path
    public void updateStats(int numNodesExpanded, double distance) {
        Platform.runLater(() -> {
            nodesExpanded.setText("Nodes expanded: " + numNodesExpanded);
            pathLength.setText(String.format("Path length: %.2f units", distance));
        });
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
