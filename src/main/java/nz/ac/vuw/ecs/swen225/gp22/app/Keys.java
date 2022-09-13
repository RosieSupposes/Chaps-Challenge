package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

class Keys implements KeyListener {
    private final Map<Integer, Runnable> actionsPressed = new HashMap<>();
    private final Map<Integer, Runnable> actionsCtrlReleased = new HashMap<>();
    private final Map<Integer, Runnable> actionsReleased = new HashMap<>();

    public void setAction(int keyCode, Runnable onPressed, boolean ctrlDown) {
        if (ctrlDown) {
            actionsCtrlReleased.put(keyCode, onPressed);
        } else {
            actionsReleased.put(keyCode, onPressed);
        }
    }

    public void setAction(int keyCode, Runnable onPressed) {
        actionsPressed.put(keyCode, onPressed);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        assert SwingUtilities.isEventDispatchThread();
        actionsPressed.getOrDefault(e.getKeyCode(), () -> {
        }).run();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        assert SwingUtilities.isEventDispatchThread();
        if (e.isControlDown()) {
            actionsCtrlReleased.getOrDefault(e.getKeyCode(), () -> {
            }).run();
        } else {
            actionsReleased.getOrDefault(e.getKeyCode(), () -> {
            }).run();
        }

    }
}
