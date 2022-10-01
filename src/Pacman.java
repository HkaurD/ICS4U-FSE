import javax.swing.*;
import java.awt.*;

// this class controls all the movements and images of pacman
public class Pacman {
    // normal pacman theme
    private final Image[] pacup = new Image[7];
    private final Image[] pacdown = new Image[7];
    private final Image[] pacleft = new Image[7];
    private final Image[] pacright = new Image[7];
    private final Image[] pacdying = new Image[8];

    // spidey theme
    private final Image[] spideyup = new Image[7];
    private final Image[] spideydown = new Image[7];
    private final Image[] spideyleft = new Image[7];
    private final Image[] spideyright = new Image[7];
    private final Image[] spideydying = new Image[8];

    // harry potter theme
    private final Image[] hpup = new Image[7];
    private final Image[] hpdown = new Image[7];
    private final Image[] hpleft = new Image[7];
    private final Image[] hpright = new Image[7];
    private final Image[] hpdying = new Image[8];

    // marvel theme
    private final Image[] marvelup = new Image[7];
    private final Image[] marveldown = new Image[7];
    private final Image[] marvelleft = new Image[7];
    private final Image[] marvelright = new Image[7];
    private final Image[] marveldying = new Image[8];

    // naruto theme
    private final Image[] narutoup = new Image[7];
    private final Image[] narutodown = new Image[7];
    private final Image[] narutoleft = new Image[7];
    private final Image[] narutoright = new Image[7];
    private final Image[] narutodying = new Image[8];

    // star wars theme
    private final Image[] starwarsup = new Image[7];
    private final Image[] starwarsdown = new Image[7];
    private final Image[] starwarsleft = new Image[7];
    private final Image[] starwarsright = new Image[7];
    private final Image[] starwarsdying = new Image[8];

    private int frame, delay, deathFrame;
    public boolean isDying;
    public static int currTheme = 5; // the default current theme is the original pacman theme
    private final boolean[] moveInDir = new boolean[4]; // each index corresponds with the constant directions; e.g moveInDir[0] represents right

    private final Image[][] pacmanSprites = {pacright, pacleft, pacup, pacdown}; // the sprites to make pac-man move across the screen
    private final Image[][] spideySprites = {spideyright, spideyleft, spideyup, spideydown}; // all spiderman's sprites
    private final Image[][] hpSprites = {hpright, hpleft, hpup, hpdown}; // all harry potter's sprites
    private final Image[][] marvelSprites = {marvelright, marvelleft, marvelup, marveldown}; // all harry potter's sprites
    private final Image[][] narutoSprites = {narutoright, narutoleft, narutoup, narutodown}; // all naruto's sprites
    private final Image[][] starWarsSprites = {starwarsright, starwarsleft, starwarsup, starwarsdown}; // all naruto's sprites
    private final Image[][][] themeSprites = {spideySprites, hpSprites, marvelSprites, narutoSprites, starWarsSprites, pacmanSprites}; // storing all the themes' sprites
    private final Image[][] themeDyingSprites = {spideydying, hpdying, marveldying, narutodying, starwarsdying, pacdying};

    public int pacx, pacy; // where pacman starts off when each level begins
    public int dir;
    public static int lives; // pacman's lives
    public int dx, dy;

    public int points;
    public static final int STARTX = 440, STARTY = 340;
    public static final int WAIT = 10, DYINGWAIT = 20; // WAIT is used to make a pause while changing pac-man's sprites and DYINGWAIT is for the sprites when pacman dies
    public static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3; // all the directions pacman can move in; according to the respective indexes in pacmanSprites

    SoundEffect pacman_die;

