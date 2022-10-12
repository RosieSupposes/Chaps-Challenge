package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import nz.ac.vuw.ecs.swen225.gp22.app.GameButton;
import nz.ac.vuw.ecs.swen225.gp22.app.GameDialog;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Load;
import org.dom4j.DocumentException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.List;

/**
 * The player for the recorder. Used to play back recorded actions.
 *
 * @author Christopher Sa, 300570735
 * @version 1.13
 */
public class Player extends JPanel {
    private static final int MAX_SPEED = 5;
    private final Base base;
    private List<GameState> gameStates;
    private JSlider scrubber;
    private int currentAction = 0;
    private boolean isPlaying = false;
    private boolean isRewinding = false;
    private int speed = 1;
    private GameButton playPause;
    private JPanel gamePanel;
    private GameButton speedBtn;

    private static final Dimension BUTTON_DIM = new Dimension(50, 30);
    private static final Dimension LONG_BTN = new Dimension(BUTTON_DIM.width * 2, BUTTON_DIM.height);
    private static final Dimension SLIDER_DIM = new Dimension(700, 30);

    /**
     * Create a new player.
     *
     * @param base The base jFrame.
     */
    public Player(Base base) {
        assert SwingUtilities.isEventDispatchThread();
        this.base = base;
        load();
        setup();
        setVisible(true);
    }

    /**
     * Set up the player.
     */
    private void setup() {
        gamePanel = base.getGameWindow();

        JButton stepBack = new GameButton("", BUTTON_DIM, e -> {
            scrubber.setValue(scrubber.getValue() - 1);
            isPlaying = false;
            isRewinding = false;
            gamePanel.repaint();
        }, "stepback");

        scrubber = gameStates == null ? new JSlider() : new JSlider(0, gameStates.size() - 1);
        scrubber.setPreferredSize(SLIDER_DIM);
        scrubber.setValue(0);
        scrubber.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                scrub(source.getValue());
            }
        });
        scrubber.setBackground(Color.MAGENTA);
        scrubber.setUI(new BasicSliderUI(scrubber) {
            @Override
            public void paintThumb(Graphics g) {
                g.setColor(Color.MAGENTA);
                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.height - 2, thumbRect.height - 2);
                g.setColor(Color.GRAY);
                g.drawOval(thumbRect.x, thumbRect.y, thumbRect.height - 2, thumbRect.height - 2);
            }
        });

        JButton home = new GameButton("", BUTTON_DIM, e -> {
            base.menuScreen();
            isRewinding = false;
            isPlaying = false;
        }, "home");

        JButton load = new GameButton("Load", new Dimension(100, 30), e -> {
            try { load(); } catch (RuntimeException ignored) {} // If the user cancels the load, ignore it.
            if (gameStates != null) scrubber.setMaximum(gameStates.size());
        });

        JButton stepForward = new GameButton("", BUTTON_DIM, e -> {
            scrubber.setValue(scrubber.getValue() + 1);
            isPlaying = false;
            isRewinding = false;
            gamePanel.repaint();
        }, "stepforward");

        JButton rewind = new GameButton("", LONG_BTN, e -> {
            playPause.changeIcon("pause");
            rewind();
        }, "rewind");

        playPause = new GameButton("", BUTTON_DIM, e -> {
            updatePlayBtn();
        }, "play");


        speedBtn = new GameButton("", LONG_BTN, e -> {
            updateSpeed();
        }, "speed" + this.speed);


        setLayout(new FlowLayout());
        add(gamePanel);
        add(stepBack);
        add(scrubber);
        add(stepForward);
        add(home);
        add(load);
        add(rewind);
        add(playPause);
        add(speedBtn);

        setPreferredSize(new Dimension(800, 520));
        setBackground(Color.MAGENTA);
    }

    /**
     * Update the speed of the replay.
     */
    private void updateSpeed() {
        speed = speed == MAX_SPEED ? 1 : speed + 1;
        System.out.println(this.speed);
        speedBtn.changeIcon("speed" + this.speed);
    }

    /**
     * Update the play button icon.
     */
    private void updatePlayBtn() {
        if (isPlaying || isRewinding) {
            isPlaying = false;
            isRewinding = false;
            playPause.changeIcon("play");
        } else {
            isPlaying = true;
            playPause.changeIcon("pause");
            play();
        }
    }

    /**
     * Load a file.
     */
    private void load() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "/resources/recordings");
        fileChooser.setDialogTitle("Select a recording to play");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Replay File (xml)", "xml");
        fileChooser.setFileFilter(filter);
        fileChooser.showOpenDialog(this);

        // Only load if a file was selected
        if (fileChooser.getSelectedFile() != null) {
            try {
                Parser parser = new Parser(fileChooser.getSelectedFile());
                gameStates = parser.getActions();
                Load.loadLevel(parser.getLevel());
                if (scrubber != null) {
                    scrubber.setMaximum(gameStates.size() - 1);
                    currentAction = 0;
                    scrubber.setValue(0);
                    gamePanel.repaint();
                }
            } catch (DocumentException e) {
                new GameDialog(base, "Error: Invalid file.").visibleFocus();
                throw new IllegalArgumentException("Invalid file");
            }

            return;
        }
        throw new RuntimeException("No file selected");
    }

    /**
     * Scrub to a position.
     *
     * @param position the position to scrub to
     */
    private void scrub(int position) {
        if (gameStates == null) return;
        if (position > currentAction) {
            // scrub forward
            for (int i = currentAction; i < position; i++) {
                gameStates.get(i).apply(base);
                gamePanel.repaint();
            }
        } else if (position < currentAction) {
            // scrub backward
            for (int i = currentAction; i > position; i--) {
                gameStates.get(i).undo(base);
                gamePanel.repaint();
            }
        }
        currentAction = position < gameStates.size() ? position : gameStates.size() - 1;
    }

    /**
     * Rewind.
     */
    private void rewind() {
        isRewinding = true;
        isPlaying = false;
        new Thread(() -> {
            for (int i = currentAction; i > 0; i--) {
                if (!isPlaying && !isRewinding) break;
                gameStates.get(i).undo(base);
                gamePanel.repaint();
                if (progress(i, isRewinding)) break;
            }
            isRewinding = false;
            playPause.changeIcon("play");
        }).start();
    }

    /**
     * Play the recording.
     */
    private void play() {
        isPlaying = true;
        isRewinding = false;
        new Thread(() -> {
            for (int i = currentAction; i < gameStates.size(); i++) {
                if (!isPlaying && !isRewinding) break;
                gameStates.get(i).apply(base);
                gamePanel.repaint();
                if (progress(i, isPlaying)) break;
            }
            isPlaying = false;
            playPause.changeIcon("play");
        }).start();
    }

    /**
     * Used for playing and rewinding.
     *
     * @param i The current action.
     * @param isProgressing If the player is progressing.
     * @return true if the player should stop progressing.
     */
    private boolean progress(int i, boolean isProgressing) {
        currentAction = i < gameStates.size() ? i : gameStates.size() - 1;
        scrubber.setValue(i);
        try {
            Thread.sleep(1000 / speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return !isProgressing;
    }
}