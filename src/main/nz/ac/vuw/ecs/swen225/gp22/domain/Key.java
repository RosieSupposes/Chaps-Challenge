package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile with a key on it, which the player
 * can pick up by walking onto the tile.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.5
 */
public class Key extends ColorableTile{
    /** Used for tile functionality that depends on the player. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position and color of the tile, and 
     * obstructiveness to false. An observer is also added to the player so
     * that when they are on this tile, it is reset and a key is added 
     * to the player inventory. 
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     * @param color {@link Color} to set the color field to. ({@link ColorableTile#color see here})
     */
    public Key(Maze.Point tilePos, Color color){
        super(tilePos, false, color);
        
        playerObserver = player -> {
            if(player.getPos().equals(tilePos)){
                Maze.resetTile(tilePos);
                Maze.player.addKey(color);
                Maze.unclaimedInteractions.offer(Entity.Action.Interaction.PickupKey);
            }
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public void deleteTile(){ 
        Maze.player.removeObserver(playerObserver);
    }

    @Override
    public String toString(){ return "K"; }
}
