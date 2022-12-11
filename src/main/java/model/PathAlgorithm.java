/**
 * Interface for path algorithms to implement
 */
package model;

import java.util.ArrayList;

public abstract class PathAlgorithm {

    protected final int MAX_X_COORDINATE;
    protected final int MAX_Y_COORDINATE;
    protected Graph graph;
    protected Node startNode;
    protected Node endNode;

    protected PathAlgorithm(Graph graph) {
        MAX_X_COORDINATE = graph.getWIDTH();
        MAX_Y_COORDINATE = graph.getHEIGHT();

        this.graph = graph;
    }

    /**
     * Visits the next node according to the algorithm.
     */
    public abstract void visitNext();

    /**
     * Determines if destination node is reached.
     * @return - boolean value whether path is found
     */
    public boolean pathIsFound() {
        return endNode.getState() == NodeState.VISITED;
    }
    
    /**
     * Getter for all visited tiles
     * @return - a list of all visited tiles
     */
    public ArrayList<Node> getVisited() {
        return graph.getVisitedNodes();
    }

    /**
     * Getter for all obstacle tiles
     * @return - a list of all obstacle tiles
     */
    public ArrayList<Node> getObstacles() {
        return graph.getObstacleNodes();
    }

    /**
     * Method returns the path from end node to start node
     * @return - a linked list containing the path from end node to start node
     */
    public ArrayList<Node> getPath() {
        ArrayList<Node> list = new ArrayList<>();
        Node temp = endNode;
        System.out.println("Distance: " + temp.dist);

        while(temp != null) {
            list.add(temp);
            temp = temp.getPrev();
        }
        return list;
    }

    /**
     * Method pre-processes different kind of nodes of the graph and their neighbors.
     * @param startPoint - defined starting position
     * @param endPoint - defined end position
     */
    protected void preProcessNodes(Point startPoint, Point endPoint) {

        if(graph == null) {
            System.out.println("Graph variable has not been assigned");
            return;
        }
        boolean[][] obstacleMap = graph.getClonedObstacleMap();
        graph = new Graph();

        for (int x = 0; x < MAX_X_COORDINATE; x++) {
            for (int y = 0; y < MAX_Y_COORDINATE; y++) {
                Node node = new Node(x, y);
                if(obstacleMap[x][y]) {
                    node.setState(NodeState.OBSTACLE);
                }
                graph.addNode(node);
            }
        }
        startNode = graph.getNodeByCoordinate(startPoint.x, startPoint.y);
        startNode.setDist(0);

        endNode = graph.getNodeByCoordinate(endPoint.x, endPoint.y);

        graph.initNeighbors();
    }

    /**
     * Method determines if the neighboring node is positioned diagonally from the origin note or not,
     * and computes distance thereafter.
     * @param a - origin node
     * @param b - the neighbor node
     * @return distance to the neighbor node
     */
    public double getDistToNeighbor(Node a, Node b) {
        int x = a.getXCoordinate(), y = a.getYCoordinate();
        int xNeighbor = b.getXCoordinate(), yNeighbor = b.getYCoordinate();

        int xTemp = x - xNeighbor, yTemp = y - yNeighbor;
        double distance = (xTemp + yTemp) % 2 == 0 ? Math.sqrt(2) : 1.0;
        return distance;
    }

    /**
     * Computes euclidean distance between coordinates.
     * @param startX - start x position
     * @param startY - start y position
     * @param endX - end x position
     * @param endY - end y position
     * @return euclidean distance to from start to end
     */
    public double getEuclideanDistance(int startX, int startY, int endX, int endY) {
        return Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
    }
}