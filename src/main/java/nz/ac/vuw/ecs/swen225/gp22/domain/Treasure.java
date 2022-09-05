package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile with a treasure on it, which the player
 * can pick up by walking onto the tile.
 * 
 * @author Abdulrahman Asfari
 */
public class Treasure extends Tile{
    /** Used for tile functionality that depends on the player. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position the tile, and 
     * obstructiveness to false. An observer is also added to the player so
     * that when they are on this tile, it is reset and the treasure counter
     * is updated.  
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     */
    public Treasure(Maze.Point tilePos){
        super(tilePos, false);
        
        playerObserver = player -> {
            if(player.getPos().equals(tilePos)){
                Maze.collectTreasure();
                Maze.resetTile(tilePos);
            }
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public void deleteTile(){ 
        Maze.player.removeObserver(playerObserver);
    }
}
