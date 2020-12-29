package Board.pers.zpl.HeartBridge;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import javax.swing.plaf.basic.*;

import java.awt.FontMetrics;
import java.util.ArrayList;

public class ChatBoard {
    JScrollPane jScrollPane;
    public JTextPane jTextPane;
    private int count = 1;
    public ChatBoard(){

        jTextPane = new JTextPane();
        jTextPane.setBounds(100,200,500,200);
        jTextPane.setOpaque(false);
        Font font = new Font("宋体",Font.BOLD,15);//set the txt
        //jTextPane.setEditable(false);
        jTextPane.setFont(font);

//




        jScrollPane = new JScrollPane(jTextPane);
        jScrollPane.setBounds(270,50,550,300);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);



    }

    public void newLine()
    {
        SimpleAttributeSet set = new SimpleAttributeSet();
        Document doc = jTextPane.getStyledDocument();
        FontMetrics fm = jTextPane.getFontMetrics(jTextPane.getFont());//得到JTextPane 的当前字体尺寸
        int paneWidth = jTextPane.getWidth();//面板的宽度
        try{

            doc.insertString(doc.getLength(),"\n",null);
        }
        catch (Exception e){
            e.printStackTrace();

        }

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
        //jTextPane.setCaretPosition(0);
        Caret caret = jTextPane.getCaret();

        System.out.println(caret);
//        DefaultCaret defaultCaret = new DefaultCaret();
//        defaultCaret.setDot(0, Position.Bias.Forward);
//
//
//        jTextPane.setCaretPosition(defaultCaret.getMark());


//        StyledDocument doc = jTextPane.getStyledDocument();
//        doc.insertString(doc.getLength(), attrib.getText() + "\n",
//                attrib.getAttrSet());
//        SimpleAttributeSet set = new SimpleAttributeSet();
//        Document doc = jTextPane.getStyledDocument();
//        FontMetrics fm = jTextPane.getFontMetrics(jTextPane.getFont());//得到JTextPane 的当前字体尺寸
//        int paneWidth = jTextPane.getWidth();//面板的宽度
//        try{
//            doc.insertString(doc.getLength(),"\n",set);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//
//        }
        newLine();

        jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
        //this.count += 2;







    }
}
