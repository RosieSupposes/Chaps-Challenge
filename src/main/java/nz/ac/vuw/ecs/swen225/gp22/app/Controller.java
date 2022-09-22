package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;

import java.awt.event.KeyEvent;

/**
 * Controller that connects key actions to actions
 *
 * @author Molly Henry, 300562038
 * @version 1.2
 */
public class Controller extends Keys {
    Base b;

    /**
     * Menu commands, exiting, replaying, making new levels
     *
     * @param b
     */
    public Controller(Base b) {
        this.b = b;
        constantCommand();
    }

    /**
     * commands for when game is running
     *
     * @param b base which commands get run on
     * @param paused is the game paused?
     */
    public Controller(Base b, boolean paused) {
        this.b = b;
        constantCommand();
        setAction(KeyEvent.VK_S, b::saveGame, true); //ctrl s

        if (paused) {
            setAction(KeyEvent.VK_ESCAPE, b::unPause, false); //esc
        } else {
            setAction(KeyEvent.VK_SPACE, b::pause, false); //space

            setAction(KeyEvent.VK_UP, () -> b.movePlayer(Entity.Direction.Up));
            setAction(KeyEvent.VK_DOWN, () -> b.movePlayer(Entity.Direction.Down));
            setAction(KeyEvent.VK_LEFT, () -> b.movePlayer(Entity.Direction.Left));
            setAction(KeyEvent.VK_RIGHT, () -> b.movePlayer(Entity.Direction.Right));
        }
    }


    private void constantCommand() {
        setAction(KeyEvent.VK_X, b::exitGame, true); //ctrl X
        setAction(KeyEvent.VK_R, b::replayPhase, true); //ctrl r
        setAction(KeyEvent.VK_1, () -> b.newLevelPhase(true), true); //ctrl 1
        setAction(KeyEvent.VK_2, () -> b.newLevelPhase(false), true); //ctrl 2

    }


}