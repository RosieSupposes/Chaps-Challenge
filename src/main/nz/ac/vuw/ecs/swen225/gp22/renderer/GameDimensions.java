package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Dimension;

/**
 * This class stores the constants needed for the dimensions of
 * the objects within the maze, as well as the sizes of the
 * window they will be displayed on.
 * 
 * @author Diana Batoon, 300475111
 * @version 1.2
 */
public class GameDimensions {
    // maze dimensions
    public static final int TILE_SIZE = 60;
    public static final int NUM_GAME_TILE = 9;
    public static final int FOCUS_AREA = NUM_GAME_TILE/2;

    // window dimensions
    public static final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    public static final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    public static final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;
    public static final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + 65);
    public static final Dimension GAME_SIZE = new Dimension(GAME_WINDOW_SIZE, WINDOW_HEIGHT);
    public static final Dimension SIDE_SIZE = new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT);
    
  }
