package Board.pers.zpl.HeartBridge;

import javax.swing.*;
import java.awt.*;

import java.awt.FontMetrics;
import java.util.ArrayList;

public class ChatBoard {
    JScrollPane jScrollPane;
    JTextPane jTextPane;
    public ChatBoard(){

        jTextPane = new JTextPane();
        jTextPane.setBounds(100,200,500,200);
        jTextPane.setOpaque(false);
        Font font = new Font("宋体",Font.BOLD,15);//set the txt
        jTextPane.setFont(font);

        this.addTextMessage("Hello",1);
        this.addTextMessage("Hellosagfdddddddddddddddddddddddddddddddddddddddddddddddddddddd",0);



        jScrollPane = new JScrollPane(jTextPane);
        jScrollPane.setBounds(270,50,550,300);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);



    }

    public void addTextMessage(String message,int flag)
    {
        Font font = jTextPane.getFont();

        FontMetrics fontMetrics = jTextPane.getFontMetrics(font);

        int width = fontMetrics.stringWidth(message);

        int height = fontMetrics.getHeight();

        ArrayList<String> str = new ArrayList<String>();

        if (width > 150){
            int  beginIndex=0;
            int endIndex=1;
            while( endIndex<message.length()){

                String s=message.substring(beginIndex,endIndex);
                if(fontMetrics.stringWidth(s)>150||endIndex==message.length()-1){
                    str.add(message.substring(beginIndex,endIndex-1));
                    beginIndex=endIndex-1;
                }
                endIndex++;
            }
        } else
            str.add(message);

        JBubble jBubble = new JBubble(str,height,flag,width);
        jBubble.setPreferredSize(new Dimension(width,height * (str.size()+1)+30));
        jTextPane.insertComponent(jBubble);


    }
}
