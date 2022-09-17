package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This JPanel contains both game panel and side panel.
 *
 * @author Molly Henry, 300562038
 * @version 1.1
 */
public class PhasePanel extends JPanel {
    JPanel gamePanel;
    JPanel sidePanel;
    List<JComponent> components = new ArrayList<>();

    public PhasePanel(JPanel gamePanel, JPanel sidePanel) {
        this.gamePanel = gamePanel;
        this.sidePanel = sidePanel;
        this.setLayout(new BorderLayout());
        setUpPanels();
        this.setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
        components.addAll(List.of(sidePanel, gamePanel));
    }

    public void setUpPanels() {
        gamePanel.setPreferredSize(new Dimension(Main.GAME_WINDOW_SIZE, Main.GAME_WINDOW_SIZE));
        sidePanel.setPreferredSize(new Dimension(Main.SIDEBAR_WIDTH, Main.WINDOW_HEIGHT));
        this.add(BorderLayout.WEST, gamePanel);
        JPanel sidePanelTest = new JPanel();
        sidePanelTest.setPreferredSize(new Dimension(Main.SIDEBAR_WIDTH, Main.WINDOW_HEIGHT));

        this.add(BorderLayout.EAST, sidePanel);
    }

    public List<JComponent> getAllComponents(){
        return components;
    }
}
