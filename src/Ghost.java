import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.*;

public class Ghost {
    // This class is used to draw and move the four ghosts. There are different methods for different movement patterns, including
    // chase mode and scatter mode, and they check for collisions with pacman in both of these modes.

    private int frame, delay, scaredFrame; // the frame represents the current sprite being drawn, and delay acts as a buffer when changing sprites
    public static int scareDelay; // time will act as a countdown and delay ensures scatter mode stays for some time
    public static int scareTime; // time will act as a countdown to control chase and frightened mode
    private final boolean[] moveInDir = new boolean[4]; // each index corresponds with the constant directions; e.g moveInDir[0] represents right

    // the ghosts for the normal pacman theme
    private final Image[] redright = new Image[2];
    private final Image[] redleft = new Image[2];
    private final Image[] redup = new Image[2];
    private final Image[] reddown = new Image[2];
    private final Image[][] redSprites = {redright, redleft, redup, reddown}; // the sprites to make the red ghost move across the screen

    private final Image[] pinkright = new Image[2];
    private final Image[] pinkleft = new Image[2];
    private final Image[] pinkup = new Image[2];
    private final Image[] pinkdown = new Image[2];
    private final Image[][] pinkSprites = {pinkright, pinkleft, pinkup, pinkdown}; // the sprites to make the pink ghost move across the screen

    private final Image[] greenright = new Image[2];
    private final Image[] greenleft = new Image[2];
    private final Image[] greenup = new Image[2];
    private final Image[] greendown = new Image[2];
    private final Image[][] greenSprites = {greenright, greenleft, greenup, greendown}; // the sprites to make the green ghost move across the screen

    private final Image[] orangeright = new Image[2];
    private final Image[] orangeleft = new Image[2];
    private final Image[] orangeup = new Image[2];
    private final Image[] orangedown = new Image[2];
    private final Image[][] orangeSprites = {orangeright, orangeleft, orangeup, orangedown}; // the sprites to make the orange ghost move across the screen

    private final Image[] scaredSprites = new Image[2]; // sprites drawn during scatter mode

    private final Image[][][] ogGhosts = {redSprites,pinkSprites,greenSprites,orangeSprites};

    // the ghosts for the Harry Potter theme
    private final Image[] bellatrixdown = new Image[3];
    private final Image[] bellatrixleft = new Image[3];
    private final Image[] bellatrixright = new Image[3];
    private final Image[] bellatrixup = new Image[3];
    private final Image[][] bellatrixSprites = {bellatrixright, bellatrixleft, bellatrixup, bellatrixdown};

    private final Image[] luciusdown = new Image[3];
    private final Image[] luciusleft = new Image[3];
    private final Image[] luciusright = new Image[3];
    private final Image[] luciusup = new Image[3];
    private final Image[][] luciusSprites = {luciusright, luciusleft, luciusup, luciusdown};

    private final Image[] snapedown = new Image[3];
    private final Image[] snapeleft = new Image[3];
    private final Image[] snaperight = new Image[3];
    private final Image[] snapeup = new Image[3];
    private final Image[][] snapeSprites = {snaperight, snapeleft, snapeup, snapedown};

    private final Image[] voldemortdown = new Image[3];
    private final Image[] voldemortleft = new Image[3];
    private final Image[] voldemortright = new Image[3];
    private final Image[] voldemortup = new Image[3];
    private final Image[][] voldemortSprites = {voldemortright, voldemortleft, voldemortup, voldemortdown};

    private final Image[][][] hpGhosts = {bellatrixSprites, luciusSprites, snapeSprites, voldemortSprites};

    // the ghosts for the Star Wars theme
    private final Image[] balddown = new Image[3];
    private final Image[] baldleft = new Image[3];
    private final Image[] baldright = new Image[3];
    private final Image[] baldup = new Image[3];
    private final Image[][] baldSprites = {baldright, baldleft, baldup, balddown};

    private final Image[] hooddown = new Image[3];
    private final Image[] hoodleft = new Image[3];
    private final Image[] hoodright = new Image[3];
    private final Image[] hoodup = new Image[3];
    private final Image[][] hoodSprites = {hoodright, hoodleft, hoodup, hooddown};

    private final Image[] maskdown = new Image[3];
    private final Image[] maskleft = new Image[3];
    private final Image[] maskright = new Image[3];
    private final Image[] maskup = new Image[3];
    private final Image[][] maskSprites = {maskright, maskleft, maskup, maskdown};

