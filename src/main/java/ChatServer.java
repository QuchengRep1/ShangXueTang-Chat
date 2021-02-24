import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    static boolean started = false;
    static ServerSocket ss = null;
    //static Socket s = null;

    public static void main(String[] args) {
        new ChatServer().serverStart();
    }

    public void serverStart() {
        try {
            ss = new ServerSocket(9989);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            started = true;
            while (started) {
                Client sc = new Client(ss.accept()); // accept为阻塞形方法
                Thread thread = new Thread(sc);
                thread.start();
                //new Thread(new Client(ss.accept())).run();
            }
        } catch (EOFException e) {
            System.out.println("Client initial failure ...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            started = false;
        }
    }

    class Client implements Runnable {
        private Socket ClientSocket ;
        private DataInputStream dis;
        private boolean bConnect = false;

        public Client(Socket s) {
            ClientSocket = s;
        }

        @Override
        public void run() {
            try {
                bConnect = true;
                System.out.println("a client connected!");
                dis = new DataInputStream(ClientSocket.getInputStream());
                while (bConnect) {
                    String str = dis.readUTF(); //readUTF为阻塞形方法
                    System.out.println(str);
                }
            } catch (EOFException e) {
                System.out.println("Client closed!");
                System.out.println("当前客户端存在数量：" + Thread.activeCount());
                if ( Thread.activeCount() <= 3) {
                    System.out.println("客户端已为空，关闭服务器端...");
                    try {
                        ss.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


