package model;

import java.util.ArrayList;

/**
 * Class representation of BFS path algorithm in a grid system
 */
public class BreadthFirstSearch extends PathAlgorithm {

    private final ArrayList<Node> queue;

    /**
     *
     * constructor initializes necessary variables and structures for the algorithm
     *
     * @param startPoint - the start point
     * @param endPoint - the destination point
     * @param graph - the graph
     */
    public BreadthFirstSearch(Point startPoint, Point endPoint, Graph graph) {
        super(graph);
        preProcessNodes(startPoint, endPoint);

        queue = new ArrayList<>();

        queue.add(startNode);
    }

    /**
     * method visits current node, and then sets current node to whatever is on top of the queue
     */
    @Override
    public void visitNext() {
        // an empty queue means there's guaranteed to be no possible path, and in that case we will interrupt the thread
        if(queue.isEmpty()) {
            Thread.currentThread().interrupt();
            return;
        }
        Node currentNode = queue.get(0);
        queue.remove(0);

        visit(currentNode);
    }

    /**
     * visits given node and explores the distances to tiles around it
     * @param node - the current node to explore
     */
    private void visit(Node node) {

        // explore the distances from current node to all unvisited neighbors
        for(Node neighbor : graph.getNeighborsFromNode(node)) {
            if(neighbor.getState() == NodeState.VISITED || neighbor.getState() == NodeState.OBSTACLE) {
                continue;
            }

            double distToNeighbor = getDistToNeighbor(node, neighbor);

            neighbor.setDist(distToNeighbor + node.getDist());
            neighbor.setPrev(node);
            neighbor.setState(NodeState.VISITED);
            queue.add(neighbor);
        }
        node.setState(NodeState.VISITED);
    }

}
