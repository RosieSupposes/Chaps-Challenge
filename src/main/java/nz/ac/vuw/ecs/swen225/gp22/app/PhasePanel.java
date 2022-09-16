package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Molly Henry, 300562038
 * @version 1.1
 */
public class PhasePanel extends JPanel {
    public final int TILE_SIZE = 60;
    public final int NUM_GAME_TILE = 9;
    public final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    public final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    public final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    public final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;
    JPanel gamePanel;
    JPanel sidePanel;

    List<JComponent> components = new ArrayList<>();

    public PhasePanel(JPanel gamePanel, JPanel sidePanel) {
        this.gamePanel = gamePanel;
        this.sidePanel = sidePanel;
        this.setLayout(new BorderLayout());
        setUpPanels();
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        components.addAll(List.of(sidePanel, gamePanel));
    }

    public void setUpPanels() {
        //gamePanel.setPreferredSize(new Dimension(GAME_WINDOW_SIZE, GAME_WINDOW_SIZE));
        //sidePanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
        this.add(BorderLayout.WEST, gamePanel);
        JPanel sidePanelTest = new JPanel();
        sidePanelTest.setPreferredSize(new Dimension(Main.SIDEBAR_WIDTH, Main.WINDOW_HEIGHT));
        sidePanelTest.add(new JButton("your mum"));
        //this.add(BorderLayout.EAST, sidePanelTest);
        this.add(BorderLayout.EAST, sidePanel);
    }

    public List<JComponent> getAllComponents(){
        return components;
    }
}
