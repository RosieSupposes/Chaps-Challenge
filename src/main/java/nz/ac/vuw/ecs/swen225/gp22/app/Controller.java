package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;

import java.awt.event.KeyEvent;

/**
 * Controller that connects key actions to actions
 *
 * @author Molly Henry, 300562038
 * @version 1.1
 */
class Controller extends Keys {
    Controller(Base b) {
        setAction(KeyEvent.VK_X, b::exitGame, true); //ctrl X
        setAction(KeyEvent.VK_S, b::saveGame, true); //ctrl s
        setAction(KeyEvent.VK_R, b::replayPhase, true); //ctrl r
        setAction(KeyEvent.VK_1, ()->b.newLevelPhase(true), true); //ctrl 1
        setAction(KeyEvent.VK_2, ()->b.newLevelPhase(false), true); //ctrl 2
        setAction(KeyEvent.VK_SPACE, b::pause, false); //space
        setAction(KeyEvent.VK_ESCAPE, b::unPause, false); //esc

        setAction(KeyEvent.VK_UP, () -> b.movePlayer(Entity.Direction.Up));
        setAction(KeyEvent.VK_DOWN, () -> b.movePlayer(Entity.Direction.Down));
        setAction(KeyEvent.VK_LEFT, () -> b.movePlayer(Entity.Direction.Left));
        setAction(KeyEvent.VK_RIGHT, () -> b.movePlayer(Entity.Direction.Right));
    }
}