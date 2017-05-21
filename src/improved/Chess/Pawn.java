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
            
            // up
            if(up.in_board() && state.get_piece(up) == null)
                if(up.y == 7){
                    valid.add(new Move(current, up, new Queen(color, up), state));
                    valid.add(new Move(current, up, new Rook(color, up), state));
                    valid.add(new Move(current, up, new Bishop(color, up), state));
                    valid.add(new Move(current, up, new Knight(color, up), state));
                }
                else
                    valid.add(new Move(current, up, state));
            
            // up right
            if(up_r.in_board() && state.get_piece(up_r) != null && state.get_piece(up_r).color != color)
                if(up_r.y == 7){
                    valid.add(new Move(current, up_r, new Queen(color, up_r), state));
                    valid.add(new Move(current, up_r, new Rook(color, up_r), state));
                    valid.add(new Move(current, up_r, new Bishop(color, up_r), state));
                    valid.add(new Move(current, up_r, new Knight(color, up_r), state));
                }
                else
                    valid.add(new Move(current, up_r, state));
            
            // up left
            if(up_l.in_board() && state.get_piece(up_l) != null && state.get_piece(up_l).color != color)
                if(up_l.y == 7){
                    valid.add(new Move(current, up_l, new Queen(color, up_l), state));
                    valid.add(new Move(current, up_l, new Rook(color, up_l), state));
                    valid.add(new Move(current, up_l, new Bishop(color, up_l), state));
                    valid.add(new Move(current, up_l, new Knight(color, up_l), state));
                }
                else
                    valid.add(new Move(current, up_l, state));
            
            // Special
            if(current.y == 1){
                    Position upup = new Position(current.x, current.y + 2);
                    if(state.get_piece(up) == null && state.get_piece(upup) == null)
                        valid.add(new Move(current, upup, state));
            }
            
        } else {
            Position down = new Position(current.x, current.y - 1);
            Position down_r = new Position(current.x + 1, current.y - 1);
            Position down_l = new Position(current.x - 1, current.y - 1);
            
            // down
            if(down.in_board() && state.get_piece(down) == null)
                if(down.y == 0){
                    valid.add(new Move(current, down, new Queen(color, down), state));
                    valid.add(new Move(current, down, new Rook(color, down), state));
                    valid.add(new Move(current, down, new Bishop(color, down), state));
                    valid.add(new Move(current, down, new Knight(color, down), state));
                }
                else
                    valid.add(new Move(current, down, state));
            
            // down right
            if(down_r.in_board() && state.get_piece(down_r) != null && state.get_piece(down_r).color != color)
                if(down_r.y == 0){
                    valid.add(new Move(current, down_r, new Queen(color, down_r), state));
                    valid.add(new Move(current, down_r, new Rook(color, down_r), state));
                    valid.add(new Move(current, down_r, new Bishop(color, down_r), state));
                    valid.add(new Move(current, down_r, new Knight(color, down_r), state));
                }
                else
                    valid.add(new Move(current, down_r, state));
            
            // down left
            if(down_l.in_board() && state.get_piece(down_l) != null && state.get_piece(down_l).color != color)
                if(down_l.y == 0){
                    valid.add(new Move(current, down_l, new Queen(color, down_l), state));
                    valid.add(new Move(current, down_l, new Rook(color, down_l), state));
                    valid.add(new Move(current, down_l, new Bishop(color, down_l), state));
                    valid.add(new Move(current, down_l, new Knight(color, down_l), state));
                }
                else
                    valid.add(new Move(current, down_l, state));
            
            // Special
            if(current.y == 6){
                    Position downdown = new Position(current.x, current.y - 2);
                    if(state.get_piece(down) == null && state.get_piece(downdown) == null)
                        valid.add(new Move(current, downdown, state));
            }
        }
        return valid;
    }
    
    public Pawn copy(){
        return new Pawn(color, get_position());
    }
}
