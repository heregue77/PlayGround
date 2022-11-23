public class Board {
    public int cnt = 60;
    public int[][] board = new int[8][8];

    public Board() {
        this.board[3][3] = 1;
        this.board[3][4] = -1;
        this.board[4][3] = -1;
        this.board[4][4] = 1;
    }
}
