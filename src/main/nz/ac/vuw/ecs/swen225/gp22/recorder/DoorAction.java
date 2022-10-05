package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * An action that opens a door.
 *
 * @author Christopher Sa, 300570735
 * @version 1.2
 * @param x the x coordinate of the door
 * @param y the y coordinate of the door
 * @param type the type of the door
 * @param color the color of the door
 */
public record DoorAction(int x, int y, String type, String color) implements Action {
    @Override
    public void execute() {
        Base.setAction(x, y, "Open", type, color);
    }

    @Override
    public void undo() {
        Base.setAction(x, y, "Close", type, color);
    }

    @Override
    public Element toXML() {
        Element door = new BaseElement("door")
            .addAttribute("x", String.valueOf(x))
            .addAttribute("y", String.valueOf(y))
            .addAttribute("type", type);

        if (!color.equals("None")) { door.addAttribute("color", color); }

        return door;
    }
}
