package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Load;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Save;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Action;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Player;
import nz.ac.vuw.ecs.swen225.gp22.recorder.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.GameConstants;
import nz.ac.vuw.ecs.swen225.gp22.renderer.SidePanel;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Viewport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    private static int timeSec = 60;
    private final int delay = 20;
    private Timer gameTimer = new Timer(20, null);
    private Recorder recorder;
    private GameMenuBar currentMenuBar;
    private JPanel currentPanel; //for setting keylistener on
    private GameDialog pauseDialog;
    private GameDialog saveDialog;
    private GameDialog gameOverDialog;
    private GameDialog gameWinDialog;

    private static int level = 1;

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

    public static void setLevel(int lvl) {
        level = lvl;
    }

    public static void setTime(int t) {
        timeSec = t;
    }

    public static int getLevel() {
        return level;
    }

    public static int getTime() {
        return timeSec;
    }

    /**
     * When you click start button, check for last save file
     * and run the level
     */
    public void startGame() {
        if (Load.previousGamePresent()) {
            Load.previousGame();
            loadLevel();

            recorder = new Recorder(level, timeMS, this);
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
    }

    /**
     * Creates and runs replayer window
     */
    public void replayPhase() {
        System.out.println("Replay");
        runClosePhase();

        Player playerWindow = new Player(this);

        add(BorderLayout.CENTER, playerWindow);
        setMinimumSize(new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT + 150));
        pack();
        playerWindow.requestFocus(); //need to be after pack

        components.add(playerWindow);
    }

    /**
     * load a game from file
     */
    public void loadGame() {
        Load.resumeGame();
        loadLevel();

        recorder = new Recorder(level, timeMS, this);
        //TODO when recorder has ability to start recording from middle of game, tell recorder

        System.out.println("Load");
    }

    public void newGame(int lvl) {
        System.out.println("New level" + lvl);
        level = lvl;
        Load.loadLevel(lvl);
        loadLevel();
        recorder = new Recorder(lvl, timeMS, this);
    }

    /**
     * save the current game
     */
    public void saveGame() {
        Save.saveGame();
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
            Maze.player.moveAndTurn(dir);
        } catch (IllegalArgumentException e) {
            Maze.player.setDir(dir);
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
        JPanel side = new SidePanel(timeSec, level);
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

        ImagePanel imagePanel = new ImagePanel("MenuSidePanel", GameConstants.SIDE_SIZE, 0.8);
        PhasePanel menu = new PhasePanel(new MenuMainPanel(this), imagePanel);

        currentPanel = menu;
        changeKeyListener(new Controller(this));

        add(BorderLayout.CENTER, menu);
        components.add(menu);
        components.addAll(menu.getAllComponents());

        setMinimumSize(GameConstants.WINDOW_SIZE);
        pack();
        menu.requestFocus();
    }

    public void undo(List<Action> actions) {
        Maze.undo(actions.stream().map(this::getAction).toList());
    }

    public void apply(List<Action> actions) {
        Maze.apply(actions.stream().map(this::getAction).toList());
    }

    public Entity.Action getAction(Action action) {
        int entity = action.entityHash();
        Entity.Action.Interaction.ActionType actionType = getActionType(action.actionType());
        Entity.Direction oldDir = getDirection(action.prevDir());
        Entity.Direction newDir = getDirection(action.currDir());
        ColorableTile.Color color = getColor(action.color());
        Maze.Point point = new Maze.Point(action.x(), action.y());
        Entity.Action.Interaction interaction = new Entity.Action.Interaction(actionType, color);
        return new Entity.Action(entity, point, oldDir, newDir, interaction);
    }

    private Entity.Direction getDirection(String dir) {
        return switch (dir) {
            case "Up" -> Entity.Direction.Up;
            case "Down" -> Entity.Direction.Down;
            case "Left" -> Entity.Direction.Left;
            case "Right" -> Entity.Direction.Right;
            default -> throw new IllegalStateException("Unexpected direction: " + dir);
        };
    }

    private ColorableTile.Color getColor(String color) {
        return switch (color) {
            case "Red" -> ColorableTile.Color.Red;
            case "Yellow" -> ColorableTile.Color.Yellow;
            case "Green" -> ColorableTile.Color.Green;
            case "Blue" -> ColorableTile.Color.Blue;
            default -> throw new IllegalStateException("Unexpected Color: " + color);
        };
    }

    private Entity.Action.Interaction.ActionType getActionType(String actionType) {
        return switch (actionType) {
            case "Pinged" -> Pinged;
            case "PickupKey" -> PickupKey;
            case "PickupTreasure" -> PickupTreasure;
            case "UnlockDoor" -> UnlockDoor;
            case "UnlockExit" -> UnlockExit;
            case "None" -> None;
            default -> throw new IllegalStateException("Unexpected action type: " + actionType);
        };
    }


    /**
     * Create, run and draw new level
     */
    public void loadLevel() {
        assert Maze.player != null;

        runClosePhase();

        JPanel game = new Viewport();
        SidePanel side = new SidePanel(timeSec, level);
        side.setTime(timeSec);
        final JPanel level = new PhasePanel(game, side);

        timeMS = 0;
        gameTimer = new Timer(delay, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            level.repaint(); //draws game
            timeMS += delay;
            if (timeMS % 1000 == 0) {
                timeSec--;
                side.setTime(timeSec);
            }

            Maze.entities.stream()
                    .filter(e -> e instanceof EnemyEntity<?> ee && timeMS % ee.getSpeed() == 0)
                    .forEach(Entity::ping);

            transformActions(Maze.getChangeMap()).forEach(a -> recorder.addAction(a, timeMS));

            if (timeSec <= 0 || Maze.isGameLost()) {
                playerDied();
            }
//            else if (Maze.gameComplete()) { //TODO uncomment when ready for game ending/level switching
//                playerWon();
//           }
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

    private List<Action> transformActions(List<Entity.Action> actions) {
        List<Action> actionRecords = new ArrayList<>();
        for (Entity.Action action : actions) {
            String actionType = action.interaction().type().toString();
            String oldDir = action.oldDir().toString();
            String newDir = action.newDir().toString();
            String color = action.interaction().color().toString();
            int entityHash = action.hashcode();
            int vx = action.moveVector().x();
            int vy = action.moveVector().y();
            actionRecords.add(new Action(entityHash, actionType, vx, vy, oldDir, newDir, color));
        }
        return actionRecords;
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
