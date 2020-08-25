package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class Dijkstra implements Algorithm{

    private final int MAX_X_COOR = 30, MAX_Y_COOR = 30;

    private ArrayList<Point> visited;

    private DijkNode[][] table;
    private DijkNode startNode;
    private DijkNode endNode;
    private DijkNode current;


    public Dijkstra(Point startPoint, Point endPoint) {

        table = new DijkNode[MAX_X_COOR][MAX_Y_COOR];
        visited = new ArrayList<Point>();

        // initalize table of nodes
        for (int i = 0; i < MAX_X_COOR; i++) {

            for (int j = 0; j < MAX_Y_COOR; j++) {
                table[i][j] = new DijkNode(i, j);

            }
        }

        startNode = table[startPoint.x][startPoint.y];
        startNode.setDist(0);
        current = startNode;

        endNode = table[endPoint.x][endPoint.y];


    }

    @Override
    public LinkedList<Point> getShortestPath() {
        System.out.println("length of path is " + endNode.getDist());
        LinkedList<Point> list = new LinkedList<Point>();
        DijkNode temp = endNode;

        while(temp != null) {

            System.out.println(" <- (" + temp.getxCoor() + " " + temp.getyCoor() + ")");
            list.add(new Point(temp.getxCoor(), temp.getyCoor()));
            temp = temp.getPrev();
        }

        return list;

    }

    private DijkNode getMinDistReachable() {
        double min = Integer.MAX_VALUE;
        DijkNode minNode = null;

        for(int x = 0; x < MAX_X_COOR; x++) {

            for(int y = 0; y < MAX_Y_COOR; y++) {

                if(table[x][y].getDist() < min && !table[x][y].isVisited()) {
                    min = table[x][y].getDist();
                    minNode = table[x][y];
                }
            }
        }

        return minNode;
    }

    private void visit(DijkNode node) {

        double min = Double.MAX_VALUE;
        DijkNode minNode = null;

        //checks the distance to the eight surrounding nodes in a grid

        for(int x = node.getxCoor() - 1; x < node.getxCoor() + 2; x++) {

            for(int y = node.getyCoor() - 1; y < node.getyCoor() + 2; y++) {

                if(x == node.getxCoor() && y == node.getyCoor()) continue;
                if(x < 0 || !(x < MAX_X_COOR) || y < 0 || !(y < MAX_Y_COOR) || table[x][y].isObstacle()) continue;

                int xTemp = node.getxCoor() - x, yTemp = node.getyCoor() - y;
                double temp;
                if((xTemp + yTemp) % 2 == 0) {
                    temp = 1.4;
                }
                else {
                    temp = 1.0;
                }

                if(temp + node.getDist() < table[x][y].getDist() && !table[x][y].isVisited()) {
                    table[x][y].setDist(temp + node.getDist());
                    table[x][y].setPrev(node);
                }

                if(temp < min) {
                    min = temp;
                    minNode = table[x][y];
                }

            }
        }

        node.setVisited(true);
        visited.add(new Point(node.getxCoor(), node.getyCoor()));
    }
    @Override
    public void visitNext() {
        visit(current);
        current = getMinDistReachable();
    }

    @Override
    public Point getStartCoor() {
        return new Point(startNode.getxCoor(), startNode.getyCoor());
    }

    @Override
    public ArrayList<Point> getVisited() {
        return visited;
    }

    @Override
    public void setObstacles(ArrayList<Point> list) {
        for(Point p : list) {
            table[p.x][p.y].setObstacle();
        }
    }

    @Override
    public boolean endNodeIsVisited() {
        return endNode.isVisited();
    }

}
