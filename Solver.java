import java.util.ArrayList;
import java.lang.Integer;

public class Solver{
    public static void main(String args[]){
    
    }
    
    public Solver(){}
    
    
    public void solve(Puzzle input){
        ArrayList<ArrayList<Integer>> q = new ArrayList();
        ArrayList<Integer> start = new ArrayList(20);
        
        start.add(input.getStart());
        q.add(start);
        boolean found = (input.getStart() == input.getGoal());
        
        ArrayList<Integer> current = new ArrayList();
        ArrayList<Integer> right;
        ArrayList<Integer> left;
        ArrayList<Integer> neighbors;
        int size;
        
        current = q.get(0);
        
        while(!q.isEmpty() && !found){ //while the queue is not empty and not found
            current = new ArrayList(q.remove(0)); //dequeue the front element from the queue and set it to current
            size = current.size(); 
            left = new ArrayList(current);
            right = new ArrayList(current);
            
            //for each neighbor of the last element in current
            neighbors = input.getNeighbors((int) current.get(size-1));
            
            //append the neighbor to the next config
            left.add(neighbors.remove(0));
            right.add(neighbors.remove(0));
            
            if(left.get(left.size()-1) == input.getGoal()){ //if the next config is the goal
                current = left; //set current to the next config
                found = true; //set found to true
                break; //break out of the for loop
            }else{
                q.add(left); //enqueue the next config
            }
            if(right.get(right.size()-1) == input.getGoal()){
                current = right;
                found = true;
                break;
            }else{
                q.add(right);
            }
        }
        
        if(found){
            int x = 0;
            while(!current.isEmpty()){
                System.out.println("Step " + x + ": " + current.remove(0));
                x++;
            }
        }else{
            System.out.println("There is no solution");
        }
    }
    
}