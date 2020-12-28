package Board.pers.zpl.HeartBridge;

import Client.pers.zpl.HeartBridge.client_ZhangPL;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;


/**
 * @Description: design the MainWindow board
 * @author: zpl
 * @Date: 2020.12.27
 * */
public class MainWindow extends JFrame {
    JPanel left;

    client_ZhangPL client;

    String user_name;


    public MainWindow(String user_name){
        this.init();
        this.user_name = user_name;
        client = new client_ZhangPL(this.user_name);
        //flag = client.check(user_name,user_password);
//        try{
//            client.init();
//        }
//        catch (IOException e1)
//        {
//            e1.printStackTrace();
//
//        }




        this.setSize(900,600);//set seze
        this.setVisible(true);//make visible
        this.setLocation(350,150);// appear lacation
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        left = new FriendList();
//        this.add(left);
        this.setVisible(true);
    }
    public void init(){
        ImageIcon icon = new ImageIcon(".\\src\\Board\\pers\\zpl\\HeartBridge\\background\\MainBackground.jpg");

        JLabel imageLabel = new JLabel(icon);

        imageLabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));
        JPanel myPanel = (JPanel)this.getContentPane();		//set as my content panel
        myPanel.setOpaque(false);
//        myPanel.setLayout(new FlowLayout());
//        this.getLayeredPane().setLayout(null);
        myPanel.setLayout(null);

        FriendList friendList = new FriendList();
        myPanel.add(friendList.jScrollPane);

        ChatBoard chatBoard = new ChatBoard();
        myPanel.add(chatBoard.jScrollPane);




    }
    public static void main(String[] args) {
        MainWindow a = new MainWindow("zpl1");

    }

}
