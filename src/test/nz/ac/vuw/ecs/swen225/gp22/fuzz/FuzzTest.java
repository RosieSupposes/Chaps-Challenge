package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.SwingUtilities;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;

/**
 * Class for Fuzz Testing
 * 
 * @author Gavin Lim, 300585341
 * @version 1.2
 */
public class FuzzTest {

    static final Random r = new Random();

    // List of possible inputs
    List<Integer> inputs = List.of(KeyEvent.VK_UP,
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);

    /**
     * Generates a list of 100 valid inputs
     * 
     * @return list of 100 valid inputs
     */
    private List<Integer> generateInputs() {
        return IntStream.range(0, 100)
                .map(i -> r.nextInt(inputs.size()))
                .mapToObj(j -> inputs.get(j))
                .toList();
    }

    /**
     * Generates a single valid input
     * 
     * @return single valid input
     */
    private int generateOneInput() {
        return inputs.get(r.nextInt(inputs.size()));
    }

    /**
     * Inputs random inputs for Level 1 of Chap's Challenge
     */
    public void test1() {
        try {
            Robot robot = new Robot();
            try {
                SwingUtilities.invokeLater(() -> new Base().newGame(1));
            } catch (Error e) {
            }
            List<Integer> generatedInputs = generateInputs();
            for (int i : generatedInputs) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        robot.keyPress(i);
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inputs random inputs for Level 2 of Chap's Challenge
     */
    public void test2() {
        try {
            Robot robot = new Robot();
            try {
                SwingUtilities.invokeLater(() -> new Base().newGame(1));
            } catch (Error e) {

            }
            List<Integer> generatedInputs = generateInputs();
            for (int i : generatedInputs) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        robot.keyPress(i);
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs tests for both level 1 and 2 of Chap's Challenge
     * Tests are limited to a one minute timer
     */
    @Test
    public void fuzzTests() {
        assertTimeout(Duration.ofSeconds(60), () -> test1());
        assertTimeout(Duration.ofSeconds(60), () -> test2());
    }
}
