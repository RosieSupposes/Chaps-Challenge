package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp22.util.GameConstants;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameButton extends JButton {
    private Image image;
    private Dimension dim;

    public GameButton(String name, Dimension dim, ActionListener action) {
        super(name);
        this.dim = dim;
        setUp(dim, action);
    }

    public GameButton(String name, Dimension dim, ActionListener action, String filename) {
        super(name);
        setUp(dim, action);
        this.dim = dim;
        image = getIcon(filename);
//        this.setIcon(icon);
    }

    private void setUp(Dimension dim, ActionListener action) {
        this.setBackground(GameConstants.BUTTON_COLOR);
        this.setForeground(GameConstants.TEXT_COLOR);

        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.addActionListener(action);
    }

    private Image getIcon(String filename) {
        URL imagePath = this.getClass().getResource("/UI/" + filename + ".png");
        ImageIcon imageIcon = new ImageIcon(imagePath);
        return imageIcon.getImage();
//        Image image = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//        return new ImageIcon(image);
    }

    public void changeName(String name) {
        this.setText(name);
    }

    public void changeIcon(String filename) {
        image = getIcon(filename);
//        this.setIcon(icon);
    }

    public void changeActionListener(ActionListener actionListener) {
        this.removeActionListener(this.getActionListeners()[0]);
        this.addActionListener(actionListener);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        if (image != null) {
//            System.out.println("imaage width: " + image.getWidth(this));
            double scale = 0.45;
            int imgWidth = (int) (image.getWidth(this) * scale);
            int imgHeight = (int) (image.getHeight(this) * scale);
            int x = dim.width / 2 - imgWidth / 2;
            int y = dim.height / 2 - imgHeight / 2;
            g2D.drawImage(image, x, y, imgWidth, imgHeight, this);
        }
    }
}
