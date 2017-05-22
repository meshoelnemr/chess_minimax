package improved;

import improved.Chess.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

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
    private int difficulty = 3; // 1 - easy   3 - medium   5 - hard
    
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
        state.init_default();
        
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
        if(!running || !p.in_board())
            return;
        
        if(selected){
            // Commit move
            if(piece != null && moves != null){
                Move move = Move.has(moves, p);
                if(move != null){
                    if(move.promotion != null)
                        move = get_promotion_type(moves);
                    state.move(move);
                    state.nuke(difficulty);
                    GUI.self().highlight(null);
                    
                    if(state.stalemate()){
                        if(state.in_check())
                            JOptionPane.showMessageDialog(null, "Checkmate");
                        else
                            JOptionPane.showMessageDialog(null, "Stalemate");
                        running = false;
                    }
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
    
    private Move get_promotion_type(ArrayList<Move> moves){
        Move move;
        int option = JOptionPane.showOptionDialog(null,
                               "Promote pawn",
                               "Choose type",
                               JOptionPane.YES_NO_OPTION,
                               JOptionPane.INFORMATION_MESSAGE,
                               null,
                               new String[]{"Queen","Rook","Bishop","Knight"},
                               "Queen");
        switch(option){
            case 1:
                move = Move.promotion_type(moves, CONSTANTS.ROOK);
                break;
            case 2:
                move = Move.promotion_type(moves, CONSTANTS.BISHOP);
                break;
            case 3:
                move = Move.promotion_type(moves, CONSTANTS.KNIGHT);
                break;
            case 0:
            default:
                move = Move.promotion_type(moves, CONSTANTS.QUEEN);
                break;
        }
        
        return move;
    }
    
    public void save(){
        if(state == null)
            return;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        
        int ret = chooser.showSaveDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(chooser.getSelectedFile()));
                bw.write(color == CONSTANTS.WHITE ? "white\n" : "black\n");
                for(int i = 0; i < 8 ; i++)
                    for(int j = 0; j < 8; j++)
                        try{
                            Piece p = state.get_piece(7 - j, i);
                            String t = String.valueOf(p.type);
                            String c = String.valueOf(p.color);
                            bw.write(t+c+i+j+"\n");
                        }catch(NullPointerException e){}
                
                bw.close();
            }catch(IOException e){}
        }
    }
    public void load(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        
        int ret = chooser.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            try{
                BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()));

                String COLOR = br.readLine();
                if(COLOR.startsWith("white")){
                    color = CONSTANTS.WHITE;
                    state = new State(false);
                    state.turn = true;
                    GUI.self().set_inverted(false);
                }
                else{
                    color = CONSTANTS.BLACK;
                    state = new State(true);
                    state.turn = false;
                    GUI.self().set_inverted(true);
                }
                
                while(br.ready()){
                    String line = br.readLine();
                    int t = Integer.valueOf(line.substring(0, 1));
                    int c = Integer.valueOf(line.substring(1, 2));
                    int i = Integer.valueOf(line.substring(2, 3));
                    int j = Integer.valueOf(line.substring(3, 4));
                    
                    Piece p;
                    switch(t){
                        case CONSTANTS.KING:
                            p = new King(c, 7 - j, i);
                            break;
                        case CONSTANTS.QUEEN:
                            p = new Queen(c, 7 - j, i);
                            break;
                        case CONSTANTS.ROOK:
                            p = new Rook(c, 7 - j, i);
                            break;
                        case CONSTANTS.BISHOP:
                            p = new Bishop(c, 7 - j, i);
                            break;
                        case CONSTANTS.KNIGHT:
                            p = new Knight(c, 7 - j, i);
                            break;
                        case CONSTANTS.PAWN:
                            p = new Pawn(c, 7 - j, i);
                            break;
                        default:
                            p = null;
                            break;
                    }
                    
                    state.set_piece(p, 7 - j, i);
                }
                br.close();
                
                GUI.self().draw_state(state);
                running = true;
            }catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
