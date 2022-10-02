package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Used to save the current game;
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.4
 */
public class Save {
    /**
     * Save current game as xml.
     * Store information about the map, dimensions, number of treasures, nextLevel
     * Store time and keysCollected
     * Store player position, direction and inventory if present
     * Store tilemap
     *
     * @param gameTime the time that has passed so far
     **/
    public static void saveGame(int gameTime){
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("maze");
        Element mapInfo = root.addElement("mapInfo");
        Maze.Point dimensions = Maze.getDimensions();
        mapInfo.addElement("width").addText(String.valueOf(dimensions.x()));
        mapInfo.addElement("height").addText(String.valueOf(dimensions.y()));
        mapInfo.addElement("treasures").addText(String.valueOf(Maze.getTreasuresLeft()));
        mapInfo.addElement("nextLevel").addText(Maze.getNextLevel());
        Element saveInfo = root.addElement("saveInfo");
        int keyCount = Maze.player.keyCount();
        saveInfo.addElement("time").addText(String.valueOf(gameTime));
        saveInfo.addElement("keysCollected").addText(String.valueOf(keyCount));
        Element player = root.addElement("player");
        addPoint(player,Maze.player.getPos());
        player.addAttribute("Direction",Maze.player.getDir().name());
        if(keyCount > 0){
            Element inventory = player.addElement("inventory");
            Map<String,Long> keyMap = Maze.player.getAllKeys().stream()
                                                 .collect(Collectors.groupingBy(ColorableTile.Color::name,Collectors.counting()));
            keyMap.forEach(
                    (k,v) -> inventory.addElement("key")
                                      .addAttribute("count",String.valueOf(v))
                                      .addAttribute("color",k));
        }
        Element tiles = root.addElement("tiles");
        for(int x = 0; x < 16; x++){
            for(int y = 0; y < 16; y++) {
                Maze.Point p = new Maze.Point(x,y);
                Tile tile = Maze.getTile(p);
                String tileID = TileDatabase.getID(tile);
                if(tileID.equals("ground"))continue;
                Element tileElement = tiles.addElement("tile");

                tileElement.addAttribute("ID", tileID);
                addPoint(tileElement,p);
                switch (tileID){
                    case "info" -> {
                        tileElement.addElement("text").addText(((InfoField)tile).getText());
                    }
                    case "door","key" -> {
                        tileElement.addElement("color").addText(((ColorableTile)tile).getColor().name());
                    }
                }
            }
        }

        String filename = System.getProperty("user.dir") + "/resources/saves/"
                + LocalDateTime.now().toString().replace(":", "-") + ".chaps.xml";
        try (FileWriter out = new FileWriter(filename)) {
            (new XMLWriter(out, OutputFormat.createPrettyPrint())).write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addPoint(Element element, Maze.Point p){
        element.addAttribute("x", String.valueOf(p.x())).addAttribute("y", String.valueOf(p.y()));
    }

}
