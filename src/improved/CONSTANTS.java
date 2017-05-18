package improved;
public class CONSTANTS {
    public static final int BLACK      = 0;
    public static final int WHITE      = 6;
    
    public static final int KING      = 0;
    public static final int QUEEN     = 1;
    public static final int ROOK      = 2;
    public static final int BISHOP    = 3;
    public static final int KNIGHT    = 4;
    public static final int PAWN      = 5;
    
    // Piece weights
    public static final int KING_W    = 20000;
    public static final int QUEEN_W   = 900;
    public static final int ROOK_W    = 500;
    public static final int BISHOP_W  = 330;
    public static final int KNIGHT_W  = 320;
    public static final int PAWN_W    = 100;
    
    // Location rating
    public static final int[][] KINGW_R = new int[][]{
        new int[]{20, 30, 10,  0,  0, 10, 30, 20},
        new int[]{20, 20,  0,  0,  0,  0, 20, 20},
        new int[]{-10,-20,-20,-20,-20,-20,-20,-10},
        new int[]{-20,-30,-30,-40,-40,-30,-30,-20},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
    };
    public static final int[][] KINGB_R = new int[][]{
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-30,-40,-40,-50,-50,-40,-40,-30},
        new int[]{-20,-30,-30,-40,-40,-30,-30,-20},
        new int[]{-10,-20,-20,-20,-20,-20,-20,-10},
        new int[]{20, 20,  0,  0,  0,  0, 20, 20},
        new int[]{20, 30, 10,  0,  0, 10, 30, 20}
    };
    public static final int[][] QUEEN_R = new int[][]{
        new int[]{-20,-10,-10, -5, -5,-10,-10,-20},
        new int[]{-10,  0,  0,  0,  0,  0,  0,-10},
        new int[]{-10,  0,  5,  5,  5,  5,  0,-10},
        new int[]{-5,  0,  5,  5,  5,  5,  0, -5},
        new int[]{0,  0,  5,  5,  5,  5,  0, -5},
        new int[]{-10,  5,  5,  5,  5,  5,  0,-10},
        new int[]{-10,  0,  5,  0,  0,  0,  0,-10},
        new int[]{-20,-10,-10, -5, -5,-10,-10,-20}
    };
    
    public static final int[][] ROOKW_R = new int[][]{
        new int[]{0,  0,  0,  5,  5,  0,  0,  0},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{5, 10, 10, 10, 10, 10, 10,  5},
        new int[]{0,  0,  0,  0,  0,  0,  0,  0}
    };
    
    public static final int[][] ROOKB_R = new int[][]{
        new int[]{0,  0,  0,  0,  0,  0,  0,  0},
        new int[]{5, 10, 10, 10, 10, 10, 10,  5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{-5,  0,  0,  0,  0,  0,  0, -5},
        new int[]{0,  0,  0,  5,  5,  0,  0,  0}
    };
    public static final int[][] BISHOP_R = new int[][]{
        new int[]{-20,-10,-10,-10,-10,-10,-10,-20},
        new int[]{-10,  0,  0,  0,  0,  0,  0,-10},
        new int[]{-10,  0,  5, 10, 10,  5,  0,-10},
        new int[]{-10,  5,  5, 10, 10,  5,  5,-10},
        new int[]{-10,  0, 10, 10, 10, 10,  0,-10},
        new int[]{-10, 10, 10, 10, 10, 10, 10,-10},
        new int[]{-10,  5,  0,  0,  0,  0,  5,-10},
        new int[]{-20,-10,-10,-10,-10,-10,-10,-20},
    };
    public static final int[][] KNIGHT_R = new int[][]{
        new int[]{-50,-40,-30,-30,-30,-30,-40,-50},
        new int[]{-40,-20,  0,  0,  0,  0,-20,-40},
        new int[]{-30,  0, 10, 15, 15, 10,  0,-30},
        new int[]{-30,  5, 15, 20, 20, 15,  5,-30},
        new int[]{-30,  0, 15, 20, 20, 15,  0,-30},
        new int[]{-30,  5, 10, 15, 15, 10,  5,-30},
        new int[]{-40,-20,  0,  5,  5,  0,-20,-40},
        new int[]{-50,-40,-30,-30,-30,-30,-40,-50},
    };
    public static final int[][] PAWNW_R = new int[][]{
        new int[]{0,  0,  0,  0,  0,  0,  0,  0},
        new int[]{5, 10, 10,-20,-20, 10, 10,  5},
        new int[]{5, -5,-10,  0,  0,-10, -5,  5},
        new int[]{0,  0,  0, 20, 20,  0,  0,  0},
        new int[]{5,  5, 10, 25, 25, 10,  5,  5},
        new int[]{10, 10, 20, 30, 30, 20, 10, 10},
        new int[]{50, 50, 50, 50, 50, 50, 50, 50},
        new int[]{0,  0,  0,  0,  0,  0,  0,  0}
    };
    public static final int[][] PAWNB_R = new int[][]{
        new int[]{0,  0,  0,  0,  0,  0,  0,  0},
        new int[]{50, 50, 50, 50, 50, 50, 50, 50},
        new int[]{10, 10, 20, 30, 30, 20, 10, 10},
        new int[]{5,  5, 10, 25, 25, 10,  5,  5},
        new int[]{0,  0,  0, 20, 20,  0,  0,  0},
        new int[]{5, -5,-10,  0,  0,-10, -5,  5},
        new int[]{5, 10, 10,-20,-20, 10, 10,  5},
        new int[]{0,  0,  0,  0,  0,  0,  0,  0}
    };
    
    public static final int[][][] RATING = new int[][][]{
        // Black
        KINGB_R,
        QUEEN_R,
        ROOKB_R,
        BISHOP_R,
        KNIGHT_R,
        PAWNB_R,
        
        // White
        KINGW_R,
        QUEEN_R,
        ROOKW_R,
        BISHOP_R,
        KNIGHT_R,
        PAWNW_R,
    };
}
