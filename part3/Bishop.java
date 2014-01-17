/**
 * Bishop.java
 */

import java.util.HashSet;

public class Bishop extends Piece{

    public Bishop(String name, int X, int Y) {
        super(name, X, Y);
    }

    @Override
    public HashSet<Point> getMoves(Point init) {
        return super.diagonalMoves(init);
    }
}