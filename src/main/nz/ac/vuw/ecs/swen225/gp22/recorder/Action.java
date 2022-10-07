package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * The action class to update the game.
 *
 * @author Christopher Sa, 300570735
 * @version 1.1
 * @param actionType The type of action.
 * @param prevDir The previous direction.
 * @param currDir The current direction.
 * @param color The color of the tile. Can be None.
 */
public record Action(int entityHash, String actionType, int x, int y, String prevDir, String currDir, String color) {

}