    public Pacman(int x, int y){
        for(int i = 0; i < 7; i++){
            pacup[i] = new ImageIcon("resources/pacman/pacmanup/pacman" + i + ".png").getImage();
            pacdown[i] = new ImageIcon("resources/pacman/pacmandown/pacman" + i + ".png").getImage();
            pacright[i] = new ImageIcon("resources/pacman/pacmanright/pacman" + i + ".png").getImage();
            pacleft[i] = new ImageIcon("resources/pacman/pacmanleft/pacman" + i + ".png").getImage();

            spideyup[i] = new ImageIcon("resources/spiderman/spidermanup/tile00" + i + ".png").getImage();
            spideydown[i] = new ImageIcon("resources/spiderman/spidermandown/tile00" + i + ".png").getImage();
            spideyright[i] = new ImageIcon("resources/spiderman/spidermanright/tile00" + i + ".png").getImage();
            spideyleft[i] = new ImageIcon("resources/spiderman/spidermanleft/tile00" + i + ".png").getImage();

            hpup[i] = new ImageIcon("resources/harrypotter/hpup/tile00" + i + ".png").getImage();
            hpdown[i] = new ImageIcon("resources/harrypotter/hpdown/tile00" + i + ".png").getImage();
            hpright[i] = new ImageIcon("resources/harrypotter/hpright/tile00" + i + ".png").getImage();
            hpleft[i] = new ImageIcon("resources/harrypotter/hpleft/tile00" + i + ".png").getImage();

            marvelup[i] = new ImageIcon("resources/marvel/marvelup/tile00" + i + ".png").getImage();
            marveldown[i] = new ImageIcon("resources/marvel/marveldown/tile00" + i + ".png").getImage();
            marvelright[i] = new ImageIcon("resources/marvel/marvelright/tile00" + i + ".png").getImage();
            marvelleft[i] = new ImageIcon("resources/marvel/marvelleft/tile00" + i + ".png").getImage();

            narutoup[i] = new ImageIcon("resources/naruto/narutoup/tile00" + i + ".png").getImage();
            narutodown[i] = new ImageIcon("resources/naruto/narutodown/tile00" + i + ".png").getImage();
            narutoright[i] = new ImageIcon("resources/naruto/narutoright/tile00" + i + ".png").getImage();
            narutoleft[i] = new ImageIcon("resources/naruto/narutoleft/tile00" + i + ".png").getImage();

            starwarsup[i] = new ImageIcon("resources/starwars/starwarsup/tile00" + i + ".png").getImage();
            starwarsdown[i] = new ImageIcon("resources/starwars/starwarsdown/tile00" + i + ".png").getImage();
            starwarsright[i] = new ImageIcon("resources/starwars/starwarsright/tile00" + i + ".png").getImage();
            starwarsleft[i] = new ImageIcon("resources/starwars/starwarsleft/tile00" + i + ".png").getImage();
        }

        for(int i = 0; i < 8; i++){
            spideydying[i] = new ImageIcon("resources/dying_pacman/spidey/spidey" + (i + 1) + ".png").getImage();
            hpdying[i] = new ImageIcon("resources/dying_pacman/hp/hp" + (i + 1) + ".png").getImage();
            marveldying[i] = new ImageIcon("resources/dying_pacman/marvel/marvel" + (i + 1) + ".png").getImage();
            narutodying[i] = new ImageIcon("resources/dying_pacman/naruto/naruto" + (i + 1) + ".png").getImage();
            starwarsdying[i] = new ImageIcon("resources/dying_pacman/starwars/starwars" + (i + 1) + ".png").getImage();
            pacdying[i] = new ImageIcon("resources/dying_pacman/pac/pac" + (i + 1) + ".png").getImage();
        }

        pacman_die = new SoundEffect("resources/sound effects/pacman_death.wav");

        dir = RIGHT; // pacman originally faces the right
        frame = 0;
        deathFrame = 0; // used to loop through the frames of pacman dying
        delay = 0;
        pacx = x;
        pacy = y;
        points = 0;
        lives = 3; // pacman starts with three lives
        isDying = false; // used to signify whether pacman is eaten and is dying
    }

    // moveBuffer() and drawBuffer() are used to draw the pacman during the buffer screen; only moves right
    public void moveBuffer(){
        pacx++;
        delay++;
        if(delay % WAIT == 0){
            frame++;
            frame %= pacmanSprites.length;
        }
    }

    public boolean drawBuffer(Graphics g){
        moveBuffer();
        g.drawImage(themeSprites[currTheme][0][frame], pacx, 375, null); // during the buffer, pacman moves to the right and off the screen
        if(pacx > 1000){ // if pacman is off the screen, start the game (flag)
            return true;
        }
        return false;
    }

    public void changeSprite(){
        delay++;
        if(delay % WAIT == 0){
            frame++;
            frame = frame % pacright.length;
        }
    }

    public void move(int dx, int dy){
        if(pacx % Maze.TILESIZE == 0 && pacy % Maze.TILESIZE == 0) {
            if (dx > 0 && moveInDir[RIGHT]) {
                dir = RIGHT;
                this.dx = dx;
                this.dy = dy;
            } else if (dx < 0 && moveInDir[LEFT]) {
                dir = LEFT;
                this.dx = dx;
                this.dy = dy;
            } else if (dy > 0 && moveInDir[DOWN]) {
                dir = DOWN;
                this.dx = dx;
                this.dy = dy;
            } else if (dy < 0 && moveInDir[UP]) {
                dir = UP;
                this.dx = dx;
                this.dy = dy;
            }
        }
    }

