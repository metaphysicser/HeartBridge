package Board.pers.zpl.HeartBridge;

import Client.pers.zpl.HeartBridge.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @Description: design the FriendList
 * @author: zpl
 * @Date: 2020.12.27 19:53
 * */
public class FriendList {
    public String friend[]={"小明","小红","幸福一家人","乐乐","项目开发组","考研1群","小杰","辅导员","测试人员1","美工人员","编程人员","甲方"};
    public JList<String> list1; //= new JList<String>(friend);
    JScrollPane jScrollPane;

    public FriendList(Controller controller,ChatBoard chatBoard){
        // transparent the console
//        this.setLayout(null);
//        this.setOpaque(false);

        //this.friend = controller.client.friend_list;
        list1 = new JList<String>(friend);

        //update(controller);





    }
    public void update(Controller controller,ChatBoard chatBoard){
        System.out.println(controller.mainWindow);
        this.friend = controller.client.friend_list;
        list1 = new JList<String>(friend);
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
                }
                if(true){
                    chatBoard.jTextPane.setText("");
                    String path = "D:\\IDEA_code\\HeartBridge_ZhangPL\\src\\Client\\pers\\zpl\\HeartBridge\\history\\"+listModel.getElementAt(indices[0])+".txt";
                    File file = new File(path);
                    BufferedReader bufferedReader = null;
                    try{
                        bufferedReader = new BufferedReader(new FileReader(file));
                        StringBuilder result = new StringBuilder();

                        String linetxt = null;
                        while ((linetxt = bufferedReader.readLine()) != null) {
                            System.out.println(linetxt);
                            result.append(linetxt);
                        }
                        utils.pers.zpl.HeartBridge.decode_history.decode_history(result.toString(),controller,listModel.getElementAt(indices[0]));


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

//        list1.setCellRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean bln, boolean bln1) {
//                Component listCellRendererComponent = super.getListCellRendererComponent(jlist, o, i, bln, bln1);
//                JLabel label=(JLabel) listCellRendererComponent;
//                label.setOpaque(false);
//                return label;
//            }
//        });
        //default selected


        jScrollPane = new JScrollPane(list1);

        jScrollPane.setBounds(30,50,200,450);

        //transparent the console
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);

        //add scrollPane

    }
}
