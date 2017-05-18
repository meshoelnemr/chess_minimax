package improved.Chess;

import improved.*;
import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(int color, int x, int y) {
        super(color, CONSTANTS.KNIGHT, x, y);
    }
    public Knight(int color, Position p) {
        super(color, CONSTANTS.KNIGHT, p);
    }
    
    public ArrayList<Move> moves(State state){
        ArrayList<Move> valid = new ArrayList();
        Position current = get_position();
        
        Position[] next = new Position[]{
            new Position(current.x + 1, current.y + 2),
            new Position(current.x - 1, current.y + 2),
            new Position(current.x + 1, current.y - 2),
            new Position(current.x - 1, current.y - 2),
            new Position(current.x + 2, current.y + 1),
            new Position(current.x - 2, current.y + 1),
            new Position(current.x + 2, current.y - 1),
            new Position(current.x - 2, current.y - 1),
        };
        
        for(Position p : next){
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null || square_piece.color != color)
                    valid.add(new Move(current, p, state));
            }
        }
        
        return valid;
    }
    
    public Knight copy(){
        return new Knight(color, get_position());
    }
}