    public void checkDir(){
        if(pacx % Maze.TILESIZE == 0 && pacy % Maze.TILESIZE == 0) {
            int right = Maze.colGrid[(pacx + Maze.TILESIZE) / 20][(pacy) / 20];
            int left = Maze.colGrid[(pacx - Maze.TILESIZE) / 20][(pacy) / 20];
            int up = Maze.colGrid[(pacx) / 20][(pacy - Maze.TILESIZE) / 20];
            int down = Maze.colGrid[(pacx) / 20][(pacy + Maze.TILESIZE) / 20];

            // the tile represented by a 1 is a normal snack pellet, 9 is a power pellet, and 16 is plain black
            moveInDir[RIGHT] = right == 1 || right == 9 || right == 16;
            moveInDir[LEFT] = left == 1 || left == 9 || left == 16;
            moveInDir[UP] = up == 1 || up == 9 || up == 16;
            moveInDir[DOWN] = down == 1 || down == 9 || down == 16;
        }
    }

    public void eatPellet(){
        if(Maze.colGrid[(pacx) / 20][(pacy) / 20] == 1){ // if pacman is "eating" a normal snack pellet
            Maze.colGrid[(pacx) / 20][(pacy) / 20] = 16; // replace that tile with a plain black tile to make it look like the pellet was eaten
            points += 10;
        }
    }

    public void eatPowerPellet(Ghost gh1, Ghost gh2, Ghost gh3, Ghost gh4){
        if(Maze.colGrid[(pacx) / 20][(pacy) / 20] == 9){ // if pacman is "eating" a power pellet
            Maze.colGrid[(pacx) / 20][(pacy) / 20] = 16; // replace that tile with a plain black tile to make it look like the pellet was eaten
            points += 50;
            gh1.scareStatGhost = true;
            gh2.scareStatGhost = true;
            gh3.scareStatGhost = true;
            gh4.scareStatGhost = true;
            Ghost.scareTime = 0;
        }
    }

    // We used this site to get an idea of how to keep pacman moving from a single key press: https://stackoverflow.com/questions/16911722/continuous-movement-with-a-single-key-press
    // this method is used to move pacman based on the last
    public void moveUpdate(){
        if(!isDying) {
            if (dx != 0 || dy != 0) { // the sprites are only looped through when an arrow key is pressed
                changeSprite(); // while pacman is moving keep changing the sprites
            }

            // moving pacman based on the last key pressed from the move method
            pacx += dx; // right or left
            pacy += dy; // up or down

            moveInPortal(); // checking if pacman is passing through the portals

            if (!moveInDir[dir]) {
                // drawing pacman before the wall
                pacx -= dx;
                pacy -= dy;
                frame = 0; // pacman is "still" so draw the first sprite
            }
        }
    }

    // this method is used to draw all of the sprites for when pacman dies; returns true when all the sprites have been reached
    // and pacman respawns to it's original position
    public void death(){
        if(isDying) {
            delay++;
            if (delay % DYINGWAIT == 0) {
                if (deathFrame == 0) {
                    pacman_die.play();
                }
                deathFrame++;
                if (deathFrame == themeDyingSprites[currTheme].length) { // if all the sprites have been reached, make the frog idle
                    pacx = STARTX;
                    pacy = STARTY;
                    deathFrame = 0;
                    frame = 0;
                    dx = 0; dy = 0;
                    loseLive(); // when pacman is eaten by a ghost in normal mode, it loses a life
                    isDying = false;
                }
            }
        }
    }

    public void loseLive(){
        if(lives > 0) {
            lives--;
        }
    }

    // this method is used to draw pacman
    public void draw(Graphics g){
        if(!isDying) {
            g.drawImage(themeSprites[currTheme][dir][frame], pacx, pacy, null); // drawing pacman based on his direction and drawing each sprite
        } else{
            g.drawImage(themeDyingSprites[currTheme][deathFrame], pacx, pacy, null); // drawing pacman based on his direction and drawing each sprite
        }
    }

    // this method is used to move pacman through the left and right portals
    public void moveInPortal(){
        if(pacx == 0 && pacy == 280){ // the black pathway on the left side
            pacx = 1000; // pacman appears from the right "portal"
        }
        else if(pacx == 1000 && pacy == 280){ // the black pathway on the right side
            pacx = 40; // pacman appears from the left "portal"
        }
    }

    public Rectangle getRect(){
        int width = themeSprites[currTheme][dir][frame].getWidth(null);
        int height = themeSprites[currTheme][dir][frame].getHeight(null);
        return new Rectangle(pacx, pacy, width, height);
    }

    public void drawLives(Graphics g){
        for(int i = 0; i < lives; i++){
            g.drawImage(themeSprites[currTheme][RIGHT][0], 200 + i * 25, 615, null);
        }
    }

    public void drawPoints(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Optima", Font.PLAIN, 15)); // changing the font for the points string
        g.drawString(""+points + " points", 500, 633); // drawing the points below the maze during the game
    }
}