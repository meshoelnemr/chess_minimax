package improved.Chess;

import improved.*;
import java.util.*;

public class King extends Piece {
    private boolean castle_stop = false;
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
        // Hasn't moved and not in check
        if(!castle_stop && !moved && current.x == 4){
            castle_stop = true;
            Position rook_k = new Position(current.x + 3, current.y);
            Position pad1 = new Position(current.x + 1, current.y);
            Position pad2 = new Position(current.x + 2, current.y);
            
            if(state.get_piece(pad1) == null
            && state.get_piece(pad2) == null
            && state.get_piece(rook_k) != null
            && state.get_piece(rook_k).type == CONSTANTS.ROOK
            && !state.get_piece(rook_k).moved
            && !state.castle_marked(pad1)
            && !state.castle_marked(pad2)
            && !state.castle_marked(current)
            ){
                valid.add(new Move(current, pad2, rook_k, pad1, state));
            }
            
            Position rook_q = new Position(current.x - 4, current.y);
            Position pad3 = new Position(current.x - 1, current.y);
            Position pad4 = new Position(current.x - 2, current.y);
            Position pad5 = new Position(current.x - 3, current.y);
            
            if(state.get_piece(pad3) == null
            && state.get_piece(pad4) == null
            && state.get_piece(pad5) == null
            && state.get_piece(rook_q) != null
            && state.get_piece(rook_q).type == CONSTANTS.ROOK
            && !state.get_piece(rook_q).moved
            && !state.castle_marked(pad3)
            && !state.castle_marked(pad4)
            && !state.castle_marked(current)
            ){
                valid.add(new Move(current, pad4, rook_q, pad3, state));
            }
            
            castle_stop = false;
        }
        return valid;
    }
    
    public King copy(){
        return new King(color, get_position());
    }
}
