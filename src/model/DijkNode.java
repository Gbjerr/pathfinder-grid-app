package model;

public class DijkNode {
    private DijkNode prev;

    private boolean visited;
    private boolean isObstacle;

    private final int xCoor;
    private final int yCoor;
    private double dist;

    public DijkNode(int xCoor, int yCoor) {
        this.prev = null;

        this.visited = false;
        this.dist = Double.MAX_VALUE;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public void setPrev(DijkNode prev) {
        this.prev = prev;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public DijkNode getPrev() {
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
