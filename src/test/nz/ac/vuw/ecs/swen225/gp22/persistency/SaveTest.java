package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.ColorableTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import org.dom4j.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SaveTest {
    @Test
    public void saveGame(){
        Load.loadLevel(1);
        Save.saveGame();
        assert Load.previousGamePresent() == true;
    }
    @Test
    public void saveInventory(){
        Maze.player.addKey(ColorableTile.Color.Red);
        Maze.player.addKey(ColorableTile.Color.Blue);
        Maze.player.addKey(ColorableTile.Color.Yellow);
        Document doc = DocumentHelper.createDocument();
        Element test = doc.addElement("test");
        Save.saveInventory(test);
        assert test.element("inventory") != null;
        assert test.element("inventory").elements("key").size() == 3;
        assert test.element("inventory").element("key").attributeValue("color").equals("Red");
    }

    @Test
    public void addPoint(){
        Document doc = DocumentHelper.createDocument();
        Element test = doc.addElement("test");
        Save.addPoint(test,new Maze.Point(1,2));
        assert test.attribute("x") != null;
        assert test.attribute("y") != null;
        assert test.attributeValue("x").equals("1");
        assert test.attributeValue("y").equals("2");
    }
}
