package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A basic enemy that INSERT BEHAVIOR HERE.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.2
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
            if(player.getPos().equals(entityPos)){
                // DEATH AND TAXES
                System.out.println("Goodbye, I have died.");
            }
        };
        Maze.player.addObserver(playerObserver);
    }

    @Override
    public Action ping(){
        System.out.println("PINGA");
        return moveAndTurn(getDir());
    }

    @Override
    public void deleteEntity(){ Maze.player.removeObserver(playerObserver); }
}