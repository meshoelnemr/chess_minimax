package improved;

import improved.Chess.*;
import java.util.*;

public class State {
    private boolean ai;
    public boolean turn = true;
    public void change_turn(){
        turn = !turn;
    }
    private Piece[][] all = new Piece[][]{
        new Piece[8],
        new Piece[8],
        new Piece[8],
        new Piece[8],
        new Piece[8],
        new Piece[8],
        new Piece[8],
        new Piece[8],
    };
    King white, black;
    public void set_piece(Piece p, int x, int y){
        if(p != null && p.type == CONSTANTS.KING){
            if(p.color == CONSTANTS.BLACK)
                black = (King)p;
            else
                white = (King)p;
        }
        all[x][y] = p;
    }
    public void set_piece(Piece p, Position pos){
        if(p != null && p.type == CONSTANTS.KING){
            if(p.color == CONSTANTS.BLACK)
                black = (King)p;
            else
                white = (King)p;
        }
        all[pos.x][pos.y] = p;
    }
    public Piece get_piece(int x, int y){
        return all[x][y];
    }
    public Piece get_piece(Position pos){
        return all[pos.x][pos.y];
    }
    public State(boolean type){
        ai = type;
    }
    
    public void init_default(){
        all[0][0] = new Rook(CONSTANTS.WHITE,     0, 0);
        all[1][0] = new Knight(CONSTANTS.WHITE,   1, 0);
        all[2][0] = new Bishop(CONSTANTS.WHITE,   2, 0);
        all[3][0] = new Queen(CONSTANTS.WHITE,    3, 0);
        all[4][0] = new King(CONSTANTS.WHITE,     4, 0);
        white = (King)all[4][0];
        all[5][0] = new Bishop(CONSTANTS.WHITE,   5, 0);
        all[6][0] = new Knight(CONSTANTS.WHITE,   6, 0);
        all[7][0] = new Rook(CONSTANTS.WHITE,     7, 0);
        
        
        all[0][7] = new Rook(CONSTANTS.BLACK,     0, 7);
        all[1][7] = new Knight(CONSTANTS.BLACK,   1, 7);
        all[2][7] = new Bishop(CONSTANTS.BLACK,   2, 7);
        all[3][7] = new Queen(CONSTANTS.BLACK,    3, 7);
        all[4][7] = new King(CONSTANTS.BLACK,     4, 7);
        black = (King)all[4][7];
        all[5][7] = new Bishop(CONSTANTS.BLACK,   5, 7);
        all[6][7] = new Knight(CONSTANTS.BLACK,   6, 7);
        all[7][7] = new Rook(CONSTANTS.BLACK,     7, 7);
        
        for(int i = 0; i < 8; i++){
            all[i][1] = new Pawn(CONSTANTS.WHITE, i, 1);
            all[i][6] = new Pawn(CONSTANTS.BLACK, i, 6);
        }
    }
    
