package Board.pers.zpl.HeartBridge;


import Client.pers.zpl.HeartBridge.Controller;
import Client.pers.zpl.HeartBridge.client_ZhangPL;
import SQL.pers.zpl.HeartBridge.account_SQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



/**
 * @Description: design the login board
 * @author: zpl
 * @Date: 2020.12.13
 * */
public class LoginWindow {
    /**
     * init the LoginWindow
     * @param
     */

    public void LoginWindow(){
        ImageIcon icon = new ImageIcon(".\\src\\Board\\pers\\zpl\\HeartBridge\\background\\LoginBackground.jpg");

        JLabel imageLabel = new JLabel(icon);

        imageLabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        //create a window object frame
        JFrame frame = new JFrame("HeartBridge Login Window");

        frame.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));
        // set the close button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set the size of window
        frame.setSize(380,250);

        // define the object of panel
        JPanel panel = new JPanel();

        JPanel j=(JPanel)frame.getContentPane();
        j.setOpaque(false);

        //add panel to the frame
        frame.add(panel);

        //add compoents to the window
        placeComponents(panel,frame);

        //set visial is true
        frame.setVisible(true);

        frame.setLocation(500,300);
    }


    private static void placeComponents(JPanel panel,JFrame frame) {
        /**
         * add compoent to the frame
         * @param  panel
         */



        // set Layout is null
        panel.setLayout(null);

        panel.setOpaque(false);

        // define a object of label
        JLabel userLabel = new JLabel("User:");

        //set the location and size of the label
        userLabel.setBounds(80, 50, 80, 25);

        //add to the panel
        panel.add(userLabel);

        // define the object of textfield
        JTextField userText = new JTextField(20);
        userText.setBounds(120, 50, 165, 25);


        // add to the panel
        panel.add(userText);


        //create the label of password
        JLabel passwordLabel = new JLabel("Password:");

        // set the location and size
        passwordLabel.setBounds(50, 80, 80, 25);

        panel.add(passwordLabel);

        // define the object of JPasswordField for safety
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(120, 80, 165, 25);


        panel.add(passwordText);


        // define the button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(70, 140, 80, 25);

        //login the system
        loginButton.addActionListener(new ActionListener() {
            @Override
            /**
             *  check the account and login the System
             * @param: ActionEvent e
             */
            public void actionPerformed(ActionEvent e) {


                account_SQL login = new account_SQL();
                //get the input user name
                String user_name = userText.getText();
                System.out.println(user_name);
                // get the input password
                String user_password = passwordText.getText();
                System.out.println(user_password);
                client_ZhangPL client = new client_ZhangPL(user_name,null);

                if(user_name.equals(""))
                {
                    JOptionPane.showMessageDialog(null,
                            "please input the account","Tips",JOptionPane.WARNING_MESSAGE);
                }
                else if(user_password.equals(""))
                {
                    JOptionPane.showMessageDialog(null,
                            "please input the password","Tips",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int flag = 0;
                    //flag = client.check(user_name,user_password);
                    try{
                        flag = client.check(user_name,user_password);
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();

                    }

                    if(flag == 0)// the account doesn't exist
                    {
                        JOptionPane.showMessageDialog(null,
                                "the account doesn't exist","Tips",JOptionPane.WARNING_MESSAGE);
                        System.out.println("the account doesn't exist");
                    }
                    else if(flag == 1)// access the system
                    {
                        System.out.println("access the system");
                        //MainWindow main = new MainWindow(user_name,);
                        Controller controller = new Controller(user_name);

                        frame.dispose();

                    }
                    else if(flag == -1)//password is wrong
                    {
                        JOptionPane.showMessageDialog(null,
                                "password is wrong","Tips",JOptionPane.WARNING_MESSAGE);
                        System.out.println("password is wrong");
                    }
                    else// some problem occur in system
                    {
                        System.out.println("some problems occur in system");
                    }

                }

            }
        });

        panel.add(loginButton);

        // define the button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(210, 140, 80, 25);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 *  click the exit button and exit the System
                 * @param: ActionEvent ae
                 */
                System.exit(0);
            }
        });

        panel.add(exitButton);
    }


    public static void main(String[] args) {
        Board.pers.zpl.HeartBridge.LoginWindow a = new Board.pers.zpl.HeartBridge.LoginWindow();
        a.LoginWindow();



    }


}


