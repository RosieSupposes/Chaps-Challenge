package nz.ac.vuw.ecs.swen225.gp22.persistency;
/**
 * Used to save the current game;
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.1
 */
public class Save {
    /**
     * Save current game as xml.
     *
     * @param name filename to save game as
     **/
    public static void saveGame(String name){
        System.out.println("Saving current game in: " + name + ".xml");
    }
}
