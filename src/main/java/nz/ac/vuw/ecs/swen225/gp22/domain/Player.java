package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity<Player>{
    private List<ColorableTile.Color> collectedKeys =  new ArrayList<>();

    public Player(Maze.Point entityPos, Direction facingDir) {
        super(entityPos, facingDir);
    }

    public void resetItems(){
        collectedKeys.clear();
    }

    public void addKey(ColorableTile.Color color){
        if(color == null) return; // BAD BAD
        collectedKeys.add(color);
        updateObservers(this);
    }

    public void consumeKey(ColorableTile.Color color){
        if(!collectedKeys.contains(color)) return; // BAD BAD
        collectedKeys.remove(color);
        updateObservers(this);
    }

    public boolean hasKey(ColorableTile.Color color){ return collectedKeys.contains(color); }
}
