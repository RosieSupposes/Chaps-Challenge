package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Viewport;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Used to save the current game;
 * Using xml files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.2
 */
public class Save {
    /**
     * Save current game as xml.
     *
     * @param gameTime the time that has passed so far
     **/
    public static void saveGame(int gameTime){
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("maze");
        Element mapInfo = root.addElement("mapInfo");
        mapInfo.addElement("width").addText("16"); //TODO get map width and height from domain
        mapInfo.addElement("height").addText("16");
        mapInfo.addElement("treasures").addText("3");  //TODO Maze get methods for saving game
        Element player = mapInfo.addElement("player");
        addPoint(player,Maze.player.getPos());
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
