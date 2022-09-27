package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * An action that moves the player.
 *
 * @author Christopher Sa, 300570735
 * @version 1.1
 * @param direction the direction to move
 * @param steps the number of steps to move
 */
public record MoveAction(String direction, int steps) implements Action {
  @Override
  public void execute() {
    System.out.println("Move " + steps + " steps " + direction);
  }

  @Override
  public void undo() {
    System.out.println("Undo move " + steps + " steps " + direction);
  }

  @Override
  public String toXML() {
    return "<move direction=\"" + direction + "\" steps=\"" + steps + "\"/>";
  }
}
