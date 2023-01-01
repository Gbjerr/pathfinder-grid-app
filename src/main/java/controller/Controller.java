package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import model.*;
import view.Screen;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class which decides what appears on the view based on key listener actions, and decides
 * which path algorithm to activate etc.
 */
public class Controller {

    private final View view;
    private final Graph graph;
    private PathAlgorithm alg;
    private PathFindingProcedure pathFindingProcedure;

    public Controller(View view) {
        this.view = view;
        graph = new Graph();
        graph.populateEmpty();

        prepareForPathfinding(false);
        pathFindingProcedure = new PathFindingProcedure(this.alg, view);

        initListeners();
    }

    /**
     * Initializes listeners which decides how the program should react to user input
     */
    private void initListeners() {

        Screen screen = view.getScreen();

        // key listener for the "Visualize path" button
        view.getStartButton().setOnAction((actionEvent) -> {

            if(pathFindingProcedure.isActive() || !prepareForPathfinding(false)) return;
            pathFindingProcedure = new PathFindingProcedure(alg, view);
            runSelectedGraphAlgorithm();

        });

        // key listener for the "Generate maze" button
        view.getGenMazeButton().setOnAction((actionEvent) -> {

            if(pathFindingProcedure.isActive() || !prepareForPathfinding(true)) return;

            MazeDfsGenerator.generateMaze(graph, alg.getStartPoint(), alg.getEndPoint());
            view.getScreen().render(graph.getObstacleNodes(), graph.getVisitedNodes());
        });

        // key listener for the "Clear" button
        view.getClearButton().setOnAction((actionEvent) -> {

            if(pathFindingProcedure.isActive()) return;
            graph.reset();
            graph.populateEmpty();
            view.resetStats();
            screen.clear(graph.getObstacleNodes());
        });

        // key listener for when mouse is dragged on the tiles to set up obstacles
        screen.setOnMouseDragged((mouseEvent) -> {

            if(pathFindingProcedure.isActive()) return;

            int x = (int) (mouseEvent.getX() - (mouseEvent.getX() % 15)) / 15;
            int y = (int) (mouseEvent.getY() - (mouseEvent.getY()) % 15) / 15;

            if(!graph.isOutOfBounds(x, y)) {

                Node obstacleNode = graph.getNodeByCoordinate(x, y);
                obstacleNode.setState(NodeState.OBSTACLE);
                System.out.printf("(%d, %d)\n", obstacleNode.getXCoordinate(), obstacleNode.getYCoordinate());
                screen.clear(graph.getObstacleNodes());

            }
        });
    }

    private void runSelectedGraphAlgorithm() {
        Screen screen = view.getScreen();

        screen.clear(graph.getObstacleNodes());
        Thread t = new Thread(pathFindingProcedure);
        t.start();
    }

    /**
     * Chooses an algorithm based on the selected item in the combo box.
     */
    private void initPathAlgorithm() {

        Point startPoint = extractPoint(view.getStartCoordinateField().getText());
        Point endPoint = extractPoint(view.getEndCoordinateField().getText());

        if(view.getAlgoMenu().getValue().equals("Dijkstra's algorithm")) {
            alg = new Dijkstra(startPoint, endPoint, graph);
        }
        else if(view.getAlgoMenu().getValue().equals("A* algorithm")){
            alg = new AStar(startPoint, endPoint, graph);
        }
        else if(view.getAlgoMenu().getValue().equals("Bidirectional Dijkstra")) {
            alg = new BidirectionalDijkstra(startPoint, endPoint, graph);
        }
        else {
            alg = new BreadthFirstSearch(startPoint, endPoint, graph);
        }
    }

    /**
     * Extracts the x and y position of a string on format "(x, y)".
     * @param text - the text to extract the coordinate from
     */
    private Point extractPoint(String text) {

        Pattern pattern = Pattern.compile("^\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)$");
        Matcher matcher = pattern.matcher(text);

        Point point = null;
        if(matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            point = new Point(x, y);
        }

        return point;
    }

    private boolean prepareForPathfinding(boolean resetGraph) {

        if (!inputCoordinatesAreValid()) {
            return false;
        }

        if(resetGraph) {
            graph.reset();
        }
        view.resetStats();
        initPathAlgorithm();

        return true;
    }

    private boolean inputCoordinatesAreValid() {
        if(extractPoint(view.getStartCoordinateField().getText()) == null) {
            view.activateDialogPopup("Start coordinate field was entered incorrectly. Write in format: \"(x, y)\"");
            return false;

        } else if(extractPoint(view.getEndCoordinateField().getText()) == null) {
            view.activateDialogPopup("End coordinate field was entered incorrectly. Write in format: \"(x, y)\"");
            return false;
        }
        return true;
    }


    /**
     * Class which defines the path finding procedure and rendering of the screen as it progresses.
     */
    private static class PathFindingProcedure extends Task<Void> {

        private final PathAlgorithm alg;
        private final View view;
        private final Graph graph;
        private final Screen screen;
        private boolean active;

        public PathFindingProcedure(PathAlgorithm alg, View view) {
            this.alg = alg;
            this.view = view;
            graph = alg.getGraph();
            screen = view.getScreen();
        }

        /**
         * Activates path finding procedure.
         */
        @Override
        public Void call() {
            active = true;

            // the path procedure continues as long as the given algorithm has yet to find the goal
            while(!alg.pathIsFound()) {
                try {
                    alg.visitNext();
                    // render the screen with the structures of the algorithm
                    screen.render(graph.getObstacleNodes(), graph.getVisitedNodes());
                    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10);

                } catch (InterruptedException e) {
                    Platform.runLater(() -> view.activateDialogPopup("There is no possible path from start to destination node."));
                    break;
                }
            }

            // render the screen with path if the algorithm found the end goal
            if(alg.pathIsFound()) {
                screen.renderWithPath(graph.getObstacleNodes(), graph.getVisitedNodes(), alg.getPath());
                view.updateStats(graph.getVisitedNodes().size(), alg.getFoundPathDistance());
            }
            active = false;
            return null;
        }

        public boolean isActive() {
            return active;
        }
    }

}