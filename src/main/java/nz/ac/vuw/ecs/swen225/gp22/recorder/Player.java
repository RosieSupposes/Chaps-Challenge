package nz.ac.vuw.ecs.swen225.gp22.recorder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * The player for the recorder. Used to play back recorded actions.
 *
 * @author Christopher Sa, 300570735
 * @version 1.7
 */
public class Player extends JPanel {
  private List<Action> actions;
  private JSlider scrubber;

  /**
   * Create a new player.
   */
  public Player() {
    assert SwingUtilities.isEventDispatchThread();
    load();
    setup();
    setVisible(true);
  }

  /**
   * Set up the player.
   */
  private void setup() {
    // Create components
    JPanel gamePanel = new JPanel();
    gamePanel.setPreferredSize(new Dimension(800, 400));
    gamePanel.setBackground(Color.BLACK);

    JButton stepBack = new PlaybackButton("Step Back", this::stepBack);

    scrubber = actions == null ? new JSlider() : new JSlider(0, actions.size() - 1);
    scrubber.setPreferredSize(new Dimension(550, 25));
    scrubber.setValue(0);
    scrubber.addChangeListener(e -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        scrub(source.getValue());
      }
    });

    JButton home = new JButton("Home");

    JButton load = new PlaybackButton("Load",  ()->{
      load();
      if (actions != null) scrubber.setMaximum(actions.size());
    });

    JButton stepForward = new PlaybackButton("Step Forward", this::stepForward);

    JButton rewind = new PlaybackButton("Rewind", this::rewind);
    JButton play = new PlaybackButton("Play", this::play);
    JButton pause = new PlaybackButton("Pause", this::pause);
    JButton forward = new PlaybackButton("Fast-Forward", this::forward);


    JPanel speedPanel = new JPanel() {
      {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(75, 25));

        JLabel speedLabel = new JLabel("Speed");

        JSpinner speed = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        speed.addChangeListener(e -> {
          JSpinner source = (JSpinner) e.getSource();
          setSpeed((int) source.getValue());
        });

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
    add(forward);
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
   * Step back one action.
   */
  private void stepBack() {
    System.out.println("Step back");
  }

  /**
   * Step forward one action.
   */
  private void stepForward() {
    System.out.println("Step forward");
  }

  /**
   * Rewind.
   */
  private void rewind() {
    System.out.println("Rewind");
  }

  /**
   * Play.
   */
  private void play() {
    System.out.println("Play");
  }

  /**
   * Pause.
   */
  private void pause() {
    System.out.println("Pause");
  }

  /**
   * Fast-forward.
   */
  private void forward() {
    System.out.println("Forward");
  }

  /**
   * Set the speed.
   *
   * @param speed the speed to set
   */
  private void setSpeed(int speed) {
    System.out.println("Setting speed to " + speed);
  }
}