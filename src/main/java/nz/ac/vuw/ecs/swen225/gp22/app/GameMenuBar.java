package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMenuBar extends JMenuBar {

    List<JComponent> components = new ArrayList<>(); //all components on window
    int menuItemWidth; //width of buttons
    Base base; //base JFrame to connect actions to
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
        this.setBackground(Main.BUTTON_COLOR);
    }

    /**
     * Adds home button to this JMenuBar
     */
    private void addHomeButton() {
        GameMenuItem home = new GameMenuItem("Home", e -> base.menuScreen(), menuItemWidth);
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
        pause = new GameMenuItem("Pause", e -> base.pause(), menuItemWidth);
        this.add(pause);
        components.add(pause);

        JMenu newLevel = new JMenu("New");
        newLevel.setBackground(Main.BUTTON_COLOR);
        newLevel.setMaximumSize(new Dimension(menuItemWidth, 30));

        GameMenuItem levelOne = new GameMenuItem("level One", e -> base.newLevelPhase(true));
        newLevel.add(levelOne);
        components.add(levelOne);

        GameMenuItem levelTwo = new GameMenuItem("Level Two", e -> base.newLevelPhase(false));
        newLevel.add(levelTwo);
        components.add(levelTwo);

        this.add(newLevel);
        components.add(newLevel);

        GameMenuItem save = new GameMenuItem("Save", e -> base.saveGame(), menuItemWidth);
        this.add(save);
        components.add(save);
    }

    public void addLoadButton() {
        GameMenuItem load = new GameMenuItem("Load", e -> base.loadGame(), menuItemWidth);
        this.add(load);
        components.add(load);
    }

    public void addExitButton() {
        GameMenuItem exit = new GameMenuItem("Exit", e -> base.exitGame(), menuItemWidth);
        this.add(exit);
        components.add(exit);
    }

    public List<JComponent> getAllComponents() {
        return components;
    }
}
