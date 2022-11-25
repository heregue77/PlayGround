package example03;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

public class ServerController {
    static ExecutorService executorService; // 스레드 풀 객체 만들기 위한 선언
    static ServerSocket serverSocekt;
    static List<Client> clientList = new Vector<Client>(); // 클라이언트를 관리하기 위한 컬렉션 선언

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(10);
        try {
            serverSocekt = new ServerSocket();
            serverSocekt.bind(new InetSocketAddress("localhost", 7777));
        } catch (IOException e) {
            if (!serverSocekt.isClosed()) {
                stopServer();
                return;
            }
        }

        // 서버에서 accept()에서 블로킹이 일어나기 때문에 따로 하나의 스레드가 담당하도록 코드를 작성한다.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("[서버가 시작되었습니다.]");
                while (true) {
                    try {
                        Socket socket = serverSocekt.accept();
                        String data = "[연결 수락 : " + socket.getRemoteSocketAddress() + " : "
                                + Thread.currentThread().getName() + "]";
                        System.out.println(data);

                        Client client = new Client(socket);
                        clientList.add(client); // 연결된 Client를 추가하는 코드
                        System.out.println("[연결된 클라이언트의 수 : " + clientList.size() + "]");

                    } catch (IOException e) {
                        if (!serverSocekt.isClosed()) {
                            stopServer();
                        }
                        break;
                    }

                }
            }
        };
        // 스레드 풀에 있는 작업큐에다가 runnable객체를 넣어두면 하나의 스레드가 맡아서 지속적으로 처리
        executorService.submit(runnable);
    }

    // 서버를 중지하는 코드
    public static void stopServer() {
        try {
            Iterator<Client> iterator = clientList.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                client.socket.close();
                iterator.remove();
            }

            // 서버소켓 닫기
            if (serverSocekt != null && !serverSocekt.isClosed()) {
                serverSocekt.close();
            }
            // 스레드풀 종료
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
            System.out.println("[서버가 종료되었습니다.]");
        } catch (IOException e) {
        }
    }

    public static class Client {
        Socket socket;

        public Client(Socket socket) {
            this.socket = socket;
            receive();
        }

        // 데이터를 받는 메서드
        public void receive() {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            byte[] byteArr = new byte[1000];
                            InputStream inputStream = socket.getInputStream();
                            int readCount = inputStream.read(byteArr); // 블로킹 상태가 됨

                            // 클라이언트가 정상적으로 Socket을 close()를 호출했을 경우
                            if (readCount == -1) {
                                throw new IOException();
                            }

                            String data = "[클라이언트의 요청 처리 : " + socket.getRemoteSocketAddress()
                                    + " : " + Thread.currentThread().getName();
                            System.out.println(data);

                            String message = new String(byteArr, 0, readCount, "UTF-8");

                            // 서버에 연결된 클라이언트 전부에게 보내기 위한 코드
                            // for (Client client : clientList) {
                            // client.send(message);
                            // }
                            for (Client client : clientList) {
                                if (!client.socket.equals(this.socket)) {

                                }
                            }

                        }
                    } catch (IOException e) {
                        try {
                            clientList.remove(Client.this);
                            String data = "[클라이언트가 끊겼습니다. : " + socket.getRemoteSocketAddress()
                                    + Thread.currentThread().getName() + "]";
                            System.out.println(data);
                            socket.close();
                        } catch (IOException e1) {
                        }
                    }
                }
            };
            executorService.submit(runnable);
        }

        protected void send(String data) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] byteArr = data.getBytes("UTF-8");
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(byteArr); // 블로킹 상태
                        outputStream.flush();
                    } catch (Exception e) {
                        try {
                            String data = "[클라이언트 연결이 끊김 : " + socket.getRemoteSocketAddress()
                                    + Thread.currentThread().getName() + "]";
                            System.out.println(data);
                            clientList.remove(Client.this);
                            socket.close();
                        } catch (IOException e1) {
                        }
                    }
                }
            };
            executorService.submit(runnable);
        }
    }
}
