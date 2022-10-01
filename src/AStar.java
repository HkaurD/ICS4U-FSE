import java.awt.*;
import java.util.*;

// this class is used to call astar to make the ghosts track pacman
public class AStar {
    private int startx, starty; // these represent the start coordinates of the ghost chasing pacman
    private ArrayList<Point> path = new ArrayList<>(); // used to store the end
    private Ghost ghost;

    public AStar(Ghost ghost){
        startx = ghost.gx/Maze.TILESIZE;
        starty = ghost.gy/Maze.TILESIZE;
        this.ghost = ghost;
    }

    // videos used to understand concept of a* pathfinding: https://www.youtube.com/watch?v=-L-WgKMFuhE and https://www.youtube.com/watch?v=aKYlikFAV4k
    public ArrayList<Point> chaseMode(int endx, int endy) {
        endx = (endx/20)*20;
        endy = (endy/20)*20;

        path = new ArrayList<>(); // re-initializing the path each time to clear it

        updateStart();

        Node[][] grid = new Node[52][31]; // the maze's grid reassembled as nodes

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Node(i * Maze.TILESIZE, j * Maze.TILESIZE);
                if (!Maze.path_tiles.contains(Maze.colGrid[i][j])){
                    grid[i][j].setObstacleStat(true);
                }
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].findNeighbours(grid);
            }
        }

        ArrayList<Node> open_nodes = new ArrayList<>();
        ArrayList<Node> closed_nodes = new ArrayList<>();
        Node start_node = grid[startx/Maze.TILESIZE][starty/Maze.TILESIZE];
        start_node.setHCost(endx, endy); // storing the first node's h cost since it doesn't have a neighbour
        open_nodes.add(start_node);

        while (true) {
            Node current = lowestFCost(open_nodes);
            open_nodes.remove(current);
            closed_nodes.add(current);

            if (current.getX() == endx && current.getY() == endy) {
                // retrace the path used to reach the end point
                Node temp = current;
                path.add(0, new Point(temp.getX(), temp.getY())); // the last node's position
                while(temp.getParent() != null){
                    temp = temp.getParent(); // going back through the path; each node before
                    path.add(0, new Point(temp.getX(), temp.getY())); // adding the parent node (node before in the path) at the beginning
                }
                return path; // shortest path was found
            }

            for (Node neighbour : current.getNeighbours()) {
                if (neighbour.isObstacle() || closed_nodes.contains(neighbour)) {
                    continue;
                }
                else { // path is smaller if fcost is smaller (put this first)
                    int tempg = current.getGCost() + Maze.TILESIZE; // since each neighbour has a distance of 20 from the parent
                    if(open_nodes.contains(neighbour) && tempg < neighbour.getGCost()){ // if the neighbour is already in the open node list so it already has a gcost
                        neighbour.setGCost(tempg); // set the new gcost as the current if a better path is found compared to the previous
                    } else if(!open_nodes.contains(neighbour)){ // if the neighbour is not in the open node list
                        neighbour.setGCost(tempg); // setting the new gcost (from start node)
                        open_nodes.add(neighbour); // add the neighbour to the evaluated list
                    }
                    neighbour.setHCost(endx,endy);
                    neighbour.setFCost();
                    neighbour.setParent(current);
                }
            }
        }
    }

    public void updateStart(){
        startx = ghost.gx/Maze.TILESIZE * 20;
        starty = ghost.gy/Maze.TILESIZE * 20;
    }

    public Node lowestFCost(ArrayList<Node> nodes) {
        int lowestF = 2147483647;
        int ind = 0;
        for(int i = 0; i < nodes.size(); i++) {
            int fcost = nodes.get(i).getFCost();
            if (fcost < lowestF) {
                lowestF = fcost;
                ind = i;
            }
        }
        return nodes.get(ind);
    }
}