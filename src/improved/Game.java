package improved;

import improved.Chess.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Game {
    // singleton
    public static Game instance = new Game();
    public static Game self(){
        return instance;
    }
    
    // player move
    private boolean selected;
    private Piece piece;
    private ArrayList<Move> moves;
    
    private int color;
    private State state;
    private boolean running;
    private int difficulty = 3; // 2 - easy   3 - medium   4 - hard
    
    public void set_difficulty(int i){
        difficulty = i;
    }
    
    private Game(){
    }
    
    public void start(){
        // Start with the normal board position
        color = GUI.self().new_game() * 6;
        
        // Initial state
        state = new State(color == CONSTANTS.BLACK);
        selected = false;
        
        if(color == CONSTANTS.BLACK){
            GUI.self().set_inverted(true);
            state.nuke(difficulty);
        } else
            GUI.self().set_inverted(false);
        
        running = true;
        draw();
    }
    public void draw(){
        GUI.self().draw_state(state);
    }
    public void pew(Position p){
        if(!p.in_board())
            return;
        
        if(selected && running){
            // Commit move
            if(piece != null && moves != null){
                Move move = Move.has(moves, p);
                if(move != null){
                    state.move(move);
                    GUI.self().highlight(null);
                    state.nuke(difficulty);
                }
            }
            GUI.self().highlight(null);
            selected = false;
        } else {
            // Select piece
            piece = state.get_piece(p);
            try{
                if(piece.color == color){
                    moves = piece.checked_moves(state);
                    
                    if(moves.size() > 0){
                        GUI.self().highlight(moves);
                        selected = true;
                    }
                }
            }catch(NullPointerException e){}
        }
    }
}
