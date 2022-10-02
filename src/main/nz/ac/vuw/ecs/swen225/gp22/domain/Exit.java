package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile that will transition to the next level
 * when the player walks onto the tile.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.3
 */
public class Exit extends Tile{
    /**
     * Default constructor, sets the position the tile, and 
     * obstructiveness to false. An observer is also added to the player so
     * that when they are on this tile, the next level is loaded. 
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     */
    public Exit(Maze.Point tilePos){
        super(tilePos, false);
        Maze.player.addObserver(player -> {
            if(player.getPos().equals(tilePos)){
                // Persistency.loadLevel(Maze.getNextLevel());
            }
        });
    }

    @Override
    public String toString(){ return "E"; }
}
