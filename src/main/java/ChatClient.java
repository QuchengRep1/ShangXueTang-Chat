import java.awt.*;

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

        setVisible(true);

    }


}
