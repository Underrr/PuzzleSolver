import java.util.ArrayList;
import java.util.Arrays;

public class Water implements Puzzle{
    private int[] jugCapacity;
    private int goal;
    private int[] jugs;
    
    //Constructor
    public Water(int g, int[] jc){
        goal = g;
        jugCapacity = jc;
        jugs = new int[jc.length];
    }
    
    //Constructor
    public Water(int g, int[] jc, int[] j){
        goal = g;
        jugCapacity = jc;
        jugs = new int[jc.length];
        System.arraycopy(j, 0, jugs, 0, j.length);
    }
    
    //Sets the jug at index to empty
    public void setEmpty(int index){
        jugs[index] = 0;
    }
    
    //Sets the jug at index to full
    public void setFull(int index){
        jugs[index] = jugCapacity[index];
    }
    
    //Pours from jug i to jug j
    public void pour(int i, int j){
        if(jugs[j] < jugCapacity[j] && jugs[i] != 0){
            int spaceInJ = jugCapacity[j] - jugs[j];
            if (jugs[i] >= spaceInJ){
                jugs[i] -= spaceInJ;
                jugs[j] = jugCapacity[j];
            }else{
                jugs[j] += jugs[i];
                jugs[i] = 0;
            }
        }

    }
    
    //Gets number of jugs
    public int[] getStart(){
        return new int[jugCapacity.length];
    }
    
    //Gets goal
    public int getGoal(){
        return goal;
    }
    
    //Checks to see if goal has been reached
    public boolean isGoal(){
        for(int i: jugs){
            if(i==goal){
                return true;
            }
        }
        return false;
    }
    
    //Returns current jug array
    public int[] getConfig(){
        return jugs;
    }
    
    public ArrayList<Puzzle> getNeighbors(){
        ArrayList<Puzzle> neighbors = new ArrayList<Puzzle>();

        // For each of the jugs in the subconfiguration
        for (int i = 0; i < jugs.length; i++){
            //Add if index was full
            Water fullconfig = new Water(goal, jugCapacity, jugs);
            fullconfig.setFull(i);
            neighbors.add(fullconfig);

            //Add if index was empty
            Water emptyconfig = new Water(goal, jugCapacity, jugs);
            emptyconfig.setEmpty(i);
            neighbors.add(emptyconfig);

            //Add all combinations of pouring
            for (int j = 0; j < jugs.length; j++){
                if (i != j){
                    Water swapconfig = new Water(goal, jugCapacity, jugs);
                    swapconfig.pour(i, j);
                    neighbors.add(swapconfig);
                }
            }
        }
        return neighbors;
    }
    
    //Converts to string
    public String toString(){
        String out = "";
        for(int j : jugs){
            out += " " + j;
        }
        return out;
    }
    
    //Checks to see if the two Water objects are equal
    public boolean equals(Object other){
        if(other instanceof Water){
            Water newother = (Water)other;
            return Arrays.equals(newother.getConfig(), getConfig());
        }else{
            return false;
        }
    }
    
    //returns hashcode for object based on toString
    public int hashCode(){
        return this.toString().hashCode();
    }
    
    public static void main(String[] args){
        if(args.length < 2){
            System.err.println("Usage: java Water amount jug1 jug2 ...");
            System.exit(0);
        }
        
        int[] jugCapacityArgs = new int[args.length-1];
        
        for(int a = 1; a < args.length; a++){
            jugCapacityArgs[a-1] = Integer.parseInt(args[a]);
        }
        
        Water c = new Water(Integer.parseInt(args[0]), jugCapacityArgs);
        Solver2 s = new Solver2();
        ArrayList<Puzzle> solution = s.solve(c);
        
        for(int i = 0; i < solution.size(); i++){
            System.out.println("Step " + i + ": " + solution.get(i));
        }
        
        if(solution.size() == 0){
            System.out.println("No solution.");
        }
    }
}