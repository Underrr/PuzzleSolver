import java.util.ArrayList;
import java.util.Arrays;

public class Water implements Puzzle<ArrayList<Integer>>{
    private ArrayList<Integer> jugs;
        private int desiredWater;
        
    public Water(String[] args){
        jugs = new ArrayList<Integer>(args.length - 1);
        desiredWater = Integer.parseInt(args[0]);
        for(int i = 1; i < args.length; i++){
           jugs.add(Integer.parseInt(args[i]));
        }
    }
        

    public boolean isGoal(ArrayList<Integer> config){
        if(config.contains(this.desiredWater)){
            return true;
        }else{
            return false;
        }
    }
        
    public ArrayList<Integer> getStart(){
        ArrayList<Integer> start = new ArrayList<Integer>();
        for(int i = 0; i < jugs.size(); i++){
            start.add(0);
        }
        return start;
    }
        
    public ArrayList<ArrayList<Integer>> getNeighbors(ArrayList<Integer> config){
               
        ArrayList<ArrayList<Integer>> neighbors = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> singleConfig = restoreConfig(config);
        for(int i = 0; i < config.size(); i++){
            if(singleConfig.size() != config.size())
                singleConfig = restoreConfig(config);
            Integer curr = singleConfig.remove(i);
                        
            if(curr != 0){
                singleConfig.add(i, 0);
                neighbors.add(singleConfig);
                singleConfig = restoreConfig(config);
                curr = singleConfig.remove(i);
            }
                        
            if(curr != jugs.get(i)){
                singleConfig.add(i, jugs.get(i));
                neighbors.add(singleConfig);
                singleConfig = restoreConfig(config);
                curr = singleConfig.remove(i);
            }
                        
            for(int j = 0; j < config.size() - 1; j++){
                if(singleConfig.size() == config.size())
                    curr = singleConfig.remove(i);
                if(curr != jugs.get(i) && singleConfig.get(j) != 0){
                    Integer pourer = singleConfig.remove(j);
                    int total = 0;
                    int leftover = 0;
                    if(curr + pourer <= jugs.get(i))
                        total = curr + pourer;
                    else{
                        total = jugs.get(i);
                        leftover = (curr + pourer) - jugs.get(i);
                    }
        
                    singleConfig.add(j, leftover);
                    singleConfig.add(i, total);
                    if(pourer != leftover){
                        neighbors.add(singleConfig);
                        singleConfig = restoreConfig(config);
                        curr = singleConfig.remove(i);
                    }
                }else{
                    singleConfig = restoreConfig(config);
                }
            }
        }
        return neighbors;
    }
        
    private ArrayList<Integer> restoreConfig(ArrayList<Integer> orig){
        ArrayList<Integer> newConfig = new ArrayList<Integer>();
        for(Integer i : orig){
            newConfig.add(i);        
        }
        return newConfig;
    }
        
    public static void main(String[] args){
        ArrayList<ArrayList<Integer>> solution;
        if(args.length >= 2){
            solution = new Solver<ArrayList<Integer>>().solve(new Water(args));
            if(solution != null){
                for(int i = 0; i < solution.size(); i++){
                    System.out.print("Step " + i + ": ");
                    for(int j = 0; j < solution.get(i).size(); j++){
                        System.out.print(solution.get(i).get(j));
                        if(j + 1 != solution.get(i).size()){
                            System.out.print(" ");
                        }
                    }
                    System.out.print("\n");
                }
            }else{
                boolean solved = false;
                for(int i = 1; i < args.length; i++){
                    if(args[i].equals(args[0])){
                        solved = true;
                        break;
                    }
                }
                if(solved){
                    String output = "Step 0: [";
                    for(int i = 1; i < args.length; i++){
                        output += args[i];
                    }
                    System.out.println(output + "]");
                }else
                    System.out.println("No solution.");
            }
        }else{
            System.out.println("Usage: java Water amount jug1 jug2 ...");
        }
    }
}