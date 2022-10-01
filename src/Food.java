import java.awt.*;
import javax.swing.*;

// this class is used to draw the food and check if it's eaten
public class Food {
    private final int x, y;
    Image[] allfood = new Image[6]; // there are six different themes; a different food for each
    public boolean eaten = false;
    SoundEffect eat_fruit;

    public Food(int x, int y){
        Image spideyfood = new ImageIcon("resources/fruits/pizza.png").getImage();
        Image hpfood = new ImageIcon("resources/fruits/golden_snitch.png").getImage();
        Image marvelfood = new ImageIcon("resources/fruits/infinity_stones.png").getImage();
        Image narutofood = new ImageIcon("resources/fruits/ramen.png").getImage();
        Image starwarsfood = new ImageIcon("resources/fruits/blue_milk.png").getImage();
        Image pacmanfood = new ImageIcon("resources/fruits/cherry.png").getImage();
        allfood[0] = spideyfood;
        allfood[1] = hpfood;
        allfood[2] = marvelfood;
        allfood[3] = narutofood;
        allfood[4] = starwarsfood;
        allfood[5] = pacmanfood;
        this.x = x;
        this.y = y;
        eat_fruit = new SoundEffect("resources/sound effects/pacman_eatfruit.wav");
    }

    public void draw(Graphics g){
        if(!eaten) {
            g.drawImage(allfood[Pacman.currTheme], x, y, null); // the food is drawn based on the theme near the middle of the maze
        }
    }

    public Rectangle getRect(){
        int width = allfood[Pacman.currTheme].getWidth(null);
        int height = allfood[Pacman.currTheme].getHeight(null);
        return new Rectangle(x, y, width, height);
    }

    public boolean ifEaten(Pacman pac){
        if(pac.getRect().intersects(getRect())){
            eat_fruit.play();
            eaten = true;
        }
        return eaten;
    }
}