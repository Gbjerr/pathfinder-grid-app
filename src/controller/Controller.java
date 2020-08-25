package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.AStar;
import model.Algorithm;
import model.Dijkstra;
import view.*;

import java.awt.*;

public class Controller implements Runnable{

    private Thread thread;

    private View view;
    private Algorithm alg;

    public Controller(View view) {
        this.view = view;
        initListeners();
    }

    private void initListeners() {

        Graph graph = view.getGraph();
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(view.getAlgoMenu().getValue() == null || view.getStartCoorField().getText() == null ||
                        view.getEndCoorField().getText() == null) return;

                graph.setClear();
                graph.drawGrid();
                graph.drawWall();
                int[] startCoor = getNums(view.getStartCoorField().getText());
                int[] endCoor = getNums(view.getEndCoorField().getText());
                Point startPoint = new Point(startCoor[0], startCoor[1]);
                Point endPoint = new Point(endCoor[0], endCoor[1]);

                if(view.getAlgoMenu().getValue().equals("Dijkstra's algorithm")) {
                    alg = new Dijkstra(startPoint, endPoint);
                }
                else {
                    alg = new AStar(startPoint, endPoint);
                }
                runAlgorithm();
            }
        });

    }

    private void runAlgorithm() {
        thread = new Thread(this, "Path algorithm thread");
        thread.start();
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

    @Override
    public void run() {

        Graph graph = view.getGraph();
        alg.setObstacles(graph.getObstacles());

        while(!alg.endNodeIsVisited()) {

            alg.visitNext();
            //graph.setClear();

            Platform.runLater(() -> {
                graph.drawVisited(alg.getVisited());
                graph.drawGrid();
                graph.drawPoint(new Point(alg.getStartCoor().x, alg.getStartCoor().y));
            });


            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        graph.drawPath(alg.getShortestPath());

    }
}
