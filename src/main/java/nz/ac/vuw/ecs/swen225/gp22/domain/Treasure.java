package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Treasure extends Tile{
    public Treasure(Maze.Point tilePos){
        super(tilePos, false);
        Maze.player.addObserver(player -> {
            if(player.getPos().equals(tilePos)){
                Maze.collectTreasure();
                Maze.resetTile(tilePos);
            }
        });
    }
}
