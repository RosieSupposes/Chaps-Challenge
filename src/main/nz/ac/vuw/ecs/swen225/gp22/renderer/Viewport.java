package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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

  private Player player;
  private final Tile[][] currentMaze;
  private final Tile[][] previousMaze; // the view of the maze before a player moves

  /**
   * Initialises a new maze upon the loading of a level.
   */
  public Viewport(){ 
    this.player = Maze.player;
    currentMaze = new Tile[GameDimensions.NUM_GAME_TILE][GameDimensions.NUM_GAME_TILE];
    previousMaze = new Tile[GameDimensions.NUM_GAME_TILE][GameDimensions.NUM_GAME_TILE]; 
  }

  /**
   * Displays all the tiles in the focus area.
   *
   * @param g Graphics object needed to render images on the canvas.
   * @param xOffset Offset in the X direction.
   * @param yOffset Offset in the Y direction.
   * @param maze The maze to be drawn on. 
   */
  public void renderTiles(Graphics g, int xOffset, int yOffset, Tile[][] maze){
    for (int x = 0; x < GameDimensions.NUM_GAME_TILE; x++){
        for (int y = 0; y < GameDimensions.NUM_GAME_TILE; y++){
            if (maze[x][y] != null) { // check that the tile is not null
                g.drawImage(getTileImg(Maze.getTile(new Maze.Point(x,y))), 
                (x*GameDimensions.TILE_SIZE) + xOffset, (y*GameDimensions.TILE_SIZE) + yOffset, this);
            }            
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
    if (tile instanceof InfoField){ 
      //TODO: display infofield details
      //displayInfo(tile);
      return Img.InfoField.image; 
    } 
    if (tile instanceof Exit){ return Img.Exit.image; } 
    if (tile instanceof LockedExit){ return Img.LockedExit.image; }
    if (tile instanceof Treasure){ return Img.Treasure.image; }
    if (tile instanceof Wall){ return Img.Wall.image; }

    return Img.FreeTile.image;
  }

  /**
   * Displays the information about the game that
   * the player needs to do in order to complete a level.
   * 
   * @param g Graphics object needed to render images on the canvas.
  */
  public void displayInfo(Graphics g){
    // if tile being stepped on is infofield then display game brief
    g.drawImage(Img.GameInfo.image, 100, 200, this); //TODO: make an image for infofield
  }

  /**
   * Displays all the entities moving on the tiles.
   * Updates the position and image of each entity that moves.
   *
   * @param g Graphics object needed to render images on the canvas.
   * @param xOffset Offset in the X direction.
   * @param yOffset Offset in the Y direction.
   */
  public void renderEntities(Graphics g, int xOffset, int yOffset){
    // draws the player based on the direction it is facing
    g.drawImage(getEntityImg(player.getDir(), true), getFocusX(player.getPos().x()*GameDimensions.TILE_SIZE),
    getFocusY(player.getPos().y()*GameDimensions.TILE_SIZE), this);
    
    //TODO: display the enemy for level 2
    //g.drawImage(getEntityImg(enemy.getDir()), getFocusX(player.getPos().x()*GameDimensions.TILE_SIZE),
    //getFocusY(player.getPos().y()*GameDimensions.TILE_SIZE), this);

  }

  /**
   * Returns a BufferedImage corresponding to the direction 
   * an entity is facing. 
   * 
   * @param dir The direction the entity is facing.
   * @param player Whether the entity is a player.
   * @return The image of an entity depending on the direction it is facing.
   */
  public BufferedImage getEntityImg(Entity.Direction dir, boolean player){
    if (player){
      // check for the facingDir of player then return image accordingly
      switch (dir){
        case Up: return Img.PlayerUp.image;
        case Down: return Img.PlayerDown.image;
        case Left: return Img.PlayerLeft.image; 
        case Right: return Img.PlayerRight.image;
        default: throw new IllegalArgumentException("Invalid direction./n");
      }
    }
    // check for the facingDir of the enemy
    switch (dir){
      case Up: return Img.EnemyUp.image;
      case Down: return Img.EnemyDown.image;
      case Left: return Img.EnemyLeft.image; 
      case Right: return Img.EnemyRight.image;
      default: throw new IllegalArgumentException("Invalid direction./n");
    }
  }

  /*
  * Renders a new maze that focuses on the tiles within 
  * the vicinity of the player's current location on the canvas. 
  */
  public void setFocusArea(){
    // loop through each tile in the maze to determine which are visible
    for (int xTick = 0, x = player.getPos().x() - GameDimensions.FOCUS_AREA; x <= player.getPos().x() + GameDimensions.FOCUS_AREA; xTick++, x++){
      for (int yTick = 0, y = player.getPos().y() - GameDimensions.FOCUS_AREA; y <= player.getPos().y() + GameDimensions.FOCUS_AREA; yTick++, y++){
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
  public int getFocusX(int x) { return GameDimensions.FOCUS_AREA + x - player.getPos().x() ; }
  
  /**
   * Convert the player's y position to the focus area's coordinates.
   *
   * @param y The player's y position.
   * @return The y position in the focus area.
   */
  public int getFocusY(int y) { return y - player.getPos().y() + GameDimensions.FOCUS_AREA; }

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

  
