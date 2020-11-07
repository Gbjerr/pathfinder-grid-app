package view;

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
        WIDTH = 300;
        HEIGHT = 300;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        gc = getGraphicsContext2D();

    }

    /**
     * method paints tiles that have been visited
     * @param list - list of visited tiles
     */
    public void drawVisited(ArrayList<Point> list) {
        if(list.size() < 1) return;
        gc.setFill(Color.CORAL);
        for(int i = 0; i < list.size(); i++) {
            gc.fillRect(list.get(i).x * 10, list.get(i).y * 10, 10, 10);
        }

    }

    /**
     * method draws obstacles
     * @param obstacles - array with obstacles
     */
    public void drawObs(boolean[][] obstacles) {
        gc.setFill(Color.BLACK);

        for(int x = 0; x < WIDTH / 10; x++) {
            for(int y = 0; y < HEIGHT / 10; y++) {
                if(obstacles[x][y]) gc.fillRect(x * 10, y * 10, 10, 10);
            }
        }
    }

    /**
     * method draws given tile
     * @param point
     */
    public void drawPoint(Point point) {
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(point.x * 10, point.y * 10, 10, 10);
    }

    /**
     * method paints grid
     */
    public void drawGrid() {
        gc.setStroke(Color.BLACK);

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

        gc.setFill(Color.BLUEVIOLET);
        for(Point p : list) {

            gc.fillRect(p.x * 10, p.y * 10, 10, 10);
        }
    }

    public void setClear() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
    }


    public GraphicsContext getGc() {
        return gc;
    }

}
