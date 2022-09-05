package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents an empty tile.
 * 
 * @author Abdulrahman Asfari
 */
public class Air extends Tile{
    /**
     * Default constructor, sets the position of the tile,
     * and obstructiveness to false.
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     */
    public Air(Maze.Point tilePos){
        super(tilePos, false);
    }
}
