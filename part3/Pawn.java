/**
 * Pawn.java
 */

import java.util.HashSet;

public class Pawn extends Piece{

    public Pawn(String name, int X, int Y) {
        super(name, X, Y);
    }

    @Override
    public HashSet<Point> getMoves(Point init) {
        HashSet<Point> moves = new HashSet<Point>();
        int x = init.getX();
        int y = init.getY();
        moves.add(new Point(x-1, y-1));
        moves.add(new Point(x+1, y-1));
        super.checkBounds(moves);
        return moves;
    }    
}