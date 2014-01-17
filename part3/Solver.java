/**
* Solver.java
*/

import java.util.ArrayList;
import java.util.HashSet;

public class Solver<E> {
    public ArrayList<E> solve(Puzzle<E> puzzle) {
        HashSet<E> visited = new HashSet<E>();
        ArrayList<ArrayList<E>> queue = new ArrayList<ArrayList<E>>();
        ArrayList<E> startConfig = new ArrayList<E>();
        ArrayList<E> current = null;
        startConfig.add(puzzle.getStart());
        boolean found = puzzle.isGoal(puzzle.getStart());
        queue.add(startConfig);
        visited.add(puzzle.getStart());
        while (!queue.isEmpty() && !found) {
            current = queue.remove(0);
            ArrayList<E> neighbors = puzzle.getNeighbors(current.get(current.size() - 1));
            for (E neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    ArrayList<E> nextConfig = new ArrayList<E>();
                    for (E item : current) {
                        nextConfig.add(item);
                    }
                    nextConfig.add(neighbor);
                    if (puzzle.isGoal(nextConfig.get(nextConfig.size() - 1))) {
                        current = nextConfig;
                        found = true;
                        break;
                    } else {
                        queue.add(nextConfig);
                    }
                    visited.add(neighbor);
                }
            }
        }
        if (found) {
            return current;
        } else {
            return null;
        }
    }
}