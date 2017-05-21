package improved;

import improved.Chess.*;
import java.util.ArrayList;

public class Move {
    public Piece[] from;
    public Piece[] to;
    
    public Position[] from_pos;
    public Position[] to_pos;
    
    public Piece promotion;
    
    public Move(Position pos1, Position pos2, State state){
        from_pos = new Position[]{pos1.copy()};
        to_pos = new Position[]{pos2.copy()};

        from = new Piece[]{state.get_piece(pos1)};
        to   = new Piece[]{state.get_piece(pos2)};

        promotion = null;
    }
    
    public Move(Position pos1, Position pos2, Position pos3, Position pos4, State state){
        from_pos = new Position[]{pos1.copy(), pos3.copy()};
        to_pos = new Position[]{pos2.copy(), pos4.copy()};

        from = new Piece[]{state.get_piece(pos1), state.get_piece(pos3)};
        to   = new Piece[]{state.get_piece(pos2), state.get_piece(pos4)};
        
        promotion = null;
    }
    
    public Move(Position pos1, Position pos2, Piece piece, State state){
        from_pos = new Position[]{pos1.copy()};
        to_pos = new Position[]{pos2.copy()};

        from = new Piece[]{state.get_piece(pos1)};
        to   = new Piece[]{state.get_piece(pos2)};
        
        promotion = piece;
    }
    
    
    // Perform move
    public void make(State state){
        if(promotion == null)
            for(int i = 0; i < from_pos.length ; i++){
                Piece p1 = state.get_piece(from_pos[i]).copy();

                state.set_piece(p1, to_pos[i]);
                p1.set_position(to_pos[i]);
                p1.moved = true;

                state.set_piece(null, from_pos[i]);
            }
        else{
            Piece p1 = promotion;

            state.set_piece(p1, to_pos[0]);
            p1.set_position(to_pos[0]);
            p1.moved = true;

            state.set_piece(null, from_pos[0]);
        }
        state.change_turn();
    }
    
    // undo move
    public void reverse(State state){
        for(int i = 0; i < from_pos.length ; i++){
            Piece p1 = from[i];
            Piece p2 = to[i];
            
            state.set_piece(p1, from_pos[i]);
            p1.set_position(from_pos[i]);
            
            state.set_piece(p2, to_pos[i]);
            if(p2 != null)
                p2.set_position(to_pos[i]);
        }
        state.change_turn();
    }
    
    public static Move has(ArrayList<Move> moves, Position p){
        for(Move m : moves)
            if(p.equals(m.to_pos[0]))
                return m;
        return null;
    }
    
    public static Move promotion_type(ArrayList<Move> moves, int type){
        for(Move m : moves)
            if(m.promotion.type == type)
                return m;
        return null;
    }
}
