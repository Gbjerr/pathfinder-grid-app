package view;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of the screen where grid, and nodes will be drawn.
 */
public class Screen extends Canvas {

    private final int numTilesPerSquareSide = 30;
    private final GraphicsContext gc;
    private final int tileSideLength;

    /**
     * Constructor sets the dimensions of the screen, with appropriate graphics context
     */
    public Screen() {

        setWidth(450);
        setHeight(450);

        tileSideLength = (int) getWidth() / numTilesPerSquareSide;

        gc = getGraphicsContext2D();
        init();
    }

    /**
     * initializes the screen in its default state
     */
    public void init() {
        Platform.runLater(() -> {
            drawBackground();
            drawGrid();
        });
    }

    /**
     * Method that renders the screen with obstacles and visited nodes taken as arguments
     * @param obstacles - obstacle nodes to be drawn
     * @param visited - visited nodes to be drawn
     */
    public void render(ArrayList<Node> obstacles, ArrayList<Node> visited) {
        Platform.runLater(() -> {
            gc.clearRect(0, 0, getWidth(), getHeight());
            drawBackground();
            drawObstacles(obstacles);
            drawVisited(visited);
            drawGrid();
        });
    }

    /**
     * Method which renders the screen similarly to the previous method, but now with the addition of a found path
     * @param obstacles - obstacles to be drawn
     * @param visited - nodes visited, to be drawn
     * @param path - the path, as a collection of nodes, to be drawn
     */
    public void renderWithPath(List<Node> obstacles, List<Node> visited, List<Node> path) {
        Platform.runLater(() -> {
            gc.clearRect(0, 0, getWidth(), getHeight());
            drawBackground();
            drawObstacles(obstacles);
            drawVisited(visited);
            drawPath(path);
            drawGrid();
        });
    }

    /**
     * method which clears the contents of the screen, however except the obstacles which should remain
     * @param obstacles - obstacle nodes which will be drawn
     */
    public void clear(List<Node> obstacles) {
        Platform.runLater(() -> {
            gc.clearRect(0, 0, getWidth(), getHeight());
            drawBackground();
            drawObstacles(obstacles);
            drawGrid();
        });
    }

    /**
     * method paints tiles that have been visited
     * @param visited - list of visited tiles
     */
    private void drawVisited(List<Node> visited) {
        gc.setFill(Color.web("768FD4"));

        for(Node visitedNode : visited) {
            gc.fillRect(visitedNode.getXCoordinate() * tileSideLength, visitedNode.getYCoordinate() * tileSideLength, tileSideLength, tileSideLength);
        }

    }

    /**
     * Method draws the obstacles
     * @param obstacles - obstacles to be drawn
     */
    private void drawObstacles(List<Node> obstacles) {
        gc.setFill(Color.web("393A3D"));

        for(Node obstacle : obstacles) {
            gc.fillRect(obstacle.getXCoordinate() * tileSideLength, obstacle.getYCoordinate() * tileSideLength, tileSideLength, tileSideLength);
        }
    }

    /**
     * Method draws the lines which makes up the grid
     */
    private void drawGrid() {
        gc.setStroke(Color.WHITE);

        for(int i = 0; i < getHeight(); i += tileSideLength) {
            gc.strokeLine(0, i, getWidth(), i); // vertical line
            gc.strokeLine(i, 0, i, getHeight()); // horizontal line
        }

    }

    /**
     * Draws given path
     * @param list - the path given as a list
     */
    private void drawPath(List<Node> list) {

        gc.setFill(Color.web("DE6E2E"));
        for(Node node : list) {

            gc.fillRect(node.getXCoordinate() * tileSideLength, node.getYCoordinate() * tileSideLength, tileSideLength, tileSideLength);
        }
    }

    /**
     * Draws the default background
     */
    private void drawBackground() {
        gc.setFill(Color.web("C0C0C0"));
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
}
