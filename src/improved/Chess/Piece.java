package improved.Chess;

import improved.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Piece {
    public boolean moved;
    public final int color;
    public final int type;
    private final Position position;
    
    public Piece(int color, int type, int x, int y){
        this.color = color;
        this.type  = type;
        
        this.position = new Position(x, y);
        moved = false;
    }
    public Piece(int color, int type, Position p){
        this.color = color;
        this.type = type;
        
        this.position = p;
        moved = false;
    }
    
    // Position shit
    public Position get_position(){
        return position.copy();
    }
    public void set_position(Position p){
        position.x = p.x;
        position.y = p.y;
    }
    public void set_position(int x, int y){
        position.x = x;
        position.y = y;
    }
    
    // moves :(
    public abstract ArrayList<Move> moves(State state);
    
    public ArrayList<Move> checked_moves(State state){
        ArrayList<Move> possible = moves(state);
        
        Iterator<Move> iter = possible.iterator();
        
        state.change_turn();
        while(iter.hasNext()){
            Move m = iter.next();
            state.move(m);
            if(state.in_check())
                iter.remove();
            state.undo();
        }
        state.change_turn();
        return possible;
    }
    
    public ArrayList<Move> axis(State state){
        ArrayList<Move> valid = new ArrayList();
        
        // up
        for(int j = position.y + 1; j < 8; j++){
            Position p = new Position(position.x, j);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        
        // left
        for(int i = position.x - 1; i >= 0; i--){
            Position p = new Position(i, position.y);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        
        // right
        for(int i = position.x + 1; i < 8; i++){
            Position p = new Position(i, position.y);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        
        // down
        for(int j = position.y - 1; j >= 0; j--){
            Position p = new Position(position.x, j);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        return valid;
    }
    public ArrayList<Move> diagonal(State state){
        ArrayList<Move> valid = new ArrayList();
        
        // up right
        for(int i = position.x + 1, j = position.y + 1; i < 8 && j < 8; i++, j++){
            Position p = new Position(i, j);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        
        // up left
        for(int i = position.x - 1, j = position.y + 1; i >= 0 && j < 8; i--, j++){
            Position p = new Position(i, j);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        
        // down right
        for(int i = position.x + 1, j = position.y - 1; i < 8 && j >= 0; i++, j--){
            Position p = new Position(i, j);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color != color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        
        // down left
        for(int i = position.x - 1, j = position.y - 1; i >= 0 && j >= 0; i--, j--){
            Position p = new Position(i, j);
            if(p.in_board()){
                Piece square_piece = state.get_piece(p);
                if(square_piece == null)
                    valid.add(new Move(get_position(), p, state));
                else if (square_piece.color!= color){
                    valid.add(new Move(get_position(), p, state));
                    break;
                } else
                    break;
            }
        }
        return valid;
    }
    
    public abstract Piece copy();
}
