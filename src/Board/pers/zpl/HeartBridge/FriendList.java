package Board.pers.zpl.HeartBridge;

import Client.pers.zpl.HeartBridge.Controller;
import SQL.pers.zpl.HeartBridge.group_SQL;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description: design the FriendList
 * @author: zpl
 * @Date: 2020.12.27 19:53
 * */
public class FriendList {
    public String friend[]={};
    public JList<String> list1; //= new JList<String>(friend);
    JScrollPane jScrollPane;
    JButton jButton;
    InputBoard inputBoard;
    JButton jButton1;
    JButton jButton2;

    public FriendList(Controller controller,ChatBoard chatBoard,JButton jButton,InputBoard inputBoard,JButton jButton1,JButton jButton2){
        // transparent the console
//        this.setLayout(null);
//        this.setOpaque(false);

        //this.friend = controller.client.friend_list;
        list1 = new JList<String>(friend);
        this.jButton = jButton;
        this.inputBoard = inputBoard;
        this.jButton1 = jButton1;
        this.jButton2 = jButton2;

        //update(controller);





    }
    public void update(Controller controller,ChatBoard chatBoard){


        SQL.pers.zpl.HeartBridge.group_SQL g = new group_SQL();
        String member = g.list_member(chatBoard.user);
        String[] list = member.split(" ");



        this.friend = controller.client.friend_list;
        int l2 = list.length;
        int l1 = friend.length;

        friend = Arrays.copyOf(friend,l1+l2);
        System.arraycopy(list,0,friend,l1,l2);


//        for(int i = 0;i<list.length;i++){
//            friend.
//        }

        list1 = new JList<String>(friend);
//        list1.removeAll();
//        list1.updateUI();
        Font font = new Font("微软雅黑",Font.PLAIN,30);//set the txt
        list1.setFont(font);

        // transparent the console
        list1.setOpaque(false);
        ((JComponent) list1.getCellRenderer()).setOpaque(false);

        list1.setBackground(Color.white);
        list1.setSelectionBackground(Color.GRAY);

        // allow mulity select
        list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //event while being selected
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[] indices = list1.getSelectedIndices();
                // 获取选项数据的 ListModel
                ListModel<String> listModel = list1.getModel();
                // 输出选中的选项
                for (int index : indices) {
                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
                    chatBoard.current_clicked = listModel.getElementAt(index);
                }
                if(inputBoard.textfield.getText()!="")
                    jButton.setEnabled(true);
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);


                if(true){
                    chatBoard.jTextPane.setText("");
                    String name = utils.pers.zpl.HeartBridge.delete_tail.delete_tail(chatBoard.current_clicked);
//                    if(chatBoard.current_clicked.contains("（未在线）"))
//                        name = chatBoard.current_clicked.split("（")[0];
//                    else
//                        name = chatBoard.current_clicked;

                    String path = "D:\\IDEA_code\\HeartBridge_ZhangPL\\src\\Client\\pers\\zpl\\HeartBridge\\history\\"+chatBoard.user+"\\"+name+".txt";
                    File file = new File(path);
                    BufferedReader bufferedReader = null;
                    try{
                        bufferedReader = new BufferedReader(new FileReader(file));
                        StringBuilder result = new StringBuilder();

                        String linetxt = null;
                        while ((linetxt = bufferedReader.readLine()) != null) {
//                            System.out.println(linetxt);
                            result.append(linetxt);
                        }
                        utils.pers.zpl.HeartBridge.decode_history.decode_history(result.toString(),controller,controller.chatBoard.current_clicked);


                    }catch (Exception e2)
                    {
                        e2.printStackTrace();
                    }
                }
                else
                {
                    System.out.println("controller null");
                }



                // 获取所有被选中的选项索引


            }
        });


        jScrollPane = new JScrollPane(list1);

        jScrollPane.setBounds(30,50,200,420);

        //transparent the console
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);

        //add scrollPane

    }

    public void real_update(Controller controller,ChatBoard chatBoard){
        SQL.pers.zpl.HeartBridge.group_SQL g = new group_SQL();
        String member = g.list_member(chatBoard.user);
        String[] list = member.split(" ");



        this.friend = controller.client.friend_list;
        int l2 = list.length;
        int l1 = friend.length;

        friend = Arrays.copyOf(friend,l1+l2);
        System.arraycopy(list,0,friend,l1,l2);


        list1.setListData(friend);
        try{
            jScrollPane.setViewportView(list1);
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
