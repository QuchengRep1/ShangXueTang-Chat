import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    static boolean started = false;

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(9989);
            started = true;
            while (started) {
                boolean bConnect = false;
                Socket s = ss.accept();
                bConnect = true;
                System.out.println("a client connected!");
                DataInputStream dis = new DataInputStream(s.getInputStream());
                while (bConnect) {
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                dis.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
