module com.example.pathfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens startup to javafx.fxml;
    exports startup;
}