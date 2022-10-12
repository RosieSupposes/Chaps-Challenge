package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

/**
 * Used to parse XML files.
 *
 * @author Gideon Wilkins, 300576057
 * @version 1.4
 */
public class Parser {
    Document document;
    /**
     * Read given xml file and store in a document
     *
     * @param file file to parse
     */
    public Parser(File file){
        try {
            document = new SAXReader().read(file);
        } catch (DocumentException e){
            throw new IllegalArgumentException("Invalid Game File");
        }
    }

    /**
     * Parse the dimensions of the level, the number of treasures and the player
     */
    public void parseMapInfo(){
        Element mapInfo = document.getRootElement().element("mapInfo");
        int width = intFromElement(mapInfo,"width");
        int height = intFromElement(mapInfo,"height");
        Maze.Point dimensions = new Maze.Point(width,height);
        Maze.generateMap(dimensions,intFromElement(mapInfo,"treasures"),intFromElement(mapInfo,"nextLevel"));
    }

    /**
     * Get the amount of time that has passed in a save file
     * Should only be used for saveFiles, not levels
     *
     * @return the time that has passed in this save file
     */
    public int getTime(){
        return intFromElement(document.getRootElement().element("saveInfo"),"time");
    }
    /**
     * Get the number of keys the player has collected
     * Should only be used for saveFiles, not levels
     *
     * @return the number of keys collected in this save file
     */
    public int getNumKeysCollected(){
        return intFromElement(document.getRootElement().element("saveInfo"),"keysCollected");
    }
    public int getLevel(){
        return intFromAttribute(document.getRootElement(),"level");
    }
    /**
     * Parse the saved player information from the file
     * create player at saved position and direction
     * add keys to inventory if present
     */
    public void parsePlayer(Player player){
        Element playerNode = document.getRootElement().element("player");
        Maze.Point position = getPoint(playerNode);
        Entity.Direction direction = Entity.Direction.valueOf(playerNode.attributeValue("direction"));
        player.setPos(position);
        player.setDir(direction);
        Element inventory = playerNode.element("inventory");
        if(inventory != null) parseInventory(player,inventory);
    }

    /**
     * Parse player inventory
     *
     * @param player player with inventory to fill
     * @param inventory dom4j element containing inventory to parse
     */
    public void parseInventory(Player player, Element inventory){
        for(Iterator<Element> it = inventory.elementIterator(); it.hasNext();){
            Element item = it.next();
            ColorableTile.Color color = ColorableTile.Color.valueOf(item.attributeValue("color"));
            for (int i = 0; i < intFromAttribute(item,"count"); i++){
                player.addKey(color);
            }
        }
    }

    /**
     * Parses all the tiles in the file into a list of tile objects
     *
     * @return returns a list of all the tiles in the file
     */
    public List<Tile> getTiles(){
        List<Element> nodes = document.getRootElement().element("tiles").elements();
        return nodes.stream().map(this::parseTile).toList();
    }

    /**
     * Parses the given element and returns a tile object
     * TileDatabase to be implemented in future by domain module
     *
     * @param element element to parse into a tile
     * @return returns a tile parsed from the element
     */
    private Tile parseTile(Element element){
        String ID = element.attributeValue("ID");
        Maze.Point p = getPoint(element);
        switch (ID){
            case "info" -> {
                return TileDatabase.create(ID,p,element.element("text").getText());
            }
            case "door","key" -> {
                ColorableTile.Color c = ColorableTile.Color.valueOf(element.element("color").getText());
                return TileDatabase.create(ID,p,c);
            }
            case "bounce-pad" -> {
                Entity.Direction direction = Entity.Direction.valueOf(element.elementText("direction"));
                return TileDatabase.create(ID,p,direction);
            }
            default -> {
                return TileDatabase.create(ID,p);
            }
        }
    }

    /**
     * Creates a Maze.Point using information from the given element
     *
     * @param element element to get point for
     * @return Maze.Point that stores the position of the given element
     */
    private Maze.Point getPoint(Element element){
        return new Maze.Point(intFromAttribute(element,"x"),intFromAttribute(element,"y"));
    }

    /**
     * Get an int value from a sub-element, of the given element, called elementName
     *
     * @param element parent element
     * @param elementName sub element to get integer from
     * @return int value stored in sub element
     */
    private int intFromElement(Element element,String elementName){
        if(element == null){throw new IllegalArgumentException("Element cannot be null");}
        return Integer.parseInt(element.element(elementName).getText());
    }
    /**
     * Get an int value from an attribute of the given element that is called attributeName
     *
     * @param element parent element
     * @param attributeName sub element to get integer from
     * @return int value stored in sub element
     */
    private int intFromAttribute(Element element,String attributeName){
        return Integer.parseInt(element.attributeValue(attributeName));
    }

}
