import java.awt.*;

public class ChatClient extends Frame{
    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();
        chatClient.launchFrame();

    }

    public void launchFrame() {
        setLocation(400,300);
        setSize(300,300);
        setVisible(true);

    }


}
