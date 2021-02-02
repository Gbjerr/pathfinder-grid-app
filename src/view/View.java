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
        setPrefSize(650, 450);

        startCoorField = new TextField("(0, 0)");
        endCoorField = new TextField("(29, 29)");

        algoMenu = new ComboBox();
        algoMenu.getItems().add("Dijkstra's algorithm");
        algoMenu.getItems().add("A* algorithm");
        algoMenu.getItems().add("Depth First Search");
        algoMenu.getItems().add("Breadth First Search");
        algoMenu.setValue("Dijkstra's algorithm");

        startButton = new Button("Visualize Path");
        startButton.setPrefSize(150, 40);
        clearButton = new Button("Clear");
        clearButton.setPrefSize(90, 30);

        screen = new Screen();
        initContent();
    }

    /**
     * method positions all modules
     */
    public void initContent() {

        startCoorField.setLayoutY(120);
        startCoorField.setLayoutX(30);

        endCoorField.setLayoutY(170);
        endCoorField.setLayoutX(30);

        screen.setLayoutX(200);

        algoMenu.setLayoutY(240);
        algoMenu.setLayoutX(30);

        startButton.setLayoutY(50);
        startButton.setLayoutX(30);

        clearButton.setLayoutY(290);
        clearButton.setLayoutX(30);

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
