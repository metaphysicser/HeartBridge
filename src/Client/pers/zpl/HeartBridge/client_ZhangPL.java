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

    public String friend_list[];

    public String friend_online[];
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
        System.out.println("the client begin to listen to service");


    }
    /**
     * write message to specific user
     * @param
     * @throws IOException
     */
    public void write_person_message(String content,String receiver) throws IOException {
        String message = "people_send&"+this.user_name+"&"+receiver+"#"+content;
        this.sc.write(charset.encode(message));
        System.out.println("the message "+message+" has sended sucessfully");

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
                System.out.println("client has received the message "+content+" sucessfully");
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
            this.controller.receive_person_message(content_,receiver.toString(),sender.toString());
        }
        else if(type.toString().equals("get_friend")&&content_.length()>0)
        {

            if(sender.toString().equals(user_name))
            {
                this.friend_list = this.controller.get_user_friend(content_.toString());
                this.friend_online = this.controller.get_friend_online(content_.toString());
            }




        }




    }

    public static void main(String[] args) throws IOException {
        //client_ZhangPL a = new client_ZhangPL("zpl1");
        //a.init();




    }
}
