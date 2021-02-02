package model;

/**
 * Class representation of node used in the AStar algorithm
 */
public class AStarNode {
    private AStarNode prev;

    private boolean visited;

    private final int xCoor;
    private final int yCoor;

    private double GCost;
    private double HCost;
    private double FCost;

    /**
     * initializes variables for node
     *
     * @param xCoor - X coordinate of node
     * @param yCoor - Y coordinate of node
     * @param HCost - Heuristic cost from node to the destination node
     */
    public AStarNode(int xCoor, int yCoor, double HCost) {
        this.prev = null;
        this.xCoor = xCoor;
        this.yCoor = yCoor;

        this.visited = false;
        this.HCost = HCost;
        this.GCost = this.FCost = Double.MAX_VALUE;
    }

    //-------------------------- Bunch of setters and getters below
    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public boolean isVisited() {
        return visited;
    }

    public AStarNode getPrev() {
        return prev;
    }

    public void setPrev(AStarNode prev) {
        this.prev = prev;
    }

    public void setVisited() {
        this.visited = true;
    }

    public double getGCost() {
        return GCost;
    }

    public void setGCost(double GCost) {
        this.GCost = GCost;
    }

    public double getHCost() {
        return HCost;
    }

    public void setHCost(double HCost) {
        this.HCost = HCost;
    }

    public double getFCost() {
        return FCost;
    }

    public void setFCost(double FCost) {
        this.FCost = FCost;
    }

}
