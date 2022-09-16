package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

public class MenuSidePanel extends JPanel {
    public MenuSidePanel() {
        this.add(new JLabel("HELLO??"));
        this.setPreferredSize(new Dimension(Main.SIDEBAR_WIDTH, Main.WINDOW_HEIGHT));
        this.setBackground(Color.YELLOW);
    }
}
