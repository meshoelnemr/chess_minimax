package improved;
public class Position {
    public int x, y;
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Position copy(){
        return new Position(x, y);
    }
    public boolean in_board(){
        return this.x >= 0 &&
               this.x <= 7 &&
               this.y >= 0 &&
               this.y <= 7;
    }
    
    public boolean equals(Object o){
        if(super.equals(o))
            return true;
        Position other = (Position)o;
        if(other.x == this.x && other.y == this.y)
            return true;
        return false;
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}

