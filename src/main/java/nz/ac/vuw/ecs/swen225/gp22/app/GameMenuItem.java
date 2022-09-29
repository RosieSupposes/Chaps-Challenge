package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenuItem extends JMenuItem {

    ImageIcon imageIcon;

    public GameMenuItem(String name, ActionListener actionList, int width) {
        super(name);
        this.setMaximumSize(new Dimension(width, 30));
        this.addActionListener(actionList);
        this.setBackground(Main.BUTTON_COLOR);
        this.setForeground(Main.TEXT_COLOR);
    }

    public GameMenuItem(String name, ActionListener actionList, int width, ImageIcon imageIcon) {
        super(name);
        this.setMaximumSize(new Dimension(width, 30));
        this.addActionListener(actionList);
        this.setBackground(Main.BUTTON_COLOR);
        this.imageIcon = imageIcon;
        this.setIcon(imageIcon);
        this.setForeground(Main.TEXT_COLOR);
    }

    public GameMenuItem(String name, ActionListener actionList) {
        super(name);
        this.addActionListener(actionList);
        this.setBackground(Main.BUTTON_COLOR);
        this.setForeground(Main.TEXT_COLOR);
    }

    public void changeActionListener(ActionListener action) {
        this.removeActionListener(this.getActionListeners()[0]);
        this.addActionListener(action);
    }

    public void changeIcon(ImageIcon imageIcon) {
        this.setIcon(imageIcon);
    }
}
