import sun.awt.windows.ThemeReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient extends Frame{
    TextField tfText = new TextField();
    TextArea taContent = new TextArea();
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.launchFrame();
    }

    public void launchFrame() {
        setLocation(400,300);
        setSize(300,300);

        add(tfText,BorderLayout.SOUTH);
        add(taContent,BorderLayout.NORTH);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
                System.exit(0);
            }
        });

        tfText.addActionListener(new TFListener());
        setVisible(true);
        connect();
        new Thread(new RecvThread()).start();
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 9989);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("connected!");
            bConnected = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            bConnected = false;
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfText.getText().trim();
            //taContent.setText(str);
            tfText.setText("");
            try {
                dos.writeUTF(str);
                dos.flush();
                //dos.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class RecvThread implements Runnable {

        @Override
        public void run() {
            try {
            while (bConnected) {
                String str = dis.readUTF();
                //System.out.println(str);
                taContent.setText(taContent.getText() + str + '\n');
            }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

}
