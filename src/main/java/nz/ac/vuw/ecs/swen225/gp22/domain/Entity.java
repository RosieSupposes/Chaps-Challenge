package nz.ac.vuw.ecs.swen225.gp22.domain;

public abstract class Entity<S extends Observable<S>> extends Observable<S>{
    public enum Direction{
        Up(0, -1),
        Down(0, 1),
        Left(-1, 0),
        Right(1, 0);

        Maze.Point posChange;
        Direction(int x, int y){ posChange = new Maze.Point(x, y); }
    }
    protected Maze.Point entityPos;
    protected Direction facingDir;

    public Entity(Maze.Point entityPos, Direction facingDir){
        this.entityPos = entityPos;
        this.facingDir = facingDir;
    }

    @SuppressWarnings("unchecked") //explain why i did this, since S is always a subtype of observable and any subtype of entity is observable the cast is safe.
    public void move(Direction direction){
        Maze.Point newPos = entityPos.add(direction);
        if(Maze.getTile(newPos).isObstructive()) return; // BAD THINGS
        entityPos = newPos; 
        updateObservers((S) this); 
    }

    public void step(){ 
        move(facingDir);
    }

    public Maze.Point getPos(){ return entityPos; }
    public Direction getDir(){ return facingDir; }

    public void setPos(Maze.Point pos){ entityPos = pos; }
    public void setDir(Direction dir){ facingDir = dir; }
}
