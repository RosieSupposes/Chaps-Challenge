package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;

import java.util.ArrayList;
import java.util.List;

/**
 * The game state class to store the game state.
 *
 * @author Christopher Sa, 300570735
 * @version 1.1
 */
public class GameState {
    private final int id;
    private final int time;
    private final List<Action> actions;

    /**
     * Create a new game state.
     *
     * @param id the id of the game state
     * @param time the time of the game state
     * @param base the base of the game
     */
    protected GameState(int id, int time, Base base) {
        this.id = id;
        this.time = time;
        this.actions = new ArrayList<>();
    }

    /**
     * Add an action to the game state.
     *
     * @param action The action to add.
     */
    protected void addAction(Action action) {
        actions.add(action);
    }

    /**
     * Get the time of the game state.
     *
     * @return The time of the game state.
     */
    public int getTime() {
        return time;
    }
}
