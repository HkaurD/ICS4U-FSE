import java.util.*;

// this class is used to call a*. all the tiles are represented as nodes with specific properties.
public class Node {
    private int gcost, hcost, fcost;
    private int x, y;
    private boolean obstacleStat;
    private final ArrayList<Node> neighbours = new ArrayList<>();
    private Node parent;

    public Node(int x, int y){
        gcost = 0;
        hcost = 0;
        fcost = 0;
        this.x = x;
        this.y = y;
        obstacleStat = false;
        parent = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGCost() {
        return gcost;
    }

    public void setHCost(int endx, int endy) {
        // H cost (heuristic) = distance from end node
        int distance = Math.abs(this.getX()-endx) + Math.abs(this.getY()-endy);
        hcost = distance;
    }

    public boolean isObstacle() {
        return obstacleStat;
    }

    public void setObstacleStat(boolean stat) {
        obstacleStat = stat;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public void setFCost() {
        fcost = gcost + hcost;
    }

    public int getFCost() {
        // F cost = H cost + G cost
        setFCost();
        return fcost;
    }

    public void setGCost(int g) {
        gcost = g;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node n) {
        parent = n;
    }

    public void findNeighbours(Node[][] grid){
        int copyx = x/Maze.TILESIZE, copyy = y/Maze.TILESIZE;
        if(copyx < 51){ // if there is a node to the right; not the rightmost node
            neighbours.add(grid[copyx + 1][copyy]); // right
        }
        if(copyx > 0){ // if there is a node to the left; not the leftmost node
            neighbours.add(grid[copyx - 1][copyy]); // left
        }
        if(copyy < 30){ // if there is a node below; not the bottommost node
            neighbours.add(grid[copyx][copyy + 1]); // below
        }
        if(copyy > 0){ // if there is a node above; not the topmost node
            neighbours.add(grid[copyx][copyy - 1]); // above
        }
    }
}