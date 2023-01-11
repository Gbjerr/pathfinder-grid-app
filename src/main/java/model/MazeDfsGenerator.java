package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

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
        for(int x = 0; x < obstacleMap.length; x++) {
            Arrays.fill(obstacleMap[x], true);
        }
        predecessorMap = new HashMap<>();

        endNode = graph.getNodeByCoordinate(end.x, end.y);

        // retrieve start node and make sure it becomes the first node that the random DFS will handle
        Node startNode = graph.getNodeByCoordinate(start.x, start.y);
        fifoQueue.push(startNode);
        predecessorMap.put(startNode, null);

        randomizedDfs();

        // connect end node to the maze so that there can be a path to it
        if(isMarkedAsObstacle(endNode)) {
            connectNodeToMaze(endNode);
        }

        transferObstacles();
    }

    /**
     * Connect node to the maze so that it can be found from any other point in the graph.
     *
     * @param node - the node to be connected
     */
    private static void connectNodeToMaze(Node node) {
        obstacleMap[node.getXCoordinate()][node.getYCoordinate()] = false;
        List<Node> visitedNeighbors = node.getNeighbors().stream()
                .filter(n -> !isMarkedAsObstacle(n)).toList();
        if(visitedNeighbors.isEmpty()) {
            connectNodeToMaze(node.getNeighbors().stream().findFirst().get());
        }
    }

    /**
     * Transfers the obstacles from the obstacle map to the Graph object.
     */
    private static void transferObstacles() {
        graph.getNodes().stream().filter(n -> isMarkedAsObstacle(n)).forEach(n -> n.setState(NodeState.OBSTACLE));
    }


    /**
     * Generates the maze using a randomized DFS algorithm.
     */
    private static void randomizedDfs() {

        while(!fifoQueue.isEmpty()) {
            Node current = fifoQueue.pop();
            if(!isMarkedAsObstacle(current) || hasNonObstacleNeighbors(current, predecessorMap.get(current))) {
                continue;
            }
            obstacleMap[current.getXCoordinate()][current.getYCoordinate()] = false;

            // shuffle the node's neighbors so that next neighbor is chosen randomly
            List<Node> neighbors = current.getNeighbors();
            Collections.shuffle(neighbors);

            for(Node n : neighbors) {
                if(isMarkedAsObstacle(n) && !hasNonObstacleNeighbors(n, current)) {
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
    private static boolean hasNonObstacleNeighbors(Node node, Node predecessor) {
        for (Node n : node.getNeighbors()) {
            if(!isMarkedAsObstacle(n) && !n.equals(predecessor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Look if the obstacle map has marked given node as an obstacle.
     *
     * @param node - node to check
     * @return if the node has been marked
     */
    private static boolean isMarkedAsObstacle(Node node) {
        return obstacleMap[node.getXCoordinate()][node.getYCoordinate()];
    }
}
