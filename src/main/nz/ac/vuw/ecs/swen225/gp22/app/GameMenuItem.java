package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp22.util.GameConstants;

import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenuItem extends JMenuItem {

    public GameMenuItem(String name, ActionListener actionList, int width) {
        super(name);
        setUp(actionList);

//        this.setMaximumSize(new Dimension(width, 30));
    }

    public GameMenuItem(String name, ActionListener actionList, int width, ImageIcon imageIcon) {
        super(name);
        setUp(actionList);

        this.setMaximumSize(new Dimension(width, 30));

        this.setIcon(imageIcon);
    }

    public GameMenuItem(String name, ActionListener actionList) {
        super(name);
        setUp(actionList);
    }

    private void setUp(ActionListener actionList){
        this.addActionListener(actionList);
        this.setBackground(GameConstants.BUTTON_COLOR);
        this.setForeground(GameConstants.TEXT_COLOR);
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public void changeActionListener(ActionListener action) {
        this.removeActionListener(this.getActionListeners()[0]);
        this.addActionListener(action);
    }

    public void changeIcon(ImageIcon imageIcon) {
        this.setIcon(imageIcon);
    }
}
