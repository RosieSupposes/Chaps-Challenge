package nz.ac.vuw.ecs.swen225.gp22.recorder;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * The action class to update the game.
 *
 * @author Christopher Sa, 300570735
 * @version 1.2
 * @param actionType The type of action.
 * @param prevDir The previous direction.
 * @param currDir The current direction.
 * @param color The color of the tile. Can be None.
 */
public record Action(int entityHash, String actionType, int x, int y, String prevDir, String currDir, String color) {
    /**
     * Creates an XML element from the action.
     *
     * @return the XML element
     */
    public Element toxml() {
        return DocumentHelper.createElement("action")
                .addAttribute("hash", String.valueOf(entityHash))
                .addAttribute("type", actionType)
                .addAttribute("x", String.valueOf(x))
                .addAttribute("y", String.valueOf(y))
                .addAttribute("prevDir", prevDir)
                .addAttribute("currDir", currDir)
                .addAttribute("color", color);
    }
}
