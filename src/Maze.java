import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// this class is used to draw the maze and check if all the snack pellets are eaten
public class Maze {
    private final HashMap<Integer, Image> tiles; // stores the image tile with a key of the key colour code from level1.txt that it represents
    private int tileWidth, tileHeight;
    public static final int[][] colGrid = new int[52][31]; // the grid of nums must be transposed to work normally
    public static final int TILESIZE = 20;
    public static final ArrayList<Integer> path_tiles = new ArrayList<>(); // stores the movable tiles (not walls)

    public Maze(String name){
        tiles = new HashMap<Integer, Image>();
        try{
            Scanner inFile = new Scanner(new File(name)); // the file in which the grid is represented by nums to be replaced
            for(int x = 0; x < 31; x++){
                for(int y = 0; y < 52; y++) {
                    colGrid[y][x] = inFile.nextInt(); // reading each of the ints in the file and making them into a grid array
                }
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
        loadHeader("resources/level1key.txt"); // adding to the hashmap of tiles and reading the level1key.txt file

        path_tiles.add(1); path_tiles.add(9); path_tiles.add(16); // these all represent movable tiles; snack pellets, power pellets and black tiles
    }

    // this method is used to load the filename as an img into the code
    public Image loadImage(String name){
        return new ImageIcon(name).getImage();
    }

    // this method is used to load all the components in the lvl key (tile size, num, what they represent)
    public void loadHeader(String name){
        try{
            Scanner inFile = new Scanner(new File(name)); // reading the file
            tileWidth = Integer.parseInt(inFile.nextLine()); // title's width
            tileHeight = Integer.parseInt(inFile.nextLine()); // title's height
            int numTile = Integer.parseInt(inFile.nextLine()); // number of unique tiles

            for(int i = 1; i < numTile + 1; i++){
                tiles.put(i, loadImage(inFile.nextLine())); // putting the colour key num that will be replaced by each tile in the hashmap
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    // this method is used to check if all the snack pellets are eaten
    public boolean won(){
        for(int i = 0; i < colGrid.length; i++){
            for(int j = 0; j < colGrid[i].length; j++){
                if(colGrid[i][j] == 1 || colGrid[i][j] == 9){ // if there is still a snack or power pellet left, the game has not been won
                    return false;
                }
            }
        }
        return true; // all of the pellets are eaten
    }

    // this method is used to draw the maze
    public void drawBack(Graphics g){
       for(int x = 0; x < colGrid.length; x++){
            for(int y = 0; y < colGrid[x].length; y++) {
                // loop through colGrid and sub num's that aren't 0 with the tile from the hashmap; 0 means put nothing
                if(colGrid[x][y] != 0){ // 0 means empty and nothing to be replaced
                    Image tile = tiles.get(colGrid[x][y]); // getting the tile
                    g.drawImage(tile, x*tileWidth, y*tileHeight,null); // drawing the tile on the screen at the correct position
                }
            }
        }
    }
}