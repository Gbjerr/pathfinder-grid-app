package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class representation of DFS path algorithm in a grid system
 */
public class DepthFirstSearch implements Algorithm{
    private final int MAX_X_COOR;
    private final int MAX_Y_COOR;

    private LinkedList<Node> stack;
    private Graph graph;
    private ArrayList<Point> visited;

    private Node[][] tileTable;
    private Node startNode;
    private Node endNode;

    /**
     *
     * constructor initializes necessary variables and structures for the algorithm
     *
     * @param startPoint - the start point
     * @param endPoint - the destination point
     * @param graph - the graph
     */
    public DepthFirstSearch(Point startPoint, Point endPoint, Graph graph) {

        this.MAX_X_COOR = graph.getWIDTH();
        this.MAX_Y_COOR = graph.getHEIGHT();

        this.tileTable = new Node[MAX_X_COOR][MAX_Y_COOR];
        this.visited = new ArrayList<Point>();
        this.stack = new LinkedList<Node>();
        this.graph = graph;

        // initalize tileTable of nodes
        for (int i = 0; i < MAX_X_COOR; i++) {

            for (int j = 0; j < MAX_Y_COOR; j++) {
                tileTable[i][j] = new Node(i, j);

            }
        }

        startNode = tileTable[startPoint.x][startPoint.y];
        startNode.setDist(0);
        stack.push(startNode);

        endNode = tileTable[endPoint.x][endPoint.y];

    }

    /**
     * method visits current node, and then sets current node to whatever is on top of the stack
     */
    @Override
    public void visitNext() {
        Node node = stack.pop();
        visit(tileTable[node.getxCoor()][node.getyCoor()]);
    }

    /**
     * visits given node and explores the distances to tiles around it
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

            tileTable[p.x][p.y].setDist(temp + tileTable[p.x][p.y].getDist());
            tileTable[p.x][p.y].setPrev(node);
            stack.push(tileTable[p.x][p.y]);

        }

        tileTable[node.getxCoor()][node.getyCoor()].setVisited();
        visited.add(new Point(node.getxCoor(), node.getyCoor()));

    }

    /**
     * getter for the start coordinate
     * @return - coordinate of start node
     */
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

    /**
     * method returns the path from end node to start node
     * @return - a linked list containing the path from end node to start node
     */
    @Override
    public LinkedList<Point> getPath() {
        System.out.println("length of path is " + tileTable[endNode.getxCoor()][endNode.getyCoor()].getDist());
        LinkedList<Point> list = new LinkedList<Point>();
        Node temp = endNode;

        while(temp != null) {

            System.out.println(" <- (" + temp.getxCoor() + " " + temp.getyCoor() + ")");
            list.add(new Point(temp.getxCoor(), temp.getyCoor()));
            temp = temp.getPrev();
        }

        return list;
    }
}
