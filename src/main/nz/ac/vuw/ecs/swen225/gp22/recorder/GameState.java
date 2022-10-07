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

    protected GameState(int id, int time, Base base) {
        this.id = id;
        this.time = time;
        this.actions = new ArrayList<>();
    }

    protected void addAction(Action action) {
        actions.add(action);
    }
    public int getTime() {
        return time;
    }
}
