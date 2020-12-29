package Client.pers.zpl.HeartBridge;

import Board.pers.zpl.HeartBridge.MainWindow;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.*;
import java.nio.charset.Charset;
/**
 * @Description: Build a Client to interact with Server
 * @author: zpl
 * @Date:2020.1 2.9 19:54
 * */


public class client_ZhangPL {

    public Charset charset = Charset.forName("UTF-8");

    public static final int port = 5000; // the port number

    public Selector selector = null;

    public SocketChannel sc = null;

    public String user_name;

    public Controller controller;
    //private MainWindow mainWindow;

    /**
     * check account's user name and password
     * @param
     * @throws IOException
     */
    public int check(String user,String password) throws IOException {

        selector = Selector.open();

        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));

        sc.configureBlocking(false); //non-blocking mode

        sc.register(selector, SelectionKey.OP_READ);

        String content = user + " " + password;

            // sc既能写也能读，这边是写
        sc.write(charset.encode("check&null&null#"+content));


//        ByteBuffer intBuff = ByteBuffer.allocate(1024);
//
//        sc.read(intBuff);// the head info read from client,include the name length.
//
//        intBuff.flip();
//
//        String str = charset.decode(intBuff).toString();
//        System.out.println(str);
//        int res  = Integer.parseInt(str);
//
//        System.out.println(res);
        int readyChannels = selector.select();
        String content_ = "";

        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys
                .iterator();
        while (keyIterator.hasNext()) {
            SelectionKey sk = (SelectionKey) keyIterator.next();
            keyIterator.remove();
            if (sk.isReadable()) {
                SocketChannel sc = (SocketChannel) sk.channel();
                ByteBuffer buff = ByteBuffer.allocate(1024);
                content_ = "";
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content_ += charset.decode(buff);
                }
                System.out.println(content_);
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
        int res  = Integer.parseInt(content_);

        sc.close();


        return res;

    }
    public client_ZhangPL(String user_name,Controller controller){
        this.user_name = user_name;
        this.controller = controller;
        //this.mainWindow = mainWindow;

    }

    /**
     * init the client always listen the server
     * @param
     * @throws IOException
     */
    public void init() throws IOException {

        selector = Selector.open();

        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));

        sc.configureBlocking(false); //non-blocking mode

        sc.register(selector, SelectionKey.OP_READ);

        String register_message = "register&"+this.user_name+"&null#null";

        sc.write(charset.encode(register_message));



        new Thread(new ClientThread()).start();

//        Scanner scan = new Scanner(System.in);
//
//        while (scan.hasNextLine()) {
//            String line = scan.nextLine();
//            if ("".equals(line)) {
//                continue;
//            }
//            int capacity = charset.encode(line).capacity();
//            String strCapacity = capacity + "";
//            // result是strCapacity经过格式（-）处理之后的结果，方便服务端解析
//            String result = null;
//            if (strCapacity.length() == 1) {
//                result = "---" + strCapacity;
//            } else if (strCapacity.length() == 2) {
//                result = "--" + strCapacity;
//            } else if (strCapacity.length() == 3) {
//                result = "-" + strCapacity;
//            } else if (strCapacity.length() == 4) {
//                result = strCapacity;
//            }
//
//            // sc既能写也能读，这边是写
//            sc.write(charset.encode(result));
//           sc.write(charset.encode(line));
////
//
//
//        }

    }
    /**
     * write message to specific user
     * @param
     * @throws IOException
     */
    public void write_person_message(String content,String receiver) throws IOException {
        String message = "people_send&"+this.user_name+"&"+receiver+"#"+content;
        this.sc.write(charset.encode(message));
        //controller.receive_person_message(content,receiver);

    }


    /**
     * create a thread to read
     * @param
     * @throws IOException
     */
    private class ClientThread implements Runnable {
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0)
                        continue;
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys
                            .iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            } catch (IOException io) {
            }
        }
        /**
         * read the message
         * @param sk
         * @throws IOException
         */
        private void dealWithSelectionKey(SelectionKey sk) throws IOException {

            if (sk.isReadable()) {
                SocketChannel sc = (SocketChannel) sk.channel();
                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content += charset.decode(buff);
                }
                System.out.println(content);
                dealWithMessage(content);
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    public void dealWithMessage(String content){
        StringBuilder content_ = new StringBuilder();

        StringBuilder type= new StringBuilder();
        StringBuilder sender = new StringBuilder();
        StringBuilder receiver = new StringBuilder();
        utils.pers.zpl.HeartBridge.decode_message.decode_message(content,content_,type,sender,receiver);

        if(type.toString().equals("people_send")&&content_.length()>0)

        {
            this.controller.receive_person_message(content_);

//            this.mainWindow.chatBoard.jTextPane.setDocument(this.mainWindow.chatBoard.jTextPane.getStyledDocument());
//            this.mainWindow.chatBoard.addTextMessage(content_.toString(),1);

        }




    }

    public static void main(String[] args) throws IOException {
        //client_ZhangPL a = new client_ZhangPL("zpl1");
        //a.init();




    }
}
