package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.Point;

/**
 * Base class for enemies, has a speed property so app
 * knows how often to ping the enemy.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.1
 */
public abstract class EnemyEntity<S extends Observable<S>> extends Entity<S>{
    /** How often the monster gets pinged. */
    private final int speed;

    /**
     * Default constructor, sets the position, direction, and speed of the enemy.
     * 
     * @param entityPos {@link Maze.Point Point} to set the position field to. ({@link Entity#entityPos see here})
     * @param facingDir {@link Direction} to set the direction field to. ({@link Entity#facingDir see here})
     * @param speed How often the monster gets pinged. 
     */
    public EnemyEntity(Point entityPos, Direction facingDir, int speed){
        super(entityPos, facingDir);
        this.speed = speed;
    }

    /** @return The {@link #speed} of the enemy. */
    public final int getSpeed(){ return speed; }
}
