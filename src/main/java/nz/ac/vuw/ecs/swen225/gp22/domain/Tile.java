package nz.ac.vuw.ecs.swen225.gp22.domain;

public abstract class Tile{
    protected final Maze.Point tilePos;
    protected boolean obstructive;

    public Tile(Maze.Point tilePos, boolean obstructive){
        this.tilePos = tilePos;
        this.obstructive = obstructive;
    }

    public Maze.Point getPos(){ return tilePos; }
    public boolean isObstructive(){ return obstructive; }
}
