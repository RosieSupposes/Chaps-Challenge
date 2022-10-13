package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.util.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Side panel for menu.
 *
 * @author Molly Henry, 300562038
 * @version 1.2
 */
public class ImagePanel extends JPanel {
	BufferedImage image; //image on panel
	int width; //width of panel
	int height; //height of panel

	int offsetX; //image x offset for panel
	int offsetY; //image y offset for panel

	/**
	 * Side panel for menu.
	 *
	 * @param filename of image
	 * @param dim      size of panel
	 * @param scale    scale of image
	 */
	public ImagePanel(String filename, Dimension dim, double scale) {
		this.setBackground(GameConstants.BG_COLOR);

		width = (int) (scale * dim.width);
		height = (int) (scale * dim.height);

		this.offsetX = (dim.width - width) / 2;
		this.offsetY = (dim.height - height) / 2;

		URL imagePath = this.getClass().getResource("/UI/" + filename + ".png");
		try {
			image = ImageIO.read(imagePath);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g.create();
		g2D.drawImage(image, offsetX, offsetY, width, height, this);
		g2D.dispose();
	}
}
