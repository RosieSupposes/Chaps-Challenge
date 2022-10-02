package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.renderer.GameDimensions;

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
    JPanel gamePanel; //main square panel
    JPanel sidePanel; //side panel
    List<JComponent> components = new ArrayList<>(); //all components in panel

    /**
     * Panel to hold game square next to side panel
     *
     * @param gamePanel the panel in the main square
     * @param sidePanel the panel on the side
     */
    public PhasePanel(JPanel gamePanel, JPanel sidePanel) {
        this.gamePanel = gamePanel;
        this.sidePanel = sidePanel;
        this.setLayout(new BorderLayout());

        setUpPanels();

        this.setPreferredSize(GameDimensions.WINDOW_SIZE);
        components.addAll(List.of(sidePanel, gamePanel));
    }

    /**
     * sets up panel sizes and adds them to panel
     */
    public void setUpPanels() {
        gamePanel.setPreferredSize(GameDimensions.GAME_SIZE);
        sidePanel.setPreferredSize(GameDimensions.SIDE_SIZE);

        this.add(BorderLayout.WEST, gamePanel);
        this.add(BorderLayout.EAST, sidePanel);
    }

    /**
     * get all components in window
     *
     * @return list of components
     */
    public List<JComponent> getAllComponents() {
        return components;
    }
}
