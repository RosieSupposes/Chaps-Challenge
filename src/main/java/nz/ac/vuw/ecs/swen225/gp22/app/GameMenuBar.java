package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMenuBar extends JMenuBar {

    List<JComponent> components = new ArrayList<>();
    int menuItemWidth;
    Base base;

    public GameMenuBar(Base base){
        this.base = base;
        setMenuBarForGame();
    }

    public void setMenuBarForGame() {
        menuItemWidth = Main.GAME_WINDOW_SIZE / 6;
        addHomeButton();
    }

    private void addHomeButton(){
        JMenuItem home = new JMenuItem("Home");
        home.setMaximumSize(new Dimension(menuItemWidth, 30));
        home.addActionListener(e -> base.startPhase());
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
        pause.addActionListener(e -> base.pauseAction.run());
        this.add(pause);
        components.add(pause);

        JMenu newLevel = new JMenu("New");
        newLevel.setMaximumSize(new Dimension(menuItemWidth, 30));

        JMenuItem levelOne = new JMenuItem("Level One");
        levelOne.addActionListener(e -> base.newLevelOneAction.run());
        newLevel.add(levelOne);
        components.add(levelOne);

        JMenuItem levelTwo = new JMenuItem("Level Two");
        levelTwo.addActionListener(e -> base.newLevelTwoAction.run());
        newLevel.add(levelTwo);
        components.add(levelTwo);

        this.add(newLevel);
        components.add(newLevel);

        JMenuItem save = new JMenuItem("Save");
        save.setMaximumSize(new Dimension(menuItemWidth, 30));
        save.addActionListener(e -> base.saveAction.run());
        this.add(save);
        components.add(save);
    }

    public void addLoadButton(){
        JMenuItem load = new JMenuItem("Load");
        load.setMaximumSize(new Dimension(menuItemWidth, 30));
        load.addActionListener(e -> base.loadAction.run());
        this.add(load);
        components.add(load);
    }

    public void addExitButton(){
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMaximumSize(new Dimension(menuItemWidth, 30));
        exit.addActionListener(e -> base.exitAction.run());
        this.add(exit);
        components.add(exit);
    }

    public List<JComponent> getAllComponents(){
        return components;
    }
}
