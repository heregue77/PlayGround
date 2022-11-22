import java.util.Random;

public class Algorithm {
    public int[][] board;
    public int[][] arr = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };
    public int valuex = 0, valuey = 0, value = 0, temp = 0;

    public boolean Pass(int pan) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    for (int cnt = 0; cnt < 8; cnt++) {
                        int tempx = i + arr[cnt][0];
                        int tempy = j + arr[cnt][1];
                        if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8) {
                            if (board[tempx][tempy] == (pan * -1))
                                return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean playerUse(int x, int y, int pos) {
        if (pos == -1) {
            for (int i = 0; i < 8; i++) {
                int tempx = x + arr[i][0];
                int tempy = y + arr[i][1];
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8
                        && board[tempx][tempy] == -1) {
                    playerUse(tempx, tempy, i);
                }
            }
        } else {
            int tempx = x + arr[pos][0];
            int tempy = y + arr[pos][1];
            if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8) {
                if (board[x][y] == 1) {
                    return true;
                } else if (board[x][y] == -1) {
                    if (playerUse(tempx, tempy, pos)) {
                        board[x][y] = 1;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void computerUseRotate() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    computerUse(i, j, -1, 0);
                    if (value < temp) {
                        valuex = i;
                        valuey = j;
                        value = temp;
                    } else if (value == temp) {
                        int rand = new Random().nextInt(2);
                        if (rand == 1) {
                            valuex = i;
                            valuey = j;
                            value = temp;
                        }
                    }
                    temp = 0;
                }
            }
        }
        board[valuex][valuey] = -1;
        computerUseCalc(valuex, valuey, -1);
    }

    public boolean computerUse(int x, int y, int pos, int count) {
        if (pos == -1) {
            for (int cnt = 0; cnt < 8; cnt++) {
                int tempx = x + arr[cnt][0];
                int tempy = y + arr[cnt][1];
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8
                        && board[tempx][tempy] == 1) {
                    computerUse(tempx, tempy, cnt, count + 1);
                }
            }
        } else {
            int tempx = x + arr[pos][0];
            int tempy = y + arr[pos][1];
            if (board[x][y] == -1) {
                return true;
            } else if (board[x][y] == 1) {
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8) {
                    if (computerUse(tempx, tempy, pos, count + 1)) {
                        temp += count;
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean computerUseCalc(int x, int y, int pos) {
        if (pos == -1) {
            for (int i = 0; i < 8; i++) {
                int tempx = x + arr[i][0];
                int tempy = y + arr[i][1];
                if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8
                        && board[tempx][tempy] == 1) {
                    computerUseCalc(tempx, tempy, i);
                }
            }
        } else {
            int tempx = x + arr[pos][0];
            int tempy = y + arr[pos][1];
            if (tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8) {
                if (board[x][y] == -1) {
                    return true;
                } else if (board[x][y] == 1) {
                    if (computerUseCalc(tempx, tempy, pos)) {
                        board[x][y] = -1;
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
                else
                    System.out.print("_ ");
            }
            System.out.println();
        }
        System.out.println();
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
