package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Img;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Side panel for menu
 *
 * @author Molly Henry, 300562038
 * @version 1.2
 */
public class ImagePanel extends JPanel {
    BufferedImage image;
    int width;
    int height;

    Dimension offset;

    /**
     * Side panel for menu
     */
    public ImagePanel(String filename, Dimension dim, Dimension offset) {
        this.setBackground(Main.BG_COLOR);

        double scale = 0.8;
        width = (int) (scale * dim.width);
        height = (int) (scale * dim.height);

        this.offset = offset;

        URL imagePath = this.getClass().getResource("/UI/" + filename + ".png");
        try {
            image = ImageIO.read(imagePath);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.drawImage(image, offset.width, offset.height, width, height, this); // see javadoc for more info on the parameters
        g2D.dispose();
    }
}