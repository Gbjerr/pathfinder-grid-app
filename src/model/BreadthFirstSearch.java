package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirstSearch implements Algorithm{
    private final int MAX_X_COOR;
    private final int MAX_Y_COOR;

    private LinkedList<Point> queue;
    private ArrayList<Point> visited;

    private Node[][] table;
    private Node startNode;
    private Node endNode;
    private Node current;

    public BreadthFirstSearch(Point startPoint, Point endPoint, Graph graph) {

        this.MAX_X_COOR = graph.getWIDTH();
        this.MAX_Y_COOR = graph.getHEIGHT();

        table = new Node[MAX_X_COOR][MAX_Y_COOR];
        visited = new ArrayList<Point>();
        queue = new LinkedList<Point>();

        // initalize table of nodes
        for (int i = 0; i < MAX_X_COOR; i++) {

            for (int j = 0; j < MAX_Y_COOR; j++) {
                table[i][j] = new Node(i, j);

            }
        }

        setObstacles(graph.getObstacles());

        startNode = table[startPoint.x][startPoint.y];
        startNode.setDist(0);
        queue.add(startPoint);
        current = startNode;

        endNode = table[endPoint.x][endPoint.y];

    }

    @Override
    public void visitNext() {
        Point p = queue.pop();
        visit(table[p.x][p.y]);
    }

    private void visit(Node node) {

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

                table[x][y].setDist(temp + node.getDist());
                table[x][y].setPrev(node);
                table[x][y].setVisited(true);
                queue.add(new Point(x, y));

            }
        }

        node.setVisited(true);
        visited.add(new Point(node.getxCoor(), node.getyCoor()));
        System.out.println(node.getxCoor() + " and " + node.getyCoor() + " is visited");
    }

    public void setObstacles(ArrayList<Point> list) {
        for(Point p : list) {
            table[p.x][p.y].setObstacle();
        }
    }

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
}
