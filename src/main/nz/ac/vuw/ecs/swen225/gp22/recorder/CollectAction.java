package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * An action that collects a key.
 *
 * @author Christopher Sa, 300570735
 * @version 1.5
 * @param item the item to collect
 */
public record CollectAction(int x, int y, String item, String color) implements Action {
    @Override
    public void execute() {
        Base.setAction(x, y, "Pick-up", item, color);
    }

    @Override
    public void undo() {
        Base.setAction(x, y, "Drop", item, color);
    }

    @Override
    public Element toXML() {
        Element element = new BaseElement("collect")
            .addAttribute("x", String.valueOf(x))
            .addAttribute("y", String.valueOf(y))
            .addAttribute("item", item);

        if (!color.equals("None")) { element.addAttribute("color", color); }

        return element;
    }
}
