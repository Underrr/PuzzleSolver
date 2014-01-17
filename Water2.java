import java.util.ArrayList;
import java.util.*;

public class Water implements Puzzle, Comparable<Water>{
    private ArrayList<Integer> jugCapacity;
    private ArrayList<Integer> jugs;
    
    private int goalWater;

    public int getGoal(){
        return this.goalWater;
    }
    
    public Water(ArrayList<Integer> jugCap, ArrayList<Integer> jugs, int goal)
    {
        this.jugCapacity = jugCap;
        this.jugs = jugs;
        this.goalWater = goal;
    }

    public ArrayList<ArrayList<Integer>> getNeighbors(ArrayList<Integer> config) {
        ArrayList<ArrayList<Integer>> newConfigs = new ArrayList<ArrayList<Integer>>();
                  
        for(int i = 0; i < jugs.size(); i++){
            ArrayList<Integer> newTemp = new ArrayList<Integer>();
            Collections.copy(newTemp, config);
            newTemp.set(i, jugCapacity.get(i));
        newConfigs.add(newTemp);
        }
                    
        for(int i = 0; i < jugs.size(); i++){
            ArrayList<Integer> newTemp = new ArrayList<Integer>();
            Collections.copy(newTemp, config);
            newTemp.set(i, 0);
            newConfigs.add(newTemp);
        }
                    
        for(int i = 0; i < jugs.size(); i++){
            for(int j = 0; j < jugs.size(); j++){
                ArrayList<Integer> newTemp = new ArrayList<Integer>();
                Collections.copy(newTemp, config);
                if(jugCapacity.get(i) > (newTemp.get(j) + newTemp.get(i))){
                    int oldI = newTemp.get(i);
                    int newI = newTemp.get(i) + newTemp.get(j);
                    newTemp.set(i, newI);
                    newTemp.set(j, (newTemp.get(j) - (newI - oldI)));
                    newConfigs.add(newTemp);
                }
            }
        }
        return newConfigs;
    }
    
    public int compareTo(Water w){
        if(this.jugs.equals(w.jugs))
        return 0;
        return 1;
    }
            
    public ArrayList<Integer> getStart() {
        return jugs;
    }

    public boolean isSolution(ArrayList<Integer> config){
        for(int i : config){
            if (i == goalWater)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int g;
        ArrayList<Integer> jugMax = new ArrayList();
        ArrayList<Integer> jugCurrent = new ArrayList();
        
        if(!(args.length < 2)){
            System.err.println("Usage: java Water amount jug1 jug2 ...");
            System.exit(0);
        }
            
        g = Integer.parseInt(args[0]);
            
        for(int i=1; i<args.length; i++){
            jugMax.add(Integer.parseInt(args[i]));
            jugCurrent.add(0);
        }
            
        Water w = new Water(jugMax, jugCurrent, g);
        Solver2 s =  new Solver2();
        s.solve(w);
                    
    }
}