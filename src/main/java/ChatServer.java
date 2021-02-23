import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    static boolean started = false;

    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;

        try {
            ss = new ServerSocket(9989);
        } catch (IOException e) {e.printStackTrace();}

        try {
            started = true;
            while (started) {
                boolean bConnect = false;
                s = ss.accept();
                bConnect = true;
                System.out.println("a client connected!");
                dis = new DataInputStream(s.getInputStream());
                while (bConnect) {
                    String str = dis.readUTF();
                    System.out.println(str);
                }

            }
        } catch (Exception e) {
            System.out.println("Client closed!");
        } finally {
            try {
                if (dis != null) dis.close();
                if (s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
           // e.printStackTrace();

        }
    }
}
