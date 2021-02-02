# pathfinder-grid-app
JavaFX program for visualizing path finding in a grid system. The program finds the path from start to destination
given its coordinates as input with a chosen path algorithm. The algorithm is chosen by selection in the drop-down menu and obstacles can be drawn by dragging the mouse on the tiles.

## Usage
To run the application, execute the .jar file. Or simply run the main class (startup/Main.java).

## Screenshot
<img width="487" alt="pic" src="https://user-images.githubusercontent.com/46920882/106622935-6597f000-6574-11eb-85cc-1d33c5bf8251.PNG">

## Structure
```bash
Src
├── controller
│   └── Controller.java
├── model
│   ├── AStar.java
│   ├── AStarNode.java
│   ├── Algorithm.java
│   ├── BreadthFirstSearch.java
│   ├── DepthFirstSearch.java
│   ├── Dijkstra.java
│   ├── Graph.java
│   └── Node.java
├── startup
│   └── Main.java
└── view
    ├── Screen.java
    └── View.java
```
    
