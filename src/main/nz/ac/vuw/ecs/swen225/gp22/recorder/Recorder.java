package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * The recorder for the game. Used to record actions.
 *
 * @author Christopher Sa, 300570735
 * @version 1.1
 */
public class Recorder {
  private final int level;
  private final List<Action> actions;

  /**
   * Create a new recorder.
   *
   * @param lvl the level to record
   */
  public Recorder(int lvl) {
    level = lvl;
    actions = new ArrayList<>();
  }

  /**
   * Add a move action to the recorder.
   *
   * @param action The action to add.
   */
  public void addMove(MoveAction action) {
    if (actions.size() > 0 && actions.get(actions.size() - 1) instanceof MoveAction move && move.direction().equals(action.direction())) {
      // Increase last move action's count as it is the same direction
      actions.set(actions.size() - 1, new MoveAction(action.direction(), move.steps() + 1));
    } else {
      actions.add(action);
    }
  }

  /**
   * Add a collect action to the recorder.
   *
   * @param action The action to add.
   */
  public void addCollect(CollectAction action) {
    actions.add(action);
  }

  /**
   * Save the recorded actions to a xml file.
   */
  public void save() {
    Document doc = DocumentHelper.createDocument();
    Element root = doc.addElement("game").addAttribute("level", String.valueOf(level));
    actions.forEach(action -> root.add(action.toXML()));

    String filename = System.getProperty("user.dir") + "\\src\\main\\resources\\recordings\\"
                      + LocalDateTime.now().toString().replace(":", "-") + ".chaps.xml";
    try (FileWriter out = new FileWriter(filename)) {
      (new XMLWriter(out, OutputFormat.createPrettyPrint())).write(doc);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
