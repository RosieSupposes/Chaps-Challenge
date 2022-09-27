package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * An action that collects a key.
 *
 * @author Christopher Sa, 300570735
 * @version 1.2
 * @param item the item to collect
 */
public record CollectAction(String item) implements Action {
  @Override
  public void execute() {
    System.out.println("Collect " + item);
  }

  @Override
  public void undo() {
    System.out.println("Undo collect " + item);
  }

  @Override
  public String toXML() {
    return "<collect item=\"" + item + "\"/>";
  }
}
