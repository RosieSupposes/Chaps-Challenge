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

  private Player player;
  private final Tile[][] currentMaze;
  private final Tile[][] previousMaze; // the view of the maze before a player moves

  /**
   * Initialises a new maze upon the loading of a level.
   */
  public Viewport(){ 
    this.player = Maze.player;
    currentMaze = new Tile[GameDimensions.getTileNum()][GameDimensions.getTileNum()];
    previousMaze = new Tile[GameDimensions.getTileNum()][GameDimensions.getTileNum()]; 
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
    for (int x = 0; x < GameDimensions.getTileNum(); x++){
        for (int y = 0; y < GameDimensions.getTileNum(); y++){
            if (maze[x][y] != null) { // check that the tile is not null
                g.drawImage(getTileImg(Maze.getTile(new Maze.Point(x,y))), 
                (x*GameDimensions.getTileSize()) + xOffset, (y*GameDimensions.getTileSize()) + yOffset, this);
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
    g.drawImage(getEntityImg(player.getDir()), getFocusX(player.getPos().x()*GameDimensions.getTileSize()),
    getFocusY(player.getPos().y()*GameDimensions.getTileSize()), this);
    
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
    for (int xTick = 0, x = player.getPos().x() - GameDimensions.getFocusArea(); x <= player.getPos().x() + GameDimensions.getFocusArea(); xTick++, x++){
      for (int yTick = 0, y = player.getPos().y() - GameDimensions.getFocusArea(); y <= player.getPos().y() + GameDimensions.getFocusArea(); yTick++, y++){
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
  private int getFocusX(int x) { return GameDimensions.getFocusArea() + x - player.getPos().x() ; }
  
  /**
   * Convert the player's y position to the focus area's coordinates.
   *
   * @param y The player's y position.
   * @return The y position in the focus area.
   */
  private int getFocusY(int y) { return y - player.getPos().y() + GameDimensions.getFocusArea(); }

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

  public static class GameDimensions {
    // maze dimensions
    private static final int TILE_SIZE = 60;
    private static final int NUM_GAME_TILE = 9;
    private static final int FOCUS_AREA = NUM_GAME_TILE/2;

    // window dimensions
    private static final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    private static final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    private static final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;
    private static final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    private static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + 65);

    /*** @return The size of a tile. */
    public static int getTileSize(){ return TILE_SIZE; }

    /*** @return The number of tiles per row and column. */
    public static int getTileNum(){ return NUM_GAME_TILE; }

    /*** @return The area of the maze being displayed on the window. */
    public static int getFocusArea() { return FOCUS_AREA; }

    /*** @return The width of the window's sidebar. */
    public static int getSideBarWidth(){ return SIDEBAR_WIDTH; }

    /*** @return The height of the window. */
    public static int getWindowHeight(){ return WINDOW_HEIGHT; }

    /*** @return The width of the window. */
    public static int getWindowWidth(){ return WINDOW_WIDTH; }

    /*** @return The size of the game window. */
    public static int getGameWindowSize(){ return GAME_WINDOW_SIZE; }

    /*** @return The size of the window in the menu. */
    public static Dimension getWindowSize(){ return WINDOW_SIZE; }
    
  }
}

  
