package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The MazeDfsGenerator class generates a maze using a depth-first search (DFS) algorithm.
 * It modifies the Graph object to represent the maze.
 */
public class MazeDfsGenerator {
    private static LinkedList<Node> fifoQueue;
    private static HashMap<Node, Node> predecessorMap;
    private static boolean[][] obstacleMap;
    private static Graph graph;
    private static Node endNode;

    /**
     * Generates a maze using a DFS algorithm.
     *
     * @param inputGraph the Graph object to be modified to represent the maze
     * @param start the starting Point of the maze
     * @param end the ending Point of the maze
     */
    public static void generateMaze(Graph inputGraph, Point start, Point end) {
        fifoQueue = new LinkedList<>();
        graph = inputGraph;
        obstacleMap = new boolean[graph.getWIDTH()][graph.getHEIGHT()];
        predecessorMap = new HashMap<>();

        endNode = graph.getNodeByCoordinate(end.x, end.y);

        Node startNode = graph.getNodeByCoordinate(start.x, start.y);
        fifoQueue.push(startNode);
        predecessorMap.put(startNode, null);

        // create a graph until the end node is not an obstacle (yes this is lazy)
        try {
            randomizedDfs();
        } catch (NoSuchElementException ex) {
            generateMaze(graph, start, end);
            return;
        }

        transferObstacles();
    }

    /**
     * Transfers the obstacles from the obstacle map to the Graph object.
     */
    private static void transferObstacles() {
        graph.getNodes().stream().filter(n -> !obstacleMap[n.getXCoordinate()][n.getYCoordinate()]).forEach(n -> n.setState(NodeState.OBSTACLE));
    }


    /**
     * Generates the maze using a randomized DFS algorithm.
     */
    private static void randomizedDfs() {

        while(!fifoQueue.isEmpty() || !obstacleMap[endNode.getXCoordinate()][endNode.getYCoordinate()]) {
            Node current = fifoQueue.pop();
            if(obstacleMap[current.getXCoordinate()][current.getYCoordinate()] || hasAVisitedNeighbor(current, predecessorMap.get(current))) {
                continue;
            }
            obstacleMap[current.getXCoordinate()][current.getYCoordinate()] = true;

            List<Node> neighbors = current.getNeighbors();
            Collections.shuffle(neighbors);

            for(Node n : neighbors) {
                if(!obstacleMap[n.getXCoordinate()][n.getYCoordinate()] && !hasAVisitedNeighbor(n, current)) {
                    predecessorMap.put(n, current);
                    fifoQueue.push(n);
                }
            }
        }
    }

    /**
     * Determines if a given Node has any visited neighbors (excluding the predecessor).
     *
     * @param node the Node to check for visited neighbors
     * @param predecessor the predecessor Node to exclude from the check
     * @return true if the Node has any visited neighbors (excluding the predecessor), false otherwise
     */
    private static boolean hasAVisitedNeighbor(Node node, Node predecessor) {
        for (Node n : node.getNeighbors()) {
            if(obstacleMap[n.getXCoordinate()][n.getYCoordinate()] && !n.equals(predecessor)) {
                return true;
            }
        }
        return false;
    }
}
