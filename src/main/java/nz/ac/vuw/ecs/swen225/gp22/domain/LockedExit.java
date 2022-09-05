package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile which the player can only walk
 * on if they have collected all the treasures.
 * 
 * @author Abdulrahman Asfari 300475089
 */
public class LockedExit extends Tile{
    /** Used for tile functionality that depends on the player. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position and color of the tile, and 
     * obstructiveness to true. An observer is also added to the player so
     * that the obstructiveness is updated based on the treasures collected. The same 
     * observer makes sure that when they are on this tile, it is reset. 
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     */
    public LockedExit(Maze.Point tilePos){
        super(tilePos, true);

        playerObserver = player -> {
            setObstructive(!Maze.collectedAllTreasures());
            if(player.getPos().equals(tilePos)){
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
