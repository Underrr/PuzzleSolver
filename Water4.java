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
            copy(newTemp, config);
            newTemp.set(i, (this.jugCapacity).get(i));
            newConfigs.add(newTemp);
            /**/System.out.println("Got neighbor");
            for(int j=0; j<(this.jugs).size(); j++){
            System.out.println("Jug " + j + " has a capacity of " + (this.jugs).get(j));
        }
        }
                    
        for(int i = 0; i < jugs.size(); i++){
            ArrayList<Integer> newTemp = new ArrayList<Integer>();
            copy(newTemp, config);
            newTemp.set(i, 0);
            newConfigs.add(newTemp);
            /**/System.out.println("Got neighbor");
            for(int j=0; j<(this.jugs).size(); j++){
            System.out.println("Jug " + j + " has a capacity of " + (this.jugs).get(j));
        }
        }
                    
        for(int i = 0; i < jugs.size(); i++){
            for(int j = 0; j < jugs.size(); j++){
                ArrayList<Integer> newTemp = new ArrayList<Integer>();
                copy(newTemp, config);
                if(jugCapacity.get(i) > (newTemp.get(j) + newTemp.get(i))){
                    int oldI = newTemp.get(i);
                    int newI = newTemp.get(i) + newTemp.get(j);
                    newTemp.set(i, newI);
                    newTemp.set(j, (newTemp.get(j) - (newI - oldI)));
                    newConfigs.add(newTemp);
                }
                for(int k=0; k<(this.jugs).size(); k++){
            System.out.println("Jug " + k + " has a capacity of " + (this.jugs).get(k));
        }
            }
            /**/System.out.println("Got neighbor");
            
        }
        return newConfigs;
    }
    
    public int compareTo(Water w){
        if(this.jugs.equals(w.jugs)){
            return 0;
        }
        return 1;
    }
    
    public void copy(ArrayList<Integer> dest, ArrayList<Integer> src){
        for(int i = 0; i<src.size(); i++){
            dest.add(src.get(i));
        }
    }
    
    public ArrayList<Integer> getStart() {
        return jugs;
    }

    public boolean isSolution(ArrayList<Integer> config){
        for(int i=0; i < config.size(); i++){
            if (config.get(i) == this.getGoal()){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int g;
        ArrayList<Integer> jugMax = new ArrayList();
        ArrayList<Integer> jugCurrent = new ArrayList();
        
        if((args.length < 2)){
            System.err.println("Usage: java Water amount jug1 jug2 ...");
            System.exit(0);
        }
            
        g = Integer.parseInt(args[0]);
            
        for(int i=1; i<args.length; i++){
            jugMax.add(i-1, Integer.parseInt(args[i]));
            jugCurrent.add(i-1, 0);
        }
            
        Water w = new Water(jugMax, jugCurrent, g);
        System.out.println("Water object created");
        for(int i=0; i<(w.jugCapacity).size(); i++){
            System.out.println("Jug " + i + " has a capacity of " + (w.jugCapacity).get(i));
        }
        for(int i=0; i<(w.jugs).size(); i++){
            System.out.println("Jug " + i + " has " + (w.jugs).get(i));
        }
        Solver2 s =  new Solver2();
        System.out.println("Solver created");
        s.solve(w);
        System.out.println("Solved");
                    
    }
}