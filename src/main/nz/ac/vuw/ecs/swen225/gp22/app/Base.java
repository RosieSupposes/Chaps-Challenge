package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Load;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Save;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Player;
import nz.ac.vuw.ecs.swen225.gp22.recorder.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.GameDimensions;
import nz.ac.vuw.ecs.swen225.gp22.renderer.SidePanel;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Viewport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static nz.ac.vuw.ecs.swen225.gp22.domain.Entity.Action.Interaction.ActionType.*;

/**
 * Base is the base window that all actions occur on.
 *
 * @author Molly Henry, 300562038
 * @version 1.6
 */
public class Base extends JFrame {
    private final List<JComponent> components = new ArrayList<>();
    private int timeMS = 0;
    private int timeSec = 60;
    private int pingTime = 0;
    private Timer gameTimer = new Timer(20, null);
    private Recorder recorder;
    private GameMenuBar currentMenuBar;
    private JPanel currentPanel; //for setting keylistener on
    private GameDialog pauseDialog;
    private GameDialog saveDialog;
    private GameDialog gameOverDialog;
    private GameDialog gameWinDialog;

    /**
     * Begin program here. Run menu phase.
     */
    public Base() {
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuScreen();
        System.out.println(this.getSize());

        pauseDialog = new GameDialog(this, "Pause");
        saveDialog = new GameDialog(this, "Save");
        gameOverDialog = new GameDialog(this, "GameOver");
        gameWinDialog = new GameDialog(this, "GameCompleted");

        setVisible(true);
        setResizable(false);
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
        if (Load.previousGamePresent()) {
            int time = Load.previousGame();
            loadLevel(time, 1); //TODO Persistency should tell me which level is loaded?

            recorder = new Recorder(1);
            recorder.addAction(new MoveAction(Maze.player.getPos().x(), Maze.player.getPos().y(), Maze.player.getDir().toString()));

            //TODO when recorder has ability to start recording from middle of game, tell recorder
        } else {
            newGame(1);
        }
    }

    /**
     * pauses game
     */
    public void pause() {
        System.out.println("Pause");
        gameTimer.stop();
        if (currentMenuBar == null) {
            return;
        }
        currentMenuBar.setPause();

        changeKeyListener(new Controller(this, true));
        //TODO learn how to make pop up windows!
        pauseDialog.visibleFocus();
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
        pauseDialog.setVisible(false);
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

        add(BorderLayout.CENTER, playerWindow);
        setMinimumSize(new Dimension(GameDimensions.WINDOW_WIDTH, GameDimensions.WINDOW_HEIGHT + 150));
        pack();
        playerWindow.requestFocus(); //need to be after pack

        components.add(playerWindow);
    }

    /**
     * load a game from file
     */
    public void loadGame() {
        int time = Load.resumeGame();
        loadLevel(time, 1); //TODO ask persistency which level was loaded

        recorder = new Recorder(1);
        recorder.addAction(new MoveAction(Maze.player.getPos().x(), Maze.player.getPos().y(), Maze.player.getDir().toString()));
        //TODO when recorder has ability to start recording from middle of game, tell recorder

        System.out.println("Load");
    }

    public void newGame(int lvl) {
        System.out.println("New level" + lvl);
        Load.loadLevel(1); //TODO change 1 to lvl when level2.xml exists
        loadLevel(60, 1);
        recorder = new Recorder(lvl);
        recorder.addAction(new MoveAction(Maze.player.getPos().x(), Maze.player.getPos().y(), Maze.player.getDir().toString()));
    }

    /**
     * save the current game
     */
    public void saveGame() {
        Save.saveGame(timeSec); //TODO persistency should choose name, App should pass current time
        System.out.println("Save");
        saveDialog.visibleFocus();
    }

    public void resetFocus() {
        currentPanel.requestFocus();
    }

    public void playerDied() {
        System.out.println("Level lost");
        recorder.save();
        gameOverDialog.visibleFocus();
        gameTimer.stop();
    }

    public void playerWon() {
        System.out.println("Level won");
        recorder.save();
        gameWinDialog.visibleFocus();
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
        Entity.Action action = null;

        try {
            action = Maze.player.moveAndTurn(dir);
        } catch (IllegalArgumentException e) {
            Maze.player.setDir(dir);
        }

        String direction = dir.toString();
        int x = Maze.player.getPos().x();
        int y = Maze.player.getPos().y();

        if (action != null) {
            Entity.Action.Interaction interaction = action.interaction();
            if (interaction.type().equals(UnlockDoor) || interaction.type().equals(UnlockExit)) {
                recorder.addAction(new DoorAction(x, y, interaction.type().toString(), interaction.color().toString()));
            } else if (interaction.type().equals(PickupKey) || interaction.type().equals(PickupTreasure)) {
                recorder.addAction(new CollectAction(x, y, interaction.type().toString(), interaction.color().toString()));
            }
        }

        recorder.addAction(new MoveAction(x, y, direction));
    }

