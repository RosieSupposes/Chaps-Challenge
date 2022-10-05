package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

import nz.ac.vuw.ecs.swen225.gp22.domain.Entity.Direction;

/**
 * This class stores the game state (player, tilemap, entities, and treasures). 
 * As such, it is accessed by other packages to query the game state for specific tiles 
 * or perform operations on the player.
 * 
 * @author Abdulrahman Asfari, 300475089
 * @version 1.9
 */
public class Maze{
    /** Stores the {@link Maze} entity so that other tiles can access it easily. */
    public static Player player;

    /** Contains all non-player entities. Suppressed the raw types warning as 
     * the generic type is only used for observers and does not affect this use case.  */
    @SuppressWarnings("rawtypes")
    public static ArrayList<Entity> entities = new ArrayList<>();

    /** A 2D array that stores the level {@link Tile} instances in a 
     *  way where they can be accessed by position.  */
    private static Tile tileMap[][];

    /** Represents how many more {@link Treasure} tiles are still on the map. */
    private static int treasuresLeft;

    /** Stores the name of the next level to load. If empty or null then the {@link #gameComplete() game over flag} returns true. */
    private static String nextLevel;

    /** Stores {@link Entity.Action.Interaction Interaction} records to be claimed by entities. */
    public static Queue<Entity.Action.Interaction> unclaimedInteractions = new ArrayDeque<>();

    private static boolean gameLost;

    /** 
     * Generates a new map. This will be used by the persistency module for level loading. 
     * 
     * @param dimensions The size of the {@link #tileMap map}.
     * @param treasures The number of treasures on the {@link #tileMap map}.
     */
    public static void generateMap(Point dimensions, int treasures){
        if(dimensions == null || dimensions.x() <= 0 || dimensions.y() <= 0) throw new IllegalArgumentException("Invalid map dimensions.");
        if(treasures < 0) throw new IllegalArgumentException("Number of treasures cannot be below 0.");

        gameLost = false;
        entities.clear();

        tileMap = new Tile[dimensions.x()][dimensions.y()];
        for(int x = 0; x < dimensions.x(); x++){
            for(int y = 0; y < dimensions.y(); y++){
                tileMap[x][y] = new Ground(new Point(x, y));
            }
        }

        treasuresLeft = treasures;
        player = new Player(new Point(0, 0), Entity.Direction.Down);
        nextLevel = "";

        entities.add(new GummyGuard(new Point(2, 1), Direction.Left));
    }

    /** @return A {@link Point} representing the maps dimensions. */
    public static Point getDimensions(){ return new Point(tileMap.length, tileMap[0].length); }

    /** 
     * Finds a {@link Tile} using the {@link #tileMap tilemap} given a {@link Point point}.
     * 
     * @param point The position of the {@link Tile tile}.
     * @return Tile object at the given position.
     */
    public static Tile getTile(Point point){
        if(point == null || !point.isValid()) throw new IllegalArgumentException("Invalid point given.");
        return tileMap[point.x()][point.y()];      
    }
    
    /** 
     * Sets the value on the {@link #tileMap tilemap} at a given {@link Point point}.
     * 
     * @param point The position the {@link Tile tile} will be at.
     * @param tile The {@link Tile tile} to add to the {@link #tileMap tilemap}.
     */
    public static void setTile(Point point, Tile tile){
        if(point == null || !point.isValid()) throw new IllegalArgumentException("Invalid point given.");
        if(tile == null) throw new IllegalArgumentException("Given tile does not exist.");
        if(!tile.getPos().equals(point)) throw new IllegalArgumentException("Tile position does not match the point it is being set to.");
        Tile oldTile = getTile(point);
        oldTile.deleteTile();
        tileMap[point.x()][point.y()] = tile;     
        assert Maze.getTile(point) != oldTile : "Tile has not been removed from the map."; 
    }

    /** 
     * Sets the {@link Tile} object at a given {@link Point point} to air.
     * 
     * @param point Point to reset.
     */
    public static void resetTile(Point point){
        if(point == null || !point.isValid()) throw new IllegalArgumentException("Invalid point given.");
        setTile(point, new Ground(point));
        assert getTile(point) instanceof Ground : "Tile not reset properly.";
    }

    /** Reduce the number of treasures left by 1. */
    public static void collectTreasure(){
        if(treasuresLeft <= 0) throw new IllegalStateException("No treasure to collect.");
        treasuresLeft--;
    }

    /** Increases the number of treasures left by 1. */
    public static void addTreasure(){ treasuresLeft++; }

    /** @return Whether or not all the {@link Treasure} tiles on the map have been collected. */
    public static boolean collectedAllTreasures(){ return treasuresLeft == 0; }

    /** @return The number of treasures left to collect. */
    public static int getTreasuresLeft(){ return treasuresLeft; }

    /** @return The name of the next level to load. */
    public static String getNextLevel(){ return nextLevel; }

    /** @return Whether or not there are more levels to load. */
    public static boolean gameComplete(){ return nextLevel == null || nextLevel.equals(""); }

    /** @return Whether or not the player has lost the game. */
    public static boolean gameLost(){ return gameLost; }

    /** Flags the game as over. */
    public static void loseGame(){ gameLost = true; }

    /** Represents a point on the {@link Maze#tileMap tilemap}. */
    public record Point(int x, int y){ 
        /**
         * Adds this point and another, then returns the result.
         * 
         * @param addX The amount to add to the X.
         * @param addY The amount to add to the Y.
         * @return Point object representing the sum of the two points.
         */
        public Point add(Point point){
            if(point == null) throw new IllegalArgumentException("Given point is null");
            return new Point(x + point.x(),y + point.y());
        }

        /**
         * Overloaded method for {@link #add add()} that accepts an {@link Entity.Direction} enum.
         * 
         * @param dir Direction to get point from.
         * @return Point object representing the sum of the point and direction.
         */
        public Point add(Entity.Direction dir){
            if(dir == null) throw new IllegalArgumentException("Given direction is null");
            return add(new Point(dir.posChange.x(), dir.posChange.y()));
        }

        /**
         * Overloaded method for {@link #add add()} that accepts two individual numbers 
         * that represent X and Y, respectively.
         * 
         * @param addX The amount to add to the X.
         * @param addY The amount to add to the Y.
         * @return Point object representing the sum of the point and the two numbers.
         */
        public Point add(int addX, int addY){
            return add(new Point(addX, addY));
        }

        /** @return Whether or not the point exists on the {@link Maze#tileMap tilemap}. */
        public boolean isValid(){
            return x >= 0 && x < tileMap.length && y >= 0 && y < tileMap[0].length;
        }
        
        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }

        @Override 
        public boolean equals(Object o){
            if(o == null || !(o instanceof Point p)) return false;
            return x == p.x() && y == p.y();
        }
    }
}
