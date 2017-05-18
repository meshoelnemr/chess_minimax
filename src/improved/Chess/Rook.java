package improved.Chess;

import improved.*;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(int color, int x, int y) {
        super(color, CONSTANTS.ROOK, x, y);
    }
    public Rook(int color, Position p) {
        super(color, CONSTANTS.ROOK, p);
    }
    
    public ArrayList<Move> moves(State state){
        return axis(state);
    }
    
    public Rook copy(){
        return new Rook(color, get_position());
    }
}
