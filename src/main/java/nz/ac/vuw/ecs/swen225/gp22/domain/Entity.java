package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Template for entities in a level, including the player. 
 * Any entities are observable.
 * 
 * @author Abdulrahman Asfari 
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
        this.entityPos = entityPos;
        this.facingDir = facingDir;
    }

    /**
     * Moves the entity in a given direction. 
     * 
     * @param direction Direction to move the entity in.
     */
    public void move(Direction direction){
        Maze.Point newPos = entityPos.add(direction);
        if(Maze.getTile(newPos).isObstructive()) return; // BAD THINGS
        entityPos = newPos; 
        updateObservers(); 
    }

    /**
     * Overloaded method for {@link #move move()}, assumes
     * the chosen direction is the direction the entity is facing.
     */
    public void move(){ 
        move(facingDir);
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
    public void setPos(Maze.Point pos){ entityPos = pos; }

    /**
     * Sets the {@link #facingDir direction} the entity is facing.
     * 
     * @param dir The new {@link Direction direction} of the entity. 
     */
    public void setDir(Direction dir){ facingDir = dir; }
}
