package model;

import java.awt.Point;
import java.util.LinkedList;

/**
 * Class representation of a graph used in with vertices and its neighbors represented as adjacency lists
 * and obstacles marked out with an boolean array
 */
public class Graph {

    // width and height of grid graph
    private static int WIDTH;
    private static int HEIGHT;

    private static boolean[][] obstacles;
    private LinkedList<Point>[][] adj;

    public Graph() {
        WIDTH = 30;
        HEIGHT = 30;

        obstacles = new boolean[WIDTH][HEIGHT];
        adj = new LinkedList[WIDTH][HEIGHT];

    }

    /**
     * initializes adjacency lists
     */
    public void initAdjList() {

        for(int x = 0; x < adj[0].length; x++) {
            for(int y = 0; y < adj[1].length; y++) {

                if(obstacles[x][y]) continue;
                adj[x][y] = new LinkedList<Point>();

                for(int i = x - 1; i < x + 2; i++) {
                    for(int j = y - 1; j < y + 2; j++) {

                        // if coordinate (i, j) is out of bounds, equal to (x, y) or if its an obstacle then skip
                        if(isOutOfBounds(i, j) || (i == x && j == y) || obstacles[i][j]) {
                            continue;
                        }

                        adj[x][y].push(new Point(i, j));
                    }
                }
            }
        }
    }

    /**
     * method resets adjacency lists and obstacles of graph
     */
    public void reset() {

        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                obstacles[x][y] = false;
                adj[x][y] = null;
            }
        }
    }

    /**
     *
     * @param x - x coordinate
     * @param y - y coordinate
     * @return - boolean whether the coordinate is out of bounds or not
     */
    private boolean isOutOfBounds(int x, int y) {
        return (x < 0) || !(x < adj[0].length) ||
                (y < 0) || !(y < adj[1].length);
    }

    //-------------------------- Bunch of setters and getters below
    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public LinkedList<Point>[][] getAdjLists() {
        return adj;
    }

    public boolean[][] getObstacles() {
        return obstacles;
    }

    public void setObstacle(double x, double y) {
        obstacles[(int) x][(int) y] = true;
    }


}
