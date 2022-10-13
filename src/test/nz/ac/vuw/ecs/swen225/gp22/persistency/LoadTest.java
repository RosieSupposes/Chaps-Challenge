package nz.ac.vuw.ecs.swen225.gp22.persistency;
import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoadTest {
    @Test
    public void loadLevel(){
        Load.loadLevel(1);
        assert Base.getTime() == 60;
        assert Base.getLevel() == 1;
        assert Maze.player.getPos().equals(new Maze.Point(8,7)): "Player position is not correct";
        assert Maze.getDimensions().equals(new Maze.Point(17,16)): "Maze dimensions are not correct";
        /**Load.loadLevel(2);
        assert Base.getTime() == 60;
        assert Base.getLevel() == 2;
        assert Maze.player.getPos().equals(new Maze.Point(4,5)): "Player position is not correct";
        assert Maze.getDimensions().equals(new Maze.Point(16,16)): "Maze dimensions are not correct";**/
    }

    /**
     * Test to see if the getFile method loads the file with the correct name
     */
    @Test
    public void getFile(){
        File f = Load.getFile("levels/level1");
        assert f != null;
        assert f.getName().equals("level1.xml");
    }
    @Test
    public void checkPreviousGamePresent(){
        Load.loadLevel(1);
        Save.saveGame();
        assert Load.previousGamePresent() == true;
    }
    @Test
    public void checkPreviousGameNotPresent(){
       deletePreviousGame();
        assert Load.previousGamePresent() == false;
    }
    @Test
    public void loadPreviousGame(){
        createPreviousGame();
        Load.previousGame();
        //TODO assert stuff
    }
    @Test
    public void loadPreviousGameNotPresent(){
        deletePreviousGame();
        Load.previousGame();
        assert Base.getTime() == 60: "Time is not correct";
        assert Base.getLevel() == 1: "Level is not correct";
        assert Maze.player.getPos().equals(new Maze.Point(8,7)): "Player position is not correct";
        assert Maze.getDimensions().equals(new Maze.Point(17,16)): "Maze dimensions are not correct";
    }
    @Test
    public void getPreviousGameInfo(){
        createPreviousGame();
        String info = Load.previousGameInfo();
        assert info.equals("Time: 60, Keys: 0"): "Previous game info is not correct";
    }
    @Test
    public void getPreviousGameInfoNotPresent(){
        deletePreviousGame();
        String info = Load.previousGameInfo();
        assert info.equals("Time: 0, Keys: 0"): "Previous game info is not correct";
    }
    @Test

    private void createPreviousGame(){
        if(!Load.previousGamePresent()){
            Load.loadLevel(1);
            Save.saveGame();
        }
    }
    private void deletePreviousGame(){
        try {
            Files.deleteIfExists(Load.getFile("saves/previousGame").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
