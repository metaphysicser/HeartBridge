package Client.pers.zpl.HeartBridge;

import Board.pers.zpl.HeartBridge.MainWindow;
import com.mysql.cj.xdevapi.Client;

import javax.swing.text.Caret;
import java.io.IOException;

public class Controller {
    public MainWindow mainWindow;
    public client_ZhangPL client;

    public Controller(String user_name){
        try {
            this.mainWindow = new MainWindow(user_name,this);
            this.client= new client_ZhangPL(user_name,this);
            this.client.init();

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void send_person_message(String content)
    {
        try {
            client.write_person_message(content,"zpl2");

        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public void receive_person_message(StringBuilder content)
    {

            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());
            this.mainWindow.chatBoard.addTextMessage(content.toString(),1);
            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());



    }


    public static void main(String[] args) {
        Controller controller = new Controller("zpl2");

    }
}
