package nz.ac.vuw.ecs.swen225.gp22.recorder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Used to parse recorded games from XML.
 *
 * @author Christopher Sa, 300570735
 * @version 1.5
 */
public class Parser {

    Document document;

    /**
     * Parses given XML file into a document and stores it.
     *
     * @param file the recording file
     */
    Parser(File file) {
        try {
            document = new SAXReader().read(file);
        } catch (DocumentException e) {
            throw new IllegalArgumentException("Invalid recording file");
        }
    }

    /**
     * @return the level loaded from the recording
     */
    public int getLevel() {
        return Integer.parseInt(document.getRootElement().attributeValue("level"));
    }

    /**
     * Gets the list of actions from the document.
     *
     * @return the list of actions
     */
    public List<GameState> getActions() {
        List<Element> nodes = document.getRootElement().elements();
        return nodes.stream().map(this::parseState).toList();
    }

    /**
     * Parses an action from an XML element.
     *
     * @param element the XML element
     * @return the action
     */
    private Action parseAction(Element element) {
        int hash = Integer.parseInt(element.attributeValue("hash"));
        String actionType = element.attributeValue("type");
        int x = Integer.parseInt(element.attributeValue("x"));
        int y = Integer.parseInt(element.attributeValue("y"));
        String prevDir = element.attributeValue("prevDir");
        String currDir = element.attributeValue("currDir");
        String color = element.attributeValue("color");
        return new Action(hash, actionType, x, y, prevDir, currDir, color);
    }

    /**
     * Parses a state from an XML element.
     *
     * @param element the XML element
     * @return the state
     */
    private GameState parseState(Element element) {
        int id = Integer.parseInt(element.attributeValue("id"));
        int time = Integer.parseInt(element.attributeValue("time"));
        List<Action> actions = element.elements().stream().map(e -> parseAction((Element) e)).toList();
        return new GameState(id, time, actions);
    }
}