    // Check and checkmate
    public boolean in_check(){
        if(turn)
            return marked(white.get_position());
        else
            return marked(black.get_position());
    }
    public boolean marked(Position pos){
        int color = get_piece(pos).color;
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = all[i][j];
                    if (p.color != color && Move.has(p.moves(this), pos) != null){
                        return true;
                    }
                }catch(NullPointerException e){}
        return false;
    }
    public boolean castle_marked(Position pos){
        int color = turn ? CONSTANTS.WHITE : CONSTANTS.BLACK;
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = all[i][j];
                    if (p.color != color && Move.has(p.moves(this), pos) != null){
                        return true;
                    }
                }catch(NullPointerException e){}
        return false;
    }
    public boolean checkmate(){
        if(!in_check())
            return false;
        
        return stalemate();
    }
    public boolean stalemate(){
        int color = turn ? CONSTANTS.WHITE : CONSTANTS.BLACK;
        
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = get_piece(i, j);
                    if(p.color == color && p.checked_moves(this).size() > 0)
                        return false;
                }catch(NullPointerException e){}
        return true;
    }
    
    // moves
    private final Stack<Move> previous = new Stack();
    public void move(Move m){
        m.make(this);
        previous.add(m);
    }
    
    public void undo(){
        try{
            Move m = previous.pop();
            m.reverse(this);
        }catch(EmptyStackException e){}
    }
    
    // minimax
    public int evaluate(){
        int[] weight = new int[12];
        int[] location = new int[12];
        
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = get_piece(i, j);
                    int index = p.color + p.type;
                    
                    location[index] += CONSTANTS.RATING[index][i][j];
                    weight[index] += 1;
                }catch(NullPointerException e){}
        
        int result = 
        CONSTANTS.KING_W * (weight[CONSTANTS.WHITE + CONSTANTS.KING] - weight[CONSTANTS.BLACK + CONSTANTS.KING])
      + CONSTANTS.QUEEN_W * (weight[CONSTANTS.WHITE + CONSTANTS.QUEEN] - weight[CONSTANTS.BLACK + CONSTANTS.QUEEN])
      + CONSTANTS.ROOK_W * (weight[CONSTANTS.WHITE + CONSTANTS.ROOK] - weight[CONSTANTS.BLACK + CONSTANTS.ROOK])
      + CONSTANTS.BISHOP_W * (weight[CONSTANTS.WHITE + CONSTANTS.BISHOP] - weight[CONSTANTS.BLACK + CONSTANTS.BISHOP])
      + CONSTANTS.KNIGHT_W * (weight[CONSTANTS.WHITE + CONSTANTS.KNIGHT] - weight[CONSTANTS.BLACK + CONSTANTS.KNIGHT])
      + CONSTANTS.PAWN_W * (weight[CONSTANTS.WHITE + CONSTANTS.PAWN] - weight[CONSTANTS.BLACK + CONSTANTS.PAWN]);
        
        int loc = 0;
        for(int i = 0; i < 6; i++){
            loc += location[6+i] - location[i];
        }
        
        int mate = 0;
        if(checkmate())
            mate = (ai == turn) ? -999999 : 999999;
        
        if(ai)// black ai
            return result + loc + mate;
        // white ai
        return -1 * (result + loc) + mate;
    }
    public void nuke(int difficulty){
        Move m = minimax(difficulty);
        if(m != null)
            move(m);        
    }
    
    public Move minimax(int depth){
        int color = turn ? CONSTANTS.WHITE : CONSTANTS.BLACK;
        
        Move best = null;
        int rating = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = get_piece(i, j);
                    if(p.color == color){
                        for(Move m : p.checked_moves(this)){
                            move(m);
                            int val = min(depth - 1, alpha, beta);
                            if(val > rating){
                                rating = val;
                                best = m;
                            }
                            undo();
                            
                            // prune
                            alpha = Math.max(alpha, rating);
                            if(alpha >= beta)
                                break;
                        }
                    }
                }catch(NullPointerException e){}
        return best;
    }
    public int min(int depth, int alpha, int beta){
        int color = turn ? CONSTANTS.WHITE : CONSTANTS.BLACK;
        int rating = Integer.MAX_VALUE;
        
        // terminal state flag
        boolean terminal = true;
        
        if(depth < 1)
            return evaluate();
        
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = get_piece(i, j);
                    if(p.color == color){
                        ArrayList<Move> moves = p.checked_moves(this);
                        if(!moves.isEmpty())
                            terminal = false;
                        for(Move m : moves){
                            // rate move
                            move(m);
                            rating = Math.min(rating, max(depth - 1, alpha, beta));
                            undo();
                            
                            // prune
                            beta = Math.min(beta, rating);
                            if(alpha >= beta)
                                break;
                        }
                    }
                }catch(NullPointerException e){}
        if(terminal){
//            System.err.println("Min " + (ai == turn));
//            System.err.println("Min " + evaluate());
            return evaluate();
        }
        return rating;
    }
    public int max(int depth, int alpha, int beta){
        int color = turn ? CONSTANTS.WHITE : CONSTANTS.BLACK;
        int rating = Integer.MIN_VALUE;
        
        // terminal state flag
        boolean terminal = true;
        
        if(depth < 1)
            return evaluate();
        
        for(int i = 0; i < 8 ; i++)
            for(int j = 0; j < 8; j++)
                try{
                    Piece p = get_piece(i, j);
                    if(p.color == color){
                        ArrayList<Move> moves = p.checked_moves(this);
                        if(!moves.isEmpty())
                            terminal = false;
                        for(Move m : moves){
                            // rate move
                            move(m);
                            rating = Math.max(rating, min(depth - 1, alpha, beta));
                            undo();
                            
                            // prune
                            alpha = Math.max(alpha, rating);
                            if(alpha >= beta)
                                break;
                        }
                    }
                }catch(NullPointerException e){}
        if(terminal){
//            System.err.println("Max " + (ai == turn));
//            System.err.println("Max " + evaluate());
            return evaluate();
        }
        return rating;
    }
    
    // Helper
    public Iterator<Piece> iterator() {
        List<Piece> l = new LinkedList();
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++){
                if(all[i][j] != null)
                    l.add(all[i][j]);
            }
        return l.iterator();
    }
    public String toString(){
        String s = "";
        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < 8; j++)
                try{
                    char c = '-';
                    switch(get_piece(j, 7 - i).type){
                        case CONSTANTS.KING:
                            c = 'K';
                            break;
                        case CONSTANTS.QUEEN:
                            c = 'Q';
                            break;
                        case CONSTANTS.ROOK:
                            c = 'R';
                            break;
                        case CONSTANTS.BISHOP:
                            c = 'B';
                            break;
                        case CONSTANTS.KNIGHT:
                            c = 'N';
                            break;
                        case CONSTANTS.PAWN:
                            c = 'P';
                            break;
                    }
                    s += c;
                }catch(NullPointerException e){
                    s += "-";
                }
            s += '\n';
        }
        return s;
    }
}
