package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import java.awt.Dimension;

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

  public static final int TILE_SIZE = 60;
  public static final int NUM_GAME_TILE = 9;
  public static final int FOCUS_AREA = NUM_GAME_TILE/2;
  public static final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
  public static final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
  public static final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
  public static final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;

  public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + 65); //for whatever reason?

  private Player player;
  private final Tile[][] currentMaze;
  private final Tile[][] previousMaze; // the view of the maze before a player moves

  /**
   * Initialises a new maze upon the loading of a level.
   */
  public Viewport(){ 
    this.player = Maze.player;
    currentMaze = new Tile[NUM_GAME_TILE][NUM_GAME_TILE];
    previousMaze = new Tile[NUM_GAME_TILE][NUM_GAME_TILE]; 
  }

  /**
   * Displays all the tiles in the focus area.
   *
   * @param g Graphics object needed to render images on the canvas.
   * @param xOffset Offset in the X direction.
   * @param yOffset Offset in the Y direction.
   * @param maze The maze to be drawn on. 
   */
  private void renderTiles(Graphics g, int xOffset, int yOffset, Tile[][] maze){
    for (int x = 0; x < NUM_GAME_TILE; x++){
        for (int y = 0; y < NUM_GAME_TILE; y++){
            if (maze[x][y] != null) { // check that the tile is not null
                g.drawImage(getTileImg(Maze.getTile(new Maze.Point(x,y))), 
                (x*TILE_SIZE) + xOffset, (y*TILE_SIZE) + yOffset, this);
            }            
        }
    }
  }

/**
 * Returns a BufferedImage corresponding to the type of tile in the maze.
 * 
 * @param tile The tile being drawn on the canvas.
*/
  private BufferedImage getTileImg(Tile tile){
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
    if (tile instanceof InfoField){ return Img.InfoField.image; } //TODO: display infofield details
    if (tile instanceof Exit){ return Img.Exit.image; } 
    if (tile instanceof LockedExit){ return Img.LockedExit.image; }
    if (tile instanceof Treasure){ return Img.Treasure.image; }
    if (tile instanceof Wall){ return Img.Wall.image; }

    return Img.FreeTile.image;
  }

  /**
   * Displays all the entities moving on the tiles.
   * Updates the position and image of each entity that moves.
   *
   * @param g Graphics object needed to render images on the canvas.
   * @param xOffset Offset in the X direction.
   * @param yOffset Offset in the Y direction.
   */
  private void renderEntities(Graphics g, int xOffset, int yOffset){
    // draws the player based on the direction it is facing
    g.drawImage(getEntityImg(player.getDir()), getFocusX(player.getPos().x()*TILE_SIZE),
    getFocusY(player.getPos().y()*TILE_SIZE), this);
    
    //TODO: display the enemy for level 2
      
  }

  /**
   * Returns a BufferedImage corresponding to the direction 
   * the player is facing. 
   * 
   * @param dir The direction the player is facing.
   */
  private BufferedImage getEntityImg(Entity.Direction dir){
    // check for the facingDir of player then return image accordingly
    switch (dir){
      case Up: return Img.PlayerUp.image;
      case Down: return Img.PlayerDown.image;
      case Left: return Img.PlayerLeft.image; 
      case Right: return Img.PlayerRight.image;
      default: throw new IllegalArgumentException("Invalid direction./n");
    }
  }

  /*
  * Renders a new maze that focuses on the tiles within 
  * the vicinity of the player's current location on the canvas. 
  */
  private void setFocusArea(){
    // loop through each tile in the maze to determine which are visible
    for (int xTick = 0, x = player.getPos().x() - FOCUS_AREA; x <= player.getPos().x() + FOCUS_AREA; xTick++, x++){
      for (int yTick = 0, y = player.getPos().y() - FOCUS_AREA; y <= player.getPos().y() + FOCUS_AREA; yTick++, y++){
        previousMaze[xTick][yTick] = currentMaze[xTick][yTick];
        
        // Check if tiles are within range
        if ((x > 0 && y > 0) && (x < currentMaze.length && y < currentMaze[x].length)) {
          currentMaze[xTick][yTick] = Maze.getTile(new Maze.Point(x, y));
        }
      }
    }
  }

  /**
   * Convert the player's x position to the focus area's coordinates.
   *
   * @param x The player's x position.
   * @return The x position in the focus area.
   */
  private int getFocusX(int x) { return FOCUS_AREA + x - player.getPos().x() ; }
  
  /**
   * Convert the player's y position to the focus area's coordinates.
   *
   * @param y The player's y position.
   * @return The y position in the focus area.
   */
  private int getFocusY(int y) { return y - player.getPos().y() + FOCUS_AREA; }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g.create();
    int xOffset = 0;
    int yOffset = 0;

    setFocusArea(); // TODO: 
    renderTiles(g2D, xOffset, yOffset, currentMaze); //TODO: make offset values change depending on the frame
    renderEntities(g, xOffset, yOffset);
    
    g2D.dispose(); // releases the graphics object
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
    updateUI();
  }

}

  
