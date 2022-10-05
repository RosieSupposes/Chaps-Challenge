package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A basic enemy that INSERT BEHAVIOR HERE.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.3
 */
public class GummyGuard extends EnemyEntity<GummyGuard>{
    /** Used for if the player walks into the enemy. */
    private final Observer<Player> playerObserver;

    /**
     * Default constructor, sets the position, direction, and speed of the enemy.
     * 
     * @param entityPos {@link Maze.Point Point} to set the position field to. ({@link Entity#entityPos see here})
     * @param facingDir {@link Direction} to set the direction field to. ({@link Entity#facingDir see here})
     * @param speed How often the monster gets pinged. 
     */
    public GummyGuard(Maze.Point entityPos, Direction facingDir){
        super(entityPos, facingDir, 400);

        playerObserver = player -> {
            if(player.getPos().equals(getPos())) Maze.loseGame();
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public Action ping(){
        Action action = moveAndTurn(getDir());
        switch(getDir()){
            case Down: 
                setDir(Direction.Left);
                break;
            case Left:
                setDir(Direction.Up);
                break;
            case Right:
                setDir(Direction.Down);
                break;
            case Up:
                setDir(Direction.Right);
                break;
            default:
                break;
        }
        if(Maze.player.getPos().equals(getPos())) Maze.loseGame();
        return action;
    }

    @Override
    public void deleteEntity(){ Maze.player.removeObserver(playerObserver); }
}