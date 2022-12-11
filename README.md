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
Java JDK 16 is required.<br>
Run with maven: <b>./mvnw clean javafx:run</b>

## Screenshot
<img width="487" alt="pic" src="https://user-images.githubusercontent.com/46920882/206899798-5a1e8d00-9da7-4a12-b4e6-d51d311db3fb.png">

## Structure
```bash
src
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
