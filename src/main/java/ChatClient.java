import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame{

    TextField tfText = new TextField();
    TextArea taContent = new TextArea();

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
                System.exit(0);
            }
        });

        tfText.addActionListener(new TFListener());
        setVisible(true);

    }

    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tfText.getText().trim();
            taContent.setText(s);
            tfText.setText("");

        }
    }

}
