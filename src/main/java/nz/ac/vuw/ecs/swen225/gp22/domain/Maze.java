package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Objects;

/**
 * This class stores the game state (player, tilemap, entities, and treasures). 
 * As such, it is accessed by other packages to query the game state for specific tiles 
 * or perform operations on the player.
 * 
 * @author Abdulrahman Asfari 300475089
 */
public class Maze{
    /** Stores the {@link Maze} entity so that other tiles can access it easily. */
    public static Player player;

    /** A 2D array that stores the level {@link Tile} instances in a 
     *  way where they can be accessed by position.  */
    private static Tile tileMap[][];

    /** Represents how many more {@link Treasure} tiles are still on the map. */
    private static int treasuresLeft;

    /** Stores the name of the next level to load. If empty or null then the {@link #gameComplete() game over flag} returns true. */
    private static String nextLevel;

    /** 
     * Generates a new map. This will be used by the persistency module for level loading. 
     * 
     * @param dimensions The size of the {@link #tileMap map}.
     * @param treasures The number of treasures on the {@link #tileMap map}.
     */
    @DevMarkers.NeedsPrecons
    public static void generateMap(Point dimensions, int treasures){
        if(treasures < 0 || dimensions.x() < 0 || dimensions.y() < 0) return; // BAD THINGS
        tileMap = new Tile[dimensions.x()][dimensions.y()];
        treasuresLeft = treasures;
        nextLevel = "";
    }

    /** 
     * Finds a {@link Tile} using the {@link #tileMap tilemap} given a {@link Point point}.
     * 
     * @param point The position of the {@link Tile tile}.
     * @return Tile object at the given position.
     */
    @DevMarkers.NeedsPrecons
    public static Tile getTile(Point point){
        if(!point.isValid()) return null; // BAD THINGS
        return tileMap[point.x()][point.y()];      
    }
    
    /** 
     * Sets the value on the {@link #tileMap tilemap} at a given {@link Point point}.
     * 
     * @param point The position the {@link Tile tile} will be at.
     * @param tile The {@link Tile tile} to add to the {@link #tileMap tilemap}.
     */
    @DevMarkers.NeedsPrecons
    public static void setTile(Point point, Tile tile){
        if(!point.isValid()) return; // BAD THINGS
        getTile(point).deleteTile();
        tileMap[point.x()][point.y()] = tile;      
    }

    /** 
     * Sets the {@link Tile} object at a given {@link Point point} to air.
     * 
     * @param point Point to reset.
     */
    public static void resetTile(Point point){
        setTile(point, new Air(point));
    }

    /** Reduce the number of treasures left by 1. */
    @DevMarkers.NeedsPrecons
    public static void collectTreasure(){
        if(treasuresLeft <= 0) return; // BAD THINGS
        treasuresLeft--;
    }

    /** @return Whether or not all the {@link Treasure} tiles on the map have been collected. */
    public static boolean collectedAllTreasures(){ return treasuresLeft == 0; }

    /** @return The name of the next level to load. */
    public static String getNextLevel(){ return nextLevel; }

    /** @return Whether or not there are more levels to load. */
    public static boolean gameComplete(){ return nextLevel == null || nextLevel.equals(""); }

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
            return new Point(x + point.x(),y + point.y());
        }

        /**
         * Overloaded method for {@link #add add()} that accepts an {@link Entity.Direction} enum.
         * 
         * @param dir Direction to get point from.
         * @return Point object representing the sum of the point and direction.
         */
        public Point add(Entity.Direction dir){
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
            return x > 0 && x < tileMap.length && y > 0 && y < tileMap[0].length;
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
