package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * AStar path algorithm representation in a grid system.
 */
public class AStar extends PathAlgorithm {

    private final PriorityQueue<AStarNode> pq;

    /**
     * constructor initializes necessary variables and structures for the algorithm
     *
     * @param startPoint - start coordinate
     * @param endPoint - end coordinate
     * @param graph - the graph representation
     */
    public AStar(Point startPoint, Point endPoint, Graph graph) {
        super(graph);
        preProcessNodes(startPoint, endPoint);

        pq = new PriorityQueue<>(Comparator.comparingDouble(AStarNode::getDist));
        pq.add((AStarNode) startNode);
    }

    @Override
    protected void preProcessNodes(Point startPoint, Point endPoint) {

        if(graph == null) {
            System.out.println("Graph variable has not been assigned");
            return;
        }
        boolean[][] obstacleMapCache = graph.getClonedObstacleMap();
        graph = new Graph();

        // initialize tiles and set their heuristic cost
        for (int x = 0; x < MAX_X_COORDINATE; x++) {
            for (int y = 0; y < MAX_Y_COORDINATE; y++) {

                double HDist = getEuclideanDistance(x, y, endPoint.x, endPoint.y);
                AStarNode node = new AStarNode(x, y, HDist);
                node.setGCost(Double.MAX_VALUE);

                if(obstacleMapCache[x][y]) {
                    node.setState(NodeState.OBSTACLE);
                }
                graph.addNode(node);
            }
        }

        AStarNode startNodeTemp = (AStarNode) graph.getNodeByCoordinate(startPoint.x, startPoint.y);
        startNodeTemp.setGCost(0);
        startNode = startNodeTemp;

        AStarNode endNodeTemp = (AStarNode) graph.getNodeByCoordinate(endPoint.x, endPoint.y);
        endNodeTemp.setHCost(0);
        endNode = endNodeTemp;

        graph.initNeighbors();
    }

    /**
     * Visits given node by exploring the distances to its neighbors.
     * @param node - the current node to explore
     */
    public void visit(AStarNode node) {

        // get neighbors from node
        ArrayList<AStarNode> neighbors = new ArrayList<>();
        graph.getNeighborsFromNode(node).forEach(n -> neighbors.add((AStarNode) n));

        // explore the distances from current node to all unvisited neighbors
        for(AStarNode neighbor : neighbors) {
            if(neighbor.getState() == NodeState.VISITED) continue;

            double distToNeighbor = getDistToNeighbor(node, neighbor);

            // when a better G cost is found for a node, update its G cost and F cost
            if(distToNeighbor + node.getGCost() < neighbor.getGCost()) {
                pq.remove(neighbor);
                neighbor.setGCost(distToNeighbor + node.getGCost());

                double GCost = neighbor.getGCost();
                double HCost = neighbor.getHCost();
                neighbor.setFCost(GCost + HCost);
                neighbor.setPrev(node);
                pq.add(neighbor);
            }
        }
        node.setState(NodeState.VISITED);
    }

    /**
     * Visits current node and find a new node for next function call
     */
    @Override
    public void visitNext() {

        AStarNode currentNode = pq.poll();

        // if current node is null, then it is guaranteed to be no possible path, and we will thus interrupt current thread
        if(currentNode == null) {
            Thread.currentThread().interrupt();
            return;
        }
        visit(currentNode);
    }
}