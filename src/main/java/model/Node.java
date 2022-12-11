package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representation of each node
 */
public class Node {

    private Node prev;
    private ArrayList<Node> neighbors; // surrounding nodes
    private final Point point; // coordinate
    private NodeState state;
    protected double dist; // distance

    public Node(int x, int y) {
        point = new Point(x, y);
        state = NodeState.UNVISITED;
        neighbors = null;
        dist = Double.MAX_VALUE;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public Node getPrev() {
        return prev;
    }

    public int getXCoordinate() {
        return point.x;
    }

    public int getYCoordinate() {
        return point.y;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public void reset() {
        state = NodeState.UNVISITED;
        neighbors = null;
        dist = 0;
        prev = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Double.compare(node.dist, dist) == 0 && Objects.equals(point, node.point) && state == node.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prev, neighbors, point, state, dist);
    }

    @Override
    public String toString() {
        return "Node{" +
                "prev=" + prev +
                ", neighbors=" + neighbors +
                ", point=" + point +
                ", state=" + state +
                ", dist=" + dist +
                '}';
    }
}
