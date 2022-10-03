package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nz.ac.vuw.ecs.swen225.gp22.domain.ColorableTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.ColorableTile.Color;

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
    private Timer timer;
    private int gameLevel = 0;

    private int numColsTiles = 5;
    private int numRowsTiles = 2;

    // private int inventoryX = GameDimensions.TILE_SIZE*5;
    // private int inventoryY = GameDimensions.TILE_SIZE*7;

    private final Tile[][] currentInventory;
    private final Tile[][] previousInventory;

    /***
     * Initialises a new side panel upon the loading of a level.
     * @param timer The timer counting down the remaining time for the level.
    */
    // public SidePanel(Timer timer, int gameLevel){ 
        public SidePanel(Timer timer){ 
        this.timer = timer; // 
        //this.gameLevel = gameLevel;

        currentInventory = new Tile[numRowsTiles][numColsTiles];
        previousInventory = new Tile[numRowsTiles][numColsTiles];
    }

    public void renderInventory(Graphics g){
        // title image
        
        System.out.println(Img.Title.image);
        //System.out.println("Title");
        for (int x = 0; x < numRowsTiles; x++){
            for (int y = 0; y < numColsTiles; y++){
                
                //if (currentInventory[x][y] == null){
                    for (Color c: Maze.player.getAllKeys()){
                        //System.out.println("Key collected.");
                        g.drawImage(Img.Title.image, GameDimensions.SIDEBAR_WIDTH, GameDimensions.SIDEBAR_WIDTH, this);
                        //g.drawImage(getKeyImg(c), x*GameDimensions.TILE_SIZE, y*GameDimensions.TILE_SIZE, this);
                    }
                //}
            }
        }
        
    }

    /***
     * 
     * @param tile The key that has been collected by the player.
     * @return The image of the key that will be displayed in the inventory.
     */
    private BufferedImage getKeyImg(Color c) {
        if (c instanceof ColorableTile.Color col){ // check the colours against the four different ones available
            switch (col){
              case Blue: return Img.BlueKey.image;
              case Green: return Img.GreenKey.image;
              case Red: return Img.RedKey.image;
              case Yellow: return Img.YellowKey.image;
              default: throw new IllegalArgumentException("Invalid colour./n");
            }
        }
        return Img.FreeTile.image;
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
        JLabel time = new JLabel("Time: " + timer);
        JLabel uncollectedTreasures = new JLabel("Treasures Left: " + Maze.getTreasuresLeft());

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
        displayLevelInfo();
        g2D.dispose(); // releases the graphics object
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        updateUI();
    }
    
}
