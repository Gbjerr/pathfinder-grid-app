package model;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Representation of a graph, which consists of a list of nodes where each node has a reference to its neighbors.
 */
public class Graph {

    // width and height of grid graph
    private static int WIDTH;
    private static int HEIGHT;
    private ArrayList<Node> nodes;

    public Graph() {
        WIDTH = 30;
        HEIGHT = 30;
        nodes = new ArrayList<>();
    }

    /**
     * Initializes adjacency lists.
     */
    public void initNeighbors() {

        for(Node node : nodes) {
                if(node.getState() == NodeState.OBSTACLE) continue;
                ArrayList<Node> neighbors = new ArrayList<>();

                int x = node.getXCoordinate();
                int y = node.getYCoordinate();
                for(int i = x - 1; i < x + 2; i++) {
                    for(int j = y - 1; j < y + 2; j++) {

                        // if coordinate (i, j) is out of bounds, equal to (x, y) or if it's an obstacle then skip
                        if(isOutOfBounds(i, j) || (i == x && j == y) || getNodeByCoordinate(i, j).getState() == NodeState.OBSTACLE) continue;

                        int xNeighbor = i;
                        int yNeighbor = j;

                        neighbors.add(
                                getNodeByCoordinate(xNeighbor, yNeighbor)
                        );
                    }
                }
                node.setNeighbors(neighbors);
        }
    }

    /**
     * Method is used when a new graph should be created, but with the obstacles of a previous one.
     * @return - a two-dimensional array where obstacles are marked.
     */
    public boolean[][] getClonedObstacleMap() {
        boolean[][] obstacleMap = new boolean[WIDTH][HEIGHT];
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
        return (x < 0) || !(x < WIDTH) ||
                (y < 0) || !(y < HEIGHT);
    }

    //-------------------------- Bunch of setters and getters below
    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public ArrayList<Node> getObstacleNodes() {
        return new ArrayList<>(
                nodes.stream().filter(n -> n.getState() == NodeState.OBSTACLE).toList()
        );
    }

    public ArrayList<Node> getVisitedNodes() {
        return new ArrayList<>(
                nodes.stream().filter(n -> n.getState() == NodeState.VISITED).toList()
        );
    }

    public ArrayList<Node> getNeighborsFromNode(Node node) {
        int idx = nodes.indexOf(node);
        if(idx == -1) return new ArrayList<>();

        return nodes.get(idx).getNeighbors();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public Node getNodeByCoordinate(int x, int y) {
        Optional<Node> optNode = getNodes().stream().filter(
                n -> x == n.getXCoordinate() && y == n.getYCoordinate()
        ).findFirst();
        if(optNode.isEmpty()) {
            System.out.println("Node could not be found");
            return null;
        }
        return optNode.get();
    }

    public void populateEmpty() {

        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                nodes.add(new Node(x, y));
            }
        }
    }

    public void reset() {
        nodes = new ArrayList<>();
    }
}
