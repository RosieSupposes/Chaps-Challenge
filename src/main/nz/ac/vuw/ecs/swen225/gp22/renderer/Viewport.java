package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.SFX.Sounds;

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
    private int boundariesX = Maze.getDimensions().x() - GameConstants.NUM_GAME_TILE;
    private int boundariesY = Maze.getDimensions().y() - GameConstants.NUM_GAME_TILE;
    private JLabel infofield = new JLabel("");
    private SFXPlayer sfxPlayer = new SFXPlayer();
    private final HashMap<String, SFX> soundList = new HashMap<>();
    private ArrayList<Entity.Action.Interaction.ActionType> actions = new ArrayList<>();

    /**
     * Initialises a new maze upon the loading of a level.
     */
    public Viewport(){ 
        timer = new Timer(50, this);
        timer.start();

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
        if (playerTile instanceof InfoField inField){
            displayInfo(inField, g2D);
            //playSFX("Background", 2); // check sound is working
        }

        // play sounds based on the player's actions and game status
        try{ 
            for (Entity.Action.Interaction.ActionType a : actions){
                checkAction(a); 
            }
        }
        catch(Exception e){ e.printStackTrace(); }

    }

    /**
     * Updates the display every 100 ms.  
     */
    @Override
    public void actionPerformed(ActionEvent e) {
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
        //System.out.println(""+iField.getText());
        infofield.setBounds(infoPos+ 10, (2*infoPos)+10, Img.InfoPost.image.getWidth()-100, Img.InfoPost.image.getHeight()-100);
        infofield.setFont(new Font("Verdana", Font.BOLD, 20));
        add(infofield);
    }

    /**
     * Plays a sound based on the action that takes place in the game.
     * @param action Action performed.
     */
    public void checkAction(Entity.Action.Interaction.ActionType action){
        switch (action){
            case None : playSFX("Background", 2);
            case PickupKey: playSFX("CollectItem", 1);
            case PickupTreasure: playSFX("CollectItem", 1);
            //case "LoseGame": playSFX("LoseGame", 1);
            //case "MainMenu": playSFX("MainMenu", 1);
            case UnlockDoor: playSFX("Unlock", 1);
            case UnlockExit: playSFX("Unlock", 1);
            //case "WinGame": playSFX("WinGame", 1);
            //case "WinLevel": playSFX("WinLevel", 1);
        }
    }

    /**
     * Plays a sound.
     * 
     * @param soundName The name of the sounds.
     * @param priorityLevel Which sound must be played over others.
     */
    public void playSFX(String soundName, int priorityLevel){
        sfxPlayer.playSound(soundList.get(soundName), priorityLevel);
    }

    /***
     * Stores the action that has happened in the game. 
     * @param action Action performed.
     */
    public void setAction(ArrayList<Entity.Action.Interaction.ActionType> action){ this.actions = action; }

}
