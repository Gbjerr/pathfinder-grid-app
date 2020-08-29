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

    public void drawWall() {
        ArrayList<Point> list = new ArrayList<Point>();

        gc.setFill(Color.BLACK);

        for (int x = 0; x < 30; x++) {

            for (int y = 0; y < 30; y++) {

                if(x == 22 && y > 4 && y < 28) {

                    gc.fillRect(x * 10, y * 10, 10, 10);
                    obstacles.add(new Point(x, y));
                }
                if(y == 27 && x > 4 && x < 23) {

                    gc.fillRect(x * 10, y * 10, 10, 10);
                    obstacles.add(new Point(x, y));
                }

                if(y < 15 && x < 15 && x > 5) {

                    gc.fillRect(x * 10, y * 10, 10, 10);
                    obstacles.add(new Point(x, y));
                }

                if(y == 21 && x > 15 && x < 29) {

                    gc.fillRect(x * 10, y * 10, 10, 10);
                    obstacles.add(new Point(x, y));
                }

                if(y > 24 && y < 28 && x > 15 && x < 29) {

                    gc.fillRect(x * 10, y * 10, 10, 10);
                    obstacles.add(new Point(x, y));
                }

            }
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

}
