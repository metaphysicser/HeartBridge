package Board.pers.zpl.HeartBridge;

import javax.swing.*;

public class InputBoard {
    public JTextPane textfield;
    public InputBoard(){
        textfield = new JTextPane();
        textfield.setBorder(BorderFactory.createLoweredBevelBorder());
        textfield.setOpaque(false);


        this.textfield.setBounds(270,360,550,140);
    }
}
