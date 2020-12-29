package Client.pers.zpl.HeartBridge;

import Board.pers.zpl.HeartBridge.MainWindow;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import javax.swing.text.Caret;
import java.io.IOException;

public class Controller {
    public MainWindow mainWindow;
    public client_ZhangPL client;

    public Controller(String user_name){
        try {
            this.client= new client_ZhangPL(user_name,this);
            this.client.init();

            this.mainWindow = new MainWindow(user_name,this);

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

    public void receive_person_message(StringBuilder content,String receiver)
    {

            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());
            this.mainWindow.chatBoard.addTextMessage(content.toString(),1,"null");
            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());



    }
    public void send_user_friend(String user) {
        try {
            client.sc.write(client.charset.encode("get_friend&" + user + "&null#null"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String[] get_user_friend(String friend) {
        System.out.println(friend);


        String list[] = friend.split(" ");
        return list;



    }



    public static void main(String[] args) {
        Controller controller = new Controller("zpl2");
        controller.mainWindow.init();

    }
}
