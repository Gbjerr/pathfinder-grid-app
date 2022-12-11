package startup;

import view.View;
import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        View view = new View(primaryStage);
        Scene scene = new Scene(view);

        new Controller(view);

        primaryStage.setTitle("Path finder");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}