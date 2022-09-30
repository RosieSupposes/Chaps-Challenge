package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

import java.io.File;
import java.util.List;

/**
 * Used to load levels and resume games.
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.3
 */
public class Load {

    private static final String resourceDirectory = System.getProperty("user.dir")+"\\src\\main\\resources\\";

    /**
     * Load saved gamed from xml.
     * Open fileChooser
     */
    public static void resumeGame(){
        System.out.println("Loading saved game");
    }

    /**
     * Determine if there is a previous unfinished level to load
     * @return true if there is an unfinished level false otherwise
     */
    public static boolean isDefaultPresent(){
        return getFile("previousGame").isFile();
    }

    /**
     * Load level from xml.
     *
     * @param name level to load.
     */
    public static void loadLevel(String name){
        File file = getFile("levels\\" + name);
        Parser parser = new Parser(file);
        parser.parseMapInfo();
        List<Tile> tiles = parser.getTiles();
        for (Tile t : tiles) {
            Maze.setTile(t.getPos(), t);
        }
    }

    private static File getFile(String file){
        return new File(resourceDirectory + file + ".xml");
    }

}
