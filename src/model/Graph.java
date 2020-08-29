package model;

import java.awt.*;
import java.util.ArrayList;

public class Graph {

    private final int WIDTH;
    private final int HEIGHT;

    private ArrayList<Point> obstacles;

    public Graph() {
        obstacles = new ArrayList<>();

        setWall();
        WIDTH = 30;
        HEIGHT = 30;
    }

    private void setWall() {
        for (int x = 0; x < 30; x++) {

            for (int y = 0; y < 30; y++) {

                if(x == 22 && y > 4 && y < 28) {

                    obstacles.add(new Point(x, y));
                }
                if(y == 27 && x > 4 && x < 23) {

                    obstacles.add(new Point(x, y));
                }

                if(y < 15 && x < 15 && x > 5) {

                    obstacles.add(new Point(x, y));
                }

                if(y == 21 && x > 15 && x < 29) {

                    obstacles.add(new Point(x, y));
                }

                if(y > 24 && y < 28 && x > 15 && x < 29) {

                    obstacles.add(new Point(x, y));
                }

            }
        }
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public ArrayList<Point> getObstacles() {
        return obstacles;
    }
}
