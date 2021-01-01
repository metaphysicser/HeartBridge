package Board.pers.zpl.HeartBridge;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class InputBoard{
    public JTextPane textfield;
    ChatBoard chatBoard;
    JButton jButton;
    public InputBoard(ChatBoard chatBoard,JButton jButton){
        this.chatBoard = chatBoard;
        this.jButton = jButton;
        textfield = new JTextPane();
        textfield.setBorder(BorderFactory.createLoweredBevelBorder());
        textfield.setOpaque(false);


        this.textfield.setBounds(270,360,550,140);

        this.textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Document doc = e.getDocument();
                    String strMoney = doc.getText(0, doc.getLength());
                    if(strMoney.equals(""))
                        jButton.setEnabled(false);
                    else if(chatBoard.current_clicked!=null)
                        jButton.setEnabled(true);

                }
                catch (BadLocationException e1){
                    e1.printStackTrace();
                }

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Document doc = e.getDocument();
                    String strMoney = doc.getText(0, doc.getLength());
                    if(strMoney.equals(""))
                        jButton.setEnabled(false);
                    else if(chatBoard.current_clicked!=null)
                        jButton.setEnabled(true);

                }
                catch (BadLocationException e1){
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    Document doc = e.getDocument();
                    String strMoney = doc.getText(0, doc.getLength());
                    if(strMoney.equals(""))
                        jButton.setEnabled(false);
                    else if(chatBoard.current_clicked!=null)
                        jButton.setEnabled(true);

                }
                catch (BadLocationException e1){
                    e1.printStackTrace();
                }
            }
        });


    }
}
