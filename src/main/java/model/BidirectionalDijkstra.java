package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BidirectionalDijkstra extends PathAlgorithm {

    private final PriorityQueue<Node> pqForward;
    private final PriorityQueue<Node> pqBackward;
    private final ArrayList<Node> closedForward;
    private final ArrayList<Node> closedBackward;
    private double shortestDist;

    private AlternationMode mode;

    private Node meetingFNode;
    private Node meetingBNode;
    private boolean pathIsFound;

    /**
     * Enum class used to alternate between forward and backward expansion.
     */
    private enum AlternationMode {
        FORWARD, BACKWARD
    }
    public BidirectionalDijkstra(Point startPoint, Point endPoint, Graph graph) {
        super(graph);
        preProcessNodes(startPoint, endPoint);

        pathIsFound = false;

        this.pqForward = new PriorityQueue<>(Comparator.comparingDouble(Node::getDist));
        pqForward.add(startNode);
        this.pqBackward = new PriorityQueue<>(Comparator.comparingDouble(Node::getDist));
        pqBackward.add(endNode);

        closedForward = new ArrayList<>();
        closedBackward = new ArrayList<>();

        shortestDist = Double.MAX_VALUE;
        mode = AlternationMode.FORWARD;
    }

    @Override
    protected void preProcessNodes(Point startPoint, Point endPoint) {

        if(graph == null) {
            return;
        }
        boolean[][] obstacleMapCache = graph.getClonedObstacleMap();
        graph = new Graph();

        // initialize tiles and set their heuristic cost
        for (int x = 0; x < MAX_X_COORDINATE; x++) {
            for (int y = 0; y < MAX_Y_COORDINATE; y++) {
                Node node = new Node(x, y);
                node.setDist(Double.MAX_VALUE);
                if(obstacleMapCache[x][y]) {
                    node.setState(NodeState.OBSTACLE);
                }
                graph.addNode(node);
            }
        }

        startNode = graph.getNodeByCoordinate(startPoint.x, startPoint.y);
        startNode.setDist(0);
        endNode = graph.getNodeByCoordinate(endPoint.x, endPoint.y);
        endNode.setDist(0);
        graph.initNeighbors();
    }

    @Override
    public void visitNext() {
        Node fwdPeek = pqForward.peek();
        Node bwdPeek = pqBackward.peek();

        // interrupt if there is no possible path from the forward and backward search
        if(fwdPeek == null || bwdPeek == null) {
            Thread.currentThread().interrupt();

        // alternate forward/backward search and expand if termination condition is not met yet
        } else if(fwdPeek.getDist() + bwdPeek.getDist() < shortestDist) {
            Node current;
            if(mode == AlternationMode.FORWARD) {
                current = pqForward.poll();
                expandForward(current);
            } else {
                current = pqBackward.poll();
                expandBackward(current);
            }
            mode = (mode == AlternationMode.FORWARD) ? AlternationMode.BACKWARD : AlternationMode.FORWARD;

        // termination condition has been fulfilled thus signal that path is found
        } else {
            pathIsFound = true;
        }
    }

    private void expandBackward(Node node) {
        ArrayList<Node> neighbors = graph.getNeighborsFromNode(node);

        for(Node neighbor : neighbors) {
            // backward search clashes with forward search, assess if found distance is the current shortest
            if(closedForward.contains(neighbor) || pqForward.contains(neighbor)) {
                double tempDist = getFoundDist(node, neighbor);
                if(shortestDist > tempDist) {
                    connectSearches(neighbor, node);
                    shortestDist = tempDist;
                }
                continue;
            } else if(neighbor.getState() == NodeState.VISITED) continue;

            double distToNeighbor = getDistToNeighbor(node, neighbor);

            // when a better distance has been found for neighbor update its variables accordingly
            double computedDist = distToNeighbor + node.getDist();
            if(neighbor.getDist() > computedDist) {
                neighbor.setDist(computedDist);
                neighbor.setPrev(node);
                pqBackward.remove(neighbor);
                pqBackward.add(neighbor);
            }
        }

        node.setState(NodeState.VISITED);
        pqBackward.remove(node);
        closedBackward.add(node);
    }

    private void expandForward(Node node) {
        ArrayList<Node> neighbors = graph.getNeighborsFromNode(node);

        for(Node neighbor : neighbors) {
            // forward search clashes with backward search, assess if found distance is current shortest
            if (closedBackward.contains(neighbor) || pqBackward.contains(neighbor)) {
                double tempDist = getFoundDist(node, neighbor);
                if (shortestDist > tempDist) {
                    connectSearches(node, neighbor);
                    shortestDist = tempDist;
                }
                continue;
            } else if (neighbor.getState() == NodeState.VISITED) continue;

            // when a better distance has been found for neighbor update its variables accordingly
            double distToNeighbor = getDistToNeighbor(node, neighbor);
            double computedDist = distToNeighbor + node.getDist();
            if (neighbor.getDist() > computedDist) {
                neighbor.setDist(computedDist);
                neighbor.setPrev(node);
                pqForward.remove(neighbor);
                pqForward.add(neighbor);
            }
        }

        node.setState(NodeState.VISITED);
        pqForward.remove(node);
        closedForward.add(node);
    }


    private void connectSearches(Node fwd, Node bwd) {
        meetingFNode = fwd;
        meetingBNode = bwd;
    }

    private double getFoundDist(Node f, Node b) {
        if(f == null || b == null) {
            return shortestDist;
        }
        return f.getDist() + b.getDist() + getDistToNeighbor(f, b);
    }

    @Override
    public boolean pathIsFound() {
        return pathIsFound;
    }

    @Override
    public ArrayList<Node> getPath() {
        return getPath(meetingFNode, meetingBNode);
    }

    public ArrayList<Node> getPath(Node forward, Node backward) {
        ArrayList<Node> list = new ArrayList<>();

        while(forward != null) {
            list.add(0, forward);
            forward = forward.getPrev();
        }
        while(backward != null) {
            list.add(backward);
            backward = backward.getPrev();
        }

        return list;
    }
    @Override
    public double getFoundPathDistance() {
        return getFoundDist(meetingFNode, meetingBNode);
    }
}
