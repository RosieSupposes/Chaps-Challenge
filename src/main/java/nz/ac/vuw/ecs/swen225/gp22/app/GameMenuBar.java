package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMenuBar extends JMenuBar {

    List<JComponent> components = new ArrayList<>(); //all components on window
    int menuItemWidth; //width of buttons
    Base base; //base JFrame to connect actions to

    /**
     * Modular menu bar for the program. Default menu has home button
     *
     * @param base the JFrame that these menu actions connect to
     */
    public GameMenuBar(Base base){
        this.base = base;
        menuItemWidth = Main.GAME_WINDOW_SIZE / 6;
        addHomeButton();
    }

    /**
     * Adds home button to this JMenuBar
     */
    private void addHomeButton(){
        JMenuItem home = new JMenuItem("Home");
        home.setMaximumSize(new Dimension(menuItemWidth, 30));
        home.addActionListener(e -> base.menuScreen());
        this.add(home);
        components.add(home);
    }

    /**
     * Adds buttons to the menu bar that are only relevant to the game window.
     * Pause, New - level 1, level 2, Save
     */
    public void addGameButtons(){
        JMenuItem pause = new JMenuItem("Pause");
        pause.setMaximumSize(new Dimension(menuItemWidth, 30));
        pause.addActionListener(e -> base.pause());
        this.add(pause);
        components.add(pause);

        JMenu newLevel = new JMenu("New");
        newLevel.setMaximumSize(new Dimension(menuItemWidth, 30));

        JMenuItem levelOne = new JMenuItem("Level One");
        levelOne.addActionListener(e -> base.newLevelPhase(true));
        newLevel.add(levelOne);
        components.add(levelOne);

        JMenuItem levelTwo = new JMenuItem("Level Two");
        levelTwo.addActionListener(e -> base.newLevelPhase(false));
        newLevel.add(levelTwo);
        components.add(levelTwo);

        this.add(newLevel);
        components.add(newLevel);

        JMenuItem save = new JMenuItem("Save");
        save.setMaximumSize(new Dimension(menuItemWidth, 30));
        save.addActionListener(e -> base.saveGame());
        this.add(save);
        components.add(save);
    }

    public void addLoadButton(){
        JMenuItem load = new JMenuItem("Load");
        load.setMaximumSize(new Dimension(menuItemWidth, 30));
        load.addActionListener(e -> base.loadGame());
        this.add(load);
        components.add(load);
    }

    public void addExitButton(){
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMaximumSize(new Dimension(menuItemWidth, 30));
        exit.addActionListener(e -> base.exitGame());
        this.add(exit);
        components.add(exit);
    }

    public List<JComponent> getAllComponents(){
        return components;
    }
}
