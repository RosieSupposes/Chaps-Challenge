package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameMenuBar extends JMenuBar {

    List<JComponent> components = new ArrayList<>(); //all components on window
    int menuItemWidth; //width of buttons
    Base base; //base JFrame to connect actions to
    Color col = new Color(220, 180, 240);
    GameMenuItem pause;

    /**
     * Modular menu bar for the program. Default menu has home button
     *
     * @param base the JFrame that these menu actions connect to
     */
    public GameMenuBar(Base base) {
        this.base = base;
        menuItemWidth = Main.GAME_WINDOW_SIZE / 6;
        addHomeButton();
        this.setBackground(col);
    }

    /**
     * Adds home button to this JMenuBar
     */
    private void addHomeButton() {
        GameMenuItem home = new GameMenuItem("Home", e -> base.menuScreen(), col, menuItemWidth);
        this.add(home);
        components.add(home);
    }

    /**
     * game is paused so set pause button to display "un pause"
     */
    public void setPause(){
        pause.setText("Un Pause");
        pause.changeActionListener(e -> base.unPause());
    }

    /**
     * game is unpaused so set pause button to display "pause"
     */
    public void setUnPause(){
        pause.setText("Pause");
        pause.changeActionListener(e -> base.pause());
    }

    /**
     * Adds buttons to the menu bar that are only relevant to the game window.
     * Pause, New - level 1, level 2, Save
     */
    public void addGameButtons() {
        pause = new GameMenuItem("Pause", e -> base.pause(), col, menuItemWidth);
        this.add(pause);
        components.add(pause);

        JMenu newLevel = new JMenu("New");
        newLevel.setBackground(col);
        newLevel.setMaximumSize(new Dimension(menuItemWidth, 30));

        GameMenuItem levelOne = new GameMenuItem("level One", e -> base.newLevelPhase(true), col);
        newLevel.add(levelOne);
        components.add(levelOne);

        GameMenuItem levelTwo = new GameMenuItem("Level Two", e -> base.newLevelPhase(false), col);
        newLevel.add(levelTwo);
        components.add(levelTwo);

        this.add(newLevel);
        components.add(newLevel);

        GameMenuItem save = new GameMenuItem("Save", e -> base.saveGame(), col, menuItemWidth);
        this.add(save);
        components.add(save);
    }

    public void addLoadButton() {
        GameMenuItem load = new GameMenuItem("Load", e -> base.loadGame(), col, menuItemWidth);
        this.add(load);
        components.add(load);
    }

    public void addExitButton() {
        GameMenuItem exit = new GameMenuItem("Exit", e -> base.exitGame(), col, menuItemWidth);
        this.add(exit);
        components.add(exit);
    }

    public List<JComponent> getAllComponents() {
        return components;
    }

    public void changeKeyListener(KeyListener keyListener){
        this.removeKeyListener(this.getKeyListeners()[0]);
        this.addKeyListener(keyListener);
    }
}
