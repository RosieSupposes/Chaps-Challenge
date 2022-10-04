package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import nz.ac.vuw.ecs.swen225.gp22.domain.ColorableTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.ColorableTile.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class displays the remaining time and treasures left
 * for this game level, and the items in the player's inventory.
 * 
 * @author Diana Batoon, 300475111 
 * @version 1.1
 */
public class SidePanel extends JPanel implements ActionListener {

    private int numColsTiles = 3;
    private int numRowsTiles = 4;

    private int xOffset = 30;
    private int yOffset = 350;
    private int yTextOffset = 30;

    private JLabel level = new JLabel("");
    private JLabel time = new JLabel("");
    private JLabel uncollectedTreasures = new JLabel("");
    private JLabel inventory = new JLabel("");

    private int timer;
    private int lvl;

    /***
     * Initialises a new side panel upon the loading of a level.
     * @param timer The timer counting down the remaining time for the level.
    */
    public SidePanel(int time, int lvl){ 
        this.timer = time;
        this.lvl = lvl;
    }

    /**
     * Draws the panel displaying the game level, timer, keys collected, 
     * and the number of treasures left. 
     * 
     * @param g The graphics object needed to render images on the canvas.
     */
    public void renderSidePanel(Graphics g){
        Image scaledImage = Img.Title.image.getScaledInstance(getWidth()-(xOffset*2), getHeight()/7,Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, xOffset, 10, this);
        int imgHeight = scaledImage.getHeight(this);

        level.setText("Level: " + lvl);
        time.setText("Time: "+ timer);
        uncollectedTreasures.setText("Treasures Left: " + Maze.getTreasuresLeft());
        inventory.setText("Inventory");

        level.setBounds(xOffset, imgHeight+yTextOffset, getWidth(), 50);
        time.setBounds(xOffset, imgHeight+(2*yTextOffset), getWidth(), 100);
        uncollectedTreasures.setBounds(xOffset, imgHeight+(4*yTextOffset), getWidth(), 100);
        inventory.setBounds(xOffset*3, imgHeight+(6*yTextOffset), getWidth(), 100);

        setLabelFont(level);
        setLabelFont(time);
        setLabelFont(uncollectedTreasures);
        setLabelFont(inventory);

        // inventory is a 3x4 grid
        for (int x = 0; x < numRowsTiles; x++){
            for (int y = 0; y < numColsTiles; y++){   
                g.drawImage(Img.Wall.image, x*GameDimensions.TILE_SIZE+xOffset, y*GameDimensions.TILE_SIZE+yOffset, this);
            }
        }

        // put the keys side by side on the inventory tiles
        int keyXPos = 0;
        int keyJPos = -1;
        for (int x = 0; x < Maze.player.getAllKeys().size(); x++){
            if (x % 2 == 0){ keyJPos++; }
            BufferedImage key = getKeyImg(Maze.player.getAllKeys().get(x)); // check the key colour
            g.drawImage(key, x*GameDimensions.TILE_SIZE+xOffset, keyJPos*GameDimensions.TILE_SIZE+yOffset, this);
            keyXPos = (keyXPos == 0 ? 1 : 0); // if a tile is occuppied, place the next collected key to the right side
        }

        add(level);
        add(time);
        add(uncollectedTreasures);
        add(inventory);
    }

    /**
     * Updates the timer when the gameTimer in Base indicates.
     * 
     * @param timer The current time of the game.
     */
    public void setTime(int timer){ this.timer = timer; }

    /**
     * Sets the font style and size of a JLabel.
     * @param jl The JLabel to whose font style and size to be set.
     */
    public void setLabelFont(JLabel jl){ jl.setFont(new Font("Verdana", Font.BOLD, 20)); }

    /***
     * 
     * @param c The key that has been collected by the player.
     * @return The image of the key that will be displayed in the inventory.
     */
    private BufferedImage getKeyImg(Color c) {
        if (c instanceof ColorableTile.Color col){ // check the colours against the four different ones available
            switch (col){
              case Blue: return Img.BlueKeyNB.image;
              case Green: return Img.GreenKeyNB.image;
              case Red: return Img.RedKeyNB.image;
              case Yellow: return Img.YellowKeyNB.image;
              default: throw new IllegalArgumentException("Invalid colour./n");
            }
        }
        return Img.Wall.image;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();        
        renderSidePanel(g2D);
        g2D.dispose(); // releases the graphics object
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        updateUI();
    }
    
}
