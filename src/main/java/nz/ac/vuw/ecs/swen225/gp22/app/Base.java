package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Load;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Save;
import nz.ac.vuw.ecs.swen225.gp22.recorder.MoveAction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Player;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Viewport;

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
 * @version 1.6
 */
public class Base extends JFrame {
    private final List<JComponent> components = new ArrayList<>();
    private int timeMS = 0;
    private int timeSec = 0;
    private Timer gameTimer = new Timer(20, null);
    private Recorder recorder;
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
            //Load.defaultSave(); //TODO make persistency run default save
        } else {
            newGame(1);
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
        //TODO learn how to make pop up windows!
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
        //TODO close pause popup window
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
        Load.resumeGame(); //TODO ask persistency for time of loaded game
        loadLevel(0, 0);

        //TODO when recorder has ability to start recording from middle of game, tell recorder

        System.out.println("Load");
    }

    public void newGame(int lvl) {
        System.out.println("New level" + lvl);
        Load.loadLevel("level" + 1); //TODO change 1 to lvl when level2.xml exists
        loadLevel(0, 0);
        recorder = new Recorder(lvl);
    }

    /**
     * save the current game
     */
    public void saveGame() {
        Save.saveGame("test-save-file"); //TODO persistency should choose name, App should pass current time
        System.out.println("Save");
    }

    public void finishLevel() {
        System.out.println("Level finished");
        recorder.save();

        //TODO make pop up
        // - if level one say "congrats" with "home", "exit", "replay", "next level", "save" buttons
        // - if final level say "Congrats!" with "home", "exit", "replay" buttons

        changeKeyListener(new Controller(this)); //switch control set back to default controls
        gameTimer.stop();
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
            Maze.player.move(dir);
            recorder.addMove(new MoveAction(dir.name(), 1));
        } catch (IllegalArgumentException e) {
            Maze.player.setDir(dir);
            //recorder.addTurn(new TurnAction(dir)); ? //TODO save turning action
        }
        //TODO ask domain if item picked up/door interacted with?
        //recorder.addCollect(new CollectAction("definitely a key"));
    }

    /**
     * Undo move from Recorder class.
     *
     * @param action action that occurred
     */
    public void undoMove(String action) {
        switch (action) {
            case "Move":
                //Maze.player.undoMove(oldDir);
                break;
            case "Turn":
                //Maze.player.setDir(dir);
                break;
            case "Collect":
                //Maze.player.dropItem(item);
                break;
        }
        //TODO tell Domain to move player backwards
        //TODO what about collect actions?
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
     * @param seconds      number of seconds into level
     * @param milliseconds number milliseconds into level
     */
    public void loadLevel(int seconds, int milliseconds) {
        runClosePhase();

        JPanel game = new Viewport();

        JPanel side = new JPanel(); //TODO link to renderer side panel
        side.setBackground(Color.CYAN);
        JLabel timeLabel = new JLabel("Time: 0");
        side.add(timeLabel);

        final PhasePanel level = new PhasePanel(game, side);
        timeSec = seconds;
        timeMS = milliseconds;
        gameTimer = new Timer(20, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            level.repaint(); //draws game
            timeMS += 20;
            if (timeMS % 1000 == 0) {
                //TODO tell viewport current time
                //System.out.println(timeSec++);
                timeLabel.setText("Time: " + timeSec);
            }

            //TODO uncomment when ready for game ending/level switching
//            if(timeSec >=60 || Maze.gameComplete()){
//                finishLevel();
//            }
        });
        gameTimer.start();

        currentPanel = level;
        changeKeyListener(new Controller(this, false));

        add(BorderLayout.CENTER, level);
        components.add(level);
        currentMenuBar = new GameMenuBar(this);
        currentMenuBar.addGameButtons();
        currentMenuBar.addLoadButton();
        currentMenuBar.addExitButton();
        setJMenuBar(currentMenuBar);


        pack();
        level.requestFocus();  //need to be after pack
    }

    /**
     * Removes current key listner, adds new one.
     * Use to ensure there is only one key listener being
     * used at any given time
     *
     * @param keyListener new keylistener to use
     */
    public void changeKeyListener(KeyListener keyListener) {
        if (currentPanel.getKeyListeners().length > 0) {
            currentPanel.removeKeyListener(currentPanel.getKeyListeners()[0]);
        }
        currentPanel.addKeyListener(keyListener);
        currentPanel.setFocusable(true);
    }
}
