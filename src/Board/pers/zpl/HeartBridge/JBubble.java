package Board.pers.zpl.HeartBridge;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JBubble extends JComponent {
//    public static final int MESSAGE_X = 50;
//    public static final int MESSAGE_Y = 80;
//
//    private static final int DEFAULT_WIDTH = 250;
//    private static final int DEFAULT_HEIGHT = 200;
//
//    private static final int DEFAULT_WIDTH = 250;
//    private static final int DEFAULT_HEIGHT = 200;

    ArrayList<String> str;
    int height;
    int flag;
    int width;

    public JBubble(ArrayList<String> str,int height,int flag,int width){
        this.str = str;
        this.height = height;
        this.flag = flag;
        this.width = width;

    }

    public void paintComponent(Graphics g)
    {
        if(this.flag == 1)
        {
            g.setColor(Color.WHITE);
            int swidth = 180;
            if(this.width<swidth)
                swidth = this.width;
            g.fillRect(10,10,swidth+20,30+str.size()*height);

            int x[] = {10,10,0};
            int y[] = {10,20,15};

            g.fillPolygon(x,y,3);


            g.setColor(Color.BLACK);

            for(int i = 0;i<str.size();i++)
            {
                g.drawString(str.get(i), 20, 40+i*height);

            }
        }
        else
        {
            g.setColor(Color.PINK);
            int swidth = 180;
            if(this.width<swidth)
                swidth = this.width;
            g.fillRect(500-swidth,10,swidth+20,30+str.size()*height);

            int x[] = {520,520,530};
            int y[] = {10,20,15};

            g.fillPolygon(x,y,3);


            g.setColor(Color.BLACK);

            for(int i = 0;i<str.size();i++)
            {
                g.drawString(str.get(i), 510-swidth, 40+i*height);

            }
        }



    }
//    public Dimension getPreferredSize()
//    {
//        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//    }


}
