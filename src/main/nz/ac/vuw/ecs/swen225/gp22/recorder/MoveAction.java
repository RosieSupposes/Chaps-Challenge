package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * An action that moves the player.
 *
 * @author Christopher Sa, 300570735
 * @version 1.4
 * @param x The x coordinate to move to.
 * @param y The y coordinate to move to.
 * @param direction The direction the player is facing.
 */
public record MoveAction(int x, int y, String direction) implements Action {
    @Override
    public void execute() {
        Base.setMove(x, y, direction);
        System.out.println("MoveAction: " + x + ", " + y + ", " + direction);
    }

    @Override
    public void undo() {
        // Undoing a move is handled by the Player class
    }

    @Override
    public Element toXML() {
        return new BaseElement("move")
            .addAttribute("x", String.valueOf(x))
            .addAttribute("y", String.valueOf(y))
            .addAttribute("direction", direction);
    }
}
