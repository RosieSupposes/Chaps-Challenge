package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Molly Henry, 300562038
 * @version 1.3
 */
public class Base extends JFrame {
    private Runnable closePhase = () -> {
        for (JComponent component : this.components) {
            remove(component);
        }
    };
    public final int TILE_SIZE = 60;
    public final int NUM_GAME_TILE = 9;
    public final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    public final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    public final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    public final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;

    private List<JComponent> components = new ArrayList<>();

    public final Runnable startAction = () -> levelPhase(true);
    public final Runnable pauseAction = () -> {
        System.out.println("Pause");
    };
    public final Runnable unPauseAction = () -> {
        System.out.println("Un Pause");
    };
    public final Runnable loadAction = () -> {
        System.out.println("Load");
    };
    public final Runnable saveAction = () -> {
        System.out.println("Save");
    };
    public final Runnable replayAction = () -> {
        System.out.println("Replay");
    };
    public final Runnable newLevelOneAction = () -> {
        System.out.println("New Level One");
    };
    public final Runnable newLevelTwoAction = () -> {
        System.out.println("New Level Two");
    };
    public final Runnable exitAction = () -> {
        System.out.println("Exit");
        closePhase.run();
        System.exit(0);
    };


    Base() {
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startPhase();
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closePhase.run();
            }
        });
    }

    public void movePlayer(Entity.Direction dir) {
        System.out.println("Move: " + dir);
    }

    public void runClosePhase() {
        closePhase.run();
        components.clear();
    }

    public void addComponent(JComponent jc) {
        components.add(jc);
    }

    private void startPhase() {
        PhasePanel menu = new PhasePanel(new MenuMainPanel(this),new MenuSidePanel());
        //Phase menu = new Phase(this);
        add(BorderLayout.CENTER, menu);
        components.add(menu);
        components.addAll(menu.getAllComponents());
    }

    public void levelPhase(boolean isLevelOne) {
        closePhase.run();
        components.clear();

        //set up the viewport and the timer
        Phase level;
        if (isLevelOne) {
            level = new Phase(this,
                    new Player(new Maze.Point(0, 0), Entity.Direction.Up));
        } else {
            level = new Phase(this,
                    new Player(new Maze.Point(0, 0), Entity.Direction.Up),
                    new Player(new Maze.Point(1, 1), Entity.Direction.Down));
        }
        Phase finalLevel = level;
        finalLevel.addKeyListener(new Controller(this));
        finalLevel.setFocusable(true);

        Timer timer = new Timer(34, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            //p.model().ping(); //TODO ping everything
            finalLevel.repaint(); //draws game
        });

        closePhase = () -> {
            timer.stop();
            remove(finalLevel);
        };

        add(BorderLayout.CENTER, finalLevel);//add the new phase viewport
        setPreferredSize(getSize());//to keep the current size
        pack();                     //after pack
        finalLevel.requestFocus();//need to be after pack
        timer.start();

        components.add(finalLevel);
        pack();
    }

    private void pausePhase() {

    }

    private void repeatPhase() {

    }
}
