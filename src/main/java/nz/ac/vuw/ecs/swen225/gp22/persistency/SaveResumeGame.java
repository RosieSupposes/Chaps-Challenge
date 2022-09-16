package nz.ac.vuw.ecs.swen225.gp22.persistency;
/**
 * Used to save and resume games.
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.1
 */
public class SaveResumeGame {
    /**
     * Save current game as xml.
     *
     * @param name filename to save game as
     **/
    public static void saveGame(String name){
        System.out.println("Saving current game in: " + name + ".xml");
    }

    /**
     * Load saved gamed from xml.
     *
     * @param name filename of game to load
     */
    public static void loadSavedGame(String name){
        System.out.println("Loading saved game from: " + name + ".xml");
    }
}
