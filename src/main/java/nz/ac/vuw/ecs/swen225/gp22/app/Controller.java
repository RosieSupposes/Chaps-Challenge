package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;

import java.awt.event.KeyEvent;

class Controller extends Keys {
    Controller(Base b) {
        setAction(KeyEvent.VK_X, b.exitAction, true); //ctrl X
        setAction(KeyEvent.VK_S, b.saveAction, true); //ctrl s
        setAction(KeyEvent.VK_R, b.replayAction, true); //ctrl r
        setAction(KeyEvent.VK_1, b.newLevelOneAction, true); //ctrl 1
        setAction(KeyEvent.VK_2, b.newLevelTwoAction, true); //ctrl 2
        setAction(KeyEvent.VK_SPACE, b.pauseAction, false); //space
        setAction(KeyEvent.VK_ESCAPE, b.unPauseAction, false); //esc

        setAction(KeyEvent.VK_UP, () -> b.movePlayer(Entity.Direction.Up));
        setAction(KeyEvent.VK_DOWN, () -> b.movePlayer(Entity.Direction.Down));
        setAction(KeyEvent.VK_LEFT, () -> b.movePlayer(Entity.Direction.Left));
        setAction(KeyEvent.VK_RIGHT, () -> b.movePlayer(Entity.Direction.Right));
    }
}