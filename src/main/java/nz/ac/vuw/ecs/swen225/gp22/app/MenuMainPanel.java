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

    /**
     * Creates menu panel, connecting base actions to buttons on panel
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
     * @return JLabel for title
     */
    private JLabel titlePanelForMenu() {
        JLabel title = new JLabel("CHAPSSS!");
        title.setBackground(Color.YELLOW);
        return title;
    }

    /**
     * button panel for menu
     * @return panel with buttons on it
     */
    private JPanel buttonPanelForMenu() {
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.GREEN);
        buttons.setPreferredSize(new Dimension(3 * Main.TILE_SIZE, 2 * Main.TILE_SIZE));

        //base.addComponent(buttons);
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Dimension vertSpace = new Dimension(0, 7);
        Dimension horizSpace = new Dimension(10, 0);
        Dimension buttonSize = new Dimension(75, 30);

        c.gridx = 0;
        c.gridy = 0;
        //column 1, x = 0
        buttons.add(Box.createRigidArea(vertSpace), c);
        c.gridy++;

        JButton start = new JButton("Start");
        start.addActionListener(e -> base.startGame());
        buttons.add(start, c);
        start.setPreferredSize(buttonSize);
        //base.addComponent(start);

        c.gridy++;
        buttons.add(Box.createRigidArea(vertSpace), c);

        JButton load = new JButton("Load");
        load.addActionListener(e -> base.loadGame());
        load.setPreferredSize(buttonSize);
        c.gridy++;
        buttons.add(load, c);
        //base.addComponent(load);

        //column 2, blank space
        c.gridx++;
        c.gridy = 0;
        buttons.add(Box.createRigidArea(horizSpace), c);

        //column 3
        c.gridx++;
        c.gridy = 0;

        buttons.add(Box.createRigidArea(vertSpace), c);
        c.gridy++;

        JLabel info = new JLabel("Info");
        buttons.add(info, c);
        //base.addComponent(info);

        c.gridy++;
        buttons.add(Box.createRigidArea(vertSpace), c);

        JButton replay = new JButton("Replay");
        replay.addActionListener(e -> base.replayPhase());
        replay.setPreferredSize(buttonSize);
        c.gridy++;
        buttons.add(replay, c);
        //base.addComponent(replay);

        c.gridy++;
        c.gridx = 0;
        buttons.add(Box.createRigidArea(vertSpace), c);

        c.gridwidth = 3;
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> base.exitGame());
        exit.setPreferredSize(new Dimension(buttonSize.width * 2 + horizSpace.width, buttonSize.height));
        c.gridy++;
        buttons.add(exit, c);
        //base.addComponent(replay);

        return buttons;
    }
}
