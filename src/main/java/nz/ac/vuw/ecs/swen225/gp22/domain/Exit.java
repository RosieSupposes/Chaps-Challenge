package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Exit extends Tile{
    public Exit(Maze.Point tilePos){
        super(tilePos, false);
        Maze.player.addObserver(player -> {
            if(player.getPos().equals(tilePos)){
                // Persistency.loadLevel(Maze.getNextLevel());
            }
        });
    }
}
