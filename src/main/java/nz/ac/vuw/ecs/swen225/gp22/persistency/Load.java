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

    /**
     * Load saved gamed from xml.
     * Open fileChooser
     */
    public static void resumeGame(){
        System.out.println("Loading saved game");
    }

    /**
     * Load level from xml.
     *
     * @param name level to load.
     */
    public static void loadLevel(String name){
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\levels\\" + name + ".xml");
        Parser parser = new Parser(file);
        parser.parseMapInfo();
        List<Tile> tiles = parser.getTiles();
        for (Tile t : tiles) {
            Maze.setTile(t.getPos(), t);
        }
    }

}
