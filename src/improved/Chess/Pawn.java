package improved.Chess;

import improved.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(int color, int x, int y) {
        super(color, CONSTANTS.PAWN, x, y);
    }
    public Pawn(int color, Position p) {
        super(color, CONSTANTS.PAWN, p);
    }
    
    public ArrayList<Move> moves(State state){
        ArrayList<Move> valid = new ArrayList();
        Position current = get_position();
        
        // White moves
        if(color == CONSTANTS.WHITE){
            Position up = new Position(current.x, current.y + 1);
            Position up_r = new Position(current.x + 1, current.y + 1);
            Position up_l = new Position(current.x - 1, current.y + 1);
            
            if(up.in_board() && state.get_piece(up) == null)
                valid.add(new Move(current, up, state));
            if(up_r.in_board() && state.get_piece(up_r) != null && state.get_piece(up_r).color != color)
                valid.add(new Move(current, up_r, state));
            if(up_l.in_board() && state.get_piece(up_l) != null && state.get_piece(up_l).color != color)
                valid.add(new Move(current, up_l, state));
            
            // Special
            switch(current.y){
                case 1:
                    Position upup = new Position(current.x, current.y + 2);
                    if(state.get_piece(up) == null && state.get_piece(upup) == null)
                        valid.add(new Move(current, upup, state));
                    break;
            }
            
        } else {
            Position down = new Position(current.x, current.y - 1);
            Position down_r = new Position(current.x + 1, current.y - 1);
            Position down_l = new Position(current.x - 1, current.y - 1);
            
            if(down.in_board() && state.get_piece(down) == null)
                valid.add(new Move(current, down, state));
            if(down_r.in_board() && state.get_piece(down_r) != null && state.get_piece(down_r).color != color)
                valid.add(new Move(current, down_r, state));
            if(down_l.in_board() && state.get_piece(down_l) != null && state.get_piece(down_l).color != color)
                valid.add(new Move(current, down_l, state));
            
            // Special
            switch(current.y){
                case 6:
                    Position downdown = new Position(current.x, current.y - 2);
                    if(state.get_piece(down) == null && state.get_piece(downdown) == null)
                        valid.add(new Move(current, downdown, state));
                    break;
            }
        }
        return valid;
    }
    
    public Pawn copy(){
        return new Pawn(color, get_position());
    }
}
