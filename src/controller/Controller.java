package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.*;
import view.View;
import view.Screen;

import java.awt.Point;

/**
 * Controller class which decides what appears on the screen based on key listener actions, and decides
 * which algorithm to activate
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

                int[] startCoor = getNums(view.getStartCoorField().getText());
                int[] endCoor = getNums(view.getEndCoorField().getText());
                Point startPoint = new Point(startCoor[0], startCoor[1]);
                Point endPoint = new Point(endCoor[0], endCoor[1]);

                if(view.getAlgoMenu().getValue().equals("Dijkstra's algorithm")) {
                    alg = new Dijkstra(startPoint, endPoint, graph);
                }
                else if(view.getAlgoMenu().getValue().equals("A* algorithm")){
                    alg = new AStar(startCoor[0], startCoor[1], endCoor[0], endCoor[1], graph);
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
                screen.drawGrid();
            }
        });

        // listener for when mouse is dragged on the tiles to set up obstacles
        screen.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = (mouseEvent.getX() - (mouseEvent.getX() % 10)) / 10;
                double y = (mouseEvent.getY() - (mouseEvent.getY()) % 10) / 10;

                if(x < graph.getWIDTH() && y < graph.getHEIGHT() && x >= 0 && y >= 0) {
                    graph.setObstacle(x, y);

                    screen.getGc().setFill(Color.BLACK);
                    screen.getGc().fillRect(x * 10, y * 10, 10, 10);
                }
            }
        });

    }

    /**
     * string processing
     * @param text
     * @return
     */
    private int[] getNums(String text) {
        int[] res = new int[2];
        int index = 0;
        for(int i = 0; i < text.length(); i++) {
            if(index >= res.length) break;

            char ch = text.charAt(i);
            if(Character.isDigit(ch)) {
                if(Character.isDigit(text.charAt(i + 1))) {
                    res[index] = Integer.parseInt(text.substring(i, i + 2));
                    index++;
                    i++;
                    continue;
                }

                res[index] = Character.getNumericValue(ch);
                index++;
            }
        }

        return res;
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
                screen.drawGrid();
                screen.drawPoint(new Point(alg.getStartCoor().x, alg.getStartCoor().y));
            });


            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        screen.drawPath(alg.getPath());

    }
}
