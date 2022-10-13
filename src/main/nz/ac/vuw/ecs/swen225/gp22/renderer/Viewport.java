package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.HashMap;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.SFX.Sounds;
import nz.ac.vuw.ecs.swen225.gp22.util.GameConstants;

/**
 * This class displays the maze, and all the entities active in the current level
 * such as the player, free tiles, walls, keys, locked doors, treasures, locked exit, and exit.
 * 
 * @author Diana Batoon, 300475111 
 * @version 1.6
 */
public class Viewport extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Tile[][] currentMaze = new Tile[GameConstants.NUM_GAME_TILE][GameConstants.NUM_GAME_TILE]; // 9x9 maze displayed on screen

    private Timer timer;
    private int boundariesX;
    private int boundariesY;
    private JLabel infofield = new JLabel("");
    private SFXPlayer sfxPlayer = new SFXPlayer();
    private final HashMap<String, SFX> soundList = new HashMap<>();
    private List<Entity.Action.Interaction.ActionType> actions;

    /**
     * Initialises a new maze upon the loading of a level.
     */
    public Viewport(){ 
        timer = new Timer(50, this);
        timer.start();
        boundariesX = Maze.getDimensions().x() - GameConstants.NUM_GAME_TILE;
        boundariesY = Maze.getDimensions().y() - GameConstants.NUM_GAME_TILE;

        //load wav files
        try{
            for (SFX.Sounds sfx : Sounds.values()){
                soundList.put(sfx.toString(), new SFX(sfx.toString()));
            }
        }
        catch(Exception e){ e.printStackTrace(); }
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

        Tile playerTile = Maze.getTile(new Maze.Point(playerX, playerY));

        if(playerX < focusX){ focusX = playerX; } // checking for the far left 
        else if(playerX > Maze.getDimensions().x() - focusX - 1){ focusX = playerX - boundariesX; } // checking for the far right
        
        if(playerY < focusY){ focusY = playerY; } // checking for the top
        else if(playerY > Maze.getDimensions().y() - focusY - 1){ focusY = playerY - boundariesY; } // checking for bottom

        focusX *= GameConstants.TILE_SIZE;
        focusY *= GameConstants.TILE_SIZE;

        // draw the player
        g2D.drawImage(getEntityImg(Maze.player.getDir()), focusX, focusY, this);

        // display infofield if the player steps on it
        //if (playerTile instanceof InfoField inField){ getInfoField(inField, g2D); }

        //renderEnemies(playerX, playerY, g2D);
        Maze.Point focusPoint = getFocusArea(playerX, playerY); // the point that the maze should be centered on

        // drawing the enemies
        for(Entity e: Maze.entities){
            EnemyEntity enemy = (EnemyEntity) e;
            int enemyX = enemy.getPos().x();
            int enemyY = enemy.getPos().y();

            int rows = focusPoint.x() + GameConstants.NUM_GAME_TILE-1;
            int cols = focusPoint.y() + GameConstants.NUM_GAME_TILE-1;

            // check that the enemy is within the current the focus area
            if (focusPoint.x() <= enemyX && rows >= enemyX
            && focusPoint.y() <= enemyX && cols >= enemyY){
                g2D.drawImage(EnemyEntity.imageMap.get(enemy.getDir()),
                        (enemyX - focusPoint.x()) * GameConstants.TILE_SIZE,
                        (enemyY - focusPoint.y()) * GameConstants.TILE_SIZE, this);
            }
        }

        // play sounds based on the player's actions and game status
        try{ 
            for (Entity.Action.Interaction.ActionType a : actions){
                checkAction(a); 
            }
            //checkAction();
            
        }
        catch(Exception ex){ ex.printStackTrace(); }

    }

    /**
     * Plays a sound based on the action that takes place in the game.
     * @param action Action performed.
     */
    public void checkAction(Entity.Action.Interaction.ActionType a){
        switch (a){
            case PickupKey -> playSFX("CollectItem", 1);
            case PickupTreasure -> playSFX("CollectItem", 1); 
            case UnlockDoor -> playSFX("Unlock", 1); 
            case UnlockExit -> playSFX("Unlock", 1);
            default -> playSFX("Background", 1);
        }
    }
    
    /**
     * Plays a sound.
     * 
     * @param soundName The name of the sound.
     * @param priorityLevel Which sound must be played over others.
     */
    public void playSFX(String soundName, int priorityLevel){
        sfxPlayer.playSound(soundList.get(soundName), priorityLevel);
    }

    /***
     * Stores the action that has happened in the game. 
     * @param action Action performed.
     */
    public void setAction(List<Entity.Action.Interaction.ActionType> actions){ this.actions = actions; }

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

        int playerX = Maze.player.getPos().x();
        int playerY = Maze.player.getPos().y();

        Maze.Point mazePoint = getFocusArea(playerX, playerY); 
        setCurrentMaze(mazePoint); // get the new 9x9 tiles to be displayed

        repaint();
    }

    /**
     * Renders a new Maze.Point that focuses on the tiles within
     * the vicinity of the player's current location on the canvas.
     *
     * @param x x coordinate of the player.
     * @param y y coordinate of the player.
     */
    private Maze.Point getFocusArea(int x, int y){
        int focusX = x - GameConstants.FOCUS_AREA;
        int focusY = y - GameConstants.FOCUS_AREA;

        // checking for boundary cases
        focusX = Math.max(focusX, 0);
        focusY = Math.max(focusY, 0);

        focusX = Math.min(focusX, boundariesX);
        focusY = Math.min(focusY, boundariesY);

        return new Maze.Point(focusX, focusY);
    }

    /**
     * Displays a 9x9 maze that shows the tiles surrounding the Player.
     *
     * @param point A point to be displayed in the maze.
     */
    private void setCurrentMaze(Maze.Point point){
        for(int i = 0; i < GameConstants.NUM_GAME_TILE; i++) {
            for(int j = 0; j < GameConstants.NUM_GAME_TILE; j++) {
                currentMaze[i][j] = Maze.getTile(new Maze.Point(i + point.x(), j + point.y()));
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
        if (tile instanceof BouncyPad bp){ 
            switch (bp.getDir()){
                case Up: return Img.BouncyPadUp.image;
                case Down: return Img.BouncyPadDown.image;
                case Left: return Img.BouncyPadLeft.image; 
                case Right: return Img.BouncyPadRight.image;
                default: throw new IllegalArgumentException("Invalid direction./n");
            }
            
        }
        if (tile instanceof InfoField){ return Img.InfoField.image; } 
        if (tile instanceof Exit){ return Img.Exit.image; } 
        if (tile instanceof LockedExit){ return Img.LockedExit.image; }
        if (tile instanceof MilkPuddle){ return Img.MilkPuddle.image; }
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
    public void getInfoField(InfoField iField, Graphics g){
        int infoPos = (GameConstants.NUM_GAME_TILE*GameConstants.TILE_SIZE)/9;
        g.drawImage(Img.InfoPost.image, infoPos, 5*infoPos, this);
        infofield.setText(iField.getText());
        //System.out.println(""+iField.getText());
        infofield.setBounds(infoPos+ 10, (2*infoPos)+10, Img.InfoPost.image.getWidth()-100, Img.InfoPost.image.getHeight()-100);
        infofield.setFont(new Font("Verdana", Font.BOLD, 20));
        add(infofield);
    }

    public void renderEnemies(int playerX, int playerY, Graphics g){
        Maze.Point focusPoint = getFocusArea(playerX, playerY); // the point that the maze should be centered on

        // drawing the enemies
        for(Entity e: Maze.entities){
            EnemyEntity enemy = (EnemyEntity) e;
            int enemyX = enemy.getPos().x();
            int enemyY = enemy.getPos().y();

            int rows = focusPoint.x() + GameConstants.NUM_GAME_TILE-1;
            int cols = focusPoint.y() + GameConstants.NUM_GAME_TILE-1;

            // check that the enemy is within the current the focus area
            if (focusPoint.x() <= enemyX && rows >= enemyX
            && focusPoint.y() <= enemyX && cols >= enemyY){
                g.drawImage(EnemyEntity.imageMap.get(enemy.getDir()),
                        (enemyX - focusPoint.x()) * GameConstants.TILE_SIZE,
                        (enemyY - focusPoint.y()) * GameConstants.TILE_SIZE, this);
            }
        }
    }

}
