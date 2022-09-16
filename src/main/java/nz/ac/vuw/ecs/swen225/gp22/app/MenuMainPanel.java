package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;

public class MenuMainPanel extends JPanel {
    Base base; //actions go to this base JFrame

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

    private JLabel titlePanelForMenu() {
        JLabel title = new JLabel("CHAPSSS!");
        title.setBackground(Color.YELLOW);
        return title;
    }

    private JPanel buttonPanelForMenu() {
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.GREEN);
        buttons.setPreferredSize(new Dimension(3 * Main.TILE_SIZE, 2 * Main.TILE_SIZE));

        //base.addComponent(buttons);
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Component vertSpace = Box.createRigidArea(new Dimension(0, 7));
        Component horizSpace = Box.createRigidArea(new Dimension(10, 30));

        c.gridx = 0;
        c.gridy = 0;
        //column 1, x = 0
        buttons.add(vertSpace, c);
        c.gridy++;

        JButton start = new JButton("Start");
        start.addActionListener(e -> base.startAction.run());
        buttons.add(start, c);
        //base.addComponent(start);

        c.gridy++;
        buttons.add(vertSpace, c);

        JButton load = new JButton("Load");
        load.addActionListener(e -> base.loadAction.run());
        c.gridy++;
        buttons.add(load, c);
        //base.addComponent(load);

        //column 2, blank space
        c.gridx++;
        c.gridy = 0;
        buttons.add(horizSpace, c);

        //column 3
        c.gridx++;
        c.gridy = 0;

        buttons.add(vertSpace, c);
        c.gridy++;

        JLabel info = new JLabel("Info");
        buttons.add(info, c);
        //base.addComponent(info);

        c.gridy++;
        buttons.add(vertSpace, c);

        JButton replay = new JButton("Replay");
        replay.addActionListener(e -> base.replayAction.run());
        c.gridy++;
        buttons.add(replay, c);
        //base.addComponent(replay);

        return buttons;
    }
}
