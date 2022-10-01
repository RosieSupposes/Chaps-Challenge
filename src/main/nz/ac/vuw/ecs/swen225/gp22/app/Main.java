package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final Color BUTTON_COLOR = new Color(220, 170, 200);
    public static final Color BG_COLOR_LIGHTER = new Color(228, 213, 242);
    public static final Color BG_COLOR_DARKER = new Color(200, 190, 230);
    public static final Color BG_COLOR = BG_COLOR_DARKER;
    public static final Color TEXT_COLOR = new Color(81, 45, 28);
    public static final Color LIGHT_YELLOW_COLOR = new Color(228, 216, 170);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Base::new);
    }
}
