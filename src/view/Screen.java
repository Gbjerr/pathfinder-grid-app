package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;


public class Screen extends Canvas{
    private final int WIDTH;
    private final int HEIGHT;

    private ArrayList<Point> obstacles;
    private GraphicsContext gc;

    public Screen() {
        WIDTH = 300;
        HEIGHT = 300;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        gc = getGraphicsContext2D();
        obstacles = new ArrayList<Point>();

    }

    public void drawVisited(ArrayList<Point> list) {
        if(list.size() < 1) return;
        gc.setFill(Color.CORAL);
        for(int i = 0; i < list.size(); i++) {
            gc.fillRect(list.get(i).x * 10, list.get(i).y * 10, 10, 10);
        }

    }

    public void drawWall(ArrayList<Point> list) {

        gc.setFill(Color.BLACK);

        for(Point p : list) {
            gc.fillRect(p.x * 10, p.y * 10, 10, 10);
            obstacles.add(new Point(p.x, p.y));
        }
    }

    public void drawPoint(Point point) {
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(point.x * 10, point.y * 10, 10, 10);
    }

    public void drawGrid() {
        gc.setStroke(Color.BLACK);

        for(int i = 0; i < HEIGHT; i += HEIGHT / 30) {
            gc.strokeLine(0, i, WIDTH, i);
        }
        for(int i = 0; i < WIDTH; i += WIDTH / 30) {
            gc.strokeLine(i, 0, i, HEIGHT);
        }

    }

    public void drawPath(LinkedList<Point> list) {

        gc.setFill(Color.BLUEVIOLET);
        for(Point p : list) {

            gc.fillRect(p.x * 10, p.y * 10, 10, 10);
        }
    }

    public void setClear() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
    }

    public ArrayList<Point> getObstacles() {
        return obstacles;
    }

    public GraphicsContext getGc() {
        return gc;
    }

}
