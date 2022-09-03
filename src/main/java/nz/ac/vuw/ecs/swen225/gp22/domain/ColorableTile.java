package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.Point;

public abstract class ColorableTile extends Tile{
    public enum Color{
        Red,
        Green,
        Blue,
        Yellow
    }
    private Color color;

    public ColorableTile(Point tilePos, boolean obstructive, Color color){
        super(tilePos, obstructive);
        this.color = color;
    }

    public Color getColor(){ return color; }
}
