package model;

/**
 * Class representation of node used for all path algorithms except AStar
 */
public class Node {
    private Node prev;

    private boolean visited;

    private final int xCoor;
    private final int yCoor;
    private double dist;

    /**
     * initializes variables for node
     *
     * @param xCoor - X coordinate of node
     * @param yCoor - Y coordinate of node
     */
    public Node(int xCoor, int yCoor) {
        this.prev = null;

        this.dist = Double.MAX_VALUE;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    // method checks if a node is visited or not
    public boolean isVisited() {
        return visited;
    }

    //-------------------------- Bunch of setters and getters below

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

    public double getDist() {
        return dist;
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

}
