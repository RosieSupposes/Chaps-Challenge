package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenuItem extends JMenuItem {

    public GameMenuItem(String name, ActionListener actionList, Color col, int width){
        super(name);
        this.setMaximumSize(new Dimension(width,30));
        this.addActionListener(actionList);
        this.setBackground(col);
    }

    public GameMenuItem(String name, ActionListener actionList, Color col){
        super(name);
        this.addActionListener(actionList);
        this.setBackground(col);
    }

    public void changeActionListener(ActionListener action){
        this.removeActionListener(this.getActionListeners()[0]);
        this.addActionListener(action);
    }
}
