package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

/**
 * Panel containing menu information
 *
 * @author Molly Henry, 300562038
 * @version 1.1
 */
public class MenuMainPanel extends JPanel {
    Base base; //actions go to this base JFrame
    Color col = new Color(220, 170, 200);
    Dimension buttonGap = new Dimension(10, 10);
    Dimension halfButtonSize = new Dimension(75, 30);
    Dimension fullButtonSize = new Dimension(halfButtonSize.width * 2 + buttonGap.width, halfButtonSize.height);

    /**
     * Creates menu panel, connecting base actions to buttons on panel
     *
     * @param base current base
     */
    public MenuMainPanel(Base base) {
        this.base = base;

        this.setBackground(Color.RED);
        this.setPreferredSize(new Dimension(Main.GAME_WINDOW_SIZE, Main.WINDOW_HEIGHT));
        //add(BorderLayout.WEST, mainPanel);
        //base.addComponent(mainPanel);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;

        c.gridy = 0;
        this.add(titlePanelForMenu(), c);

        c.gridy = 1;
        Component vertSpace = Box.createRigidArea(new Dimension(0, 20));
        this.add(vertSpace, c);

        c.gridy = 2;
        this.add(buttonPanelForMenu(), c);
    }

    /**
     * Creates title for menu page
     *
     * @return JLabel for title
     */
    private JLabel titlePanelForMenu() {
        JLabel title = new JLabel("CHAPSSS!");
        title.setBackground(Color.YELLOW);
        return title;
    }

    /**
     * button panel for menu
     *
     * @return panel with buttons on it
     */
    private JPanel buttonPanelForMenu() {
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.GREEN);
        buttons.setPreferredSize(new Dimension(3 * Main.TILE_SIZE, 3 * Main.TILE_SIZE));

        buttons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        //column 1, x = 0
        buttons.add(Box.createRigidArea(buttonGap), c);
        c.gridy++;

        GameButton start = new GameButton("Start", col, halfButtonSize, e -> base.startGame());
        buttons.add(start, c);

        c.gridy++;
        buttons.add(Box.createRigidArea(buttonGap), c); //vert

        GameButton load = new GameButton("Load", col, halfButtonSize, e -> base.loadGame());
        c.gridy++;
        buttons.add(load, c);

        //column 2, blank space
        c.gridx++;
        c.gridy = 0;
        buttons.add(Box.createRigidArea(buttonGap), c); //horiz

        //column 3
        c.gridx++;
        c.gridy = 0;

        buttons.add(Box.createRigidArea(buttonGap), c); //vert
        c.gridy++;

        JLabel info = new JLabel("Info");
        buttons.add(info, c);

        c.gridy++;
        buttons.add(Box.createRigidArea(buttonGap), c); //vert

        GameButton replay = new GameButton("Replay", col, halfButtonSize, e -> base.replayPhase());
        c.gridy++;
        buttons.add(replay, c);

        c.gridy++;
        c.gridx = 0;
        buttons.add(Box.createRigidArea(buttonGap), c); //vert

        c.gridwidth = 3;
        c.gridy++;
        GameButton exit = new GameButton("Exit", col, fullButtonSize, e -> base.exitGame());
        buttons.add(exit, c);

        return buttons;
    }
}
