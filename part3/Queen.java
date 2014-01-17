/**
 * Queen.java
 */

import java.util.HashSet;

public class Queen extends Piece{

    public Queen(String name, int X, int Y) {
        super(name, X, Y);
    }

    @Override
    public HashSet<Point> getMoves(Point init) {
        HashSet<Point> moves = super.diagonalMoves(init);
        moves.addAll(super.cardinalMoves(init));
        return moves;
    }    
}