package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.util.ArrayList;

/**
 * This class displays the remaining time and treasures left
 * for this game level, and the items in the player's inventory.
 * 
 * @author Diana Batoon, 300475111 
 * @version 1.1
 */
public class SidePanel extends JPanel implements ActionListener {
    private ArrayList<Tile> inventory = new ArrayList<>();
    private int treasuresLeft;

    private Timer timer;
    private int gameLevel = 0;

    private int numColsTiles = 4;
    private int numRowsTiles = 2;
    private final Tile[][] currentInventory;
    private final Tile[][] previousInventory;

    /***
     * Initialises a new side panel upon the loading of a level.
     * @param timer The timer counting down the remaining time for the level.
    */
    public SidePanel(Timer timer, int gameLevel){ 
        this.timer = timer; // 
        this.gameLevel = gameLevel;
        this.treasuresLeft = Maze.getTreasuresLeft();
        //this.inventory = Maze.player.getAllKeys();

        currentInventory = new Tile[numRowsTiles][numColsTiles];
        previousInventory = new Tile[numRowsTiles][numColsTiles];
    }

    public void renderInventory(Graphics g){
        // loop through each item in the inventory list
        // check its type then draw the image on the inventory
        for (int x = 0; x < numRowsTiles; x++){
            for (int y = 0; y < numColsTiles; y++){
                if (currentInventory[x][y] == null){
                    for (Tile t: inventory){
                        g.drawImage(getTileImg(t), x*GameDimensions.TILE_SIZE, y*GameDimensions.TILE_SIZE, this);
                    }
                }
            }
        }
    }

    /***
     * 
     * @param tile The item that has been collected by the player.
     * @return The image of the item that will be displayed in the inventory.
     */
    private BufferedImage getTileImg(Tile tile) {
        if (tile instanceof Key k){ // check the colours against the four different ones available
            switch (k.getColor()){
              case Blue: return Img.BlueKey.image;
              case Green: return Img.GreenKey.image;
              case Red: return Img.RedKey.image;
              case Yellow: return Img.YellowKey.image;
              default: throw new IllegalArgumentException("Invalid colour./n");
            }
        }
        
        return Img.Treasure.image; 
    }

    /*** 
    * Displays the current game level, the number of treasures yet to be collected,
    * and the remaining time left to complete the level. 
    */
    public void displayLevelInfo(){
        JFrame frame = new JFrame("Side Panel");
        JPanel panel = new JPanel();

        // labels
        JLabel Level = new JLabel("Level: " + gameLevel);
        JLabel time = new JLabel("Timer" + timer);
        JLabel uncollectedTreasures = new JLabel("Treasures Left: " + treasuresLeft);

        panel.add(Level);
        panel.add(time);
        panel.add(uncollectedTreasures);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        
        renderInventory(g2D);

        g2D.dispose(); // releases the graphics object
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        updateUI();
    }
    
}
