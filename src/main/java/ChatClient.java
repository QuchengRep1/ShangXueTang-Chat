import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Frame{
    TextField tfText = new TextField();
    TextArea taContent = new TextArea();
    Socket s = null;
    DataOutputStream dos = null;

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
    }

    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfText.getText().trim();
            taContent.setText(str);
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

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 9989);
            dos = new DataOutputStream(s.getOutputStream());
            System.out.println("connected!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
