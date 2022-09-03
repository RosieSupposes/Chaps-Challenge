package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Objects;

public class Maze{
    public static Player player;

    private static Point dimensions;
    private static Tile tileMap[][];

    private static int treasuresLeft;

    private static String nextLevel;

    public static void generateMap(Point dimensionsP, int treasures){
        if(treasures < 0 || dimensionsP.x() < 0 || dimensionsP.y() < 0) return; // BAD THINGS
        dimensions = dimensionsP;
        tileMap = new Tile[dimensions.x()][dimensions.y()];
        treasuresLeft = treasures;
        nextLevel = "TEMP.xml";
    }

    public static Tile getTile(Point point){
        if(!point.isValid()) return null; // BAD THINGS
        return tileMap[point.x()][point.y()];      
    }

    public static void setTile(Point point, Tile tile){
        if(!point.isValid()) return; // BAD THINGS
        tileMap[point.x()][point.y()] = tile;      
    }

    public static void resetTile(Point point){
        if(!point.isValid()) return; // BAD THINGS
        tileMap[point.x()][point.y()] = new Air(point);  
    }

    public static void collectTreasure(){
        if(treasuresLeft <= 0) return; // BAD THINGS
        treasuresLeft--;
    }

    public static boolean collectedAllTreasures(){ return treasuresLeft == 0; }

    public static String getNextLevel(){ return nextLevel; }

    public record Point(int x, int y){ 
        public Point add(int addX, int addY){
            return new Point(x + addX, y + addY);
        }

        public Point add(Entity.Direction dir){
            return new Point(x + dir.posChange.x(), y + dir.posChange.y());
        }

        public boolean isValid(){
            return x > 0 && x < dimensions.x() && y > 0 && y < dimensions.y();
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
