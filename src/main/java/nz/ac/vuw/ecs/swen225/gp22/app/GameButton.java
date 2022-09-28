package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameButton extends JButton {
    public GameButton(String name, Dimension dim, ActionListener action){
        super(name);
        this.setBackground(Main.BUTTON_COLOR);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.addActionListener(action);
    }

    public void changeActionListener(ActionListener actionListener){
        this.removeActionListener(this.getActionListeners()[0]);
        this.addActionListener(actionListener);
    }
}
