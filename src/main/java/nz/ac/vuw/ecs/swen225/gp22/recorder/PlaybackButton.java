package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Main;

import javax.swing.*;

/**
 * A button that is used for playback controls.
 *
 * @author Christopher Sa, 300570735
 * @version 1.1
 */
public class PlaybackButton extends JButton {

  /**
   * Create a new playback button.
   *
   * @param text the text to display on the button
   * @param action the action to perform when the button is clicked
   */
  public PlaybackButton(String text, Runnable action) {
    super(text);
    addActionListener(e -> action.run());
    setBackground(Main.BUTTON_COLOR);
  }
}
