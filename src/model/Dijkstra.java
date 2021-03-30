package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class representation of dijkstra path algorithm in a tile system
 */
public class Dijkstra implements Algorithm{

    private final int MAX_X_COOR;
    private final int MAX_Y_COOR;

    private ArrayList<Point> visited;

    private Graph graph;

    private Node[][] tileTable;
    private Node startNode;
    private Node endNode;
    private Node current;

    /**
     *
     * constructor initializes necessary variables and structures for the algorithm
     *
     * @param startPoint - the start point
     * @param endPoint - the destination point
     * @param graph - the graph
     */
    public Dijkstra(Point startPoint, Point endPoint, Graph graph) {

        this.MAX_X_COOR = graph.getWIDTH();
        this.MAX_Y_COOR = graph.getHEIGHT();

        this.tileTable = new Node[MAX_X_COOR][MAX_Y_COOR];
        this.graph = graph;
        this.visited = new ArrayList<Point>();

        // initalize tileTable of nodes
        for (int i = 0; i < MAX_X_COOR; i++) {

            for (int j = 0; j < MAX_Y_COOR; j++) {
                tileTable[i][j] = new Node(i, j);

            }
        }

        startNode = tileTable[startPoint.x][startPoint.y];
        startNode.setDist(0);
        current = startNode;

        endNode = tileTable[endPoint.x][endPoint.y];
    }

    /**
     * method returns the path from end node to start node
     * @return - a linked list containing the path from end node to start node
     */
    @Override
    public LinkedList<Point> getPath() {
        System.out.println("length of path is " + endNode.getDist());
        LinkedList<Point> list = new LinkedList<Point>();
        Node temp = endNode;

        while(temp != null) {

            System.out.println(" <- (" + temp.getxCoor() + " " + temp.getyCoor() + ")");
            list.add(new Point(temp.getxCoor(), temp.getyCoor()));
            temp = temp.getPrev();
        }

        return list;
    }

    /**
     * method returns the minimum cost tile based on its distance
     * @return - the current minimum cost node
     */
    private Node getMinDistReachable() {
        double min = Integer.MAX_VALUE;
        Node minNode = null;

        // go through visited nodes and their neighbors to find the tile which has shortest distance from the start tile
        for(Point p : visited) {
            if(graph.getAdjLists()[p.x][p.y] == null) continue;

            LinkedList<Point> neighbors = graph.getAdjLists()[p.x][p.y];

            int neighborCount = neighbors.size();
            for(Point neighbor : neighbors) {

                if(tileTable[neighbor.x][neighbor.y].isVisited()) {
                    if(neighborCount - 1 == 0) {
                        graph.resetList(p.x, p.y);
                        break;
                    }
                    neighborCount--;
                    continue;
                }

                // update the min node if current min is greater than the distance from start to current node
                if(tileTable[neighbor.x][neighbor.y].getDist() < min) {
                    min = tileTable[neighbor.x][neighbor.y].getDist();
                    minNode = tileTable[neighbor.x][neighbor.y];
                }
            }

        }

        return minNode;
    }

    /**
     * visits given node and its distance to other neighbors
     * @param node - the current node to explore
     */
    private void visit(Node node) {

        // explore the distances from current node to all unvisited neighbors
        for(Point p : graph.getAdjLists()[node.getxCoor()][node.getyCoor()]) {
            if(tileTable[p.x][p.y].isVisited()) {
                continue;
            }

            int xTemp = node.getxCoor() - p.x, yTemp = node.getyCoor() - p.y;
            double temp;
            if((xTemp + yTemp) % 2 == 0) {
                temp = 1.4;
            }
            else {
                temp = 1.0;
            }

            if(temp + node.getDist() < tileTable[p.x][p.y].getDist()) {
                tileTable[p.x][p.y].setDist(temp + node.getDist());
                tileTable[p.x][p.y].setPrev(node);
            }

        }

        node.setVisited();
        visited.add(new Point(node.getxCoor(), node.getyCoor()));
    }

    /**
     * method visits current node, and then sets current node to the minimum cost one
     */
    @Override
    public void visitNext() {
        visit(current);
        current = getMinDistReachable();
    }

    /**
     * getter for the start coordinate
     * @return - coordinate of start node
     */
    @Override
    public Point getStartCoor() {
        return new Point(startNode.getxCoor(), startNode.getyCoor());
    }

    /**
     * getter for all visited tiles
     * @return - a list of all visited tiles
     */
    @Override
    public ArrayList<Point> getVisited() {
        return visited;
    }

    /**
     * returns a boolean showing if destination node is reached or not
     * @return - boolean value whether destination node is visited
     */
    @Override
    public boolean endNodeIsVisited() {
        return endNode.isVisited();
    }

}