    private final Image[] redfacedown = new Image[3];
    private final Image[] redfaceleft = new Image[3];
    private final Image[] redfaceright = new Image[3];
    private final Image[] redfaceup = new Image[3];
    private final Image[][] redfaceSprites = {redfaceright, redfaceleft, redfaceup, redfacedown};

    private final Image[][][] starwarsGhosts = {baldSprites, hoodSprites, maskSprites, redfaceSprites};

    // the ghosts for the Marvel theme
    private final Image[] lokidown = new Image[3];
    private final Image[] lokileft = new Image[3];
    private final Image[] lokiright = new Image[3];
    private final Image[] lokiup = new Image[3];
    private final Image[][] lokiSprites = {lokiright, lokileft, lokiup, lokidown};

    private final Image[] thanosdown = new Image[3];
    private final Image[] thanosleft = new Image[3];
    private final Image[] thanosright = new Image[3];
    private final Image[] thanosup = new Image[3];
    private final Image[][] thanosSprites = {thanosright, thanosleft, thanosup, thanosdown};

    private final Image[] ultrondown = new Image[3];
    private final Image[] ultronleft = new Image[3];
    private final Image[] ultronright = new Image[3];
    private final Image[] ultronup = new Image[3];
    private final Image[][] ultronSprites = {ultronright, ultronleft, ultronup, ultrondown};

    private final Image[] wintsoldierdown = new Image[3];
    private final Image[] wintsoldierleft = new Image[3];
    private final Image[] wintsoldierright = new Image[3];
    private final Image[] wintsoldierup = new Image[3];
    private final Image[][] wintsoldierSprites = {wintsoldierright, wintsoldierleft, wintsoldierup, wintsoldierdown};

    private final Image[][][] marvelGhosts = {lokiSprites, thanosSprites, ultronSprites, wintsoldierSprites};

    // the ghosts for the Spiderman theme
    private final Image[] appendagedown = new Image[3];
    private final Image[] appendageleft = new Image[3];
    private final Image[] appendageright = new Image[3];
    private final Image[] appendageup = new Image[3];
    private final Image[][] appendageSprites = {appendageright, appendageleft, appendageup, appendagedown};

    private final Image[] fatbalddown = new Image[3];
    private final Image[] fatbaldleft = new Image[3];
    private final Image[] fatbaldright = new Image[3];
    private final Image[] fatbaldup = new Image[3];
    private final Image[][] fatbaldSprites = {fatbaldright, fatbaldleft, fatbaldup, fatbalddown};

    private final Image[] mysteriodown = new Image[3];
    private final Image[] mysterioleft = new Image[3];
    private final Image[] mysterioright = new Image[3];
    private final Image[] mysterioup = new Image[3];
    private final Image[][] mysterioSprites = {mysterioright, mysterioleft, mysterioup, mysteriodown};

    private final Image[] venomdown = new Image[3];
    private final Image[] venomleft = new Image[3];
    private final Image[] venomright = new Image[3];
    private final Image[] venomup = new Image[3];
    private final Image[][] venomSprites = {venomright, venomleft, venomup, venomdown};

    private final Image[][][] spidermanGhosts = {appendageSprites, fatbaldSprites, mysterioSprites, venomSprites};

    // the ghosts for the Naruto theme
    private final Image[] gaaradown = new Image[4];
    private final Image[] gaaraleft = new Image[4];
    private final Image[] gaararight = new Image[4];
    private final Image[] gaaraup = new Image[4];
    private final Image[][] gaaraSprites = {gaararight, gaaraleft, gaaraup, gaaradown};

    private final Image[] paindown = new Image[4];
    private final Image[] painleft = new Image[4];
    private final Image[] painright = new Image[4];
    private final Image[] painup = new Image[4];
    private final Image[][] painSprites = {painright, painleft, painup, paindown};

    private final Image[] itachidown = new Image[4];
    private final Image[] itachileft = new Image[4];
    private final Image[] itachiright = new Image[4];
    private final Image[] itachiup = new Image[4];
    private final Image[][] itachiSprites = {itachiright, itachileft, itachiup, itachidown};

    private final Image[] sasukedown = new Image[4];
    private final Image[] sasukeleft = new Image[4];
    private final Image[] sasukeright = new Image[4];
    private final Image[] sasukeup = new Image[4];
    private final Image[][] sasukeSprites = {sasukeright, sasukeleft, sasukeup, sasukedown};

