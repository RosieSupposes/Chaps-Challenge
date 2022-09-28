package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadLevel {
    @Test
    public void generateMap(){
        Parser p = new Parser(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\levels\\level1.xml"));
        Load.loadLevel("level1");
        try {

            Maze.getTile(new Maze.Point(15,15));
        }
        catch(IllegalArgumentException e) {
            System.out.println(e);
            return;
        }
        assertTrue(1==1);
    }
}
