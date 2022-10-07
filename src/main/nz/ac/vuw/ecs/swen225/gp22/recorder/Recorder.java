package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
 * @version 1.3
 */
public class Recorder {
    private final int level;
    private final List<GameState> gameStates;
    private final GameState prevState;


    /**
     * Create a new recorder.
     *
     * @param lvl the level to record
     */
    public Recorder(int lvl, int time) {
        level = lvl;
        gameStates = new ArrayList<>();
        prevState = new GameState(0, time);
    }

    /**
     * Add an action to the recorder.
     *
     * @param action The action to add.
     */
    public void addAction(Action action, int time) {
        if (gameStates.isEmpty() || prevState.getTime() != time) {
            gameStates.add(new GameState(gameStates.size(), time));
        }
        gameStates.get(gameStates.size() - 1).addAction(action);
    }

    /**
     * Save the recorded actions to a xml file.
     */
    public void save() {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("game").addAttribute("level", String.valueOf(level));
//        actions.forEach(action -> root.add(action.toXML()));

        String filename = System.getProperty("user.dir") + "/resources/recordings/"
            + LocalDateTime.now().toString().replace(":", "-") + ".chaps.xml";
        try (FileWriter out = new FileWriter(filename, StandardCharsets.UTF_8)) {
            (new XMLWriter(out, OutputFormat.createPrettyPrint())).write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
