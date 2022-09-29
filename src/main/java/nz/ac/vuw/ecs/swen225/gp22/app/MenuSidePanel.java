package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

/**
 * Side panel for menu
 *
 * @author Molly Henry, 300562038
 * @version 1.1
 */
public class MenuSidePanel extends JPanel {
    /**
     * Side panel for menu
     */
    public MenuSidePanel() {
        this.add(new JLabel("HELLO??"));
        this.setPreferredSize(new Dimension(Main.SIDEBAR_WIDTH, Main.WINDOW_HEIGHT));
        this.setBackground(Color.YELLOW);
    }
}
