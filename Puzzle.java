import java.util.ArrayList;

public interface Puzzle{
    public boolean isGoal();
    
    public ArrayList<Puzzle> getNeighbors();
}