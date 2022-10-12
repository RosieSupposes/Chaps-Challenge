package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A basic enemy that moves back and forth.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.7
 */
public class GummyGuard extends EnemyEntity<GummyGuard>{
    /** Used for if the player walks into the enemy. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position, direction, and speed of the enemy.
     * 
     * @param entityPos {@link Maze.Point Point} to set the position field to. ({@link Entity#entityPos see here})
     * @param facingDir {@link Direction} to set the direction field to. ({@link Entity#facingDir see here})
     */
    public GummyGuard(Maze.Point entityPos, Direction facingDir){
        super(entityPos, facingDir, 400);

        playerObserver = player -> {
            if(player.getPos().equals(getPos())) Maze.loseGame();
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public void ping(){
        Direction oldDir = getDir();
        Maze.Point oldPos = getPos();
        moveAndTurn(getDir());
        if(Maze.getTile(getPos().add(getDir())).isObstructive()) setDir(getDir().opposite());
        if(Maze.player.getPos().equals(getPos())) Maze.loseGame();
        action = new Action(id(), getPos().subtract(oldPos), oldDir, getDir(), new Action.Interaction(Action.Interaction.ActionType.Pinged, ColorableTile.Color.None));
    }

    @Override
    public void unping(){
        System.out.println("IM NOT SURE YET, WE WILL SEE");
    }

    @Override
    public void deleteEntity(){ Maze.player.removeObserver(playerObserver); }
}