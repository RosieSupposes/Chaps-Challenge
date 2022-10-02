package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile which the player can only walk
 * on if they have a key of the correct color.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.4
 */
public class LockedDoor extends ColorableTile{
    /** Used for tile functionality that depends on the player. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position and color of the tile, and 
     * obstructiveness to true. An observer is also added to the player so
     * that the obstructiveness is updated based on the player's keys. The same 
     * observer makes sure that when they are on this tile, it is reset and the 
     * key is consumed from the player's inventory. 
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     * @param color {@link Color} to set the color field to. ({@link ColorableTile#color see here})
     */
    public LockedDoor(Maze.Point tilePos, Color color){
        super(tilePos, true, color);
        
        playerObserver = player -> {
            setObstructive(!player.hasKey(color));
            if(player.getPos().equals(tilePos)){
                Maze.resetTile(tilePos);
                Maze.player.consumeKey(color);
            }
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public void deleteTile(){ 
        Maze.player.removeObserver(playerObserver);
    }

    @Override
    public String toString(){ return "D"; }
}
