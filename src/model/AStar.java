package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class AStar implements Algorithm{

    private final int MAX_X_COOR = 30, MAX_Y_COOR = 30;

    private ArrayList<Point> visited;

    private AStarNode[][] table;
    private AStarNode startNode;
    private AStarNode endNode;
    private AStarNode current;


    public AStar(Point startPoint, Point endPoint, Graph graph) {

        visited = new ArrayList<Point>();
        this.table = new AStarNode[MAX_X_COOR][MAX_Y_COOR];

        // initalize table of nodes
        for (int x = 0; x < MAX_X_COOR; x++) {

            for (int y = 0; y < MAX_Y_COOR; y++) {
                double HDist = Math.sqrt(Math.pow(endPoint.x - x, 2) + Math.pow(endPoint.y - y, 2));
                table[x][y] = new AStarNode(x, y, HDist);

            }
        }

        setObstacles(graph.getObstacles());

        this.startNode = table[startPoint.x][startPoint.y];
        this.startNode.setGDist(0);

        this.current = startNode;

        this.endNode = table[endPoint.x][endPoint.y];


    }

    private AStarNode getMinDistReachable() {
        double min = Integer.MAX_VALUE;
        AStarNode minNode = null;

        for(int x = 0; x < MAX_X_COOR; x++) {

            for(int y = 0; y < MAX_Y_COOR; y++) {

                if(table[x][y].getFCost() < min && !table[x][y].isVisited()) {
                    min = table[x][y].getFCost();
                    minNode = table[x][y];
                }
            }
        }

        return minNode;
    }


    private void visit(AStarNode node) {
        //checks the distance to the eight surrounding nodes in a grid

        for(int x = node.getxCoor() - 1; x < node.getxCoor() + 2; x++) {

            for(int y = node.getyCoor() - 1; y < node.getyCoor() + 2; y++) {

                if(x == node.getxCoor() && y == node.getyCoor()) continue;
                if(x < 0 || !(x < MAX_X_COOR) || y < 0 || !(y < MAX_Y_COOR)
                        || table[x][y].isObstacle() || table[x][y].isVisited()) continue;

                int xTemp = node.getxCoor() - x, yTemp = node.getyCoor() - y;
                double temp;
                if((xTemp + yTemp) % 2 == 0) {
                    temp = 1.4;
                }
                else {
                    temp = 1.0;
                }

                if(temp + node.getGDist() < table[x][y].getGDist()) {
                    table[x][y].setGDist(temp + node.getGDist());
                    table[x][y].setFCost(table[x][y].getHDist() + table[x][y].getGDist());
                    table[x][y].setPrev(node);
                }

            }
        }

        node.setVisited(true);
        visited.add(new Point(node.getxCoor(), node.getyCoor()));
    }

    @Override
    public LinkedList<Point> getPath() {
        System.out.println("length of path is " + endNode.getGDist());
        LinkedList<Point> list = new LinkedList<Point>();
        AStarNode temp = endNode;

        while(temp != null) {

            System.out.println(" <- (" + temp.getxCoor() + " " + temp.getyCoor() + ")");
            list.add(new Point(temp.getxCoor(), temp.getyCoor()));
            temp = temp.getPrev();
        }

        return list;

    }

    public void setObstacles(ArrayList<Point> list) {
        for(Point p : list) {
            table[p.x][p.y].setObstacle();
        }
    }

    @Override
    public void visitNext() {
        visit(current);
        current = getMinDistReachable();
    }

    @Override
    public boolean endNodeIsVisited() {
        return endNode.isVisited();
    }

    @Override
    public Point getStartCoor() {
        return new Point(startNode.getxCoor(), startNode.getyCoor());
    }

    @Override
    public ArrayList<Point> getVisited() {
        return visited;
    }
}