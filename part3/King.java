/**
 * King.java
 */

import java.util.HashSet;

public class King extends Piece{
    public King(String name, int X, int Y) {
        super(name, X, Y);
    }

    @Override
    public HashSet<Point> getMoves(Point init) {
        HashSet<Point> moves = new HashSet<Point>();
        int x = init.getX();
        int y = init.getY();
        moves.add(new Point(x-1, y-1));
        moves.add(new Point(x+1, y-1));
        moves.add(new Point(x-1, y+1));
        moves.add(new Point(x+1, y+1));
        moves.add(new Point(x, y-1));
        moves.add(new Point(x, y+1));
        moves.add(new Point(x+1, y));
        moves.add(new Point(x-1, y));
        super.checkBounds(moves);
        return moves;
    }
}