# pathfinder-grid-app
JavaFX program for visualizing path finding in a grid system. The program finds the path from start to destination
given its coordinates as input with a chosen path algorithm. The algorithm is chosen by selection in the drop-down menu and obstacles can be drawn by dragging the mouse on the tiles.

<img height="460" width="329" alt="path" src="https://user-images.githubusercontent.com/46920882/98447188-16802180-2123-11eb-8cfc-24e2058cfdde.PNG">

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
    
