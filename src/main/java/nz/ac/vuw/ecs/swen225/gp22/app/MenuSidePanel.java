package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

/**
 * Side panel for menu
 *
 * @author Molly Henry, 300562038
 * @version 1.2
 */
public class MenuSidePanel extends JPanel {
    /**
     * Side panel for menu
     */
    public MenuSidePanel() {
        this.add(new JLabel("Ctrl + 1 --> New Level One"));
        this.add(new JLabel("Ctrl + 2 --> New Level Two"));
        this.add(new JLabel("Ctrl + R --> Resume Last Game"));
        this.add(new JLabel("Ctrl + X --> Exit Game"));
        this.add(new JLabel("Ctrl + S --> Save Game"));
        this.add(new JLabel("Space --> Pause Game"));
        this.add(new JLabel("Esc --> Un-Pause Game"));
        this.add(new JLabel("Arrow Keys --> Move Player"));
        this.setPreferredSize(new Dimension(Main.SIDEBAR_WIDTH, Main.WINDOW_HEIGHT));
        this.setBackground(Color.YELLOW);
    }
}
