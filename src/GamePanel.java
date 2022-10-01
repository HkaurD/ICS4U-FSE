import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// This class contains all the game's components including the graphics, movements and interactions with other classes.
// It also checks for any actions, keys pressed and mouse actions.
public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    // all the different levels in the game
    public static final int INTRO = 0, HOWTOPLAY = 1, HOWTOPLAY2 = 2, SETTINGS = 3, HIGHSCORE = 4, THEMES = 5, BUFFER = 6, LEVEL1 = 7, BUFFER2 = 8, LEVEL2 = 9, LOSE = 10, WIN = 11;
    public static final int SPIDERMAN = 100, HP = 101, MARVEL = 102, NARUTO = 103, STARWARS = 104, OGPACMAN = 105; // these represent the corresponding index in chosenTheme; value % 100
    public static int level; // stores the level the user is at in the game
    public static ArrayList<Integer> chosenTheme = new ArrayList<Integer>(); // there are six theme options; 1 means the theme is clicked/chosen

    private String name; // the name of the player
    private final boolean[] keys;
    private int lastKey = -1; // this is used to store the last key as true until a new key is pressed
    private final Point mouse = new Point(); // stores the x and y-coordinates of the mouse; used for hovering over buttons
    // the areas contained by each button used to check if the mouse is hovering over the button or if a button is pressed
    private final Rectangle introHowToPlay, next, backIntro, backpg1, introPlay, introSettings, settingshs, settingstheme, spidermantheme, hptheme, marveltheme, narutotheme, starwarstheme, pacmantheme, replay, quit;

    // main intro backgrounds
    Image introbg;
    Image introbgHowToPlayHover;
    Image introbgPlayHover;
    Image introbgSettingsHover;

    // how to play backgrounds
    Image howToPlaybg1; // pg 1 of background
    Image howToPlayNext; // next button on how to play (hover)
    Image howToPlayBackPg2; // back to pg 1 (hover)
    Image howToPlayBackToMain; // back to main intro (hover)
    Image howToPlaybg2; // pg 2 of background

    // setting backgrounds
    Image settingsMain;
    Image settingsHighScoreHover; // high score hover
    Image settingsThemeHover; // theme hover
    Image settingsBackHover; // back button hover

    // high score backgrounds
    Image highscorebg; // the high scores background
    Image highscorebghover; // the high scores background with a hover effect

    // theme backgrounds
    Image themeMain;
    Image themeBackHover;

    // different buffer screens for each theme
    Image spidermanbg;
    Image hpbg;
    Image marvelbg;
    Image narutobg;
    Image starwarsbg;
    Image pacmanbg;

    // all the backgrounds based on the chosen theme for the win screen
    Image[] winbgs = new Image[6];
    Image[] winbgshover = new Image[6]; // the same winbgs with the hover effect on the back button

    // all the text to add on the lose screen
    Image gameOver;
    Image playAgain;

    // objects of all of the classes used in the game
    Timer timer; // timer object
    Pacman bufferPacman; // the pacman moving across the screen during the intro's buffer screen
    PacmanMain mainFrame;
    Maze maze;
    Pacman gamePac; // main game's pacman
    Ghost redghost;
    Ghost pinkghost;
    Ghost greenghost;
    Ghost orangeghost;
    AStar redGhostAI;
    AStar greenGhostAI;
    AStar orangeGhostAI;
    AStar pinkGhostAI;
    Food food;

    SoundEffect beginning; // the sound played during the buffer screen
    // background music played during the game based on the chosen theme
    BgMusic pacman_bgmusic;
    BgMusic hp_bgmusic;
    BgMusic starwars_bgmusic;
    BgMusic naruto_bgmusic;
    BgMusic spiderman_bgmusic;
    BgMusic marvel_bgmusic;

    ArrayList<Score> high; // the list of stored high scores

    public GamePanel(PacmanMain pm) {
        level = INTRO; // the game starts from the intro screen
        mainFrame = pm; // storing the original frame

        // creating the ArrayList with zeros and 1 represents the currently chosen theme
        for(int i = 0; i < 6; i++){
            chosenTheme.add(0);
        }
        chosenTheme.set(5, 1); // the default theme is normal pacman

        // loading all the images from the resources file
        introbg = new ImageIcon("resources/introbgs/titlescreen.png").getImage();
        introbgHowToPlayHover = new ImageIcon("resources/introbgs/titlebghowtoplay.png").getImage();
        introbgPlayHover = new ImageIcon("resources/introbgs/titlebgplay.png").getImage();
        introbgSettingsHover = new ImageIcon("resources/introbgs/titlebgsettings.png").getImage();
        howToPlaybg1 = new ImageIcon("resources/howtoplaybgs/howtoplaybg1.png").getImage();
        howToPlayNext = new ImageIcon("resources/howtoplaybgs/howtoplaynext.png").getImage();
        howToPlayBackPg2 = new ImageIcon("resources/howtoplaybgs/howtoplaybackpg2.png").getImage();
        howToPlayBackToMain = new ImageIcon("resources/howtoplaybgs/howtoplaybacktomain.png").getImage();
        howToPlaybg2 = new ImageIcon("resources/howtoplaybgs/howtoplaybg2.png").getImage();
        settingsMain = new ImageIcon("resources/settingsbgs/settingsmain.png").getImage();
        settingsHighScoreHover = new ImageIcon("resources/settingsbgs/settingshighscorehover.png").getImage();
        settingsThemeHover = new ImageIcon("resources/settingsbgs/settingsthemehover.png").getImage();
        settingsBackHover = new ImageIcon("resources/settingsbgs/settingsbackhover.png").getImage();
        themeMain = new ImageIcon("resources/settingsbgs/themebgs/themesbg.png").getImage();
        themeBackHover = new ImageIcon("resources/settingsbgs/themebgs/themesbackhover.png").getImage();

        // loading all the high score backgrounds
        highscorebg = new ImageIcon("resources/highscores/highscores.png").getImage();
        highscorebghover = new ImageIcon("resources/highscores/highscoreshover.png").getImage();

        // loading all the buffer backgrounds for all the themes
        spidermanbg = new ImageIcon("resources/bufferscreens/spidermanbg.png").getImage();
        hpbg = new ImageIcon("resources/bufferscreens/hpbg.png").getImage();
        marvelbg = new ImageIcon("resources/bufferscreens/marvelbg.png").getImage();
        narutobg = new ImageIcon("resources/bufferscreens/narutobg.png").getImage();
        starwarsbg = new ImageIcon("resources/bufferscreens/starwarsbg.png").getImage();
        pacmanbg = new ImageIcon("resources/bufferscreens/pacmanbg.png").getImage();

        // loading the winning backgrounds for all the themes
        for(int i = 0; i < 6; i++){
            winbgs[i] = new ImageIcon("resources/winbgs/" + i + ".png").getImage(); // normal backgrounds
            winbgshover[i] = new ImageIcon("resources/winbgs/hover" + i + ".png").getImage(); // hover backgrounds
        }

        // loading all the losing screen text
        gameOver = new ImageIcon("resources/losebg/gameover.png").getImage();
        playAgain = new ImageIcon("resources/losebg/playagain.png").getImage();

        // creating all the rectangles of the buttons to check for hovers and clicks
        introHowToPlay = new Rectangle(110, 640, 250, 40); // how to play button's rect
        introPlay = new Rectangle(460, 640, 90, 40); // start button's rect
        introSettings = new Rectangle(690, 640, 160, 40); //settings button's rect
        next = new Rectangle(809, 652, 140, 54); // next button's rect in how to play
        backpg1 = new Rectangle(37, 669, 138, 55); // back to how to play pg 1 rect
        backIntro = new Rectangle(43, 64, 132, 52); // back to main intro screen rect
        settingshs = new Rectangle(356, 277, 304, 123); // settings high score button rect
        settingstheme = new Rectangle(352, 500, 285, 108); // settings theme rect
        // all the themes in the settings theme section; used to click and choose a theme
        spidermantheme = new Rectangle(77, 210, 206, 228);
        hptheme = new Rectangle(397, 210, 206, 228);
        marveltheme = new Rectangle(718, 211, 206, 228);
        narutotheme = new Rectangle(77, 484, 206, 228);
        starwarstheme = new Rectangle(397, 484, 206, 228);
        pacmantheme = new Rectangle(718, 484, 206, 228);
        replay = new Rectangle(294, 492, 455, 120); // play again button on the lose screen
        quit = new Rectangle(800, 633, 50, 20); // the quit button while the player is in the game; goes back to the intro screen

        bufferPacman = new Pacman(0, 0); // pacman in buffer screen
        gamePac = new Pacman(440, 340); // the main character
        maze = new Maze("resources/level1.txt"); // the text file with the maze represented as nums
        food = new Food(500, 340); // food displayed for extra points

        // all the different ghosts
        redghost = new Ghost(520,280,"red");
        pinkghost = new Ghost(460,280,"pink");
        greenghost = new Ghost(500,280,"green");
        orangeghost = new Ghost(540,300,"orange");

        redGhostAI = new AStar(redghost); // a* for the red ghost following pacman
        greenGhostAI = new AStar(greenghost); // a* for the green ghost following pacman
        orangeGhostAI = new AStar(orangeghost); // a* making the orange ghost go to the corner of the maze
        pinkGhostAI = new AStar(pinkghost); // a* making the pink ghost go to the corner of the maze

        keys = new boolean[KeyEvent.KEY_LAST + 1]; // all keys to record the one being pressed
        high = loadScores(); // loading all the high scores into an arraylist from the score.txt file

        //sound effects
        beginning = new SoundEffect("resources/sound effects/pacman_beginning.wav");
        pacman_bgmusic = new BgMusic("resources/sound effects/pacman_bg.wav");
        hp_bgmusic = new BgMusic("resources/sound effects/hp_bg.wav");
        starwars_bgmusic = new BgMusic("resources/sound effects/starwars_bg.wav");
        naruto_bgmusic = new BgMusic("resources/sound effects/naruto_bg.wav");
        spiderman_bgmusic = new BgMusic("resources/sound effects/spiderman_bg.wav");
        marvel_bgmusic = new BgMusic("resources/sound effects/marvel_bg.wav");

        setPreferredSize(new Dimension(1000, 750)); // the size of the main intro screen

        timer = new Timer(10, this);
        timer.start();
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(level == LEVEL1 || level == LEVEL2){
            gamePac.moveUpdate(); // this continuously moves pacman based on the last key pressed until a wall is reached
            // constantly updates the tiles in all four directions of pacman (right, down, left, up)
            gamePac.checkDir();
            pinkghost.checkDir();
            move(); // all the movement of objects; pacman key movement and ghosts
        }
        Pacman.currTheme = chosenTheme.indexOf(1); // constantly updating the current chosen theme; used to draw the new theme
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(level == LEVEL1 || level == LEVEL2) {
            if (lastKey != ke.getKeyCode()) {
                if (lastKey != -1) { // if the key is pressed for the first time, there is no previous key to set false
                    keys[lastKey] = false;
                }
                lastKey = ke.getKeyCode();
                keys[lastKey] = true; // the most recent key pressed is stored
            }
        } else {
            int key = ke.getKeyCode();
            keys[key] = true; // storing the current key pressed
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(level == INTRO){
            int key = ke.getKeyCode();
            keys[key] = false; // releasing means no longer storing it
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (level == INTRO) {
            if (introHowToPlay.contains(mouse)) { // clicked the how to play button
                level = HOWTOPLAY;
            }
            if (introSettings.contains(mouse)) { // clicked the settings button
                level = SETTINGS;
            }
            if (introPlay.contains(mouse)) { // clicked the play button
                name = JOptionPane.showInputDialog("Name: "); // taking the player's name as input
                level = BUFFER;
            }
        } else if (level == HOWTOPLAY) {
            if (next.contains(mouse)) { // if the next arrow is pressed, go to instructions pg 2
                level = HOWTOPLAY2;
            }
        } else if (level == HOWTOPLAY2) {
            if (backpg1.contains(mouse)) { // if the back arrow is pressed, go to instructions pg 1
                level = HOWTOPLAY;
            } else if (backIntro.contains(mouse)) { // if the back button is pressed, go back to the main intro screen
                level = INTRO;
            }
        } else if (level == SETTINGS) {
            if (backIntro.contains(mouse)) { // if the back button is pressed, go back to the main intro screen
                level = INTRO;
            } else if (settingstheme.contains(mouse)) { // if the themes button is pressed, display all the themes
                level = THEMES;
            } else if(settingshs.contains(mouse)){ // if the high score button is pressed, display the top ten high scores
                level = HIGHSCORE;
            }
        } else if(level == HIGHSCORE){
            if(backIntro.contains(mouse)){ // back to the settings form the high score page
                level = SETTINGS;
            }
        } else if(level == THEMES){
            if(backIntro.contains(mouse)){ // if the back button is pressed, go back to the settings screen
                level = SETTINGS;
            } else if(spidermantheme.contains(mouse)){
                chosenTheme.set(chosenTheme.indexOf(1), 0); // "de-clicking" the previous chosen theme
                chosenTheme.set(SPIDERMAN % 100, 1); // setting the current chosen theme to 1 to find and draw characters throughout the game
            } else if(hptheme.contains(mouse)){
                chosenTheme.set(chosenTheme.indexOf(1), 0);
                chosenTheme.set(HP % 100, 1);
            } else if(marveltheme.contains(mouse)){
                chosenTheme.set(chosenTheme.indexOf(1), 0);
                chosenTheme.set(MARVEL % 100, 1);
            } else if(narutotheme.contains(mouse)){
                chosenTheme.set(chosenTheme.indexOf(1), 0);
                chosenTheme.set(NARUTO % 100, 1);
            } else if(starwarstheme.contains(mouse)){
                chosenTheme.set(chosenTheme.indexOf(1), 0);
                chosenTheme.set(STARWARS % 100, 1);
            } else if(pacmantheme.contains(mouse)){
                chosenTheme.set(chosenTheme.indexOf(1), 0);
                chosenTheme.set(OGPACMAN % 100, 1);
            }
        } else if(level == LEVEL1 || level == LEVEL2){
            if(quit.contains(mouse)){ // if the player presses quit during the game, they go back to the intro screen
                screenSize(1000, 750); // to go back to the main screen, the screen size has to be the same as before
                playBgMusic().stop();
                level = INTRO;
            }
        } else if(level == WIN){
            int points = gamePac.points; // player's score
            if (points > high.get(9).getScore()) { // adding the score to the top ten highscores if it's greater than the smallest one
                high.set(9, new Score(name, points));
                Collections.sort(high); // sorting in decreasing order of scores
            }
            if (backIntro.contains(mouse)) { // if the back button is pressed, go back to the main intro screen
                screenSize(1000, 750);
                level = INTRO;
            }
        } else if(level == LOSE){
            if(replay.contains(mouse)){ // if the play again button is pressed, go back to the main intro screen
                screenSize(1000, 750);
                level = INTRO;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) { // this method is used to constantly update and store the mouse's x and y-coordinates
        // storing the mouse's x and y positions
        mouse.x = e.getX();
        mouse.y = e.getY();
    }

    // we used Mr. McKenzie's code for all the high score components
    // this method is used to save all the scores in the scores.txt file
    public void saveScores(){
        try{
            PrintWriter out = new PrintWriter(new File("resources/scores.txt"));
            for(Score sc:high){
                out.println(sc.twoLine()); // printing each score to the scores.txt file
            }
            out.close();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    // this method is used to load the scores into an arraylist which is returned
    public ArrayList<Score>loadScores(){
        ArrayList<Score>tmp=new ArrayList<Score>();
        try{
            Scanner input = new Scanner(new File("resources/scores.txt")); // reading from the file
            for(int i=0; i<10; i++){
                String name = input.nextLine(); // username
                int points = Integer.parseInt(input.nextLine()); // score
                tmp.add(new Score(name, points)); // adding to the arraylist
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return tmp;
    }

    // this method is used to change the screen size
    public void screenSize(int x, int y) {
        int w = getWidth(); // the current window's width
        int h = getHeight(); // window's height
        int extraX = mainFrame.getWidth() - w; // the extra width of the window off the frame
        int extraY = mainFrame.getHeight() - h; // the extra height of the window off the frame
        setSize(x,y); // changing the window size from 1000 by 750 to 1040 by 620
        mainFrame.setSize(x+extraX, y+extraY); // changing the frame's size
    }

    // this method is used to draw the into screen
    public void drawIntro(Graphics g) {
        g.drawImage(introbg, 0, 0, this); // the main intro background
        if (introHowToPlay.contains(mouse)) {
            g.drawImage(introbgHowToPlayHover, 0, 0, this); // making the hover effect on how to play button
        } else if (introPlay.contains(mouse)) {
            g.drawImage(introbgPlayHover, 0, 0, this); // making the hover effect on play button
        } else if (introSettings.contains(mouse)) {
            g.drawImage(introbgSettingsHover, 0, 0, this); // making the hover effect on settings button
        }
    }

    // this method is used to draw the how to play screen
    public void drawHowToPlay(Graphics g) {
        if (level == HOWTOPLAY) {
            g.drawImage(howToPlaybg1, 0, 0, this); // the first set of instructions
            if (next.contains(mouse)) { // hovering effect for the next button (to pg 2)
                g.drawImage(howToPlayNext, 0, 0, this);
            }
        } else if (level == HOWTOPLAY2) {
            g.drawImage(howToPlaybg2, 0, 0, this); // going to the second page of instructions
            if (backpg1.contains(mouse)) { // back effect for the next button (to pg 2)
                g.drawImage(howToPlayBackPg2, 0, 0, this); // hovering effect for the back button to instructions pg 1
            } else if (backIntro.contains(mouse)) {
                g.drawImage(howToPlayBackToMain, 0, 0, this); // hovering effect for the back button to the main intro screen
            }
        }
    }

    // this method is used to draw the settings page
    public void drawSettings(Graphics g) {
        g.drawImage(settingsMain, 0, 0, this);
        if (settingshs.contains(mouse)) { // hovering over the high score button
            g.drawImage(settingsHighScoreHover, 0, 0, this);
        } else if (settingstheme.contains(mouse)) { // hovering over the theme button
            g.drawImage(settingsThemeHover, 0, 0, this);
        } else if (backIntro.contains(mouse)) { // hovering over the back button
            g.drawImage(settingsBackHover, 0, 0, this);
        }
    }

    // this method is used to draw the highscores
    public void drawHighscores(Graphics g){
        g.drawImage(highscorebg, 0, 0, this);
        if(backIntro.contains(mouse)){ // hovering effect on the back button
            g.drawImage(highscorebghover, 0, 0, this);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Optima", Font.PLAIN, 15)); // changing the font for the points string
        int y=0;
        if(high!= null){
            for(Score s : high){ // looping through all the high scores and drawing them on the screen
                g.drawString(""+s, 400, y*40 + 250);
                y++;
            }
        }
    }

    // this method is used to draw all the different themes in the themes screen from settings
    public void drawThemes(Graphics g){
        g.drawImage(themeMain, 0, 0, this); // the background with all the theme choices
        g.setColor(Color.WHITE);

        if(backIntro.contains(mouse)){
            g.drawImage(themeBackHover, 0, 0, this);
        }

        // drawing the outline around the theme that is clicked; this theme has a value of 1 at it's index in the chosenTheme arraylist
        if(chosenTheme.get(SPIDERMAN % 100) == 1){
            g.drawRect(spidermantheme.x, spidermantheme.y, spidermantheme.width, spidermantheme.height);
        } else if(chosenTheme.get(HP % 100) == 1){
            g.drawRect(hptheme.x, hptheme.y, hptheme.width, hptheme.height);
        } else if(chosenTheme.get(MARVEL % 100) == 1){
            g.drawRect(marveltheme.x, marveltheme.y, marveltheme.width, marveltheme.height);
        } else if(chosenTheme.get(NARUTO % 100) == 1){
            g.drawRect(narutotheme.x, narutotheme.y, narutotheme.width, narutotheme.height);
        } else if(chosenTheme.get(STARWARS % 100) == 1){
            g.drawRect(starwarstheme.x, starwarstheme.y, starwarstheme.width, starwarstheme.height);
        } else if(chosenTheme.get(OGPACMAN % 100) == 1){
            g.drawRect(pacmantheme.x, pacmantheme.y, pacmantheme.width, pacmantheme.height);
        }
    }

    // this method is used to draw the buffer screen with pacman running across the screen; resets values to begin/replay the game
    public void drawBuffer(Graphics g, int lvl) {
        Image[] bufferscreens = {spidermanbg, hpbg, marvelbg, narutobg, starwarsbg, pacmanbg}; // stores all the buffer screen backgrounds
        g.setColor(Color.BLACK);
        g.drawImage(bufferscreens[Pacman.currTheme], 0, 0, null); // drawing pacman according to the chosen theme

        if(bufferPacman.pacx == 100) {
            beginning.play(); // the sound effect
        }

        maze = new Maze("resources/level1.txt"); // re-drawing the maze with all the pellets
        bufferPacman.drawBuffer(g); // drawing the pacman moving across the screen
        if (bufferPacman.drawBuffer(g)) { // if pacman is off the screen, start the game
            screenSize(1040, 640); // resizing the screen to be 1040 by 640 to accustom to the maze
            Pacman.lives = 3; // when re-playing the game, the lives reset to 3
            gamePac.points = 0; // when re-playing the game, the points reset to 0

            gamePac.pacx = Pacman.STARTX;
            gamePac.pacy = Pacman.STARTY;
            redghost.gx = redghost.STARTX; redghost.gy = redghost.STARTY;
            greenghost.gx = greenghost.STARTX; greenghost.gy = greenghost.STARTY;
            orangeghost.gx = orangeghost.STARTX; orangeghost.gy = orangeghost.STARTY;
            pinkghost.gx = pinkghost.STARTX; pinkghost.gy = pinkghost.STARTY;

            level = lvl;
            bufferPacman.pacx = 0; // resetting pacman to the left side of the screen for when buffer is callef again
        }

        if(bufferPacman.pacx == 900) { // when pacman is close to off the screen, start the game's bg music
            playBgMusic();
        }
    }

    // this method is used to draw the bg music depending on the chosen theme and corresponding inices
    public BgMusic playBgMusic() {
        BgMusic currBgMusic = null;
        if (Pacman.currTheme == 0) {
            currBgMusic = spiderman_bgmusic;
        }
        else if (Pacman.currTheme == 1) {
            currBgMusic = hp_bgmusic;
        }
        else if (Pacman.currTheme == 2) {
            currBgMusic = marvel_bgmusic;
        }
        else if (Pacman.currTheme == 3) {
            currBgMusic = naruto_bgmusic;
        }
        else if (Pacman.currTheme == 4) {
            currBgMusic = starwars_bgmusic;
        }
        else if(Pacman.currTheme == 5) {
            currBgMusic = pacman_bgmusic;
            pacman_bgmusic.play();
        }
        if(currBgMusic != null) {
            currBgMusic.play();
        }
        return currBgMusic;
    }

    // this method is used to store the keys pressed and move pacman if possible
    public void moveKey(){
        if (keys[KeyEvent.VK_RIGHT]) {
            gamePac.move(2, 0);
        } else if (keys[KeyEvent.VK_LEFT]) {
            gamePac.move(-2, 0);
        } else if (keys[KeyEvent.VK_UP]) {
            gamePac.move(0, -2);
        } else if (keys[KeyEvent.VK_DOWN]) {
            gamePac.move(0, 2);
        }
    }

    ArrayList<Point> redGhPath=null; // this stores the path the red ghost follows
    ArrayList<Point> greenGhPath=null; // this stores the path the green ghost follows
    // this methos is used to move all the components on the screen during the game; all the ghosts and pacman
    public void move(){
        moveKey(); // moving pacman based on the key pressed

        if(!gamePac.isDying) { // when pacman dies, all the ghosts stop moving and disappear
            greenghost.outOfBox(); // moving the green ghost out of the box
            if (!(greenghost.gx >= 460 && greenghost.gx <= 580 && greenghost.gy >= 240 && greenghost.gy <= 330)) { // not in the box
                redghost.outOfBox(); // the ghosts exit the box one at a time
                if (!greenghost.scareStatGhost) { // moving the green ghost using a* while it's in chase mode
                    // the green ghost follows pacman one tile ahead based on pacman's direction
                    // right
                    if (gamePac.dir == Pacman.RIGHT && ((Maze.colGrid[(gamePac.pacx + 20) / 20][gamePac.pacy / 20] == 1) || (Maze.colGrid[(gamePac.pacx + 20) / 20][gamePac.pacy / 20] == 9) || (Maze.colGrid[(gamePac.pacx + 20) / 20][gamePac.pacy / 20] == 16))) {
                        greenGhPath = greenGhostAI.chaseMode(gamePac.pacx + 20, gamePac.pacy);
                        // left
                    } else if (gamePac.dir == Pacman.LEFT && ((Maze.colGrid[(gamePac.pacx - 20) / 20][gamePac.pacy / 20] == 1) || (Maze.colGrid[(gamePac.pacx - 20) / 20][gamePac.pacy / 20] == 9) || (Maze.colGrid[(gamePac.pacx - 20) / 20][gamePac.pacy / 20] == 16))) {
                        greenGhPath = greenGhostAI.chaseMode(gamePac.pacx - 20, gamePac.pacy);
                        // up
                    } else if (gamePac.dir == Pacman.UP && ((Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy - 20) / 20] == 1) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy - 20) / 20] == 9) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy - 20) / 20] == 16))) {
                        greenGhPath = greenGhostAI.chaseMode(gamePac.pacx, gamePac.pacy - 20);
                        // down
                    } else if (gamePac.dir == Pacman.DOWN && ((Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy + 20) / 20] == 1) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy + 20) / 20] == 9) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy + 20) / 20] == 16))) {
                        greenGhPath = greenGhostAI.chaseMode(gamePac.pacx, gamePac.pacy + 20);
                    } else { // if the tile ahead of pacman is a wall, then the ghost will just follow pacman
                        greenGhPath = greenGhostAI.chaseMode(gamePac.pacx, gamePac.pacy);
                    }
                    greenghost.move(greenGhPath);
                } else { // if the ghosts are in fright mode, they move randomly
                    greenghost.scatterMode(greenGhostAI, "topright");
                }
            }

            if (!(redghost.gx >= 460 && redghost.gx <= 580 && redghost.gy >= 240 && redghost.gy <= 330)) { // not in the box
                pinkghost.outOfBox(); // move the pink ghost out of the box
                if (!redghost.scareStatGhost) {
                    // the red ghost follows pacman one tile behind
                    // right
                    if (gamePac.dir == Pacman.RIGHT && ((Maze.colGrid[(gamePac.pacx - 20) / 20][gamePac.pacy / 20] == 1) || (Maze.colGrid[(gamePac.pacx - 20) / 20][gamePac.pacy / 20] == 9) || (Maze.colGrid[(gamePac.pacx - 20) / 20][gamePac.pacy / 20] == 16))) {
                        redGhPath = redGhostAI.chaseMode(gamePac.pacx - 20, gamePac.pacy);
                        // left
                    } else if (gamePac.dir == Pacman.LEFT && ((Maze.colGrid[(gamePac.pacx + 20) / 20][gamePac.pacy / 20] == 1) || (Maze.colGrid[(gamePac.pacx + 20) / 20][gamePac.pacy / 20] == 9) || (Maze.colGrid[(gamePac.pacx + 20) / 20][gamePac.pacy / 20] == 16))) {
                        redGhPath = redGhostAI.chaseMode(gamePac.pacx + 20, gamePac.pacy);
                        // up
                    } else if (gamePac.dir == Pacman.UP && ((Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy + 20) / 20] == 1) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy + 20) / 20] == 9) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy + 20) / 20] == 16))) {
                        redGhPath = redGhostAI.chaseMode(gamePac.pacx, gamePac.pacy + 20);
                        // down
                    } else if (gamePac.dir == Pacman.DOWN && ((Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy - 20) / 20] == 1) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy - 20) / 20] == 9) || (Maze.colGrid[gamePac.pacx / 20][(gamePac.pacy - 20) / 20] == 16))) {
                        redGhPath = redGhostAI.chaseMode(gamePac.pacx, gamePac.pacy - 20);
                    } else {
                        // if the tile behind pacman is a wall, the red ghost just follows pacman
                        redGhPath = redGhostAI.chaseMode(gamePac.pacx, gamePac.pacy);
                    }
                    redghost.move(redGhPath);
                } else {
                    redghost.scatterMode(redGhostAI, "bottomright");
                }
            }

            if (!(pinkghost.gx >= 460 && pinkghost.gx <= 580 && pinkghost.gy >= 240 && pinkghost.gy <= 330)) { // not in the box
                orangeghost.outOfBox(); // move the orange out of the box
                pinkghost.scatterMode(pinkGhostAI, "bottomleft"); // move the pink ghost randomly
            }

            if(!(orangeghost.gx >= 460 && orangeghost.gx <= 580 && orangeghost.gy >= 240 && orangeghost.gy <= 330)) { // not in the box
                orangeghost.scatterMode(orangeGhostAI, "topleft"); // the orange ghost moves around the top left corner
            }
        }
    }

    // drawing the main level; all ghosts, pacman, food etc.
    public void drawLevel(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // filling the bg as black
        maze.drawBack(g); // drawing the background

        if(gamePac.points >= 400 && !food.eaten) { // the food appears when pacman ate 400 pts
            food.draw(g);
            if (food.ifEaten(gamePac) && !food.eaten){ // if pacman eats the food, make it disappear and give pacman extra pts
                gamePac.points += 100;
                food.eaten = true;
            }
            else if(gamePac.points >= 800){ // the food disappears when pacman ate 800 pts
                food.eaten = true;
            }
        }

        gamePac.draw(g); // drawing pacman
        gamePac.eatPellet(); // pacman eating pellets replaces the tile with a black one

        gamePac.eatPowerPellet(redghost, pinkghost, greenghost, orangeghost); // when pacman eats a power pellet, all the ghosts go into frightened mode

        if(!gamePac.isDying) { // when ANY ghost eats pacman, they all disappear
            redghost.draw(g);
            pinkghost.draw(g);
            greenghost.draw(g);
            orangeghost.draw(g);
        }

        // displaying pacman's lives, the points and quit btn in the bottom panel
        gamePac.drawLives(g);
        gamePac.drawPoints(g);
        g.drawString("Q U I T", 800, 633); // drawing the points below the maze during the game

        if(quit.contains(mouse)){ // if the mouse is hovering over the quit button
            g.setColor(new Color(83, 120, 123)); // draw the quit button/text in a different colour for a hovering effect
        }
    }

    // drawing the game which is used for lvl 1 and 2
    public void game(Graphics g, int lvl) {
        drawLevel(g); // drawing everything in the lvl

        // the ghosts will go into scatter mode when pacman eats a power pellet
        redghost.trackScatterMode();
        greenghost.trackScatterMode();
        pinkghost.trackScatterMode();
        orangeghost.trackScatterMode();

        // if any ghost eats pacman, it dies and loses a life
        if(redghost.ifEat(gamePac) || greenghost.ifEat(gamePac) || pinkghost.ifEat(gamePac) || orangeghost.ifEat(gamePac)){
            gamePac.isDying = true;

            // re-setting all the ghosts to their starting position when pacman loses a life (is eaten)
            redghost.gx = redghost.STARTX; redghost.gy = redghost.STARTY;
            greenghost.gx = greenghost.STARTX; greenghost.gy = greenghost.STARTY;
            pinkghost.gx = pinkghost.STARTX; pinkghost.gy = pinkghost.STARTY;
            orangeghost.gx = orangeghost.STARTX; orangeghost.gy = orangeghost.STARTY;

            // if pacman eats a ghost, which then eats pacman while other ghosts are still in frightened mode, the mode changes back to chase
            redghost.endScatterMode();
            greenghost.endScatterMode();
            pinkghost.endScatterMode();
            orangeghost.endScatterMode();
        }

        // when any ghost is eaten by pacman in frightened mode, they go back to their starting position and get 200 extra pts
        if(redghost.ifEatenBy(gamePac)){
            redghost.gx = redghost.STARTX; redghost.gy = redghost.STARTY;
            gamePac.points += 200;
        } if(greenghost.ifEatenBy(gamePac)){
            greenghost.gx = greenghost.STARTX; greenghost.gy = greenghost.STARTY;
            gamePac.points += 200;
        } if(pinkghost.ifEatenBy(gamePac)){
            pinkghost.gx = pinkghost.STARTX; pinkghost.gy = pinkghost.STARTY;
            gamePac.points += 200;
        } if(orangeghost.ifEatenBy(gamePac)){
            orangeghost.gx = orangeghost.STARTX; orangeghost.gy = orangeghost.STARTY;
            gamePac.points += 200;
        }

        if(Pacman.lives <= 0){ // when pacman loses all it's lives, the game is over
            level = LOSE;
        }

        gamePac.death(); // pacman's death sprites are drawn when it's dying

        if(lvl == LEVEL1) {
            if (maze.won()) { // if all the pellets are eaten, the player goes to level two
                level = BUFFER2;
                screenSize(1000, 750);// re-szing the screen to fit the buffer screen's components
                gamePac.dx = 0; gamePac.dy = 0; // resetting pacman's speed to make it idle
            }
        } else if(lvl == LEVEL2) {
            if (maze.won()) { // if all the pellets are eaten, the player wins
                level = WIN;
            }
        }
    }

    // this method is used to draw the losing screen
    public void lose(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // black background
        maze.drawBack(g); // drawing the half eaten maze
        g.drawImage(gameOver, 464, 260, this);
        g.drawImage(playAgain, 294, 492, this);
        gamePac.drawPoints(g); // displaying pacman's points
    }

    // this method is used to draw the winning screen
    public void win(Graphics g){
        screenSize(1000, 750); // changing the screen size
        g.drawImage(winbgs[Pacman.currTheme], 0, 0, this);
        if (backIntro.contains(mouse)) { // hovering over the back button
            g.drawImage(winbgshover[Pacman.currTheme], 0, 0, this);
        }
        gamePac.points = 0; // points change back to 0
    }

    // drawing all the methods based on the current lvl stage
    public void paint(Graphics g) {
        if (level == INTRO) {
            drawIntro(g);
        } else if (level == HOWTOPLAY || level == HOWTOPLAY2) {
            drawHowToPlay(g);
        } else if (level == SETTINGS) {
            drawSettings(g);
        } else if(level == THEMES){
            drawThemes(g);
        } else if(level == HIGHSCORE){
            drawHighscores(g);
        } else if (level == BUFFER) {
            drawBuffer(g, LEVEL1);
        } else if (level == LEVEL1) {
            game(g, LEVEL1);
        } else if(level == BUFFER2){
            drawBuffer(g, LEVEL2);
        } else if(level == LEVEL2){
            game(g, LEVEL2);
        } else if (level == LOSE) {
            lose(g);
        }else if (level == WIN) {
            win(g);
        }
    }
}

// this is Mr. McKenzie's code for high scores
class Score implements Comparable<Score>{
    String name;
    int score;

    public Score(String n, int s){
        name = n;
        score = s;
    }
    public int getScore(){return score;}


    @Override
    public int compareTo(Score s2){
        if(score==s2.score){
            return -name.compareTo(s2.name);
        }
        else{
            return  s2.score-score;
        }
    }

    public String twoLine(){
        return String.format("%s\n%d", name, score);
    }

    @Override
    public String toString(){
        return String.format("%-20s%5d", name, score);
    }
}