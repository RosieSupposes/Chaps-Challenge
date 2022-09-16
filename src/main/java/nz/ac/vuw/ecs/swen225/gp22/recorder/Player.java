package nz.ac.vuw.ecs.swen225.gp22.recorder;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The player for the recorder. Used to play back recorded actions.
 *
 * @author Christopher Sa, 300570735
 * @version 1.4
 */
public class Player extends JPanel {

  /**
   * Create a new player.
   */
  public Player() {
    assert SwingUtilities.isEventDispatchThread();

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

    JSlider slider = new JSlider();
    slider.setPreferredSize(new Dimension(550, 25));
    slider.addChangeListener(e -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        scrub(source.getValue());
      }
    });

    JButton load = new PlaybackButton("Load",  () -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.showOpenDialog(this);
      load(fileChooser.getSelectedFile());
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
    add(slider);
    add(stepForward);
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
   *
   * @param file the file to load
   */
  private void load(File file) {
    System.out.println("Loading " + file);
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