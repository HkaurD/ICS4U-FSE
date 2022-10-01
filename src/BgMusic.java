import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

// https://youtu.be/TErboGLHZGA -> video used to learn how to loop background music in java
public class BgMusic {
    private String musicLocation;
    private Clip clip;
    public BgMusic(String ml) {
        musicLocation = ml;
    }

    void play () {
        try {
            File musicPath = new File(musicLocation);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    void stop() {
        clip.stop();
    }
}
