package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameButton extends JButton {
    public GameButton(String name, Dimension dim, ActionListener action){
        super(name);
        setUp(dim,action);
    }

    public GameButton(String name, Dimension dim, ActionListener action, String filename){
        super(name);
        setUp(dim,action);
        ImageIcon icon = getIcon(filename);
        this.setIcon(icon);
    }

    public void setUp(Dimension dim, ActionListener action){
        this.setBackground(Main.BUTTON_COLOR);
        this.setForeground(Main.TEXT_COLOR);

        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.addActionListener(action);
    }

    public ImageIcon getIcon(String filename) {
        URL imagePath = this.getClass().getResource("/UI/" + filename + ".png");
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image img = imageIcon.getImage();
        Image image = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public void changeName(String name){
        this.setText(name);
    }

    public void changeIcon(String filename){
        ImageIcon icon = getIcon(filename);
        this.setIcon(icon);
    }

    public void changeActionListener(ActionListener actionListener){
        this.removeActionListener(this.getActionListeners()[0]);
        this.addActionListener(actionListener);
    }
}
