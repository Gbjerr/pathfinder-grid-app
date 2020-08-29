package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class View extends Pane {

    private Screen screen;

    private TextField startCoorField;
    private TextField endCoorField;
    private ComboBox algoMenu;
    private Button startButton;

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

        screen = new Screen();
        initContent();
    }

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

        screen.drawGrid();
        screen.drawWall();

        getChildren().addAll(startButton, screen, startCoorField, endCoorField, algoMenu);

    }


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
}
