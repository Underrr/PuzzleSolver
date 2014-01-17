import java.util.ArrayList;

public interface Puzzle<E> {
    public boolean isGoal(E config);

    public ArrayList<E> getNeighbors(E config);

    public E getStart();
}