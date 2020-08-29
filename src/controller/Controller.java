package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.*;

import java.awt.Point;

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

    private void initListeners() {

        Screen screen = view.getScreen();
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(view.getAlgoMenu().getValue() == null || view.getStartCoorField().getText() == null ||
                        view.getEndCoorField().getText() == null) return;

                screen.setClear();
                screen.drawGrid();
                screen.drawWall();
                int[] startCoor = getNums(view.getStartCoorField().getText());
                int[] endCoor = getNums(view.getEndCoorField().getText());
                Point startPoint = new Point(startCoor[0], startCoor[1]);
                Point endPoint = new Point(endCoor[0], endCoor[1]);

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

    }

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

    private void runAlgorithm() {
        thread = new Thread(this, "Path algorithm thread");
        thread.start();
    }

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
