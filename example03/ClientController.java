package example03;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientController {
    Socket socket;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ClientController a = new ClientController();
        a.startClient();
        while (true) {
            a.send(br.readLine());
        }
    }

    public void startClient() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress("localhost", 7777));
                    System.out.println("[연결 성공 : " + socket.getRemoteSocketAddress() + "]");
                } catch (IOException e) {
                    System.out.println("[서버 통신 불가]");
                    if (!socket.isClosed()) {
                        stopClient();
                    }
                    return;
                }
                // 연결에 성공을 하면 데이터 수신을 먼저 하기 위해 호출
                receive();
            }
        };
        thread.start();
    }

    public void stopClient() {
        try {
            System.out.println("[접속 종료]");
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    public void receive() {
        // 데이터를 지속적으로 받기 위해서 무한루프로 작성
        while (true) {
            try {
                byte[] byteArr = new byte[1000];
                InputStream inputStream = socket.getInputStream();

                // 서버가 비정상적으로 종료했을 경우 IOException을 발생시킴
                int readCount = inputStream.read(byteArr);

                // 서버가 정상적으로 Socket의 close() 호출함
                if (readCount == -1) {
                    throw new IOException();
                }

                // 읽은 데이터를 디코딩함.
                String data = new String(byteArr, 0, readCount, "UTF-8");
                System.out.println("OP : " + data);
            } catch (Exception e) {
                System.out.println("[서버 통신 불가]");
                stopClient();
                break;
            }
        }
    }

    public void send(String data) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    byte[] byteArr = data.getBytes("UTF-8");
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(byteArr);
                    outputStream.flush();
                    System.out.println("Me : " + data);
                } catch (Exception e) {
                    System.out.println("[서버 통신 불가]");
                    stopClient();
                }
            }
        };
        thread.start();
    }
}
