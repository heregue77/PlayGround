import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        Board b = new Board();
        Algorithm al = new Algorithm(b.board);

        while (!al.gameOver(b.cnt)) {
            al.displayGame();
            if (al.Pass(1)) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken()) - 1;
                b.board[x][y] = 1;
                al.displayGame();
                al.playerUse(x, y, -1);
                al.displayGame();
                b.cnt--;
            }
            if (al.Pass(-1)) {
                al.computerUseRotate();
                al.displayGame();
                b.cnt--;
            }
        }

        al.whoIsWinner();

        bw.flush();
        br.close();
        bw.close();
    }
}
