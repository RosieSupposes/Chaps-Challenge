package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.Point;

/**
 * Template for Tile objects that need a color property.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.3
 */
public abstract class ColorableTile extends Tile{
    /** Enum with values that represent all valid colors. */
    public enum Color{
        None,
        Red,
        Green,
        Blue,
        Yellow;
    }

    /** The color of this tile. */
    private final Color color;

    /**
     * Default constructor, sets the position, obstructiveness, and color of the tile.
     * 
     * @param tilePos Point to set the position field to. 
     * @param obstructive Boolean to set the obstructive field to. 
     * @param color Color to set the color field to. 
     */
    public ColorableTile(Point tilePos, boolean obstructive, Color color){
        super(tilePos, obstructive);
        if(color == null || color == Color.None) throw new IllegalArgumentException("Given color is null.");
        this.color = color;
    }

    /** @return The color of the tile. */
    public Color getColor(){ return color; }
}
