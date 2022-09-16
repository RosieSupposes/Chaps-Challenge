package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The entity that will be controller by user input, this functions just like
 * a base {@link Entity} but with an inventory to hold keys.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.2
 */
public class Player extends Entity<Player>{
    /** Stores all the keys that the player has. */
    private List<ColorableTile.Color> collectedKeys =  new ArrayList<>();

    /**
     * Default constructor, sets the position and direction of the player.
     * 
     * @param entityPos {@link Maze.Point Point} to set the position field to. ({@link Entity#entityPos see here})
     * @param facingDir {@link Direction} to set the direction field to. ({@link Entity#facingDir see here})
     */
    public Player(Maze.Point entityPos, Direction facingDir) {
        super(entityPos, facingDir);
    }

    /** Clears all the keys that the player has. */
    public void resetItems(){ collectedKeys.clear(); }

    /** 
     * Adds a key to the player's inventory.
     * 
     * @param color {@link ColorableTile.Color Color} of the key. 
     */
    @DevMarkers.NeedsPrecons
    public void addKey(ColorableTile.Color color){
        if(color == null) return; // BAD BAD
        collectedKeys.add(color);
        updateObservers();
    }

    /** 
     * Consumes a key from the player's inventory.
     * 
     * @param color {@link ColorableTile.Color Color} of the key.
     */
    @DevMarkers.NeedsPrecons
    public void consumeKey(ColorableTile.Color color){
        if(!collectedKeys.contains(color)) return; // BAD BAD
        collectedKeys.remove(color);
        updateObservers();
    }

    /**
     * Checks if the player has a key of a certain color.
     * 
     * @param color {@link ColorableTile.Color Color} of the key.
     * @return Whether or not the key is in the player's inventory.
     */
    public boolean hasKey(ColorableTile.Color color){ return collectedKeys.contains(color); }
}
