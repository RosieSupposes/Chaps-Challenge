package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;

/**
 * This class displays the maze, and all the entities active in the current level
 * such as the player, free tiles, walls, keys, locked doors, treasures, locked exit, and exit.
 * 
 * @author Diana Batoon, 300475111 
 * @version 1.4
 */
public class Viewport extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Tile[][] currentMaze = new Tile[GameConstants.NUM_GAME_TILE][GameConstants.NUM_GAME_TILE];
    private Timer timer;

    /**
     * Initialises a new maze upon the loading of a level.
     */
    public Viewport(){ 
        for (int x = 0 ; x < GameConstants.NUM_GAME_TILE; x++){
            for (int y = 0; y < GameConstants.NUM_GAME_TILE; y++){
                currentMaze[x][y] = Maze.getTile(new Maze.Point(x, y));
            }
        }

        timer = new Timer(300, this);
        timer.start();
    }
    
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D)g.create();
        for (int x = 0; x < GameConstants.NUM_GAME_TILE; x++){
            for (int y = 0; y < GameConstants.NUM_GAME_TILE; y++){
                // draw the tiles
                g2D.drawImage(getTileImg(Maze.getTile(new Maze.Point(x, y))), 
                x * GameConstants.TILE_SIZE,
                y * GameConstants.TILE_SIZE, this);

                // draw the player
                g2D.drawImage(getEntityImg(Maze.player.getDir()), 
                Maze.player.getPos().x() * GameConstants.TILE_SIZE,
                Maze.player.getPos().y() * GameConstants.TILE_SIZE, this);
            }
        }
    }

    /*** Updates every 100 ms */
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: only works when the timer starts
        //Tile[][] tempMaze = new Tile[Maze.getDimensions().x()][Maze.getDimensions().y()];
        Tile[][] tempMaze = new Tile[GameConstants.NUM_GAME_TILE][GameConstants.NUM_GAME_TILE];
        int playerX = Maze.player.getPos().x();
        int playerY = Maze.player.getPos().y();

        //System.out.println("Action Performed.");

        for(int x = 0; x < tempMaze.length; x++) {
            for(int y = 0; y < tempMaze[x].length; y++) {
                //System.out.println("Second for loop.");
                // show only the tiles in the player's vicinity
                if (x == playerX && y == playerY){
                    setFocusArea(x,y,tempMaze); //TODO: throws an error
                    //System.out.println("Reached focus area.");
                }
            }
        }
        repaint();
    }

    private void setFocusArea(int x, int y, Tile[][] tempMaze) {
        int focusX = x - GameConstants.FOCUS_AREA; 
        int focusY = y - GameConstants.FOCUS_AREA;

        for(int i = 0; i < GameConstants.NUM_GAME_TILE; i++) {
            for(int j = 0; j < GameConstants.NUM_GAME_TILE; j++) {
                currentMaze[i][j] = tempMaze[i + focusX][j+ focusY];
                //System.out.println("Update focus area.");
            }
        }
    }

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

}