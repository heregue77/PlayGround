public class Algorithm {
    public int[][] board;

    public boolean Pass(int[][] board) {
        int[][] arr = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                for (int cnt = 0; cnt < 8; cnt++) {
                    if (i + arr[cnt][0] >= 0 && i + arr[cnt][0] <= 8 && j + arr[cnt][1] >= 0 && j + arr[cnt][1] <= 8) {
                        if (board[i][j] == -1) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public void playerUse(int[][] board, int x, int y) {

    }

    public void computerUse(int[][] board) {

    }

    public boolean gameOver(int cnt) {
        if (cnt == 0)
            return true;
        return false;
    }

    public void displayGame(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void whoIsWinner() {
        int a = 0, b = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1)
                    a++;
                else
                    b++;
            }
        }
        if (a > b)
            System.out.println("Player Win!");
        else if (a < b)
            System.out.println("Computer Win!");
        else
            System.out.println("Draw!");
    }

    public Algorithm(int[][] board) {
        this.board = board;
    }
}
