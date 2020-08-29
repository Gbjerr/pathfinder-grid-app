/**
 * Interface for path algorithms to implement
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public interface Algorithm {
    public void visitNext();

    public boolean endNodeIsVisited();

    public Point getStartCoor();
    public ArrayList<Point> getVisited();
    public LinkedList<Point> getPath();

}