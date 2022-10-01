package nz.ac.vuw.ecs.swen225.gp22.recorder;

import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * An action that moves the player.
 *
 * @author Christopher Sa, 300570735
 * @version 1.3
 * @param x The x coordinate to move to.
 * @param y The y coordinate to move to.
 * @param direction The direction the player is facing.
 */
public record MoveAction(int x, int y, String direction) implements Action {
  @Override
  public void execute() {
    System.out.println("Moved to " + x + ", " + y + " in direction " + direction);
  }

  @Override
  public void undo() {
    System.out.println("Moved back to " + x + ", " + y + " in direction " + direction);
  }

  @Override
  public Element toXML() {
    return new BaseElement("move")
        .addAttribute("x", String.valueOf(x))
        .addAttribute("y", String.valueOf(y))
        .addAttribute("direction", direction);
  }
}
