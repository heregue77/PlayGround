import java.util.Random;

public class Algorithm {
    public int[][] board;
    public int[][] weight;
    public int[][] arr = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };
    public int valuex = 0, valuey = 0, temp = 0;

    public boolean Pass(int pan) {
        weight = new int[8][8];
        int weightcnt = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    PassCount(pan, i, j, -1, 0);
                    if (weightcnt < weight[i][j]) {
                        valuex = i;
                        valuey = j;
                        weightcnt = weight[i][j];
                    } else if (weightcnt == weight[i][j]) {
                        int rand = new Random().nextInt(2);
                        if (rand == 1) {
                            valuex = i;
                            valuey = j;
                            weightcnt = weight[i][j];
                        }
                    }
                }
            }
        }
        if (weightcnt == 0) {
            System.out.println("Pass");
            return false;
        } else
            return true;
    }

    public boolean PassCount(int pan, int x, int y, int pos, int count) {
        if (pos == -1) {
            for (int cnt = 0; cnt < 8; cnt++) {
                int tempx = x + arr[cnt][0];
                int tempy = y + arr[cnt][1];
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8
                        && board[tempx][tempy] == (pan * -1)) {
                    PassCount(pan, tempx, tempy, cnt, count + 1);
                    weight[x][y] += temp;
                    temp = 0;
                }
            }
        } else {
            int tempx = x + arr[pos][0];
            int tempy = y + arr[pos][1];
            if (board[x][y] == pan) {
                return true;
            } else if (board[x][y] == (pan * -1)) {
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8) {
                    if (PassCount(pan, tempx, tempy, pos, count + 1)) {
                        temp += count;
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean coinToss(int pan, int x, int y, int pos) {
        if (pos == -1) {
            for (int i = 0; i < 8; i++) {
                int tempx = x + arr[i][0];
                int tempy = y + arr[i][1];
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8
                        && board[tempx][tempy] == (pan * -1)) {
                    coinToss(pan, tempx, tempy, i);
                }
            }
        } else {
            int tempx = x + arr[pos][0];
            int tempy = y + arr[pos][1];
            if (board[x][y] == pan) {
                return true;
            } else if (board[x][y] == (pan * -1)) {
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8) {
                    if (coinToss(pan, tempx, tempy, pos)) {
                        board[x][y] = pan;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean gameOver(int cnt) {
        if (cnt == 0)
            return true;
        return false;
    }

    public void displayGame() {
        System.out.print("  ");
        for (int i = 1; i <= board.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= board.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < board.length; j++) {
                if (board[j][i - 1] == 1)
                    System.out.print("O ");
                else if (board[j][i - 1] == -1)
                    System.out.print("X ");
                else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println((valuex + 1) + " " + (valuey + 1));
    }

    public void whoIsWinner() {
        int a = 0, b = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1)
                    a++;
                else if (board[i][j] == -1)
                    b++;
            }
        }
        System.out.println("Player : " + a + " vs Computer : " + b);
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
