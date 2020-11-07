package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Class representation of the view containing all visual modules, screens and buttons
 */
public class View extends Pane {

    private Screen screen;

    private TextField startCoorField;
    private TextField endCoorField;
    private ComboBox algoMenu;

    private Button startButton;
    private Button clearButton;

    /**
     * constructor initializes all visual modules
     */
    public View() {
        setPrefSize(300, 430);

        startCoorField = new TextField();
        endCoorField = new TextField();

        algoMenu = new ComboBox();
        algoMenu.getItems().add("Dijkstra's algorithm");
        algoMenu.getItems().add("A* algorithm");
        algoMenu.getItems().add("Depth First Search");
        algoMenu.getItems().add("Breadth First Search");

        startButton = new Button("Get Shortest path");
        clearButton = new Button("Clear");

        screen = new Screen();
        initContent();
    }

    /**
     * method positions all modules
     */
    public void initContent() {

        startCoorField.setLayoutY(40);
        startCoorField.setLayoutX(70);

        endCoorField.setLayoutY(70);
        endCoorField.setLayoutX(70);

        screen.setLayoutY(130);

        algoMenu.setLayoutY(100);
        algoMenu.setLayoutX(70);

        startButton.setLayoutX(90);
        startButton.setLayoutY(10);

        clearButton.setLayoutY(100);
        clearButton.setLayoutX(240);

        screen.drawGrid();

        getChildren().addAll(startButton, clearButton, screen, startCoorField, endCoorField, algoMenu);

    }

    //-------------------------- Bunch of setters and getters below

    public Screen getScreen() {
        return screen;
    }

    public TextField getStartCoorField() {
        return startCoorField;
    }

    public TextField getEndCoorField() {
        return endCoorField;
    }

    public ComboBox getAlgoMenu() {
        return algoMenu;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getClearButton() {
        return clearButton;
    }
}
