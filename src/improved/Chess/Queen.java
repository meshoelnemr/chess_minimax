package improved.Chess;

import improved.*;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(int color, int x, int y) {
        super(color, CONSTANTS.QUEEN, x, y);
    }
    public Queen(int color, Position p) {
        super(color, CONSTANTS.QUEEN, p);
    }
    
    public ArrayList<Move> moves(State state){
        ArrayList<Move> valid = axis(state);
        valid.addAll(diagonal(state));
        return valid;
    }
    
    public Queen copy(){
        return new Queen(color, get_position());
    }
}
