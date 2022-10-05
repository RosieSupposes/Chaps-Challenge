package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.domain.GummyGuard;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;

/**
 * Used to load levels and resume games.
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.5
 */
public class Load {
    private static final String resourceDirectory = System.getProperty("user.dir") + "/resources/";
    private static final String previousGame = "saves/previousGame.xml";

    /**
     * Load saved game from xml.
     * Open fileChooser.
     */
    public static void resumeGame() {
        JFileChooser fileChooser = new JFileChooser(resourceDirectory + "/saves");
        fileChooser.setDialogTitle("Select a game to load");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game File (xml)", "xml");
        fileChooser.setFileFilter(filter);
        fileChooser.showOpenDialog(null);

        // only load if a file was selected
        if (fileChooser.getSelectedFile() != null) {
            Parser parser = loadGame(fileChooser.getSelectedFile());
            Base.setLevel(parser.getLevel());
            Base.setTime(parser.getTime());
        } else {
            loadLevel(1);
            Base.setLevel(1);
        }
    }

    /**
     * Load level from xml.
     *
     * @param levelNum level to load.
     */
    public static void loadLevel(int levelNum) {
        loadGame(getFile("levels/level" + levelNum + ".xml"));
        Base.setTime(60);
    }

    /**
     * Determine if there is a previous unfinished level to load.
     *
     * @return true if there is an unfinished level false otherwise.
     */
    public static boolean previousGamePresent() {
        return getFile(previousGame).isFile();
    }

    /**
     * Loads the previous game if present.
     * If there is no previous game present loads level1.
     *
     * @return an int representing how long the previous game was played for
     */
    public static void previousGame() {
        if (!previousGamePresent()) {
            loadLevel(1);
            Base.setLevel(1);
        }
        Parser parser = loadGame(getFile(previousGame));
        Base.setLevel(parser.getLevel());
        Base.setTime(parser.getTime());
    }

    /**
     * Parses information about the previous game
     *
     * @return a string containing information about the previous game, time passed & keys collected
     */
    public static String previousGameInfo() {
        if (!previousGamePresent()) return "Time: 0, Keys Collected: 0";
        Parser parser = new Parser(getFile(previousGame));
        return "Time: " + parser.getTime() + ", Keys: " + parser.getNumKeysCollected();
    }

    /**
     * Parses and loads a game from the provided file
     *
     * @param file the file to parse and load the game from
     * @return returns the parser on the file for further use if necessary
     */
    private static Parser loadGame(File file) {
        Parser parser = new Parser(file);
        parser.parseMapInfo();
        Maze.player = parser.parsePlayer();
        List<Tile> tiles = parser.getTiles();
        if (parser.entitiesPresent()) {
            List<Entity> entities = parser.getEntities();
            Maze.entities.add(entities.get(0));
        }
        for (Tile t : tiles) {
            Maze.setTile(t.getPos(), t);
        }
        return parser;
    }

    /**
     * Loads and returns a file using the provided file name
     *
     * @param file fileName to find
     * @return The File that was found associated with the provided name,
     */
    private static File getFile(String file) {
        return new File(resourceDirectory + file);
    }

    /**
     * Loads classes from a jar file
     *
     * @param levelNum level number to load associated jar for
     */
    private static void loadJar(int levelNum) {
        File file = getFile("level/level" + levelNum + ".jar");
        System.out.println("loaded level"+levelNum+".jar");
    }

}
