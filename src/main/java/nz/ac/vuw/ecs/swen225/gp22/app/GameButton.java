package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameButton extends JButton {
    public GameButton(String name, Color col, Dimension dim, ActionListener action){
        //TODO update all Base buttons to use this class
        super(name);
        this.setBackground(col);
        this.setPreferredSize(dim);
        this.addActionListener(action);
    }
}
