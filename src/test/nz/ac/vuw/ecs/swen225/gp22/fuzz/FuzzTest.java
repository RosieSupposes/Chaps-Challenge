package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import nz.ac.vuw.ecs.swen225.gp22.app.Base;
import org.junit.jupiter.api.Test;

import java.util.*;
import javax.swing.SwingUtilities;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;

/**
 * Class for Fuzz Testing.
 *
 * @author Gavin Lim, 300585341
 * @version 1.5
 */
public class FuzzTest {

    static final Random r = new Random();
    private static Base base;

    // List of possible inputs.
    private final List<Integer> inputs = List.of(KeyEvent.VK_UP,
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);

    //Map of inputs and their opposite inputs.
    private final Map<Integer, Integer> inputsAndOpposite =
            Map.of(KeyEvent.VK_UP, KeyEvent.VK_DOWN,
                    KeyEvent.VK_DOWN, KeyEvent.VK_UP,
                    KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                    KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);

    /**
     * Generates a list of 10000 valid inputs.
     * Chap will not move back to the tile he was previously on.
     *
     * @return list of 10000 valid inputs.
     */
    private List<Integer> generateInputs() {
        int prev = -1;
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            while (true) {
                int random = r.nextInt(inputs.size());
                int move = inputs.get(random);
                if (prev == -1 || move != inputsAndOpposite.get(prev)) {
                    moves.add(move);
                    prev = move;
                    break;
                }
            }
        }
        return moves;
    }

    /**
     * Inputs random inputs for Level 1 of Chap's Challenge.
     */
    public void test1() {
        try {
            SwingUtilities.invokeLater(() -> (base = new Base()).newGame(1));
        } catch (Error e) {
        }
        runTest();
    }

    /**
     * Inputs random inputs for Level 2 of Chap's Challenge.
     */
    public void test2() {
        try {
            SwingUtilities.invokeLater(() -> base.newGame(2));
        } catch (Error e) {
        }
        runTest();
    }

    /**
     * Runs the test on current Base.
     */
    private void runTest() {
        try {
            Robot robot = new Robot();
            List<Integer> generatedInputs = generateInputs();
            for (int i : generatedInputs) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        robot.keyPress(i);
                    }
                });
                try {
                    Thread.sleep(5);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            robot.keyRelease(i);
                        }
                    });
                } catch (InterruptedException e) {
                }
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

        /**
     * Runs tests for both level 1 and 2 of Chap's Challenge.
     * Tests are limited to a one minute timer.
     */
    @Test
    public void fuzzTests() {
        try {
            assertTimeout(Duration.ofSeconds(60), () -> test1());
            assertTimeout(Duration.ofSeconds(60), () -> test2());
        } catch (Exception e) {

        }
    }
}
