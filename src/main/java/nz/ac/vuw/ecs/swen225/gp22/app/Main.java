package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int TILE_SIZE = 60;
    public static final int NUM_GAME_TILE = 9;
    public static final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    public static final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    public static final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    public static final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;

    public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

    public static final Color BUTTON_COLOR = new Color(220, 170, 200);
    public static final Color BG_COLOR = new Color(200, 190, 230);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Base::new);
    }
}
