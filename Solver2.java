import java.util.ArrayList;
import java.util.HashSet;

public class Solver2
{
    public Solver2(){}

    public ArrayList<Puzzle> solve(Puzzle c){
        ArrayList<ArrayList <Puzzle>> queue = new ArrayList<ArrayList <Puzzle>>();
        HashSet<Puzzle> visited = new HashSet<Puzzle> ();

        //Generate a current config containing the starting config
        ArrayList<Puzzle> current = new ArrayList<Puzzle>();
        current.add(c);
        queue.add(current);
        boolean found = (c.isGoal());

        // Return the current config if it is the goal
        if (found){
            return current;
        }

        while (queue.size() != 0 && !found){
            current = queue.remove(0);

            for (Puzzle neighbor : current.get(current.size()-1).getNeighbors()){
                //Check if that neighbor has already been examined
                if (!visited.contains(neighbor)){
                    visited.add(neighbor);
                    ArrayList<Puzzle> nextConfig = new ArrayList<Puzzle>(current);
                    nextConfig.add(neighbor);
                    if (nextConfig.get(nextConfig.size()-1).isGoal()){
                        current = nextConfig;
                        found = true;
                        break;
                    }else{
                        queue.add(nextConfig);
                    }
                }
            }
        }
        if (found){
            //If solution was found, return
            return current;
        }else{
            //Otherwise, return empty config
            return new ArrayList<Puzzle>();
        }
    }
}