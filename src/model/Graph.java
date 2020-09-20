package model;

import java.awt.*;
import java.util.ArrayList;

public class Graph {

    private final int WIDTH;
    private final int HEIGHT;

    private ArrayList<Point> obstacles;

    public Graph() {
        obstacles = new ArrayList<>();

        WIDTH = 30;
        HEIGHT = 30;
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

    public void setObstacle(double x, double y) {
        System.out.println("x = " + (int) x + " y = " + (int) y);
        obstacles.add(new Point((int) x,(int) y));
    }

    public void reset() {
        obstacles.clear();
    }
}
