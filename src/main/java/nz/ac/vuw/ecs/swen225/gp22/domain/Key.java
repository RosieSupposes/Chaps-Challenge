package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Key extends ColorableTile{
    public Key(Maze.Point tilePos, Color color){
        super(tilePos, false, color);
        Maze.player.addObserver(player -> {
            if(player.getPos().equals(tilePos)){
                Maze.player.addKey(color);
                Maze.resetTile(tilePos);
            }
        });
    }
}
