package model;

import java.util.PriorityQueue;

/**
 * Class representation of dijkstra path algorithm in a tile system
 */
public class Dijkstra extends PathAlgorithm {

    private final PriorityQueue<Node> pq;

    /**
     * @param startPoint - the start point
     * @param endPoint - the destination point
     * @param graph - contains nodes and their neighbors
     */
    public Dijkstra(Point startPoint, Point endPoint, Graph graph) {
        super(graph);
        preProcessNodes(startPoint, endPoint);

        pq = new PriorityQueue<>((a, b) -> Double.compare(a.getDist(), b.getDist()));
        pq.add(startNode);
    }

    /**
     * Visits given node and its distance to other neighbors
     * @param node - the current node to explore
     */
    private void visit(Node node) {

        for(Node neighbor : graph.getNeighborsFromNode(node)) {
            if(neighbor.getState() == NodeState.VISITED) {
                continue;
            }

            double distToNeighbor = getDistToNeighbor(node, neighbor);

            if(distToNeighbor+ node.getDist() < neighbor.getDist()) {
                pq.remove(neighbor);
                neighbor.setDist(distToNeighbor + node.getDist());
                neighbor.setPrev(node);
                pq.add(neighbor);
            }
        }
        node.setState(NodeState.VISITED);
    }

    /**
     * Visits current node, and then reassigns it to the minimum cost one
     */
    @Override
    public void visitNext() {
        Node currentNode = pq.poll();

        // if current node is null, then there's guaranteed to be no possible path, and we will thus interrupt the thread
        if(currentNode == null) {
            Thread.currentThread().interrupt();
            return;
        }
        visit(currentNode);
    }

}
