package Client.pers.zpl.HeartBridge;

import Board.pers.zpl.HeartBridge.ChatBoard;
import Board.pers.zpl.HeartBridge.FriendList;
import Board.pers.zpl.HeartBridge.MainWindow;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import javax.swing.text.Caret;
import java.io.IOException;

public class Controller {
    public MainWindow mainWindow;
    public client_ZhangPL client;
    public ChatBoard chatBoard;
    public FriendList friendList;

    public Controller(String user_name){
        try {
            this.client= new client_ZhangPL(user_name,this);
            this.client.init();
            this.mainWindow = new MainWindow(user_name,this);
            this.chatBoard = this.mainWindow.chatBoard;
            this.friendList = this.mainWindow.friendList;
            //this.mainWindow.init();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("the Controller has initilizated sucessfully");

    }

    public void send_person_message(String content)
    {
        try {
            client.write_person_message(content,mainWindow.chatBoard.current_clicked);

        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public void receive_person_message(StringBuilder content,String receiver)
    {


            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());
            this.mainWindow.chatBoard.addTextMessage(content.toString(),1,receiver);
            this.mainWindow.chatBoard.write_history(content.toString(),1,receiver);
            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());
            System.out.println("the chatboard has received the message sucessfully");



    }
    public void send_user_friend(String user) {
        try {
            client.sc.write(client.charset.encode("get_friend&" + user + "&null#null"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String[] get_user_friend(String friend) {
        System.out.println("friend list is "+friend);
        String friend_name = friend.split("@")[0];
        String online[] = get_friend_online(friend);

        String list[] = friend_name.split(" ");

        for(int i = 0;i<list.length;i++){
            if(online[i].equals("0"))
                list[i] +="（未在线）";
        }

        return list;
    }

    public String[] get_friend_online(String friend) {
        System.out.println("friend list is "+friend);
        String friend_name = friend.split("@")[1];
        String list[] = friend_name.split(" ");
        return list;
    }



    public static void main(String[] args) {
        //Controller controller = new Controller("zpl2");
        Controller controller1 = new Controller("zpl2");

    }
}
