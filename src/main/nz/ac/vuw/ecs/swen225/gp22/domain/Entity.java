package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Template for entities in a level, including the player. 
 * Any entities are observable.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.12
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

    /**
     * A record used to store an action taken. This will be 
     * used to revert actions when replaying a game.
     */
    public record Action(int hashcode, Maze.Point moveVector, Direction oldDir, Direction newDir, Interaction interaction){
        public record Interaction(ActionType type, ColorableTile.Color color){
            /** Represents the entity interacting with a tile. */
            public enum ActionType{
                None,
                PickupKey,
                PickupTreasure,
                UnlockDoor,
                UnlockExit,
                Pinged;
            }
        }
    }

    /** Position of the entity in regards to the {@link Maze#tileMap tilemap}. */
    private Maze.Point entityPos;

    /** The direction the entity is facing. */
    private Direction facingDir;

    /** The action which the entity has taken, used for recorder. */
    public Action action; 

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

    /** Non-player entities will act based on how often this is called. */
    abstract public void ping();

    /** @return Whether or not this entity has done an action since the last call of {@link Maze#getChangeMap()}. */
    public boolean hasAction(){ return action != null; }

    public Action pollAction(){
        Action storedAction = action;
        action = null;
        return storedAction;
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
     * Overloaded method for {@link #move move()}, takes in a point.
     * 
     * @param moveVector Amount to move by.
     */
    public void move(Maze.Point moveVector){
        if(moveVector == null) throw new IllegalArgumentException("Given point is null");
        Maze.Point newPos = entityPos.add(moveVector);
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
    public void moveAndTurn(Direction dir){
        setDir(dir);
        move();
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

    /** 
     * Called when an entity is removed.
     * This method is not abstract because not all entities 
     * will need special code to run on deletion.
     */
    public void deleteEntity(){ }
}
