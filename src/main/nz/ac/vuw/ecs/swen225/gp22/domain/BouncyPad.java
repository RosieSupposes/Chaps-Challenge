package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile that makes the player jump by 2 tiles
 * in a direction.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.1
 */
public class BouncyPad extends Tile{
    /** Used for tile functionality that depends on the player. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position of the tile,
     * obstructiveness to false, and the direction of the bounce pad.
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     * @param dir The {@link Entity.Direction} the player will get bounced in.
     */
    public BouncyPad(Maze.Point tilePos, Entity.Direction dir){
        super(tilePos, false);

        playerObserver = player -> {
            if(player.getPos().equals(tilePos)){
                player.setPos(tilePos.add(dir).add(dir));
                player.setDir(dir);
            }
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public void deleteTile(){ 
        Maze.player.removeObserver(playerObserver);
    }

    @Override
    public String toString(){ return "B"; }
}
