package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.renderer.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Creates Pop-up boxes for our game. There are four types of pop-ups.
 * Pause, GameOver, GameComplete, Save
 *
 * @author Molly Henry, 300562038
 * @version 1.2
 */
public class GameDialog extends JDialog {

    private static final Dimension BUTTON_SIZE = new Dimension(180, 30);
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    private Base base;
    private Timer timer = new Timer(20, e -> {
    });

    public enum PopUp{
        Pause,
        GameOver,
        GameCompleted,
        Save;
    }

    /**
     * New Dialog window, can create four types based on type passed in
     *
     * @param base current base
     * @param type Pause, GameOver, GameComplete, Save
     */
    public GameDialog(Base base, PopUp type) {
        this.base = base;
        setUpWindow();
        List<JComponent> components = List.of();
        switch (type) {
            case Pause -> components = setUpPause();
            case GameOver -> components = setUpGameOver();
            case GameCompleted -> components = setUpGameCompleted();
            case Save -> components = setUpSave();
        }
        setUpComponents(components);
    }

    /**
     * For error pop-ups
     *
     * @param base
     * @param message
     */
    public GameDialog(Base base, String message){
        this.base = base;
        setUpWindow();
        setUpComponents(setUpError(message));
    }

    private void setUpWindow(){
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(GameConstants.LIGHT_YELLOW_COLOR);
        this.setResizable(false);
    }

    /**
     * add components to dialog.
     */
    private void setUpComponents(List<JComponent> components){
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        for (JComponent component : components) {
            this.add(component, c);
            c.gridy++;
        }
    }

    /**
     * Set size and position of pop-up based on fields and current base coordinates
     */
    public void makeBounds() {
        this.setBounds(base.getX() + xOffset, base.getY() + yOffset, width, height);
    }

    /**
     * Set pop-up as visible, in focus and reset it's bounds
     * Start timer (only Save pop-up has timer).
     */
    public void visibleFocus() {
        this.timer.start();
        this.makeBounds();
        this.setVisible(true);
        this.requestFocus();
    }

    private int timeMS = 0;

    /**
     * Creates "saved game" pop-up
     *
     * @return list of components to be added to box
     */
    private List<JComponent> setUpSave() {
        width = 100;
        height = 70;
        xOffset = GameConstants.GAME_WINDOW_SIZE - width / 2;
        yOffset = 100;
        this.makeBounds();

        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game Saved");
        info.setFont(new Font("Arial", Font.BOLD, 14));
        info.setForeground(GameConstants.TEXT_COLOR);

        timeMS = 0;
        timer = new Timer(250, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            timeMS += 1;
            if (timeMS >= 3) {
                this.setVisible(false);
                timer.stop();
                timeMS = 0;
            }
        });
        return List.of(info);
    }

    /**
     * Sets up "game won" pop-up
     *
     * @return list of components to be added to window
     */
    private List<JComponent> setUpGameCompleted() {
        width = 230;
        height = 400;
        xOffset = GameConstants.WINDOW_WIDTH / 2 - width / 2;
        yOffset = GameConstants.WINDOW_HEIGHT / 4;
        this.makeBounds();

        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("You Win!");
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setForeground(GameConstants.TEXT_COLOR);

        return List.of(info, loadButton(), newOneButton(), newTwoButton(), exitButton());
    }

    /**
     * sets up "game over" pop-up
     *
     * @return list of components to be added to window
     */
    private List<JComponent> setUpGameOver() {
        width = 230;
        height = 300;
        xOffset = GameConstants.WINDOW_WIDTH / 2 - width / 2;
        yOffset = GameConstants.WINDOW_HEIGHT / 4;
        this.makeBounds();

        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game is Over");
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setForeground(GameConstants.TEXT_COLOR);

        return List.of(info, loadButton(), newOneButton(), newTwoButton(), exitButton());
    }

    /**
     * sets up "pause" pop up
     *
     * @return list of components to be added to pop-up
     */
    private List<JComponent> setUpPause() {
        width = 230;
        height = 400;
        xOffset = GameConstants.WINDOW_WIDTH / 2 - width / 2;
        yOffset = GameConstants.WINDOW_HEIGHT / 4;
        this.makeBounds();

        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game is Paused");
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setForeground(GameConstants.TEXT_COLOR);

        if (!this.isFocusOwner()) {
            base.unPause();
            this.setVisible(false);
        }

        return List.of(info, playButton(), loadButton(), newOneButton(), newTwoButton(), saveButton(), exitButton());
    }

    /**
     * set up error pop up
     *
     * @return list of components for to be added to pop-up
     */
    private List<JComponent> setUpError(String message) {
        width = 230;
        height = 130;
        xOffset = GameConstants.GAME_WINDOW_SIZE - width / 2;
        yOffset = 100;
        this.makeBounds();

        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel(message);

        GameButton button = new GameButton("OK", BUTTON_SIZE, e -> {
            this.dispose();
        });

        return List.of(info, button);
    }

    /**
     * Makes play button. With action to set pop-up invisible again.
     *
     * @return play button
     */
    private GameButton playButton() {
        Runnable action = () -> {
            base.unPause();
            this.setVisible(false);
        };
        GameButton button = new GameButton("Play Game", BUTTON_SIZE, e -> action.run());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                action.run();
            }
        });
        return button;
    }

    /**
     * Makes load level button. Closes pop-up when clicked
     *
     * @return load button
     */
    private GameButton loadButton() {
        return new GameButton("Load Previous Game", BUTTON_SIZE, e -> {
            base.loadGame();
            this.setVisible(false);
        });
    }

    /**
     * Makes load level one button. Closes pop-up when clicked
     *
     * @return load level one button
     */
    private GameButton newOneButton() {
        return new GameButton("Load Level One", BUTTON_SIZE, e -> {
            base.newGame(1);
            this.setVisible(false);
        });
    }

    /**
     * Makes load level two button. Closes pop-up when clicked
     *
     * @return load level two button
     */
    private GameButton newTwoButton() {
        return new GameButton("Load Level Two", BUTTON_SIZE, e -> {
            base.newGame(2);
            this.setVisible(false);
        });
    }

    /**
     * Makes save game button
     *
     * @return save button
     */
    private GameButton saveButton() {
        return new GameButton("Save and Exit", BUTTON_SIZE, e -> {
            base.saveExit();
        });
    }

    /**
     * Makes exit game button. Closes pop-up when clicked
     *
     * @return exit button
     */
    private GameButton exitButton() {
        return new GameButton("Exit Game", BUTTON_SIZE, e -> {
            base.exitGame();
            this.setVisible(false);
        });
    }
}
