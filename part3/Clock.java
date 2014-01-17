import java.util.ArrayList;

public class Clock implements Puzzle<Integer>{
    private int hours;        //Hours in the Clock
    private int start;        //Starting hour
    private int goal;        //Goal hour
        
        
    public Clock(int numHours, int startTime, int goalTime){
        hours = numHours;
        start = startTime;
        goal = goalTime;
    }

        
    public Integer getStart(){
        return start;
    }


        
    public boolean isGoal(Integer config){
        return config == this.goal;
    }

    
    public ArrayList<Integer> getNeighbors(Integer config) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        Integer sub1, sub2;
                
        if (config > 1 && config < hours) {
            sub1 = config - 1;
            sub2 = config + 1;
        }else{
            if (config == 1) {
                sub1 = hours;
                sub2 = config + 1;
            }else{
                sub1 = config - 1;
                sub2 = 1;
            }
        }
        neighbors.add(sub1);
        neighbors.add(sub2);
        return neighbors;
    }
    
    public static void main(String[] args){
        if (args.length != 3){
            System.out.println("Usage: java Clock hours start goal");
            System.exit(0);
        }
        Clock c = new Clock(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
        Solver s = new Solver();
        ArrayList<Puzzle> solution = s.solve(c);

        for (int i = 0; i < solution.size(); i++){
            System.out.println("Step " + i + ": " + solution.get(i));   
        }
                
        if (solution.size() == 0){
            System.out.println("No Solution Found");
        }
    }
}