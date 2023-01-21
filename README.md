# pathfinder-grid-app
A JavaFX application to visualize path finding in a grid system. The program finds the path from start to destination
given its coordinates as input with a chosen path algorithm. The algorithm is chosen by selection in the drop-down menu 
and obstacles can be drawn by dragging the mouse on the tiles. <br><br><b>Blue</b> tiles are the visited nodes, <b>Dark</b> tiles 
are obstacle nodes and <b>Orange</b> tiles represents the found path.<br><br>Algorithms to choose from:
<ul>
    <li>Breadth first search</li>
    <li>Dijkstra's algorithm</li>
    <li>A* algorithm</li>
    <li>Bidirectional Dijkstra</li>
</ul>

## Usage
Java 16 is required.<br>
Run with maven: <b>.mvn clean javafx:run</b>

## Screenshot
<img src="https://user-images.githubusercontent.com/46920882/210182514-7973dc7e-341f-46fa-b6ab-cee55939cec6.png" alt="" width="550"/>

## Structure
```bash
src/
└── main
    ├── java
    │   ├── controller
    │   │   └── Controller.java
    │   ├── model
    │   │   ├── AStar.java
    │   │   ├── AStarNode.java
    │   │   ├── BidirectionalDijkstra.java
    │   │   ├── BreadthFirstSearch.java
    │   │   ├── Dijkstra.java
    │   │   ├── Graph.java
    │   │   ├── MazeDfsGenerator.java
    │   │   ├── Node.java
    │   │   ├── NodeState.java
    │   │   ├── PathAlgorithm.java
    │   │   └── Point.java
    │   ├── module-info.java
    │   ├── startup
    │   │   └── Main.java
    │   └── view
    │       ├── Screen.java
    │       └── View.java
    └── resources


```
