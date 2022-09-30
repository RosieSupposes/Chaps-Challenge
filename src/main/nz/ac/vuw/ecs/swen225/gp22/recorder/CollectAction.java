package nz.ac.vuw.ecs.swen225.gp22.recorder;

import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * An action that collects a key.
 *
 * @author Christopher Sa, 300570735
 * @version 1.3
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
  public Element toXML() {
    return new BaseElement("collect")
        .addAttribute("item", item);
  }
}
