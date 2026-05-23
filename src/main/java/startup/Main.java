package startup;

import model.Graph;
import view.GridConfigView;
import view.View;
import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        GridConfigView gridConfigView = new GridConfigView(gridDimension -> {
            Graph graph = new Graph(gridDimension, gridDimension);
            View mainView = new View(primaryStage, gridDimension);
            new Controller(mainView, graph);

            primaryStage.setTitle("Path finder");
            primaryStage.setScene(new Scene(mainView));
            primaryStage.setResizable(false);
        });

        primaryStage.setScene(new Scene(gridConfigView));
        primaryStage.setTitle("Grid configuration");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}