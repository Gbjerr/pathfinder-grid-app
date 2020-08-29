package model;

public class Node {
    private Node prev;

    private boolean visited;
    private boolean isObstacle;

    private final int xCoor;
    private final int yCoor;
    private double dist;

    public Node(int xCoor, int yCoor) {
        this.prev = null;

        this.visited = false;
        this.dist = Double.MAX_VALUE;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public Node getPrev() {
        return prev;
    }

    public boolean isVisited() {
        return visited;
    }

    public double getDist() {
        return dist;
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public void setObstacle() {
        this.isObstacle = true;
    }

    public boolean isObstacle() {
        return isObstacle;
    }
}
