import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// this class controls and draws the game through gamepanel on the frame
public class PacmanMain extends JFrame{
    GamePanel game = new GamePanel(this); // so we can store the original frame to resize later on in the code

    public PacmanMain(){
        super("Pac-Man"); // title of the window/game
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(game); // "adding" the game to the screen
        pack(); // sizing the frame based on the game's and the contents' proportions
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                game.saveScores(); // saving the high scores even when the program is closed
            }
        });
    }
    public static void main(String[] args){
        PacmanMain frame = new PacmanMain();
    }
}