package nz.ac.vuw.ecs.swen225.gp22.domain;

public class InfoField extends Tile{
    private final String helpText;

    public InfoField(Maze.Point tilePos, String helpText){
        super(tilePos, false);
        this.helpText = helpText;
    }

    public String getText(){ return helpText; }
}
