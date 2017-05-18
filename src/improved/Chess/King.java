package improved.Chess;

import improved.*;
import java.util.*;

public class King extends Piece {
    public King(int color, int x, int y) {
        super(color, CONSTANTS.KING, x, y);
    }
    public King(int color, Position p) {
        super(color, CONSTANTS.KING, p);
    }
    
    public ArrayList<Move> moves(State state){
        ArrayList<Move> valid = new ArrayList();
        Position current = get_position();
        
        Position[] next = new Position[]{
            new Position(current.x + 1, current.y + 1),
            new Position(current.x + 1, current.y + 0),
            new Position(current.x + 1, current.y - 1),
            new Position(current.x + 0, current.y - 1),
            new Position(current.x - 1, current.y - 1),
            new Position(current.x - 1, current.y + 0),
            new Position(current.x - 1, current.y + 1),
            new Position(current.x + 0, current.y + 1),
        };
        
        for(Position p : next){
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null || square_piece.color != color)
                    valid.add(new Move(current, p, state));
            }
        }
        
        // Castle moves
        return valid;
    }
    
    public King copy(){
        return new King(color, get_position());
    }
}
