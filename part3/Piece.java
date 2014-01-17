/**
 * Piece.java
 */

import java.util.HashSet;

public abstract class Piece {
    private String name;
    private int X, Y, localInstanceNum;
    private static int numInstances = 0;
        
    public Piece(String name, int X, int Y) {
        this.name = name;
        this.X = X;
        this.Y = Y;
        this.localInstanceNum = numInstances;
        numInstances += 1;
    }

    public String getName() {
        return this.name;
    }
    public abstract HashSet<Point> getMoves(Point init);

    protected HashSet<Point> diagonalMoves(Point init) {
        HashSet<Point> moves = new HashSet<Point>();
        int xTemp = init.getX(), yTemp = init.getY();

        while (xTemp < this.X && yTemp < this.Y) {
            moves.add(new Point(xTemp, yTemp));
            xTemp++;
            yTemp++;
        }

        xTemp = init.getX();
        yTemp = init.getY();
    
        while (xTemp >= 0 && yTemp >= 0) {
            moves.add(new Point(xTemp, yTemp));
            xTemp--;
            yTemp--;
        }
    
        xTemp = init.getX();
        yTemp = init.getY();
    
        while (xTemp >= 0 && yTemp < this.Y) {
            moves.add(new Point(xTemp, yTemp));
            xTemp--;
            yTemp++;
        }

        xTemp = init.getX();
        yTemp = init.getY();

        while (xTemp < this.X && yTemp >= 0) {
            moves.add(new Point(xTemp, yTemp));
            xTemp++;
            yTemp--;
        }

        moves.remove(init);

        return moves;
    }

    protected HashSet<Point> cardinalMoves(Point init) {
        HashSet<Point> moves = new HashSet<Point>();
        int xTemp = init.getX(), yTemp = init.getY();

        while (yTemp >= 0) {
            moves.add(new Point(init.getX(), yTemp));
            yTemp--;
        }

        xTemp = init.getX();
        yTemp = init.getY();

        while (yTemp < this.Y) {
            moves.add(new Point(init.getX(), yTemp));
            yTemp++;
        }

        xTemp = init.getX();
        yTemp = init.getY();

        while (xTemp < this.X) {
            moves.add(new Point(xTemp, init.getY()));
            xTemp++;
        }

        xTemp = init.getX();
        yTemp = init.getY();

        while (xTemp >= 0) {
            moves.add(new Point(xTemp, init.getY()));
            xTemp--;
        }

        moves.remove(init);
        
        return moves;
    }

    protected HashSet<Point> checkBounds(HashSet<Point> set) {
        HashSet<Point> toRemove = new HashSet<Point>();
        for (Point p : set) {
            if(p.getX() < 0 || p.getX() >= this.X || p.getY() < 0|| p.getY() >= this.Y){
                toRemove.add(p);
            }
        }
        
        for(Point p : toRemove){
            set.remove(p);
        }
                return set;
        }

        public String toString() {
                return name;
        }
        
        public int hashCode() {
                int ret = 0;
                for (char c : this.name.toCharArray()) {
                        ret += (int) c;
                }
                return ret * this.localInstanceNum;
        }
        
    //test
    public static void main(String[] args){}        
}