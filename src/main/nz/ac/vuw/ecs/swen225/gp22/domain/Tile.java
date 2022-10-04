package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Template for tiles that has fields and methods that all tiles will need.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.4
 */
public abstract class Tile{
    /** The position of the tile on the {@link Maze#tileMap tilemap}. */
    private Maze.Point tilePos;

    /** This represents whether or not an {@link Entity} can walk onto the tile. */
    private boolean obstructive;

    /**
     * Default constructor, sets the position and obstructiveness of the tile.
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link #tilePos see here})
     * @param obstructive Boolean to set the obstructive field to. ({@link #obstructive see here})
     */
    public Tile(Maze.Point tilePos, boolean obstructive){
        setPos(tilePos);
        this.obstructive = obstructive;
    }

    /** @return The {@link #tilePos position} of the tile. */
    public Maze.Point getPos(){ return tilePos; }

    /** @return The {@link #obstructive obstructiveness} of the tile. */
    public boolean isObstructive(){ return obstructive; }

    /**
     * Sets the {@link #tilePos position} of the tile.
     * 
     * @param pos {@link Maze.Point Point} that represents the tile's new position.
     */
    public void setPos(Maze.Point pos){ 
        if(pos == null || !pos.isValid()) throw new IllegalArgumentException("Invalid point given.");
        tilePos = pos; 
    }

    /**
     * Updates the {@link #obstructive obstructiveness} of this tile.
     * 
     * @param obstructive The new value of the {@link #obstructive} field.
     */
    public void setObstructive(boolean obstructive){ this.obstructive = obstructive; }

    /** 
     * Called when a tile is removed or replaced.
     * This method is not abstract because not all tiles 
     * will need special code to run on deletion.
     */
    public void deleteTile(){ }
}
