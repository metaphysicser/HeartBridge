package Board.pers.zpl.HeartBridge;

import SQL.pers.zpl.HeartBridge.group_SQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Group {
    public Group(){
        JFrame frame = new JFrame("Group Window");
        frame.setSize(380,250);
        frame.setLocation(500,300);
        frame.setVisible(true);

        JPanel panel=(JPanel)frame.getContentPane();
        panel.setLayout(null);
        panel.setOpaque(false);

        JLabel userLabel = new JLabel("name:");

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
        JLabel passwordLabel = new JLabel("member:");

        // set the location and size
        passwordLabel.setBounds(60, 80, 80, 25);

        panel.add(passwordLabel);

        // define the object of JPasswordField for safety
        JTextField passwordText = new JTextField(20);
        passwordText.setBounds(120, 80, 165, 25);


        panel.add(passwordText);


        // define the button
        JButton loginButton = new JButton("create");
        loginButton.setBounds(70, 140, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userText.getText()+"（群聊）";
                String member = passwordText.getText();

                SQL.pers.zpl.HeartBridge.group_SQL g = new group_SQL();
                g.create_table(name);
                String[] m = member.split(",");
                for(int i = 0;i<m.length;i++){
                    g.insert_friend(name,m[i]);
                    g.insert_friend(m[i],name);
                }
                JOptionPane.showMessageDialog(null,
                        "建群成功","Tips",JOptionPane.WARNING_MESSAGE);

                frame.dispose();
            }

        });
        panel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(210, 140, 80, 25);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 *  click the exit button and exit the System
                 * @param: ActionEvent ae
                 */
               frame.dispose();
            }
        });

        panel.add(exitButton);
    }

    public static void main(String[] args) {
        Group g = new Group();
    }
}
