package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
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

  private final Tile[][] currentMaze;
  private final Tile[][] previousMaze; // the view of the maze before a player moves

  /**
   * Initialises a new maze upon the loading of a level.
   */
  public Viewport(){ 
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
    if (tile instanceof InfoField){ return Img.InfoField.image; } 
    if (tile instanceof Exit){ return Img.Exit.image; } 
    if (tile instanceof LockedExit){ return Img.LockedExit.image; }
    if (tile instanceof Treasure){ return Img.Treasure.image; }
    if (tile instanceof Wall){ return Img.Wall.image; }

    return Img.FreeTile.image;
  }

  /**
   * When the player is stepping on the InfoField, 
   * the information about the game that the player 
   * needs to do in order to complete a level will be displayed.
   * 
   * @param g Graphics object needed to render images on the canvas.
  */
  public void displayInfo(Tile t, Graphics g){
    g.drawImage(Img.GameInfo.image, 100, 200, this); 
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
    g.drawImage(getEntityImg(Maze.player.getDir(), true), getFocusX(Maze.player.getPos().x()*GameDimensions.TILE_SIZE),
    getFocusY(Maze.player.getPos().y()*GameDimensions.TILE_SIZE), this);
    
    //TODO: display the enemy for level 2
    for (Entity enemy: Maze.entities){
      g.drawImage(Img.EnemyRight.image, enemy.getPos().x()*GameDimensions.TILE_SIZE, enemy.getPos().y()*GameDimensions.TILE_SIZE, this);
    }
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
    // check for the facingDir of the enemy for level 2
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
    for (int xTick = 0, x = Maze.player.getPos().x() - GameDimensions.FOCUS_AREA; x <= Maze.player.getPos().x() + GameDimensions.FOCUS_AREA; xTick++, x++){
      for (int yTick = 0, y = Maze.player.getPos().y() - GameDimensions.FOCUS_AREA; y <= Maze.player.getPos().y() + GameDimensions.FOCUS_AREA; yTick++, y++){
        previousMaze[xTick][yTick] = currentMaze[xTick][yTick];
        
        // Check if tiles are within range
        if ((x > 0 && y > 0) && (x < currentMaze.length && y < currentMaze[x].length)) {
          currentMaze[xTick][yTick] = Maze.getTile(new Maze.Point(x, y));
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Tile[][] temp = new Tile[GameDimensions.NUM_GAME_TILE][GameDimensions.NUM_GAME_TILE];
    for (int x =0; x < temp.length; x++){
      for(int y =0; y < temp.length; y++){
        if (Maze.player instanceof Player){
          setFocusArea(x,y, temp);
        }
      }
    }
    repaint();
    updateUI();
  }

  public void setFocusArea(int x, int y, Tile[][] temp){
    int focusX = x-GameDimensions.FOCUS_AREA;
    int focusY = y-GameDimensions.FOCUS_AREA;

    for (int i = 0; i < GameDimensions.NUM_GAME_TILE; i++){
      for (int j = 0; j < GameDimensions.NUM_GAME_TILE; j++){
        currentMaze[i][j] = temp[i+focusX][j+focusY];
      }

    }
  }


  /**
   * Convert the player's x position to the focus area's coordinates.
   *
   * @param x The player's x position.
   * @return The x position in the focus area.
   */
  public int getFocusX(int x) { return GameDimensions.FOCUS_AREA + x - Maze.player.getPos().x() ; }
  
  /**
   * Convert the player's y position to the focus area's coordinates.
   *
   * @param y The player's y position.
   * @return The y position in the focus area.
   */
  public int getFocusY(int y) { return y - Maze.player.getPos().y() + GameDimensions.FOCUS_AREA; }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g.create();
    int xOffset = 0;
    int yOffset = 0;

    setFocusArea(); // TODO: replace with new focusarea
    renderTiles(g2D, xOffset, yOffset, currentMaze); //TODO: make offset values change depending on the frame
    renderEntities(g, xOffset, yOffset);
    
    //g2D.dispose(); // releases the graphics object
  }

}

  
