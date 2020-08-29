package model;

public class AStarNode {
    private AStarNode prev;

    private boolean visited;
    private boolean obstacle;

    private final int xCoor;
    private final int yCoor;

    private double GDist;
    private double HDist;
    private double FCost;


    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public AStarNode(int xCoor, int yCoor, double HDist) {
        this.prev = null;
        this.xCoor = xCoor;
        this.yCoor = yCoor;

        this.visited = false;
        this.HDist = HDist;
        this.GDist = this.FCost = Double.MAX_VALUE;
    }

    public AStarNode getPrev() {
        return prev;
    }

    public void setPrev(AStarNode prev) {
        this.prev = prev;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double getGDist() {
        return GDist;
    }

    public void setGDist(double GDist) {
        this.GDist = GDist;
    }

    public double getHDist() {
        return HDist;
    }

    public void setHDist(double HDist) {
        this.HDist = HDist;
    }

    public double getFCost() {
        return FCost;
    }

    public void setFCost(double FCost) {
        this.FCost = FCost;
    }


    public void setObstacle() {
        this.obstacle = true;
    }

    public boolean isObstacle() {
        return obstacle;
    }
}
