package nz.ac.vuw.ecs.swen225.gp22.renderer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * This stores an enum with the names of all the images to be used in the game.
 * It creates a new instance of the image by loading the object and returning
 * a BufferedImage object from a png file with its name.  
 * 
 * @author Diana Batoon, 300475111
 * @version 1.1 
 */
public enum Img {
    // images to be displayed for level 1 of Chap's Challenge
    BlueKey, BlueLockedDoor, GreenKey, GreenLockedDoor, RedKey, RedLockedDoor, YellowKey, YellowLockedDoor,
    FreeTile, Exit, LockedExit, InfoField, GameInfo, PlayerDown, PlayerLeft, PlayerRight, PlayerUp, Treasure, Wall, 
    
    // images to be displayed for level 2 of Chap's Challenge
    EnemyDown, EnemyLeft, EnemyRight, EnemyUp;

    public final BufferedImage image;

    Img(){ image = loadImage(this.name()); }
    static private BufferedImage loadImage(String name){
        URL imagePath = Img.class.getResource("/imgs/"+name+".png");
        try{ return ImageIO.read(imagePath); }
        catch(IOException e) { throw new Error(e); }
    }
}