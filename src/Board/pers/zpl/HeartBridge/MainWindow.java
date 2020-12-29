package Board.pers.zpl.HeartBridge;

import Client.pers.zpl.HeartBridge.Controller;
import Client.pers.zpl.HeartBridge.client_ZhangPL;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * @Description: design the MainWindow board
 * @author: zpl
 * @Date: 2020.12.27
 * */
public class MainWindow extends JFrame {
    JPanel left;

    //client_ZhangPL client;

    public String user_name;
    public Controller controller;
    public ChatBoard chatBoard;
    public InputBoard inputBoard;

    public MainWindow(String user_name, Controller controller){
        this.init();
        this.user_name = user_name;
        this.controller = controller;
//        try{
//            this.client = new client_ZhangPL(this.user_name,this);
//            this.client.init();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }

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

        this.chatBoard = new ChatBoard();
        myPanel.add(chatBoard.jScrollPane);



        JButton jButton = new JButton();
        jButton.setText("发送");
        jButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton.setBackground(Color.WHITE);
        jButton.setBounds(730,460,60,20);
        myPanel.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = inputBoard.textfield.getText();
                chatBoard.jTextPane.setDocument(chatBoard.jTextPane.getStyledDocument());
                chatBoard.addTextMessage(content,0);

                inputBoard.textfield.setText("");
                chatBoard.jTextPane.setDocument(chatBoard.jTextPane.getStyledDocument());
//                try{
//                    client.write_person_message(content,"zpl2");
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
                controller.send_person_message(content);




            }
        });

        this.inputBoard = new InputBoard();
        myPanel.add(inputBoard.textfield);




    }
    public static void main(String[] args) {
        //MainWindow a = new MainWindow("zpl2",null);

    }

}
