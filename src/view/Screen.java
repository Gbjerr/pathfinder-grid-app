package view;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class representation of screen containing methods for painting various tiles
 * and the grid
 */
public class Screen extends Canvas{
    private final int WIDTH;
    private final int HEIGHT;

    private GraphicsContext gc;

    /**
     * constructor
     */
    public Screen() {
        WIDTH = 450;
        HEIGHT = 450;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        gc = getGraphicsContext2D();
        init();
    }

    /**
     * initializes screen
     */
    public void init() {
        Platform.runLater(() -> {
            gc.setFill(Color.web("C0C0C0"));
            gc.fillRect(0, 0, WIDTH, HEIGHT);
            drawGrid();
        });
    }

    public void update(boolean[][] obstacles, ArrayList<Point> visited) {
        Platform.runLater(() -> {
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            drawBackground();
            drawObs(obstacles);
            drawVisited(visited);
            drawGrid();
        });
    }

    /**
     * method paints tiles that have been visited
     * @param list - list of visited tiles
     */
    public void drawVisited(ArrayList<Point> list) {
        if(list.size() < 1) return;
        gc.setFill(Color.web("768FD4"));
        for(int i = 0; i < list.size(); i++) {
            gc.fillRect(list.get(i).x * 15, list.get(i).y * 15, 15, 15);
        }

    }

    /**
     * method draws obstacles
     * @param obstacles - array with obstacles
     */
    public void drawObs(boolean[][] obstacles) {
        gc.setFill(Color.web("393A3D"));

        for(int x = 0; x < WIDTH / 15; x++) {
            for(int y = 0; y < HEIGHT / 15; y++) {
                if(obstacles[x][y]) gc.fillRect(x * 15, y * 15, 15, 15);
            }
        }
    }

    /**
     * method draws given tile
     * @param point
     */
    public void drawPoint(Point point) {

        Platform.runLater(() -> {
            gc.setFill(Color.LIMEGREEN);
            gc.fillRect(point.x * 15, point.y * 15, 15, 15);
        });
    }

    /**
     * method paints grid
     */
    public void drawGrid() {
        gc.setStroke(Color.WHITE);

        for(int i = 0; i < HEIGHT; i += HEIGHT / 30) {
            gc.strokeLine(0, i, WIDTH, i);
        }
        for(int i = 0; i < WIDTH; i += WIDTH / 30) {
            gc.strokeLine(i, 0, i, HEIGHT);
        }

    }

    /**
     * method for draw path from start to destination
     * @param list - the path given as a list
     */
    public void drawPath(LinkedList<Point> list) {

        gc.setFill(Color.web("DE6E2E"));
        for(Point p : list) {

            gc.fillRect(p.x * 15, p.y * 15, 15, 15);
        }
    }

    public void clear() {
        Platform.runLater(() -> {
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            drawBackground();
            drawGrid();
        });
    }


    public GraphicsContext getGc() {
        return gc;
    }

    public void drawBackground() {
        gc.setFill(Color.web("C0C0C0"));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }
}
