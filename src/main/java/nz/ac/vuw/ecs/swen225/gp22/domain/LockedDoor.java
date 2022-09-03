package nz.ac.vuw.ecs.swen225.gp22.domain;

public class LockedDoor extends ColorableTile{
    public LockedDoor(Maze.Point tilePos, Color color){
        super(tilePos, true, color);
        Maze.player.addObserver(player -> {
            obstructive = !player.hasKey(color);
            if(player.getPos().equals(tilePos)){
                Maze.resetTile(tilePos);
                Maze.player.consumeKey(color);
            }
        });
    }
}
