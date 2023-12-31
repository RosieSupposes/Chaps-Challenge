package nz.ac.vuw.ecs.swen225.gp22.util;

import java.awt.*;

/**
 * This class stores the constants needed for the dimensions of
 * the objects within the maze, as well as the colors used and
 * sizes of the window they will be displayed on.
 *
 * @author Diana Batoon, 300475111
 * @version 1.3
 */
public class GameConstants {
    // maze dimensions
    public static final int TILE_SIZE = 60;
    public static final int NUM_GAME_TILE = 9;
    public static final int FOCUS_AREA = NUM_GAME_TILE / 2;
    public static final int NUM_INVENTORY_TILES = 12;

    // window dimensions
    public static final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    public static final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    public static final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;
    public static final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    public static final Dimension GAME_SIZE = new Dimension(GAME_WINDOW_SIZE, WINDOW_HEIGHT);
    public static final Dimension SIDE_SIZE = new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT);

    //color constants
    public static final Color BUTTON_COLOR = new Color(220, 170, 200);
    public static final Color BG_COLOR_LIGHTER = new Color(228, 213, 242);
    public static final Color BG_COLOR_DARKER = new Color(200, 190, 230);
    public static final Color BG_COLOR = BG_COLOR_DARKER;
    public static final Color TEXT_COLOR = new Color(81, 45, 28);
    public static final Color LIGHT_YELLOW_COLOR = new Color(228, 216, 170);

}
