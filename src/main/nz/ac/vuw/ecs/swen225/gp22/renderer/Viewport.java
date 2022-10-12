package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.util.GameConstants;

/**
 * This class displays the maze, and all the entities active in the current level
 * such as the player, free tiles, walls, keys, locked doors, treasures, locked exit, and exit.
 * 
 * @author Diana Batoon, 300475111 
 * @version 1.5
 */
public class Viewport extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Tile[][] currentMaze = new Tile[GameConstants.NUM_GAME_TILE][GameConstants.NUM_GAME_TILE]; // 9x9 maze displayed on screen
    private Timer timer;
    private int boundariesX;
    private int boundariesY;
    private JLabel infofield = new JLabel("");

    /**
     * Initialises a new maze upon the loading of a level.
     */
    public Viewport(){ 
        timer = new Timer(50, this);
        timer.start();
        boundariesX = Maze.getDimensions().x() - GameConstants.NUM_GAME_TILE;
        boundariesY = Maze.getDimensions().y() - GameConstants.NUM_GAME_TILE;
    }

    /**
     * Displays all the tiles and entities in the maze.
     * 
     * @param g Graphics object needed to render images on the canvas.
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g.create();
        for (int x = 0; x < GameConstants.NUM_GAME_TILE; x++){
            for (int y = 0; y < GameConstants.NUM_GAME_TILE; y++){
                // draw the tiles
                g2D.drawImage(getTileImg(currentMaze[x][y]), 
                x * GameConstants.TILE_SIZE,
                y * GameConstants.TILE_SIZE, this);
            }
        }

        int focusX = GameConstants.FOCUS_AREA;
        int focusY = GameConstants.FOCUS_AREA;

        int playerX = Maze.player.getPos().x();
        int playerY = Maze.player.getPos().y();

        if(playerX < focusX){ focusX = playerX; } // checking for the far left 
        else if(playerX > Maze.getDimensions().x() - focusX - 1){ focusX = playerX - boundariesX; } // checking for the far right
        
        if(playerY < focusY){ focusY = playerY; } // checking for the top
        else if(playerY > Maze.getDimensions().y() - focusY - 1){ focusY = playerY - boundariesY; } // checking for bottom
        
        focusX *= GameConstants.TILE_SIZE;
        focusY *= GameConstants.TILE_SIZE;

        // draw the player
        g2D.drawImage(getEntityImg(Maze.player.getDir()), focusX, focusY, this);

        // display infofield if the player steps on it
        if (Maze.getTile(new Maze.Point(playerX, playerY)) instanceof InfoField inField){
            displayInfo(inField, g2D);
        }

    }


    /**
     * Updates the display every 100 ms.  
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.isValid()){ 
            timer.stop(); // method doesn't get called anymore
            timer = null; // removes reference to the Viewport class
            return; 
        }
        Tile[][] tempMaze = new Tile[Maze.getDimensions().x()][Maze.getDimensions().y()];  
        int playerX = Maze.player.getPos().x();
        int playerY = Maze.player.getPos().y(); 

        setFocusArea(playerX, playerY, tempMaze);

        repaint();
    }

    /**
     * Renders a new maze that focuses on the tiles within 
     * the vicinity of the player's current location on the canvas. 
     * 
     * @param x x coordinate of the tile.
     * @param y y coordinate of the tile.
     * @param tempMaze the maze to be updated.
     */
    private void setFocusArea(int x, int y, Tile[][] tempMaze) {
        int focusX = x - GameConstants.FOCUS_AREA; 
        int focusY = y - GameConstants.FOCUS_AREA;

        // check for if the tiles are less than the focusX and focusY
        focusX = (focusX < 0) ? 0 : focusX;
        focusY = (focusY < 0) ? 0 : focusY; 

        focusX = (focusX > boundariesX) ? boundariesX : focusX;
        focusY = (focusY > boundariesY) ? boundariesY : focusY; 

        for(int i = 0; i < GameConstants.NUM_GAME_TILE; i++) {
            for(int j = 0; j < GameConstants.NUM_GAME_TILE; j++) {
                currentMaze[i][j] = Maze.getTile(new Maze.Point(i + focusX, j + focusY));
            }
        }
    }

    /**
     * Returns a BufferedImage corresponding to the type of tile in the maze.
     * 
     * @param tile The tile being drawn on the canvas.
     */
    public BufferedImage getTileImg(Tile tile){
        if (tile instanceof Key k){ // check the colours against the four different ones available
            switch (k.getColor()){
                case Blue: return Img.BlueKey.image;
                case Green: return Img.GreenKey.image;
                case Red: return Img.RedKey.image;
                case Yellow: return Img.YellowKey.image;
                default: throw new IllegalArgumentException("Invalid colour./n");
            }
        }            
        if (tile instanceof LockedDoor ld){
            switch (ld.getColor()){
                case Blue: return Img.BlueLockedDoor.image;
                case Green: return Img.GreenLockedDoor.image;
                case Red: return Img.RedLockedDoor.image;
                case Yellow: return Img.YellowLockedDoor.image;
                default: throw new IllegalArgumentException("Invalid colour./n");
            }
        }
        if (tile instanceof InfoField){ return Img.InfoField.image; } 
        if (tile instanceof Exit){ return Img.Exit.image; } 
        if (tile instanceof LockedExit){ return Img.LockedExit.image; }
        if (tile instanceof Treasure){ return Img.Treasure.image; }
        if (tile instanceof Wall){ return Img.Wall.image; }

        return Img.FreeTile.image;
    }

    /**
     * Returns a BufferedImage corresponding to the direction 
     * an entity is facing. 
     * 
     * @param dir The direction the entity is facing.
     * @return The image of an entity depending on the direction it is facing.
     */
    public BufferedImage getEntityImg(Entity.Direction dir){
        // check for the facingDir of player then return image accordingly
        switch (dir){
            case Up: return Img.PlayerUp.image;
            case Down: return Img.PlayerDown.image;
            case Left: return Img.PlayerLeft.image; 
            case Right: return Img.PlayerRight.image;
            default: throw new IllegalArgumentException("Invalid direction./n");
        }
    }

    /**
     * When the player is stepping on the InfoField, 
     * the information about the game that the player 
     * needs to do in order to complete a level will be displayed.
     * 
     * @param g Graphics object needed to render images on the canvas.
     */
    public void displayInfo(InfoField iField, Graphics g){
        int infoPos = (GameConstants.NUM_GAME_TILE*GameConstants.TILE_SIZE)/9;
        g.drawImage(Img.InfoPost.image, infoPos, 2*infoPos, this);
        infofield.setText(iField.getText());
        infofield.setBounds(infoPos+ 10, (2*infoPos)+10, Img.InfoPost.image.getWidth()-100, Img.InfoPost.image.getHeight()-100);
        infofield.setFont(new Font("Verdana", Font.BOLD, 20));
    }

}
