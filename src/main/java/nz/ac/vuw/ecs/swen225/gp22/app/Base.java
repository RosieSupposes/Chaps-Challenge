package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Base is the base window that all actions occur on.
 *
 * @author Molly Henry, 300562038
 * @version 1.5
 */
public class Base extends JFrame {
    private final List<JComponent> components = new ArrayList<>();
    private int timeMS = 0;
    private int timeSec = 0;
    private Timer gameTimer = new Timer(20, null);
    GameMenuBar currentMenuBar;

    JPanel currentPanel; //for setting keylistener on

    /**
     * Begin program here. Run menu phase.
     */
    public Base() {
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        menuScreen();

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

    /**
     * remove all components in window, clears list and stops timer
     */
    public void runClosePhase() {
        for (JComponent component : this.components) {
            remove(component);
        }
        components.clear();
        gameTimer.stop();
    }

    /**
     * When you click start button, check for last save file
     * and run the level
     */
    public void startGame() {
        //TODO check for last save file - Persistency

        if (false) { //if file exists
            //Load save file
        } else {
            newLevelPhase(true); //new level one
        }
    }

    /**
     * pauses game
     */
    public void pause() {
        //runClosePhase();
        System.out.println("Pause");
        gameTimer.stop();
        if (currentMenuBar == null) {
            return;
        }
        currentMenuBar.setPause();

        changeKeyListener(new Controller(this, true));
        //pack();
    }

    /**
     * un-pauses game
     */
    public void unPause() {
        System.out.println("Un Pause");
        gameTimer.start();
        if (currentMenuBar == null) {
            return;
        }
        currentMenuBar.setUnPause();

        changeKeyListener(new Controller(this, false));
    }

    /**
     * Creates and runs replayer window
     */
    public void replayPhase() {
        System.out.println("Replay");
        runClosePhase();

        Player playerWindow = new Player(this);
        add(BorderLayout.CENTER, playerWindow);//add the new phase viewport
        setPreferredSize(getSize());//to keep the current size
        pack();                     //after pack
        playerWindow.requestFocus();//need to be after pack

        components.add(playerWindow);
    }

    /**
     * load a game from file
     */
    public void loadGame() {
        //TODO ask persistency
        System.out.println("Load");
    }

    /**
     * save the current game
     */
    public void saveGame() {
        //TODO tell persistency
        System.out.println("Save");
    }

    /**
     * exit the game
     */
    public void exitGame() {
        System.out.println("Exit");
        runClosePhase();
        System.exit(0);
    }

    /**
     * When key is pressed, move player and tell recorder
     *
     * @param dir direction player moves
     */
    public void movePlayer(Entity.Direction dir) {
        System.out.println("Move: " + dir);
        try {
            //Maze.player.move(dir); TODO when player isn't null, uncomment this line
        } catch (IllegalArgumentException e) {
            //TODO make player face dir in Maze
        }
        //TODO tell recorder, should ask domain if item picked up/door interacted with?
    }

    /**
     * Run and display menu
     */
    public void menuScreen() {
        runClosePhase();
        setJMenuBar(null);

        PhasePanel menu = new PhasePanel(new MenuMainPanel(this), new MenuSidePanel());

        currentPanel = menu;
        changeKeyListener(new Controller(this));

        add(BorderLayout.CENTER, menu);
        components.add(menu);
        components.addAll(menu.getAllComponents());

        pack();
    }

    /**
     * Create, run and draw new level
     *
     * @param isLevelOne true for making level one
     */
    public void newLevelPhase(boolean isLevelOne) {
        runClosePhase();

        //set up the viewport and the timer
        PhasePanel level;
        if (isLevelOne) {
            System.out.println("New Level One");
            JPanel game = new JPanel(); //TODO link to actual game window
            game.setBackground(Color.MAGENTA);

            JPanel side = new JPanel();
            side.setBackground(Color.CYAN);

            level = new PhasePanel(game, side);
        } else {
            System.out.println("New Level Two");
            JPanel game = new JPanel();
            game.setBackground(Color.ORANGE);

            JPanel side = new JPanel();
            side.setBackground(Color.PINK);

            level = new PhasePanel(game, side);
        }

        PhasePanel finalLevel = level;

        currentPanel = finalLevel;
        changeKeyListener(new Controller(this, false));

        timeSec = 0;
        gameTimer = new Timer(20, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            //p.model().ping(); //TODO ping everything in domain
            finalLevel.repaint(); //draws game
            timeMS += 20;
            if (timeMS % 1000 == 0) {
                //TODO tell viewport current time
                System.out.println(timeSec++);
            }
        });
        gameTimer.start();

        add(BorderLayout.CENTER, finalLevel);//add the new phase viewport
        components.add(finalLevel);
        currentMenuBar = new GameMenuBar(this);
        currentMenuBar.addGameButtons();
        currentMenuBar.addLoadButton();
        currentMenuBar.addExitButton();
        setJMenuBar(currentMenuBar);

        pack();                     //after pack
        finalLevel.requestFocus();  //need to be after pack
    }

    public void changeKeyListener(KeyListener keyListener) {
        if (currentPanel.getKeyListeners().length > 0) {
            currentPanel.removeKeyListener(currentPanel.getKeyListeners()[0]);
        }
        currentPanel.addKeyListener(keyListener);
        currentPanel.setFocusable(true);
    }
}
