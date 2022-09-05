package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.Point;

/**
 * Template for {@link Tile} objects that need a color property.
 * 
 * @author Abdulrahman Asfari
 */
public abstract class ColorableTile extends Tile{
    /** Enum with values that represent all valid colors. */
    public enum Color{
        Red,
        Green,
        Blue,
        Yellow
    }

    /** The color of this tile. */
    private final Color color;

    /**
     * Default constructor, sets the position, obstructiveness, and color of the tile.
     * 
     * @param tilePos {@link Maze.Point Point} to set the position field to. ({@link Tile#tilePos see here})
     * @param obstructive Boolean to set the obstructive field to. ({@link Tile#obstructive see here})
     * @param color {@link Color} to set the color field to. ({@link #color see here})
     */
    public ColorableTile(Point tilePos, boolean obstructive, Color color){
        super(tilePos, obstructive);
        this.color = color;
    }

    /** @return The {@link #color} of the tile. */
    public Color getColor(){ return color; }
}
