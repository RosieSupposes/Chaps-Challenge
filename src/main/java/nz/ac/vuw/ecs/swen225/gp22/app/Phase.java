package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

/**
 * Phase has constructors to build all the different windows
 *
 * @author henrymoll
 */
public class Phase extends JPanel {
    private Base base; //base the phase is being added to

    private final Runnable startAction = () -> {
        base.levelOnePhase();
    };
    private final Runnable pauseAction = () -> {
        System.out.println("Pause");
    };
    private final Runnable loadAction = () -> {
        System.out.println("Load");
    };
    private final Runnable saveAction = () -> {
        System.out.println("Save");
    };
    private final Runnable replayAction = () -> {
        System.out.println("Replay");
    };
    private final Runnable newLevelOneAction = () -> {
        System.out.println("New Level One");
    };
    private final Runnable newLevelTwoAction = () -> {
        System.out.println("New Level Two");
    };
    private final Runnable exitAction = () -> {
        System.out.println("Exit");
        base.runClosePhase();
        System.exit(0);
    };

    /**
     * For creating the menu window
     *
     * @param b add to this base
     */
    Phase(Base b) { //run menu
        this.base = b;

        b.runClosePhase();

        base.add(BorderLayout.EAST, sideBarForMenu());
        base.add(BorderLayout.WEST, mainPanelForMenu());
    }

    /**
     * For creating the recorder window.
     *
     * @param b    add to this base
     * @param file which file is being replayed
     */
    Phase(Base b, String file) { //run recorder replay screen

    }

    /**
     * For creating level one window
     *
     * @param b      add to this base
     * @param person chap player
     */
    Phase(Base b, int person) { //run level one, int to be replaced with Chap character
        this.base = b;

        base.setJMenuBar(menuBarForGame());
    }

    /**
     * For creating level two window
     *
     * @param b       add to this base
     * @param person  chap player
     * @param person2 other actor
     */
    Phase(Base b, int person, int person2) { //run level two, int to be replaced with Chap + Actor

    }

    /**
     * create a menu bar
     *
     * @return the menu bar to add to the window
     */
    public JMenuBar menuBarForGame() {
        int menuItemWidth = base.GAME_WINDOW_SIZE / 5;
        JMenuBar menuBar = new JMenuBar();
        base.addComponent(menuBar);

        JMenuItem pause = new JMenuItem("Pause");
        pause.setMaximumSize(new Dimension(menuItemWidth, 30));
        pause.addActionListener(e -> pauseAction.run());
        menuBar.add(pause);
        base.addComponent(pause);

        JMenu newLevel = new JMenu("New");
        newLevel.setMaximumSize(new Dimension(menuItemWidth, 30));

        JMenuItem levelOne = new JMenuItem("Level One");
        levelOne.addActionListener(e -> newLevelOneAction.run());
        newLevel.add(levelOne);
        base.addComponent(levelOne);

        JMenuItem levelTwo = new JMenuItem("Level Two");
        levelTwo.addActionListener(e -> newLevelTwoAction.run());
        newLevel.add(levelTwo);
        base.addComponent(levelTwo);

        menuBar.add(newLevel);
        base.addComponent(newLevel);

        JMenuItem save = new JMenuItem("Save");
        save.setMaximumSize(new Dimension(menuItemWidth, 30));
        save.addActionListener(e -> saveAction.run());
        menuBar.add(save);
        base.addComponent(save);

        JMenuItem load = new JMenuItem("Load");
        load.setMaximumSize(new Dimension(menuItemWidth, 30));
        load.addActionListener(e -> loadAction.run());
        menuBar.add(load);
        base.addComponent(load);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMaximumSize(new Dimension(menuItemWidth, 30));
        exit.addActionListener(e -> exitAction.run());
        menuBar.add(exit);
        base.addComponent(exit);

        return menuBar;
    }

    /**
     * set up and add main panel to the window for the menu
     */
    public JPanel mainPanelForMenu() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.RED);
        mainPanel.setPreferredSize(new Dimension(base.GAME_WINDOW_SIZE, base.WINDOW_HEIGHT));
        add(BorderLayout.WEST, mainPanel);
        base.addComponent(mainPanel);

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;

        c.gridy = 0;
        mainPanel.add(titlePanelForMenu(), c);

        c.gridy = 1;
        Component vertSpace = Box.createRigidArea(new Dimension(0, 20));
        mainPanel.add(vertSpace, c);

        c.gridy = 2;
        mainPanel.add(buttonPanelForMenu(), c);

        return mainPanel;
    }

    private JLabel titlePanelForMenu() {
        JLabel title = new JLabel("CHAPSSS!");
        title.setBackground(Color.YELLOW);
        return title;
    }

    /**
     * makes and adds buttons for the menu
     *
     * @return panel with buttons on it
     */
    public JPanel buttonPanelForMenu() {
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.GREEN);
        buttons.setPreferredSize(new Dimension(3 * base.TILE_SIZE, 2 * base.TILE_SIZE));

        base.addComponent(buttons);
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Component vertSpace = Box.createRigidArea(new Dimension(0, 7));
        Component horizSpace = Box.createRigidArea(new Dimension(10, 30));

        c.gridx = 0;
        c.gridy = 0;
        //column 1, x = 0
        buttons.add(vertSpace, c);
        c.gridy++;

        JButton start = new JButton("Start");
        start.addActionListener(e -> startAction.run());
        buttons.add(start, c);
        base.addComponent(start);

        c.gridy++;
        buttons.add(vertSpace, c);

        JButton load = new JButton("Load");
        load.addActionListener(e -> loadAction.run());
        c.gridy++;
        buttons.add(load, c);
        base.addComponent(load);

        //column 2, blank space
        c.gridx++;
        c.gridy = 0;
        buttons.add(horizSpace, c);

        //column 3
        c.gridx++;
        c.gridy = 0;

        buttons.add(vertSpace, c);
        c.gridy++;

        JLabel info = new JLabel("Info");
        buttons.add(info, c);
        base.addComponent(info);

        c.gridy++;
        buttons.add(vertSpace, c);

        JButton replay = new JButton("Replay");
        replay.addActionListener(e -> replayAction.run());
        c.gridy++;
        buttons.add(replay, c);
        base.addComponent(replay);

        return buttons;
    }

    /**
     * create and add the sidebar to this phase for the menu page
     *
     * @return panel for the sidebar for menu
     */
    public JPanel sideBarForMenu() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.BLUE);
        sidePanel.setPreferredSize(new Dimension(base.SIDEBAR_WIDTH, base.WINDOW_HEIGHT));
        add(BorderLayout.EAST, sidePanel);
        base.addComponent(sidePanel);
        return sidePanel;
    }
}