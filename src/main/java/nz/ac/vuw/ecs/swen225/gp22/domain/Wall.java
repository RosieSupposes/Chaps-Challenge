package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a wall, the main tile that will
 * obstruct the player.
 * 
 * @author Abdulrahman Asfari 300475089
 */
public class Wall extends Tile{
    /**
     * Default constructor, sets the position of the tile,
     * and obstructiveness to true.
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     */
    public Wall(Maze.Point tilePos){
        super(tilePos, true);
    }
}