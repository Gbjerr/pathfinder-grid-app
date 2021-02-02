package startup;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.View;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        View view = new View();
        Scene scene = new Scene(view);

        new Controller(view);
        scene.getStylesheets().add("startup/styles.css");

        primaryStage.setTitle("Path finder");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
