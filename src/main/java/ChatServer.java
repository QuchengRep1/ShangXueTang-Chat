import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {
    boolean started = false;
    ServerSocket ss = null;
    List<Client> clients = new ArrayList<>();

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try {
            ss = new ServerSocket(9989);
            started = true;
        } catch (BindException e) {
            System.out.println("端口使用中...");
            System.out.println("请关闭程序重新运行...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (started) {
                Socket s = ss.accept();
                Client c = new Client(s);
                new Thread(c).start();
                clients.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnect = false;

        public Client(Socket s) {
            this.s = s;
            try {
                this.dis = new DataInputStream(s.getInputStream());
                this.dos = new DataOutputStream(s.getOutputStream());
                bConnect = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String str) {
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                clients.remove(this);
                System.out.println("对方已退出，将从List中去除C端...");
            }
        }

        @Override
        public void run() {
            System.out.println("a client connected!");
            try {
                while (bConnect) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for (int i=0;i<clients.size();i++) {
                        Client c = clients.get(i);
                        c.send(str);
                    }

/*                    for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
                        Client c = it.next();
                        c.send(str);
                   } //使用Iterator接口实现List的遍历
*/
/*                   Iterator<Client> it = clients.iterator();
                   while (it.hasNext()) {
                       Client c = it.next();
                       c.send(str);
                   }
*/
                }
            } catch (EOFException e) {
                System.out.println("Client closed!");
                System.out.println("当前客户端存在数量：" + Thread.activeCount());
                if ( Thread.activeCount() <= 3) {
                    System.out.println("客户端已为空，关闭服务器端...");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(dis != null) dis.close();
                    if(dos != null) dos.close();
                    if(s  !=null) {
                        s.close();
                        //s = null;
                    }
                }catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}


