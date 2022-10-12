package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class constructs sounds clips to be played in the SFXPlayer class.
 * 
 * @author Diana Batoon, 300475111
 * @version 1.1  
 */
public class SFX {
    AudioInputStream audioIStream;
    private Clip clip;
    private String clipName = "";

    /***
     * An enum of the sounds to be played during the game.
     */ 
    public enum Sounds {
        Background, CollectItem, LoseGame, MainMenu, Unlock, WinGame, WinLevel;
    }

    /***
     * Loads a chosen file and initialises a clip from AudioInputStream.
     * 
     * @param name
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public SFX(String name) throws LineUnavailableException, UnsupportedAudioFileException {
        clipName = name;

        try {
            audioIStream = AudioSystem.getAudioInputStream(new File("resources/sfx/"+name+".wav"));
            AudioFormat format = audioIStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioIStream);
        }
        catch (Exception e){ e.printStackTrace();}
    }

    /*** @return the SFX clip. */
    public Clip getClip(){ return clip; }

    /*** @return the SFX clip name. */
    public String getClipName(){ return clipName; }

}
