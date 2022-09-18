package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;

/**
 * Used to save and resume games.
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.2
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
     * Open fileChooser
     */
    public static void loadSavedGame(){
        System.out.println("Loading saved game");
    }

    /**
     * Load level from xml.
     *
     * @param name level to load.
     */
    public static void loadLevel(String name){
        File file = new File(name + ".xml");
        Parser parser = new Parser(file);
    }

}
