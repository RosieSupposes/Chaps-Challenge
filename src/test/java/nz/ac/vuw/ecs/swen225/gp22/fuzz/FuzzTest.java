package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import org.junit.jupiter.api.Test;

/**
 * Class for Fuzz Testing
 * 
 * @author Gavin Lim, 300585341
 * @version 1.1
 */
public class FuzzTest {

    public void test1() {
            
    }

    public void test2() {
        
    }

    @Test
    public void fuzzTests() {
        assertTimeout(Duration.ofSeconds(60), () -> test1());
        assertTimeout(Duration.ofSeconds(60), () -> test2());
    }
}
