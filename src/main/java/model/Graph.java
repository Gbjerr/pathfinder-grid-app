package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representation of a graph, which consists of a list of nodes where each node has a reference to its neighbors.
 */
public class Graph {

    // width and height of grid graph
    private final int width;
    private final int height;
    private final Node[][] nodes;
    private final List<Node> obstacleNodes;
    private final List<Node> visitedNodes;

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        nodes = new Node[this.width][this.height];
        obstacleNodes = Collections.synchronizedList(new ArrayList<>());
        visitedNodes = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Method is used when a new graph should be created, but with the obstacles of a previous one.
     * @return - a two-dimensional array where obstacles are marked.
     */
    public boolean[][] getClonedObstacleMap() {
        boolean[][] obstacleMap = new boolean[width][height];
        getObstacleNodes().forEach(n ->
                obstacleMap[n.getXCoordinate()][n.getYCoordinate()] = true
        );

        return obstacleMap;
    }

    /**
     * Method used to determine if a position is out of bounds.
     * @param x - x coordinate
     * @param y - y coordinate
     * @return - returns whether the coordinate is out of bounds or not
     */
    public boolean isOutOfBounds(int x, int y) {
        return (x < 0) || !(x < width) ||
                (y < 0) || !(y < height);
    }

    //-------------------------- Bunch of setters and getters below
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Node> getObstacleNodes() {
        return this.obstacleNodes;
    }

    public List<Node> getVisitedNodes() {
        return this.visitedNodes;
    }

    public List<Node> getNeighborsFromNode(Node node) {
        List<Node> neighbors = new ArrayList<>();

        int x = node.getXCoordinate();
        int y = node.getYCoordinate();
        for(int neighborX = x - 1; neighborX < x + 2; neighborX++) {
            for(int neighborY = y - 1; neighborY < y + 2; neighborY++) {

                // if coordinate (i, j) is out of bounds, equal to (x, y) or if it's an obstacle then skip
                if(isOutOfBounds(neighborX, neighborY)
                        || (neighborX == x && neighborY == y)
                        || nodes[neighborX][neighborY].getState() == NodeState.OBSTACLE) {
                    continue;
                }

                neighbors.add(getNodeByCoordinate(neighborX, neighborY));
            }
        }

        return neighbors;
    }

    public List<Node> getNodes() {
        List<Node> visitedNodes = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                visitedNodes.add(nodes[x][y]);
            }
        }
        return visitedNodes;
    }

    public Node getNodeByCoordinate(int x, int y) {
        if(isOutOfBounds(x, y)) {
            System.out.println("Requested node is out of bounds.");
            return null;
        }
        return nodes[x][y];
    }

    public void populateEmpty() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
    }

    public void reset() {
        for(int i = 0; i < width; i++) {
            Arrays.fill(nodes[i], null);
        }
        obstacleNodes.clear();
        visitedNodes.clear();
    }

    public void setNode(int x, int y, Node node) {
        if(nodes == null) {
            System.out.println("Graph variable has not been assigned");
            return;
        }
        if(isOutOfBounds(x, y)) {
            System.out.println("Requested node is out of bounds.");
            return;
        }

        nodes[x][y] = node;
    }
}
