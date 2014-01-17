/**
 * Point.java
 */

public class Point {
    
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
        
    public int getX() {return this.x;}
       
    public int getY() {return this.y;}
        
    @Override
    public boolean equals(Object o) {
        Point p = (Point)o;
        if (p.getX() == this.x && p.getY() == this.y) {
            return true;
        } else {
            return false;
        }
    }
        
    @Override
    public int hashCode() {
        if (this.x < 0 || this.y < 0) {
            return -1;
        }
        return Integer.parseInt(String.valueOf(this.x) + String.valueOf(this.y));
    }
        
    @Override
    public String toString() {
        return String.format("[%s,%s]", this.x, this.y);
    }
}