    private final Image[][][] narutoGhosts = {gaaraSprites, painSprites, itachiSprites, sasukeSprites};

    //private final Image[][][][] themeGhosts = {spidermanGhosts, hpGhosts, marvelGhosts, narutoGhosts, starwarsGhosts, ogGhosts};

    public int gx, gy; // where the ghost starts off when the level begins
    public String col; // the colour of the ghost
    public int dir, dx, dy; // dir is to change the sprite and dx and dy are used to move the ghosts

    public boolean scareStatGhost; // represents whether the ghost is in scared mode or not

    public int STARTX, STARTY; // start positions of the ghosts
    public static final int WAIT = 10; // WAIT is used to make a pause while changing the sprites
    public static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3; // values of the directions the ghosts can move in (according to the indices in the sprite arrays)

    // the points the ghosts will move through in scatter mode at each corner of the maze
    private final ArrayList<Point> tlcorner = cornerArray("topleft");
    private final ArrayList<Point> trcorner = cornerArray("topright");
    private final ArrayList<Point> brcorner = cornerArray("bottomright");
    private final ArrayList<Point> blcorner = cornerArray("bottomleft");

    SoundEffect eat_ghost;

    public Ghost(int x, int y, String c){
        // storing the initial position of the ghost; used to respawn the ghosts when their eaten or when pacman loses a live
        STARTX = x;
        STARTY = y;

        // loading all the sprites for the ghosts of all themes in all four directions (right, left, up, down)
        for(int i = 0; i < 8; i++){
            if(i<2){ // right
                redright[i] = new ImageIcon("resources/theme_sprites/pacman_ghosts/redghost/red" + i + ".png").getImage();
                pinkright[i] = new ImageIcon("resources/theme_sprites/pacman_ghosts/pinkghost/pink" + i + ".png").getImage();
                greenright[i] = new ImageIcon("resources/theme_sprites/pacman_ghosts/greenghost/green" + i + ".png").getImage();
                orangeright[i] = new ImageIcon("resources/theme_sprites/pacman_ghosts/orangeghost/orange" + i + ".png").getImage();
                scaredSprites[i] = new ImageIcon("resources/theme_sprites/pacman_ghosts/scaredghost/scare" + i + ".png").getImage();
            }
            else if(i<4){ // down
                reddown[i-2] = new ImageIcon("resources/theme_sprites/pacman_ghosts/redghost/red" + i + ".png").getImage();
                pinkdown[i-2] = new ImageIcon("resources/theme_sprites/pacman_ghosts/pinkghost/pink" + i + ".png").getImage();
                greendown[i-2] = new ImageIcon("resources/theme_sprites/pacman_ghosts/greenghost/green" + i + ".png").getImage();
                orangedown[i-2] = new ImageIcon("resources/theme_sprites/pacman_ghosts/orangeghost/orange" + i + ".png").getImage();
            }
            else if(i<6){ // left
                redleft[i-4] = new ImageIcon("resources/theme_sprites/pacman_ghosts/redghost/red" + i + ".png").getImage();
                pinkleft[i-4] = new ImageIcon("resources/theme_sprites/pacman_ghosts/pinkghost/pink" + i + ".png").getImage();
                greenleft[i-4] = new ImageIcon("resources/theme_sprites/pacman_ghosts/greenghost/green" + i + ".png").getImage();
                orangeleft[i-4] = new ImageIcon("resources/theme_sprites/pacman_ghosts/orangeghost/orange" + i + ".png").getImage();
            }
            else if(i<8){ // up
                redup[i-6] = new ImageIcon("resources/theme_sprites/pacman_ghosts/redghost/red" + i + ".png").getImage();
                pinkup[i-6] = new ImageIcon("resources/theme_sprites/pacman_ghosts/pinkghost/pink" + i + ".png").getImage();
                greenup[i-6] = new ImageIcon("resources/theme_sprites/pacman_ghosts/greenghost/green" + i + ".png").getImage();
                orangeup[i-6] = new ImageIcon("resources/theme_sprites/pacman_ghosts/orangeghost/orange" + i + ".png").getImage();
            }
        }

        for(int i = 0; i < 12; i++) {
            if(i<3) {
                bellatrixdown[i] = new ImageIcon("resources/theme_sprites/hp_ghosts/bellatrix/tile"+i+".png").getImage();
                luciusdown[i] = new ImageIcon("resources/theme_sprites/hp_ghosts/lucius/tile"+i+".png").getImage();
                snapedown[i] = new ImageIcon("resources/theme_sprites/hp_ghosts/snape/tile"+i+".png").getImage();
                voldemortdown[i] = new ImageIcon("resources/theme_sprites/hp_ghosts/voldemort/tile"+i+".png").getImage();

                balddown[i] = new ImageIcon("resources/theme_sprites/starwars_ghosts/bald/tile"+i+".png").getImage();
                hooddown[i] = new ImageIcon("resources/theme_sprites/starwars_ghosts/hood/tile"+i+".png").getImage();
                maskdown[i] = new ImageIcon("resources/theme_sprites/starwars_ghosts/mask/tile"+i+".png").getImage();
                redfacedown[i] = new ImageIcon("resources/theme_sprites/starwars_ghosts/redface/tile"+i+".png").getImage();

                lokidown[i] = new ImageIcon("resources/theme_sprites/marvel_ghosts/loki/tile"+i+".png").getImage();
                thanosdown[i] = new ImageIcon("resources/theme_sprites/marvel_ghosts/thanos/tile"+i+".png").getImage();
                ultrondown[i] = new ImageIcon("resources/theme_sprites/marvel_ghosts/ultron/tile"+i+".png").getImage();
                wintsoldierdown[i] = new ImageIcon("resources/theme_sprites/marvel_ghosts/wintsoldier/tile"+i+".png").getImage();

                appendagedown[i] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/appendageman/tile"+i+".png").getImage();
                fatbalddown[i] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/fatbald/tile"+i+".png").getImage();
                mysteriodown[i] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/mysterio/tile"+i+".png").getImage();
                venomdown[i] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/venom/tile"+i+".png").getImage();
            }
            else if(i<6) {
                int i0 = i-3;
                bellatrixleft[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/bellatrix/tile"+i+".png").getImage();
                luciusleft[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/lucius/tile"+i+".png").getImage();
                snapeleft[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/snape/tile"+i+".png").getImage();
                voldemortleft[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/voldemort/tile"+i+".png").getImage();

                baldleft[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/bald/tile"+i+".png").getImage();
                hoodleft[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/hood/tile"+i+".png").getImage();
                maskleft[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/mask/tile"+i+".png").getImage();
                redfaceleft[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/redface/tile"+i+".png").getImage();

                lokileft[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/loki/tile"+i+".png").getImage();
                thanosleft[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/thanos/tile"+i+".png").getImage();
                ultronleft[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/ultron/tile"+i+".png").getImage();
                wintsoldierleft[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/wintsoldier/tile"+i+".png").getImage();

                appendageleft[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/appendageman/tile"+i+".png").getImage();
                fatbaldleft[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/fatbald/tile"+i+".png").getImage();
                mysterioleft[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/mysterio/tile"+i+".png").getImage();
                venomleft[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/venom/tile"+i+".png").getImage();
            }
            else if(i<9) {
                int i0 = i-6;
                bellatrixright[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/bellatrix/tile"+i+".png").getImage();
                luciusright[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/lucius/tile"+i+".png").getImage();
                snaperight[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/snape/tile"+i+".png").getImage();
                voldemortright[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/voldemort/tile"+i+".png").getImage();

                baldright[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/bald/tile"+i+".png").getImage();
                hoodright[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/hood/tile"+i+".png").getImage();
                maskright[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/mask/tile"+i+".png").getImage();
                redfaceright[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/redface/tile"+i+".png").getImage();

                lokiright[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/loki/tile"+i+".png").getImage();
                thanosright[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/thanos/tile"+i+".png").getImage();
                ultronright[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/ultron/tile"+i+".png").getImage();
                wintsoldierright[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/wintsoldier/tile"+i+".png").getImage();

                appendageright[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/appendageman/tile"+i+".png").getImage();
                fatbaldright[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/fatbald/tile"+i+".png").getImage();
                mysterioright[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/mysterio/tile"+i+".png").getImage();
                venomright[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/venom/tile"+i+".png").getImage();
            }
            else {
                int i0 = i-9;
                bellatrixup[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/bellatrix/tile"+i+".png").getImage();
                luciusup[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/lucius/tile"+i+".png").getImage();
                snapeup[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/snape/tile"+i+".png").getImage();
                voldemortup[i0] = new ImageIcon("resources/theme_sprites/hp_ghosts/voldemort/tile"+i+".png").getImage();

                baldup[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/bald/tile"+i+".png").getImage();
                hoodup[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/hood/tile"+i+".png").getImage();
                maskup[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/mask/tile"+i+".png").getImage();
                redfaceup[i0] = new ImageIcon("resources/theme_sprites/starwars_ghosts/redface/tile"+i+".png").getImage();

                lokiup[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/loki/tile"+i+".png").getImage();
                thanosup[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/thanos/tile"+i+".png").getImage();
                ultronup[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/ultron/tile"+i+".png").getImage();
                wintsoldierup[i0] = new ImageIcon("resources/theme_sprites/marv_ghosts/wintsoldier/tile"+i+".png").getImage();

                appendageup[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/appendageman/tile"+i+".png").getImage();
                fatbaldup[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/fatbald/tile"+i+".png").getImage();
                mysterioup[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/mysterio/tile"+i+".png").getImage();
                venomup[i0] = new ImageIcon("resources/theme_sprites/spiderman_ghosts/venom/tile"+i+".png").getImage();
            }
        }

        for (int i = 0; i < 16; i++) {
            if(i<4) {
                gaaradown[i] = new ImageIcon("resources/theme_sprites/naruto_ghosts/gaara/tile"+i+".png").getImage();
                paindown[i] = new ImageIcon("resources/theme_sprites/naruto_ghosts/pain/tile"+i+".png").getImage();
                itachidown[i] = new ImageIcon("resources/theme_sprites/naruto_ghosts/itachi/tile"+i+".png").getImage();
                sasukedown[i] = new ImageIcon("resources/theme_sprites/naruto_ghosts/sasuke/tile"+i+".png").getImage();
            }
            else if(i<8) {
                int i0 = i-4;
                gaaraleft[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/gaara/tile"+i+".png").getImage();
                painleft[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/pain/tile"+i+".png").getImage();
                itachileft[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/itachi/tile"+i+".png").getImage();
                sasukeleft[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/sasuke/tile"+i+".png").getImage();
            }
            else if(i<12) {
                int i0 = i-8;
                gaararight[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/gaara/tile"+i+".png").getImage();
                painright[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/pain/tile"+i+".png").getImage();
                itachiright[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/itachi/tile"+i+".png").getImage();
                sasukeright[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/sasuke/tile"+i+".png").getImage();
            }
            else {
                int i0 = i-12;
                gaaraup[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/gaara/tile"+i+".png").getImage();
                painup[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/pain/tile"+i+".png").getImage();
                itachiup[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/itachi/tile"+i+".png").getImage();
                sasukeup[i0] = new ImageIcon("resources/theme_sprites/naruto_ghosts/sasuke/tile"+i+".png").getImage();
            }
        }

        eat_ghost = new SoundEffect("resources/sound effects/pacman_eatghost.wav");

        frame = 0;
        scaredFrame = 0;
        delay = 0;
        scareTime = 0;
        scareDelay = 0;
        scareStatGhost = false;
        gx = x;
        gy = y;
        col = c;
    }

    public void changeSprite(){
        // this method is used to change the sprites of the ghosts when moving
        if(Pacman.currTheme == 0 || Pacman.currTheme == 1 || Pacman.currTheme == 2 || Pacman.currTheme == 4) {
            delay++;
            if (delay % WAIT == 0) { // creating a buffer when changing sprites
                frame++;
                frame = frame % 3; // looping through the num of sprites the ghosts have (diff based on theme)
            }
        } else if(Pacman.currTheme == 3) { // naruto ghost sprites
            delay++;
            if (delay % WAIT == 0) {
                frame++;
                frame = frame % 4;
            }
        } else if(Pacman.currTheme == 5) { // original pacman ghost sprites
            delay++;
            if (delay % WAIT == 0) {
                frame++;
                frame = frame % 2;
            }
        }

        if(scareStatGhost){
            delay++;
            if (delay % WAIT == 0) {
                scaredFrame++;
                scaredFrame = scaredFrame % 2;
            }
        }
    }

    public void move(ArrayList<Point> coor){
        // this method is used to move the ghosts based on the path found by the A* algorithm.
        // the arraylist of points are passed in as a parameter.

        changeSprite();
        checkDir();

        int i; // the index of the coordinate pacman is moving to from the coor list
        if (coor.size() > 1) { // if the list of points is greater than 1
            i = 1; // get the next coordinate in the list
        }
        else { // if theres only one coordinate to move to
            i = 0; // move to it
        }
        if(gx == coor.get(i).x){ // moving vertically
            if(GamePanel.level == GamePanel.LEVEL1) {
                if (gy > coor.get(i).y && moveInDir[UP]) { // moving upwards
                    dir = UP;
                    dx = 0;
                    dy = -1;
                } else if (gy < coor.get(i).y && moveInDir[DOWN]) { // moving downwards
                    dir = DOWN;
                    dx = 0;
                    dy = 1;
                }
            } else if(GamePanel.level == GamePanel.LEVEL2){
                if (gy > coor.get(i).y && moveInDir[UP]) { // moving upwards
                    dir = UP;
                    dx = 0;
                    dy = -2;
                } else if (gy < coor.get(i).y && moveInDir[DOWN]) { // moving downwards
                    dir = DOWN;
                    dx = 0;
                    dy = 2;
                }
            }
        }
        else if(gy == coor.get(i).y) { // moving horizontally
            if(GamePanel.level == GamePanel.LEVEL1) {
                if (gx > coor.get(i).x && moveInDir[LEFT]) { // moving left
                    dir = LEFT;
                    dx = -1;
                    dy = 0;
                } else if (gx < coor.get(i).x && moveInDir[RIGHT]) { // moving right
                    dir = RIGHT;
                    dx = 1;
                    dy = 0;
                }
            } else if(GamePanel.level == GamePanel.LEVEL2) {
                if (gx > coor.get(i).x && moveInDir[LEFT]) { // moving left
                    dir = LEFT;
                    dx = -2;
                    dy = 0;
                } else if (gx < coor.get(i).x && moveInDir[RIGHT]) { // moving right
                    dir = RIGHT;
                    dx = 2;
                    dy = 0;
                }
            }
        }
        gx += dx;
        gy += dy;
        if(gx % Maze.TILESIZE != 0 && gy % Maze.TILESIZE != 0) {
            gx += dx;
            gy += dy;
        }
    }

//    public void randomMove() {
//        // random movement for the orange and blue ghosts
//        // the ghosts will move randomly during frightened mode
//        changeSprite(); // while pacman is moving keep changing the sprites
//
//        int SPEED = 0;
//        if (GamePanel.level == GamePanel.LEVEL1) {
//            SPEED = 1;
//        }
//        else if (GamePanel.level == GamePanel.LEVEL2) {
//            SPEED = 2;
//        }
//
//        if(dir == RIGHT){
//            dx = SPEED;
//            dy = 0;
//        } if(dir == LEFT){
//            dx = -SPEED;
//            dy = 0;
//        } if(dir == UP){
//            dx = 0;
//            dy = -SPEED;
//        } if(dir == DOWN){
//            dx = 0;
//            dy = SPEED;
//        }
//
//        gx += dx;
//        gy += dy;
//
//        if(!moveInDir[dir]) { // if the ghost has reached a wall
//            gx -= dx;
//            gy -= dy;
//            ArrayList<Integer> possibleDirs = new ArrayList<>();
//            for (int i = 0; i < moveInDir.length; i++) {
//                if (moveInDir[i] && i != oppDir(dir)) {
//                    possibleDirs.add(i);
//                }
//            }
//            Collections.shuffle(possibleDirs);
//            dir = possibleDirs.get(0);
//        }
//    }


    public void trackScatterMode(){
        // keeps track of the time during scatter mode
        if(scareStatGhost) {
            scareDelay++;
            if(scareDelay % WAIT == 0){
                scareTime++;
                if(GamePanel.level == GamePanel.LEVEL1) {
                    if (scareTime == 100) {
                        endScatterMode(); // scatter mode only stays for a certain time period
                    }
                } else if(GamePanel.level == GamePanel.LEVEL2) {
                    if (scareTime == 80) {
                        endScatterMode(); // scatter mode only stays for a certain time period
                    }
                }
            }
        }
    }

    public void endScatterMode(){
        // resets variables so that the scatter mode ends
        scareStatGhost = false; // no longer in scatter mode; back to chasing pacman
        scareTime = 0; // re-setting the time each time for when pacman eats another power pellet
    }

    public void outOfBox() {
        // to move the ghosts out of the ghost house
        // box opening tile: (500, 220)
        if (gx >= 460 && gx <= 580 && gy >= 240 && gy <= 330) { // if the ghost is inside the box
            if(gy != 260){
                gy--;
            }
            if(gx != 520){
                if(gx > 520){
                    gx--;
                }
                else{
                    gx++;
                }
            }
            gx = 520;
            gy = 220;
        }
    }

    public int oppDir(int dir){
        // returns the opposite direction of the input dir
        if (dir == RIGHT){
            return LEFT;
        } else if(dir == LEFT){
            return RIGHT;
        } else if(dir == UP){
            return DOWN;
        } else if(dir == DOWN){
            return UP;
        }
        return -1;
    }

    boolean reachedCorner = false;
    int index = 0; // represents which node pacman is moving to
    public void scatterMode(AStar ai, String c) {
        // the ghost will retreat to its respective corner and stay circling that corner for the duration of scatter mode
        if (c.equals("topleft")) { // goes to the top left corner
            if (reachedCorner) { // if its at the corner, it will circle it
                circleCorner(ai,tlcorner);
            }
            else { // if not, we call the a* algorithm to get to the corner
                move(ai.chaseMode(20, 20));
                if (gx == 20 && gy == 20) {
                    reachedCorner = true;
                }
            }
        }
        else if (c.equals("topright")) { // goes to the top right corner
            if (reachedCorner) { // if its at the corner, it will circle it
                circleCorner(ai,trcorner);
            }
            else { // if not, we call the a* algorithm to get to the corner
                move(ai.chaseMode(1000, 20));
                if (gx == 1000 && gy == 20) {
                    reachedCorner = true;
                }
            }
        }
        else if (c.equals("bottomright")) { // goes to the bottom right corner
            if (reachedCorner) { // if its at the corner, it will circle it
                circleCorner(ai,brcorner);
            }
            else { // if not, we call the a* algorithm to get to the corner
                move(ai.chaseMode(1000, 580));
                if (gx == 1000 && gy == 580) {
                    reachedCorner = true;
                }
            }
        }
        else if (c.equals("bottomleft")) { // goes to the bottom left corner
            if (reachedCorner) { // if its at the corner, it will circle it
                circleCorner(ai,blcorner);
            }
            else { // if not, we call the a* algorithm to get to the corner
                move(ai.chaseMode(20, 580));
                if (gx == 20 && gy == 580) {
                    reachedCorner = true;
                }
            }
        }
    }

    public void circleCorner(AStar ai,ArrayList<Point> points) {
        // moves the ghost through all the points in p and restarts when done
        if (index == points.size()-1) { // base case
            index = 0;
        }
        else {
            // set the end node
            int x = (int)points.get(index).getX();
            int y = (int)points.get(index).getY();
            if (gx == x && gy == y) { // if the end node has been reached
                index++; // move on to the next node in the points list
                // reset the x and y
                x = (int)points.get(index).getX();
                y = (int)points.get(index).getY();
            }
            move(ai.chaseMode(x,y));
        }
    }

    public ArrayList<Point> cornerArray(String c) {
        // returns an arraylist of all the nodes in the respective corner
        // the ghost will move through the nodes in circleCorner using AStar
        ArrayList <Point> cornerNodes = new ArrayList<>();
        if (c.equals("topleft")) { // top left corner
            cornerNodes.add(new Point(240,20));
            cornerNodes.add(new Point(240,100));
            cornerNodes.add(new Point(120,100));
            cornerNodes.add(new Point(120,160));
            cornerNodes.add(new Point(20,160));
            cornerNodes.add(new Point(20,20));
        }
        else if (c.equals("topright")) { // goes to the top right corner
            cornerNodes.add(new Point(780,20));
            cornerNodes.add(new Point(780,100));
            cornerNodes.add(new Point(900,100));
            cornerNodes.add(new Point(900,160));
            cornerNodes.add(new Point(1000,160));
            cornerNodes.add(new Point(1000,20));
        }
        else if (c.equals("bottomright")) { // goes to the bottom right corner
            cornerNodes.add(new Point(1000,520));
            cornerNodes.add(new Point(900,520));
            cornerNodes.add(new Point(900,460));
            cornerNodes.add(new Point(840,460));
            cornerNodes.add(new Point(840,520));
            cornerNodes.add(new Point(780,520));
            cornerNodes.add(new Point(780,580));
            cornerNodes.add(new Point(1000,580));
        }
        else if (c.equals("bottomleft")) { // goes to the bottom left corner
            cornerNodes.add(new Point(20,520));
            cornerNodes.add(new Point(120,520));
            cornerNodes.add(new Point(120,460));
            cornerNodes.add(new Point(180,460));
            cornerNodes.add(new Point(180,520));
            cornerNodes.add(new Point(240,520));
            cornerNodes.add(new Point(240,580));
            cornerNodes.add(new Point(20,580));

        }
        return cornerNodes;
    }

    public void draw(Graphics g){
        // draws the ghosts
        if (scareStatGhost) { // ghosts during frightened mode
            g.drawImage(scaredSprites[scaredFrame], gx, gy, null);
        }
        else {
            int ghostNum = 0; // represents which ghost is being drawn out of the 4
            if (col.equals("red") || col.equals("bellatrix") || col.equals("bald") || col.equals("loki") || col.equals("appendage") || col.equals("gaara")) {
                ghostNum = 0;
            }
            else if (col.equals("pink") || col.equals("lucius") || col.equals("hood") || col.equals("thanos") || col.equals("fatbald") || col.equals("pain")) {
                ghostNum = 1;
            }
            else if (col.equals("green") || col.equals("snape") || col.equals("mask") || col.equals("ultron") || col.equals("mysterio") || col.equals("itachi")) {
                ghostNum = 2;
            }
            else if (col.equals("orange") || col.equals("voldemort") || col.equals("redface") || col.equals("wintsoldier") || col.equals("venom") || col.equals("sasuke")) {
                ghostNum = 3;
            }
            // drawing the specific ghosts based on the current chosen theme
            if(Pacman.currTheme == 0){
                g.drawImage(spidermanGhosts[ghostNum][dir][frame], gx, gy, null); // drawing the ghosts based on their direction and drawing the current sprite
            } else if(Pacman.currTheme == 1){
                g.drawImage(hpGhosts[ghostNum][dir][frame], gx, gy, null);
            } else if(Pacman.currTheme == 2){
                g.drawImage(marvelGhosts[ghostNum][dir][frame], gx, gy, null);
            } else if(Pacman.currTheme == 3){
                g.drawImage(narutoGhosts[ghostNum][dir][frame], gx, gy, null); // drawing pacman based on his direction and drawing each sprite
            } else if(Pacman.currTheme == 4){
                g.drawImage(starwarsGhosts[ghostNum][dir][frame], gx, gy, null); // drawing pacman based on his direction and drawing each sprite
            } else{
                g.drawImage(ogGhosts[ghostNum][dir][frame], gx, gy, null); // drawing pacman based on his direction and drawing each sprite
            }
        }
    }

    public void checkDir(){
        // checks if the neighbouring tiles are ok to move to (ie. not an obstacle)
        if(gx % Maze.TILESIZE == 0 && gy % Maze.TILESIZE == 0) {
            int right = Maze.colGrid[(gx + Maze.TILESIZE) / 20][(gy) / 20];
            int left = Maze.colGrid[((gx - Maze.TILESIZE) / 20)][(gy) / 20];
            int up = Maze.colGrid[(gx) / 20][((gy - Maze.TILESIZE) / 20)];
            int down = Maze.colGrid[(gx) / 20][(gy + Maze.TILESIZE) / 20];

            // the tile represented by a 1 is a normal snack pellet, 16 is plain black, 9 is a power pellet
            moveInDir[RIGHT] = right == 1 || right == 16 || right == 9;
            moveInDir[LEFT] = left == 1 || left == 16 || left == 9;
            moveInDir[UP] = up == 1 || up == 16 || up == 9;
            moveInDir[DOWN] = down == 1 || down == 16 || down == 9;

            if((gx == 500 && gy == 240) || (gx == 520 && gy == 240)){
                moveInDir[DOWN] = false;
            }
        }
    }

    public Rectangle getRect(){
        // returns a rectangle of the ghost
        int width = 20;
        int height = 20;
        //int height = ogGhosts[0][dir][frame].getHeight(null);
        return new Rectangle(gx, gy, width, height);
    }

    public boolean ifEat(Pacman pac){
        // is called when pacman gets eaten by a ghost
        boolean eaten = false;
        if(!scareStatGhost){ // in normal mode, the ghosts eat pacman
            if(pac.getRect().intersects(getRect())){
                eaten = true;
            }
        }
        return eaten;
    }

    public boolean ifEatenBy(Pacman pac){
        // is called when a ghost gets eaten by pacman
        boolean eaten = false;
        if(scareStatGhost){ // when the ghosts are in frightened mode, pacman eats the ghosts
            if(pac.getRect().intersects(getRect())){
                eat_ghost.play();
                eaten = true;
                scareStatGhost = false;
            }
        }
        return eaten;
    }
}