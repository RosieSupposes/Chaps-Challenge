package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.SwingUtilities;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import nz.ac.vuw.ecs.swen225.gp22.app.*;

/**
 * Class for Fuzz Testing
 * 
 * @author Gavin Lim, 300585341
 * @version 1.1
 */
public class FuzzTest {

    static final Random r = new Random();

    // List of possible inputs
    List<Integer> inputs = List.of(KeyEvent.VK_UP,
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);

    private List<Integer> generateInputs() {
        return IntStream.range(0, 100)
                .map(i -> r.nextInt(inputs.size()))
                .mapToObj(j -> inputs.get(j))
                .toList();
    }

    private int generateOneInput() {
        return inputs.get(r.nextInt(inputs.size()));
    }

    public void test1() {
        try {
            Robot robot = new Robot();
            try {
                SwingUtilities.invokeLater(() -> new Base().levelPhase(true));
            } catch (Error e) {
            }
            List<Integer> generatedInputs = generateInputs();
            for (int i : generatedInputs) {
                if (SwingUtilities.isEventDispatchThread()) {

                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            robot.keyPress(generateOneInput());
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    SwingUtilities.invokeLater(() -> robot.keyPress(generatedInputs.get(i)));
                }
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        try {
            Robot robot = new Robot();
            try {
                SwingUtilities.invokeLater(() -> new Base().levelPhase(true));
            } catch (Error e) {

            }
            List<Integer> generatedInputs = generateInputs();
            for (int i : generatedInputs) {
                if (SwingUtilities.isEventDispatchThread()) {

                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            robot.keyPress(generateOneInput());
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    SwingUtilities.invokeLater(() -> robot.keyPress(generatedInputs.get(i)));
                }
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fuzzTests() {
        assertTimeout(Duration.ofSeconds(60), () -> test1());
        assertTimeout(Duration.ofSeconds(60), () -> test2());
    }
}
