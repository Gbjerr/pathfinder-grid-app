package model;

import java.util.Objects;

/**
 * Class representation of node used in the AStar algorithm
 */
public class AStarNode extends Node {
    private double gCost;
    private double hCost;

    /**
     * Initializes variables for node
     *
     * @param x - X coordinate of node
     * @param y - Y coordinate of node
     * @param hCost - Heuristic cost, which is the euclidean distance from node to the destination node
     */
    public AStarNode(int x, int y, double hCost) {
        super(x, y);

        this.hCost = hCost;
        gCost = Double.MAX_VALUE;
        dist = Double.MAX_VALUE;
    }

    //-------------------------- Bunch of setters and getters below

    public double getGCost() {
        return gCost;
    }
    public void setGCost(double GCost) {
        gCost = GCost;
    }
    public double getHCost() {
        return hCost;
    }
    public void setFCost(double FCost) {
        dist = FCost;
    }
    public double getFCost() {
        return dist;
    }

    public void reset() {
        gCost = Double.MAX_VALUE;
        hCost = 0;
        super.reset();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AStarNode starNode = (AStarNode) o;
        return Double.compare(starNode.gCost, gCost) == 0 && Double.compare(starNode.hCost, hCost) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gCost, hCost);
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
    }

    @Override
    public String toString() {
        return "AStarNode{" +
                "gCost=" + gCost +
                ", hCost=" + hCost +
                ", dist=" + dist +
                '}';
    }
}
