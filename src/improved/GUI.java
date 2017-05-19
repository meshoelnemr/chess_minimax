package improved;

import improved.Chess.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUI extends JFrame{
    // singleton
    private static final GUI instance = new GUI();
    public static GUI self(){
        return instance;
    }
    
    // Contents
    private final Panel panel = new Panel();
    private final JMenuBar menu_bar = new JMenuBar();
    
    private GUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Hola");
        
        // add components
        init_menu_bar();
        
        Container c = getContentPane();
        c.add(menu_bar, BorderLayout.NORTH);
        c.add(panel, BorderLayout.SOUTH);
        
        pack();
    }
    
    public void draw_state(State state){
        panel.set_state(state);
        panel.repaint();
    }
    
    private void init_menu_bar(){
        JMenu[] menus = new JMenu[]{
            new JMenu("File"),
            new JMenu("Options"),
        };
        
        // File
        JMenuItem[] file = new JMenuItem[]{
            new JMenuItem("Play"),
            new JMenuItem("Exit"),
        };
        file[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.self().start();
            }
        });
        menus[0].add(file[0]);
        file[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menus[0].add(file[1]);
        
        // Options
        JMenuItem invert = new JMenuItem("Invert");
        invert.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.invert();
                }
            });
        menus[1].add(invert);
        
        JMenuItem[] difficulty = new JMenuItem[]{
            new JMenuItem("Easy"),
            new JMenuItem("Medium"),
            new JMenuItem("Hard"),
        };
        for(JMenuItem m : difficulty){
            m.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JMenuItem j = (JMenuItem)e.getSource();
                    switch(j.getText()){
                        case "Easy":
                            Game.self().set_difficulty(1);
                            break;
                        case "Medium":
                            Game.self().set_difficulty(3);
                            break;
                        case "Hard":
                            Game.self().set_difficulty(5);
                            break;
                    }
                }
            });
            menus[1].add(m);
        }
        
        
        for(JMenu m : menus)
            menu_bar.add(m);
    }
    public void set_inverted(boolean bool){
        panel.set_inverted(bool);
    }
    public int new_game(){
        return JOptionPane.showOptionDialog(null,
                               "Black or White",
                               "Choose your color",
                               JOptionPane.YES_NO_OPTION,
                               JOptionPane.INFORMATION_MESSAGE,
                               null,
                               new String[]{"Black","White"},
                               "White");
    }
    public void highlight(ArrayList<Move> moves){
        panel.highlight(moves);
    }
}


class Panel extends JPanel{
    private ArrayList<Move> selected;
    private State state;
    private boolean inverted = false;
    // Chess items
    private final Image background = new ImageIcon(getClass().getResource("images/board.gif")).getImage();
    private final Image pieces[] = new Image[]{
        new ImageIcon(getClass().getResource("images/b_king.png")).getImage(),
        new ImageIcon(getClass().getResource("images/b_queen.png")).getImage(),
        new ImageIcon(getClass().getResource("images/b_rook.png")).getImage(),
        new ImageIcon(getClass().getResource("images/b_bishop.png")).getImage(),
        new ImageIcon(getClass().getResource("images/b_knight.png")).getImage(),
        new ImageIcon(getClass().getResource("images/b_pawn.png")).getImage(),
        
        new ImageIcon(getClass().getResource("images/w_king.png")).getImage(),
        new ImageIcon(getClass().getResource("images/w_queen.png")).getImage(),
        new ImageIcon(getClass().getResource("images/w_rook.png")).getImage(),
        new ImageIcon(getClass().getResource("images/w_bishop.png")).getImage(),
        new ImageIcon(getClass().getResource("images/w_knight.png")).getImage(),
        new ImageIcon(getClass().getResource("images/w_pawn.png")).getImage(),
    };
    public Panel(){
        setPreferredSize(new Dimension(641, 641));
        setFocusable(true);
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                if(inverted)
                    Game.self().pew(new Position(7 - e.getX() / 80, e.getY() / 80));
                else
                    Game.self().pew(new Position(e.getX() / 80, 7 - e.getY() / 80));
            }
        });
    }
    public void paint(Graphics g){
        super.paint(g);
        
        if(state == null)
            return;
        
        Iterator<Piece> iterator = state.iterator();
        while(iterator.hasNext()){
            Piece p = iterator.next();
            Position loc = p.get_position();
            if(inverted)
                g.drawImage(
                        pieces[p.color + p.type],
                        (7 - loc.x) * 80,
                        loc.y * 80,
                        null);
            else
                g.drawImage(
                        pieces[p.color + p.type],
                        loc.x * 80,
                        (7 - loc.y) * 80,
                        null);
        }
        
        if(selected != null){
            g.setColor(Color.red);
            if(inverted){
                for(Move m : selected){
                    Position p = m.to_pos[0];
                    g.drawRect((7 - p.x) * 80, p.y * 80, 80, 80);
                }
            }else{
                for(Move m : selected){
                    Position p = m.to_pos[0];
                    g.drawRect(p.x * 80, (7 - p.y) * 80, 80, 80);
                }
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
    
    public void invert(){
        inverted = !inverted;
        repaint();
    }
    public void set_inverted(boolean bool){
        inverted = bool;
        repaint();
    }
    public void set_state(State state){
        this.state = state;
    }
    public void highlight(ArrayList<Move> moves){
        selected = moves;
        repaint();
    }
}