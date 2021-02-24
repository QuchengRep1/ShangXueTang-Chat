import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    static boolean started = false;
    static ServerSocket ss = null;
    static Socket s = null;


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
            System.out.println("Client closed!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

