package nz.ac.vuw.ecs.swen225.gp22.recorder;

import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * An action that collects a key.
 *
 * @author Christopher Sa, 300570735
 * @version 1.4
 * @param item the item to collect
 */
public record CollectAction(int x, int y, String item, String color) implements Action {
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
    Element element = new BaseElement("collect")
        .addAttribute("x", String.valueOf(x))
        .addAttribute("y", String.valueOf(y))
        .addAttribute("item", item);

    if (color != null) { element.addAttribute("color", color); }

    return element;
  }
}
