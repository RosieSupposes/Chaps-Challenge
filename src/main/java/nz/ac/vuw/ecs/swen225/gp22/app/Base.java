package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Player;

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
    private final List<JComponent> components = new ArrayList<>();
    public final Runnable startAction = () -> levelPhase(true);
    private int timeMS = 0;
    private int timeSec = 0;
    private Timer timer = new Timer(20,null);

    public final Runnable loadAction = () -> {
        System.out.println("Load");
    };
    public final Runnable saveAction = () -> {
        System.out.println("Save");
    };
    public final Runnable replayAction = () -> {
        System.out.println("Replay");
        replayPhase();
    };
    public final Runnable newLevelOneAction = () -> {
        System.out.println("New Level One");
        levelPhase(true);
    };
    public final Runnable newLevelTwoAction = () -> {
        System.out.println("New Level Two");
        levelPhase(false);
    };
    public final Runnable exitAction = () -> {
        System.out.println("Exit");
        runClosePhase();
        System.exit(0);
    };

    public final Runnable pauseAction = () -> {
        System.out.println("Pause");
        pausePhase();
    };

    public final Runnable unPauseAction = () -> {
        System.out.println("Un Pause");
        unPause();
    };

    Base() {
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        startPhase();

        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                runClosePhase();
            }
        });
    }

    public void movePlayer(Entity.Direction dir) {
        System.out.println("Move: " + dir);
    }

    public void runClosePhase() {
        for (JComponent component : this.components) {
            remove(component);
        }
        components.clear();
        timer.stop();
    }

    public void addComponent(JComponent jc) {
        components.add(jc);
    }

    public void startPhase() {
        runClosePhase();
        setJMenuBar(null);

        PhasePanel menu = new PhasePanel(new MenuMainPanel(this),new MenuSidePanel());
        add(BorderLayout.CENTER, menu);
        components.add(menu);
        components.addAll(menu.getAllComponents());

        pack();
    }

    public void levelPhase(boolean isLevelOne) {
        runClosePhase();

        //set up the viewport and the timer
        PhasePanel level;
        if (isLevelOne) {
            JPanel game = new JPanel(); //TODO link to actual game window
            game.setBackground(Color.MAGENTA);

            JPanel side = new JPanel();
            side.setBackground(Color.CYAN);

            level = new PhasePanel(game, side);
        } else {
            JPanel game = new JPanel();
            game.setBackground(Color.ORANGE);

            JPanel side = new JPanel();
            side.setBackground(Color.PINK);

            level = new PhasePanel(game, side);
        }

        PhasePanel finalLevel = level;
        finalLevel.addKeyListener(new Controller(this));
        finalLevel.setFocusable(true);

        timer = new Timer(20, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            //p.model().ping(); //TODO ping everything
            finalLevel.repaint(); //draws game
            timeMS+=20;
            if(timeMS%1000 == 0){
                System.out.println(timeSec++);
            }
        });
        timer.start();

        add(BorderLayout.CENTER, finalLevel);//add the new phase viewport
        components.add(finalLevel);
        GameMenuBar gameMenuBar = new GameMenuBar(this);
        gameMenuBar.addGameButtons();
        gameMenuBar.addLoadButton();
        gameMenuBar.addExitButton();
        setJMenuBar(gameMenuBar);

        pack();                     //after pack
        finalLevel.requestFocus();  //need to be after pack
    }

    private void pausePhase() {
        //runClosePhase();
        timer.stop();
        //pack();
    }

    private void unPause(){
        timer.start();
    }

    private void replayPhase() {
        runClosePhase();

        Player playerWindow = new Player();
        add(BorderLayout.CENTER, playerWindow);//add the new phase viewport
        setPreferredSize(getSize());//to keep the current size
        pack();                     //after pack
        playerWindow.requestFocus();//need to be after pack

        components.add(playerWindow);
    }
}
