package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.*;
import view.View;
import view.Screen;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class which decides what appears on the screen based on key listener actions, and decides
 * which path algorithm to activate etc.
 */

public class Controller implements Runnable{

    private Thread thread;

    private View view;
    private Algorithm alg;
    private Graph graph;

    public Controller(View view) {
        this.view = view;
        this.graph = new Graph();

        initListeners();


    }

    /**
     * method initiates a bunch of listeners and how the program should react to things
     */
    private void initListeners() {

        Screen screen = view.getScreen();

        // key listener for the "Get shortest path" button
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                if(view.getAlgoMenu().getValue() == null || view.getStartCoorField().getText() == null ||
                        view.getEndCoorField().getText() == null) return;

                screen.setClear();
                screen.drawGrid();
                screen.drawObs(graph.getObstacles());
                graph.initAdjList();

                Point startPoint = extractPoint(view.getStartCoorField().getText());
                Point endPoint = extractPoint(view.getEndCoorField().getText());
                if(startPoint == null || endPoint == null) {
                    System.out.println("Enter the coordinate fields on the format (x, y)");
                    return;
                }

                if(view.getAlgoMenu().getValue().equals("Dijkstra's algorithm")) {
                    alg = new Dijkstra(startPoint, endPoint, graph);
                }
                else if(view.getAlgoMenu().getValue().equals("A* algorithm")){
                    alg = new AStar(startPoint, endPoint, graph);
                }
                else if(view.getAlgoMenu().getValue().equals("Depth First Search")){
                    alg = new DepthFirstSearch(startPoint, endPoint, graph);
                }
                else {
                    alg = new BreadthFirstSearch(startPoint, endPoint, graph);
                }
                runAlgorithm();
            }
        });

        // key listener for the "Clear" button
        view.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graph.reset();
                screen.setClear();
            }
        });

        // listener for when mouse is dragged on the tiles to set up obstacles
        screen.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = (mouseEvent.getX() - (mouseEvent.getX() % 15)) / 15;
                double y = (mouseEvent.getY() - (mouseEvent.getY()) % 15) / 15;

                if(x < graph.getWIDTH() && y < graph.getHEIGHT() && x >= 0 && y >= 0) {
                    graph.setObstacle(x, y);

                    screen.drawObs(graph.getObstacles());
                    screen.drawGrid();
                }
            }
        });

    }

    /**
     * Extracts the x and y position of a string on format "(x, y)"
     * @param text
     * @return
     */
    private Point extractPoint(String text) {

        Pattern pattern = Pattern.compile("^\\(\\d+\\,( \\d+|\\d+)\\)$");
        Matcher matcher = pattern.matcher(text);
        if(!matcher.matches()) return null;

        Point point = new Point();

        for(int i = 1; i < text.length(); i++) {

            char ch = text.charAt(i);
            if(Character.isDigit(ch)) {

                for(int j = i; j < text.length(); j++) {

                    if (!Character.isDigit(text.charAt(j + 1))) {
                        int number = Integer.parseInt(text.substring(i, j + 1));

                        if(i == 1) {
                            point.x = number;
                        } else {
                            point.y = number;
                        }
                        i = j;
                        break;
                    }
                }

            }
        }

        return point;
    }

    /**
     * starts thread and runs the chosen path algorithm
     */
    private void runAlgorithm() {
        thread = new Thread(this, "Path algorithm thread");
        thread.start();
    }

    /**
     * method is run when a path algorithm is selected from a chosen point to a destination point
     */
    @Override
    public void run() {

        Screen screen = view.getScreen();

        while(!alg.endNodeIsVisited()) {

            alg.visitNext();

            Platform.runLater(() -> {
                screen.drawVisited(alg.getVisited());
                screen.drawPoint(new Point(alg.getStartCoor().x, alg.getStartCoor().y));
                screen.drawGrid();
            });


            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        screen.drawPath(alg.getPath());
        screen.drawGrid();

    }
}
