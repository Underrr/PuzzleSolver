/**
 * Rook.java
 */

import java.util.HashSet;

public class Rook extends Piece{

    public Rook(String name, int X, int Y) {
        super(name, X, Y);
    }

    @Override
    public HashSet<Point> getMoves(Point init) {
        return super.cardinalMoves(init);
    }
}