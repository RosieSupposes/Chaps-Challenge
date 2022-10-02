package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity.Action.Interaction;

/**
 * Template for entities in a level, including the player. 
 * Any entities are observable.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.8
 */
public abstract class Entity<S extends Observable<S>> extends Observable<S>{
    /**
     * Enum with values for each direction, which each have a {@link Maze.Point point}
     * representing the change in position.
     */
    public enum Direction{
        Up(0, -1),
        Down(0, 1),
        Left(-1, 0),
        Right(1, 0);

        /** Amount the position of the entity will change by if it takes a step. */
        Maze.Point posChange;

        /** Default constructor to set {@link #posChange}. */
        Direction(int x, int y){ posChange = new Maze.Point(x, y); }
    }

    public record Action(Maze.Point pos, Direction dir, Interaction interaction){
        public enum Interaction{
            None,
            PickupKey,
            PickupTreasure,
            UnlockDoor,
            UnlockExit;
        }
    }

    /** Position of the entity in regards to the {@link Maze#tileMap tilemap}. */
    private Maze.Point entityPos;

    /** The direction the entity is facing. */
    private Direction facingDir;

    /**
     * Default constructor, sets the position and direction of the entity.
     * 
     * @param entityPos {@link Maze.Point Point} to set the position field to. ({@link #entityPos see here})
     * @param facingDir {@link Direction} to set the direction field to. ({@link #facingDir see here})
     */
    public Entity(Maze.Point entityPos, Direction facingDir){
        setPos(entityPos);
        setDir(facingDir);
    }

    /**
     * Moves the entity in a given direction. 
     * 
     * @param direction Direction to move the entity in.
     */
    public void move(Direction direction){
        if(direction == null) throw new IllegalArgumentException("Given direction is null");
        Maze.Point newPos = entityPos.add(direction);
        if(Maze.getTile(newPos).isObstructive()) throw new IllegalArgumentException("Entity cannot move onto this tile.");
        if(!newPos.isValid()) throw new IllegalArgumentException("Entity is trying to move onto a nonexistent tile.");
        setPos(newPos);
        assert entityPos.isValid() && newPos.equals(entityPos) : "Moving the player resulted in the incorrect position.";
        updateObservers(); 
    }

    /**
     * Overloaded method for {@link #move move()}, assumes
     * the chosen direction is the direction the entity is facing.
     */
    public void move(){ 
        move(facingDir);
    }

    /**
     * Combines methods {@link #setDir setDir()} and {@link #move move()}.
     * Also returns an {@link Action} record for the app module to use.
     * 
     * @param dir The new {@link Direction direction} of the entity. 
     */
    public Action moveAndTurn(Direction dir){
        setDir(dir);
        move();

        Action.Interaction interaction = Interaction.None;
        if(!Maze.unclaimedInteractions.isEmpty()) interaction = Maze.unclaimedInteractions.poll();
        return new Action(entityPos, facingDir, interaction);
    }

    /** @return The {@link #entityPos position} of the entity. */
    public Maze.Point getPos(){ return entityPos; }

    /** @return The {@link #facingDir direction} the entity is facing. */
    public Direction getDir(){ return facingDir; }

    /**
     * Sets the {@link #entityPos position} of the entity.
     * 
     * @param pos {@link Maze.Point Point} that represents the entity's new position.
     */
    public void setPos(Maze.Point pos){ 
        if(pos == null || !pos.isValid()) throw new IllegalArgumentException("Invalid point given.");
        entityPos = pos; 
        updateObservers(); 
    }

    /**
     * Sets the {@link #facingDir direction} the entity is facing.
     * 
     * @param dir The new {@link Direction direction} of the entity. 
     */
    public void setDir(Direction dir){ 
        if(dir == null) throw new IllegalArgumentException("Given direction is null");
        facingDir = dir; 
        updateObservers(); 
    }
}
