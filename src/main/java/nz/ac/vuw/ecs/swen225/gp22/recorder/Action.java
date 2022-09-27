package nz.ac.vuw.ecs.swen225.gp22.recorder;

import org.dom4j.Element;

/**
 * Interface for actions that can be recorded.
 *
 * @author Christopher Sa, 300570735
 * @version 1.2
 */
public interface Action {

  /**
   * Execute the action.
   */
  void execute();

  /**
   * Undo the action.
   */
  void undo();

  /**
   * Convert the action to XML for saving.
   *
   * @return the XML representation of the action
   */
  Element toXML();
}