    /**
     * For undoing or redoing a move from Recorder class.
     * Sets player position and the direction they face
     *
     * @param x         x position of player
     * @param y         y position of player
     * @param direction action that occurred
     */
    public static void setMove(int x, int y, String direction) {
        Maze.Point pos = new Maze.Point(x, y);
        Maze.player.setPos(pos);
        switch (direction) {
            case "Left" -> Maze.player.setDir(Entity.Direction.Left);
            case "Right" -> Maze.player.setDir(Entity.Direction.Right);
            case "Up" -> Maze.player.setDir(Entity.Direction.Up);
            case "Down" -> Maze.player.setDir(Entity.Direction.Down);
        }
    }

    /**
     * for undoing and redoing actions from Recorder class.
     * sets tiles.
     *
     * @param x      x coord of tile
     * @param y      y coord of tile
     * @param action Open, Close, Pick-up, Drop
     * @param object Door, Exit, Key, Treasure
     * @param color  Red, Green, Blue, Yellow
     */
    public static void setAction(int x, int y, String action, String object, String color) {
        Maze.Point pos = new Maze.Point(x, y);
        switch (action) {
            case "Open", "Pick-up" -> Maze.setTile(pos, new Ground(pos));
            case "Close" -> {
                switch (object) {
                    case "UnlockDoor" -> {
                        switch (color) {
                            case "Red" -> Maze.setTile(pos, new LockedDoor(pos, ColorableTile.Color.Red));
                            case "Green" -> Maze.setTile(pos, new LockedDoor(pos, ColorableTile.Color.Green));
                            case "Blue" -> Maze.setTile(pos, new LockedDoor(pos, ColorableTile.Color.Blue));
                            case "Yellow" -> Maze.setTile(pos, new LockedDoor(pos, ColorableTile.Color.Yellow));
                        }
                    }
                    case "UnlockExit" -> Maze.setTile(pos, new LockedExit(pos));
                }
            }
            case "Drop" -> {
                switch (object) {
                    case "PickupKey" -> {
                        switch (color) {
                            case "Red" -> Maze.setTile(pos, new Key(pos, ColorableTile.Color.Red));
                            case "Green" -> Maze.setTile(pos, new Key(pos, ColorableTile.Color.Green));
                            case "Blue" -> Maze.setTile(pos, new Key(pos, ColorableTile.Color.Blue));
                            case "Yellow" -> Maze.setTile(pos, new Key(pos, ColorableTile.Color.Yellow));
                        }
                    }
                    case "PickupTreasure" -> Maze.setTile(pos, new Treasure(pos));
                }
            }
        }

    }

    /**
     * gets the game window.
     *
     * @return game window
     */
    public JPanel getGameWindow() {
        assert Maze.player != null;
        JPanel game = new Viewport();
        JPanel side = new JPanel(); //TODO link to renderer side panel
        side.setBackground(Main.LIGHT_YELLOW_COLOR);
        return new PhasePanel(game, side);
    }

    /**
     * Run and display menu
     */
    public void menuScreen() {
        runClosePhase();
        setJMenuBar(null);

//        ImagePanel testGame = new ImagePanel("TEST_game",Main.GAME_SIZE,new Dimension(0,0));
//        ImagePanel testSide = new ImagePanel("TEST_side",Main.SIDE_SIZE,new Dimension(0,0));
//        PhasePanel menu = new PhasePanel(testGame,testSide);

        ImagePanel imagePanel = new ImagePanel("MenuSidePanel", GameDimensions.SIDE_SIZE, 0.8);
        PhasePanel menu = new PhasePanel(new MenuMainPanel(this), imagePanel);

        currentPanel = menu;
        changeKeyListener(new Controller(this));

        add(BorderLayout.CENTER, menu);
        components.add(menu);
        components.addAll(menu.getAllComponents());

        setMinimumSize(GameDimensions.WINDOW_SIZE);
        pack();
        menu.requestFocus();
    }

    /**
     * Create, run and draw new level
     *
     * @param seconds number of seconds into level
     */
    public void loadLevel(int seconds, int lvl) {
        assert Maze.player != null;

        runClosePhase();

        JPanel game = new Viewport();
        SidePanel side = new SidePanel(timeSec, lvl);
        side.setTime(timeSec);
        final JPanel level = new PhasePanel(game, side);

        timeSec = seconds;
        timeMS = 0;
        pingTime = 0;
        gameTimer = new Timer(20, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            level.repaint(); //draws game
            timeMS += 20;
            if (timeMS % 1000 == 0) {
                //TODO tell viewport current time

                timeSec--;
                side.setTime(timeSec);
            }

            for (Entity entity : Maze.entities) {
                if (entity instanceof EnemyEntity<?> e) {
                    if (pingTime % e.getSpeed() == 0) {
                        e.ping();
                    }
                }
            }
            pingTime++;

            //TODO uncomment when ready for game ending/level switching
            if (timeSec <= 0) {
                playerDied();
            }
//            else if (Maze.gameComplete()) {
//                playerWon();
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
     * Removes current key listener, adds new one.
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
