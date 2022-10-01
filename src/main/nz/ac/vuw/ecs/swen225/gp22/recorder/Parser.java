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
 * @version 1.2
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
  public List<Action> getActions() {
    List<Element> nodes = document.getRootElement().elements();
    return nodes.stream().map(this::parseAction).toList();
  }

  /**
   * Parses an action from an XML element.
   *
   * @param element the XML element
   * @return the action
   */
  private Action parseAction(Element element) {
    switch (element.getName()) {
      case "move" -> {
        int x = Integer.parseInt(element.attributeValue("x"));
        int y = Integer.parseInt(element.attributeValue("y"));
        String direction = element.attributeValue("direction");
        return new MoveAction(Integer.parseInt(element.attributeValue("x")),
                              Integer.parseInt(element.attributeValue("y")),
                              direction);
      }
      case "collect" -> {
        String item = element.attributeValue("item");
        return new CollectAction(item);
      }
      default -> throw new IllegalArgumentException("Invalid action");
    }
  }
}
