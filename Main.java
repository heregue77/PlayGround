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
            if (al.Pass(null)) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                b.board[x][y] = 1;
                al.playerUse(b.board, x, y);
                b.cnt--;
            }
            al.computerUse(b.board);
        }

        al.whoIsWinner();

        bw.flush();
        br.close();
        bw.close();
    }
}
