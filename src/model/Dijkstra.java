package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class Dijkstra implements Algorithm{

    private final int MAX_X_COOR;
    private final int MAX_Y_COOR;

    private ArrayList<Point> visited;

    private Node[][] table;
    private Node startNode;
    private Node endNode;
    private Node current;


    public Dijkstra(Point startPoint, Point endPoint, Graph graph) {

        this.MAX_X_COOR = graph.getWIDTH();
        this.MAX_Y_COOR = graph.getHEIGHT();

        table = new Node[MAX_X_COOR][MAX_Y_COOR];
        visited = new ArrayList<Point>();

        // initalize table of nodes
        for (int i = 0; i < MAX_X_COOR; i++) {

            for (int j = 0; j < MAX_Y_COOR; j++) {
                table[i][j] = new Node(i, j);

            }
        }

        setObstacles(graph.getObstacles());

        startNode = table[startPoint.x][startPoint.y];
        startNode.setDist(0);
        current = startNode;

        endNode = table[endPoint.x][endPoint.y];


    }

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

    private Node getMinDistReachable() {
        double min = Integer.MAX_VALUE;
        Node minNode = null;

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

    private void visit(Node node) {

        double min = Double.MAX_VALUE;
        Node minNode = null;

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

                if(temp + node.getDist() < table[x][y].getDist()) {
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
    public Point getStartCoor() {
        return new Point(startNode.getxCoor(), startNode.getyCoor());
    }

    @Override
    public ArrayList<Point> getVisited() {
        return visited;
    }

    @Override
    public boolean endNodeIsVisited() {
        return endNode.isVisited();
    }

}
