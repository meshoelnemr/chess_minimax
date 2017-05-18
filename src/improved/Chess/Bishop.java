package improved.Chess;

import improved.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(int color, int x, int y) {
        super(color, CONSTANTS.BISHOP, x, y);
    }
    public Bishop(int color, Position p) {
        super(color, CONSTANTS.BISHOP, p);
    }
    
    public ArrayList<Move> moves(State state){
        return diagonal(state);
    }
    public Bishop copy(){
        return new Bishop(color, get_position());
    }
}
