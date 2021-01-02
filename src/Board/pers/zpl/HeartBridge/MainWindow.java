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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;


/**
 * @Description: design the MainWindow board
 * @author: zpl
 * @Date: 2020.12.27
 * */
public class MainWindow extends JFrame {
    public JPanel mypanel;

    //client_ZhangPL client;

    public String user_name;
    public Controller controller;
    public ChatBoard chatBoard;
    public InputBoard inputBoard;
    public FriendList friendList;



    public MainWindow(String user_name, Controller controller){


        this.user_name = user_name;
        this.controller = controller;
        controller.send_user_friend(this.user_name);
        this.chatBoard = new ChatBoard(this.user_name);




        this.init();
        new Thread(new mainThread()).start();
//        this.setLocation(350,150);// appear lacation
//        this.setLocationRelativeTo(null);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dimension = tk.getScreenSize();//获取屏幕大小
        int width=dimension.width;
        int height=dimension.height;
        int x=(width-422)/2;
        int y=(height-529)/2;
        this.setLocation(x, y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        left = new FriendList();
//        this.add(left);

        this.setSize(900,600);//set seze
        try{
            this.setVisible(true);//make visible
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        System.out.println("the MainWindow has initilizated sucessfully");
    }
    public void init(){
        ImageIcon icon = new ImageIcon(".\\src\\Board\\pers\\zpl\\HeartBridge\\background\\MainBackground.jpg");

        JLabel imageLabel = new JLabel(icon);

        imageLabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));
        JPanel myPanel = (JPanel)this.getContentPane();		//set as my content panel
        this.mypanel = myPanel;
        myPanel.setOpaque(false);
//        myPanel.setLayout(new FlowLayout());
//        this.getLayeredPane().setLayout(null);
        myPanel.setLayout(null);



        JButton jButton1 = new JButton();
        jButton1.setText("删除聊天记录");
        jButton1.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton1.setBackground(Color.WHITE);
        jButton1.setBounds(600,460,100,20);
        jButton1.setEnabled(false);
        myPanel.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatBoard.jTextPane.setText("");
                String name;
                if(chatBoard.current_clicked.contains("（未在线）"))
                    name = chatBoard.current_clicked.split("（")[0];
                else
                    name = chatBoard.current_clicked;
                String fileName = "D:\\IDEA_code\\HeartBridge_ZhangPL\\src\\Client\\pers\\zpl\\HeartBridge\\history\\"+user_name+"\\"+name+".txt";
                File file =new File(fileName);
                try {
                    if(!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter =new FileWriter(file);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }


            }
        });




        myPanel.add(chatBoard.jScrollPane);



        JButton jButton = new JButton();
        jButton.setText("发送");
        jButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton.setBackground(Color.WHITE);
        jButton.setBounds(730,460,60,20);
        jButton.setEnabled(false);
        myPanel.add(jButton);
        this.inputBoard = new InputBoard(chatBoard,jButton);
        friendList = new FriendList(controller,chatBoard,jButton,inputBoard,jButton1);
        friendList.update(controller,chatBoard);



        myPanel.add(friendList.jScrollPane);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = inputBoard.textfield.getText();
                chatBoard.jTextPane.setDocument(chatBoard.jTextPane.getStyledDocument());
//                int[] indices = friendList.list1.getSelectedIndices();
//                // 获取选项数据的 ListModel
//                ListModel<String> listModel = friendList.list1.getModel();



                System.out.println("current click: "+chatBoard.current_clicked);
                if(chatBoard.current_clicked != null)
                {
                    chatBoard.addTextMessage(content,0, chatBoard.current_clicked);
                    chatBoard.write_history(content,0, chatBoard.current_clicked);
                }

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


        myPanel.add(inputBoard.textfield);




    }
    private class mainThread implements Runnable {
        public void run() {
            {
                int n = 0;
                while (true) {
                    if (n%5==0) {
                        System.out.println("username"+user_name);
                        controller.send_user_friend(user_name);
                        //friendList.update(controller, chatBoard);
                        friendList.real_update(controller);
//                        mypanel.validate();
//                        mypanel.repaint();
//                        mypanel.updateUI();
                        setVisible(true);

                    }
                    n++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            //public static void main(String[] args) {
            //MainWindow a = new MainWindow("zpl2",null);

        }

    }}
