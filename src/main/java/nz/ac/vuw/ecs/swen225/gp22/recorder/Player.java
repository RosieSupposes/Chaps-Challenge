package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.List;

/**
 * The player for the recorder. Used to play back recorded actions.
 *
 * @author Christopher Sa, 300570735
 * @version 1.9
 */
public class Player extends JPanel {
  private final Base base;
  private List<Action> actions;
  private JSlider scrubber;
  private boolean isPlaying = false;
  private boolean isRewinding = false;
  private int speed = 1;

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
    JPanel gamePanel = new JPanel();
    gamePanel.setPreferredSize(new Dimension(800, 400));
    gamePanel.setBackground(Color.BLACK);

    JButton stepBack = new PlaybackButton("Step Back", () -> scrubber.setValue(scrubber.getValue() - 1));

    scrubber = actions == null ? new JSlider() : new JSlider(0, actions.size() - 1);
    scrubber.setPreferredSize(new Dimension(550, 25));
    scrubber.setValue(0);
    scrubber.addChangeListener(e -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        scrub(source.getValue());
      }
    });

    JButton home = new PlaybackButton("Home", base::menuScreen);

    JButton load = new PlaybackButton("Load",  ()->{
      load();
      if (actions != null) scrubber.setMaximum(actions.size());
    });

    JButton stepForward = new PlaybackButton("Step Forward", () -> scrubber.setValue(scrubber.getValue() + 1));

    JButton rewind = new PlaybackButton("Rewind", this::rewind);
    JButton play = new PlaybackButton("Play", this::play);
    JButton pause = new PlaybackButton("Pause", () -> {
      isPlaying = false;
      isRewinding = false;
    });


    JPanel speedPanel = new JPanel() {
      {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(75, 25));

        JLabel speedLabel = new JLabel("Speed");

        JSpinner speed = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        speed.addChangeListener(e -> Player.this.speed = (int) ((JSpinner) e.getSource()).getValue());

        add(speedLabel, BorderLayout.WEST);
        add(speed, BorderLayout.EAST);
      }
    };


    setLayout(new FlowLayout());
    add(gamePanel);
    add(stepBack);
    add(scrubber);
    add(stepForward);
    add(home);
    add(load);
    add(rewind);
    add(play);
    add(pause);
    add(speedPanel);

    setPreferredSize(new Dimension(800, 520));
  }

  /**
   * Load a file.
   */
  private void load() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select a recording to play");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Replay File (xml)", "xml");
    fileChooser.setFileFilter(filter);
    fileChooser.showOpenDialog(this);

    // only load if a file was selected
    if (fileChooser.getSelectedFile() != null) {
      Parser parser = new Parser(fileChooser.getSelectedFile());
      int level = parser.getLevel();
      actions = parser.getActions();
      System.out.println(level);
    }
  }

  /**
   * Scrub to a position.
   *
   * @param position the position to scrub to
   */
  private void scrub(int position) {
    System.out.println("Scrubbing to " + position);
  }

  /**
   * Rewind.
   */
  private void rewind() {
    isRewinding = true;
    isPlaying = false;
    new Thread(() -> {
      for (int i = scrubber.getValue(); i >= 0; i--) {
        System.out.println(actions.get(i));
        scrubber.setValue(i);
        try {
          Thread.sleep(1000 / speed);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (!isRewinding) break;
      }
      isRewinding = false;
    }).start();
  }

  /**
   * Play the recording.
   */
  private void play() {
    isPlaying = true;
    isRewinding = false;
    new Thread(() -> {
      for (int i = scrubber.getValue(); i < actions.size(); i++) {
        System.out.println(actions.get(i));
        scrubber.setValue(i);
        try {
          Thread.sleep(1000 / speed);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (!isPlaying) break;
      }
      isPlaying = false;
    }).start();
  }
}