# pathfinder-grid-app
JavaFX program, for visualizing path finding in a grid system. The program finds the path from start to destination
given its coordinates as input with a path algorithm which the user chooses in the drop-down menu. Obstacles can be
drawn by draggin the mouse on the tiles.

Has structure:
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
    
