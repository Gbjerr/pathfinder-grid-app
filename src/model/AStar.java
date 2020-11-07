package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * AStar path algorithm representation in a grid system
 */
public class AStar implements Algorithm{

    private final int MAX_X_COOR;
    private final int MAX_Y_COOR;

    private ArrayList<Point> visited;

    private Graph graph;

    private AStarNode[][] tileTable;

    private AStarNode startNode;
    private AStarNode endNode;
    private AStarNode currentNode;

    /**
     * constructor initializes necessary variables and structures for the algorithm
     *
     * @param startTileX - x coordinate for start tile
     * @param startTileY - y coordinate for start tile
     * @param endTileX - x coordinate for destination tile
     * @param endTileY - y coordinate for destination tile
     * @param graph - the graph
     */
    public AStar(int startTileX, int startTileY, int endTileX, int endTileY, Graph graph) {

        this.MAX_X_COOR = graph.getWIDTH();
        this.MAX_Y_COOR = graph.getHEIGHT();

        this.visited = new ArrayList<Point>();
        this.graph = graph;
        this.tileTable = new AStarNode[MAX_X_COOR][MAX_Y_COOR];

        // initalize tiles
        for (int x = 0; x < MAX_X_COOR; x++) {

            for (int y = 0; y < MAX_Y_COOR; y++) {

                double HDist = Math.sqrt(Math.pow(endTileX - x, 2) + Math.pow(endTileY - y, 2));
                tileTable[x][y] = new AStarNode(x, y, HDist);

            }
        }

        this.startNode = tileTable[startTileX][startTileY];
        this.startNode.setGCost(0);
        this.endNode = tileTable[endTileX][endTileY];

        this.currentNode = startNode;

    }

    /**
     * method returns the minimum cost tile based on its F value
     * @return - the current minimum cost node
     */
    private AStarNode getMinDistReachable() {
        double min = Integer.MAX_VALUE;
        AStarNode minNode = null;

        // go through visited nodes and their neighbors to find the tile which has shortest distance from the start tile
        for(int index = 0; index < visited.size(); index++) {
            Point p = visited.get(index);


            LinkedList<Point> neighbors = graph.getAdjLists()[p.x][p.y];
            int amtVisited = 0;
            for (Point neighbor : neighbors) {

                if (tileTable[neighbor.x][neighbor.y].isVisited()) {
                    amtVisited++;
                    continue;
                }

                // update the min node if current min is greater than the distance from start to current node
                if (tileTable[neighbor.x][neighbor.y].getFCost() < min) {
                    min = tileTable[neighbor.x][neighbor.y].getFCost();
                    minNode = tileTable[neighbor.x][neighbor.y];
                }
            }

            // when all neighbors are visited for a node, we don't have to go through that node again thus delete it
            if (amtVisited == neighbors.size()) {
                visited.remove(index);
                index--;
            }
        }

        return minNode;
    }

    /**
     * visits given node and explores distances to tiles around it
     * @param node - the current node to explore
     */
    private void visit(AStarNode node) {

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

            if(temp + node.getGCost() < tileTable[p.x][p.y].getGCost()) {
                tileTable[p.x][p.y].setGCost(temp + node.getGCost());

                double GCost = tileTable[p.x][p.y].getGCost();
                double HCost = tileTable[p.x][p.y].getHCost();
                tileTable[p.x][p.y].setFCost(GCost + HCost);
                tileTable[p.x][p.y].setPrev(node);
            }

        }

        node.setVisited(true);
        visited.add(new Point(node.getxCoor(), node.getyCoor()));
    }

    /**
     * method returns the path from end node to start node
     * @return - a linked list containing the path from end node to start node
     */
    @Override
    public LinkedList<Point> getPath() {
        System.out.println("length of path is " + endNode.getGCost());
        LinkedList<Point> list = new LinkedList<Point>();
        AStarNode temp = endNode;

        while(temp != null) {

            System.out.println(" <- (" + temp.getxCoor() + " " + temp.getyCoor() + ")");
            list.add(new Point(temp.getxCoor(), temp.getyCoor()));
            temp = temp.getPrev();
        }

        return list;

    }


    /**
     * method visits current node, and then sets current node to the minimum cost one
     */
    @Override
    public void visitNext() {
        visit(currentNode);
        currentNode = getMinDistReachable();
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
}