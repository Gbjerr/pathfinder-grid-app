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

        screen = new Screen();
        initContent();
    }

    /**
     * method positions all modules
     */
    public void initContent() {

        startCoorField = new TextField("(0, 0)");
        startCoorField.setLayoutY(120);
        startCoorField.setLayoutX(10);

        endCoorField = new TextField("(29, 29)");
        endCoorField.setLayoutY(170);
        endCoorField.setLayoutX(10);

        screen.setLayoutX(200);

        algoMenu = new ComboBox();
        algoMenu.getItems().add("Dijkstra's algorithm");
        algoMenu.getItems().add("A* algorithm");
        algoMenu.getItems().add("Depth First Search");
        algoMenu.getItems().add("Breadth First Search");
        algoMenu.setValue("Dijkstra's algorithm");
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
