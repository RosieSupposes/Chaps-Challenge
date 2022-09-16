package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a tile which will give the player
 * additional information if they are on it.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.2
 */
public class InfoField extends Tile{
    /** The text that will be displayed if the player is on this tile. */
    private final String infoText;

    /**
     * Default constructor, sets the position of the tile,
     * and obstructiveness to false.
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     */
    public InfoField(Maze.Point tilePos, String infoText){
        super(tilePos, false);
        this.infoText = infoText;
    }

    /** @return The {@link #infoText info text} of this tile. */
    public String getText(){ return infoText; }
}
