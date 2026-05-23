package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.function.Consumer;

/**
 * View which appears at the start of the program where the user can select the grid dimension.
 */
public class GridConfigView extends Pane {
    private static final int DEFAULT_DIMENSION = 30;
    private static final int MIN_DIMENSION = 2;
    private static final int MAX_DIMENSION = 200;


    public GridConfigView(Consumer<Integer> onGridDimensionSelected) {
        setPrefSize(300, 200);
        initialize(onGridDimensionSelected);
    }

    private void initialize(Consumer<Integer> onGridDimensionSelected) {

        Text configText = new Text("Enter grid dimension (min: " + MIN_DIMENSION + ", max: " + MAX_DIMENSION + "):");
        configText.setLayoutX(50);
        configText.setLayoutY(75);

        TextField dimensionField = new TextField(DEFAULT_DIMENSION + "");
        dimensionField.setPromptText("Enter grid dimension");

        dimensionField.setLayoutX(100);
        dimensionField.setLayoutY(90);
        dimensionField.setPrefWidth(100);

        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(100);
        submitButton.setLayoutY(130);
        submitButton.setPrefWidth(100);
        submitButton.setOnAction(e -> {

            int dimension = DEFAULT_DIMENSION;
            try {
                dimension = Integer.parseInt(dimensionField.getText());
            } catch(NumberFormatException ignored) {}

            if(dimension < MIN_DIMENSION) {
                dimension = MIN_DIMENSION;
            } else if(dimension > MAX_DIMENSION) {
                dimension = MAX_DIMENSION;
            }

            onGridDimensionSelected.accept(dimension);
        });

        getChildren().addAll(configText, dimensionField, submitButton);
    }
}
