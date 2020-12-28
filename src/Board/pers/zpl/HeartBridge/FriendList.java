package Board.pers.zpl.HeartBridge;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * @Description: design the FriendList
 * @author: zpl
 * @Date: 2020.12.27 19:53
 * */
public class FriendList {
    String friend[] ={"小明","小红","幸福一家人","乐乐","项目开发组","考研1群","小杰","辅导员","测试人员1","美工人员","编程人员","甲方"};
    final JList<String> list1 = new JList<String>(friend);
    JScrollPane jScrollPane;

    public FriendList(){
        // transparent the console
//        this.setLayout(null);
//        this.setOpaque(false);


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
                // 获取所有被选中的选项索引
                int[] indices = list1.getSelectedIndices();
                // 获取选项数据的 ListModel
                ListModel<String> listModel = list1.getModel();
                // 输出选中的选项
                for (int index : indices) {
                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
                }
                System.out.println();
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
        list1.setSelectedIndex(0);

        jScrollPane = new JScrollPane(list1);

        jScrollPane.setBounds(30,50,200,450);

        //transparent the console
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);

         //add scrollPane

    }
}
