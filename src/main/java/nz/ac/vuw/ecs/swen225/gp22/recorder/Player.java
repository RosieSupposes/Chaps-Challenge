package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import nz.ac.vuw.ecs.swen225.gp22.app.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.List;

/**
 * The player for the recorder. Used to play back recorded actions.
 *
 * @author Christopher Sa, 300570735
 * @version 1.10
 */
public class Player extends JPanel {
  private final Base base;
  private List<Action> actions;
  private JSlider scrubber;
  private int currentAction = 0;
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
    gamePanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT)); // Will be changed when I get passed the viewport later
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
    scrubber.setBackground(Main.BG_COLOR);
    scrubber.setUI(new BasicSliderUI(scrubber) {
      @Override
      public void paintThumb(Graphics g) {
        g.setColor(Main.BUTTON_COLOR);
        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.height - 2, thumbRect.height - 2);
        g.setColor(Color.GRAY);
        g.drawOval(thumbRect.x, thumbRect.y, thumbRect.height - 2, thumbRect.height - 2);
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

        JLabel speedLabel = new JLabel("Speed:");

        JSpinner speed = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        speed.addChangeListener(e -> Player.this.speed = (int) ((JSpinner) e.getSource()).getValue());
        speed.setBackground(Main.BUTTON_COLOR);
        speed.getEditor().getComponent(0).setBackground(Main.BUTTON_COLOR);
        speed.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        setBackground(Main.BUTTON_COLOR);
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
    setBackground(Main.BG_COLOR);
  }

  /**
   * Load a file.
   */
  private void load() {
    JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "\\src\\main\\resources\\recordings");
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
    if (actions == null) return;
    if (position > currentAction) {
      // scrub forward
      for (int i = currentAction; i < position; i++) {
        actions.get(i).execute();
      }
    } else if (position < currentAction) {
      // scrub backward
      for (int i = currentAction; i > position; i--) {
        actions.get(i).undo();
      }
    }
    currentAction = position;
  }

  /**
   * Rewind.
   */
  private void rewind() {
    isRewinding = true;
    isPlaying = false;
    new Thread(() -> {
      for (int i = scrubber.getValue(); i >= 0; i--) {
        actions.get(i).undo();
        if (progress(i, isRewinding)) break;
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
        actions.get(i).execute();
        if (progress(i, isPlaying)) break;
      }
      isPlaying = false;
    }).start();
  }

  /**
   * Used for playing and rewinding.
   *
   * @param i The current action.
   * @param isProgressing If the player is progressing.
   * @return True if the player should stop progressing.
   */
  private boolean progress(int i, boolean isProgressing) {
    scrubber.setValue(i);
    currentAction = i;
    try {
      Thread.sleep(1000 / speed);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return !isProgressing;
  }
}