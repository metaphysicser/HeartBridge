package Board.pers.zpl.HeartBridge;

import Client.pers.zpl.HeartBridge.Controller;
import Client.pers.zpl.HeartBridge.client_ZhangPL;
import SQL.pers.zpl.HeartBridge.friend_SQL;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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


            this.setVisible(true);
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

        // define the object of textfield
        JTextField userText = new JTextField();
        userText.setBounds(30, 25, 135, 25);
        // add to the panel
        mypanel.add(userText);

        JButton jButton3 = new JButton();
        jButton3.setText("添加好友");
        jButton3.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton3.setBackground(Color.WHITE);
        jButton3.setBounds(165,25,70,25);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userText.getText();
                SQL.pers.zpl.HeartBridge.account_SQL a = new SQL.pers.zpl.HeartBridge.account_SQL();
                int res = a.user_check(name);
                if(res==1)
                {
                    SQL.pers.zpl.HeartBridge.friend_SQL f = new friend_SQL();
                    f.insert_friend(user_name,name);
                    JOptionPane.showMessageDialog(null,
                            "add successfully","Tips",JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,
                            "user not exist","Tips",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        myPanel.add(jButton3);

        JButton jButton2 = new JButton();
        jButton2.setText("删除好友");
        jButton2.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton2.setBackground(Color.WHITE);
        jButton2.setBounds(20,480,60,20);
        jButton2.setEnabled(false);
        myPanel.add(jButton2);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = utils.pers.zpl.HeartBridge.delete_tail.delete_tail(chatBoard.current_clicked);
                SQL.pers.zpl.HeartBridge.friend_SQL a = new SQL.pers.zpl.HeartBridge.friend_SQL();
                a.delete_friend(user_name,name);
            }
        });

        JButton jButton4 = new JButton();
        jButton4.setText("建群");
        jButton4.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton4.setBackground(Color.WHITE);
        jButton4.setBounds(190,480,50,20);
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Group g = new Group();


            }
        });

        myPanel.add(jButton4);



        JButton jButton1 = new JButton();
        jButton1.setText("删除聊天记录");
        jButton1.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton1.setBackground(Color.WHITE);
        jButton1.setBounds(90,480,90,20);
        jButton1.setEnabled(false);
        myPanel.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatBoard.jTextPane.setText("");
                String name = utils.pers.zpl.HeartBridge.delete_tail.delete_tail(chatBoard.current_clicked);
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

        JLabel passwordLabel = new JLabel(chatBoard.current_clicked);

        // set the location and size
        passwordLabel.setBounds(280, 20, 80, 25);

        mypanel.add(passwordLabel);



        JButton jButton = new JButton();
        jButton.setText("发送");
        jButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        jButton.setBackground(Color.WHITE);
        jButton.setBounds(730,460,60,20);
        jButton.setEnabled(false);
        myPanel.add(jButton);
        this.inputBoard = new InputBoard(chatBoard,jButton);
        friendList = new FriendList(controller,chatBoard,jButton,inputBoard,jButton1,jButton2,passwordLabel);
        friendList.update(controller,chatBoard);



        myPanel.add(friendList.jScrollPane);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = inputBoard.textfield.getText();
                String name = utils.pers.zpl.HeartBridge.delete_tail.delete_tail(chatBoard.current_clicked);
                chatBoard.jTextPane.setDocument(chatBoard.jTextPane.getStyledDocument());
//                int[] indices = friendList.list1.getSelectedIndices();
//                // 获取选项数据的 ListModel
//                ListModel<String> listModel = friendList.list1.getModel();



                System.out.println("current click: "+name);
                if(chatBoard.current_clicked != null)
                {
                    chatBoard.addTextMessage(content,0, name);
                    chatBoard.write_history(content,0, name);
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

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    controller.client.sc.write(controller.client.charset.encode("bye&"+user_name+"&null#null"));
                }catch (IOException e3){
                    e3.printStackTrace();
                }


            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });




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
                        friendList.real_update(controller,chatBoard);
//                        mypanel.validate();
//                        mypanel.repaint();
//                        mypanel.updateUI();
                        setVisible(true);

                    }
                    n++;
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            //public static void main(String[] args) {
            //MainWindow a = new MainWindow("zpl2",null);

        }

    }}
