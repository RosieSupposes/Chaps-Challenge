package nz.ac.vuw.ecs.swen225.gp22.domain;

public class LockedExit extends Tile{
    public LockedExit(Maze.Point tilePos){
        super(tilePos, true);
        Maze.player.addObserver(player -> {
            obstructive = !Maze.collectedAllTreasures();
            if(player.getPos().equals(tilePos)){
                Maze.resetTile(tilePos);
            }
        });
    }
}
