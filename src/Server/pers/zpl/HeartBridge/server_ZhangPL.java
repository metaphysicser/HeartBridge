package Server.pers.zpl.HeartBridge;

import SQL.pers.zpl.HeartBridge.account_SQL;
import SQL.pers.zpl.HeartBridge.friend_SQL;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.nio.charset.Charset;
import java.util.*;




/**
 * @Description: Build a service to interact with client
 * @author: zpl
 * @Date: 2020.12.9 19:54
 * */

public class server_ZhangPL {

    private static List<Map<String, SocketChannel>> maps = new LinkedList<Map<String, SocketChannel>>();//list stores the couple of name and channel

    private Map<String, SocketChannel> map = new HashMap<String, SocketChannel>();//the couple of name and channel

    private final static String USER_EXIST = "System message: User exist, please change a name!";//while registering

    private final static String USER_NOTEXIST = "System message: User not exist, please change a name!";// while send message to specific people

    private final static String MESSAGE_FORMAT_ERROR = "System message: Can't send a message to yourself!";// while send message to yourself

    private HashSet<SocketChannel> socketChannelOnline = new HashSet<SocketChannel>();// hashset stores online channels

    private Charset charset = Charset.forName("UTF-8");

    private Selector selector = null;

    static final int port = 5000;// the port number

    public void init() throws IOException {
        /** init the server
         * @param: none
         */
        selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();// open the channel

        server.socket().bind(new InetSocketAddress(port));

        server.configureBlocking(false);// non-blocking mode

        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening ...");

        while (true) {

            int readyChannels = selector.select();

            if (readyChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();// create iterator

            while (keyIterator.hasNext()) {
                SelectionKey sk = (SelectionKey) keyIterator.next();
                keyIterator.remove();
                dealWithSelectionKey(server, sk); // deal with the key
            }
        }
    }

    public void dealWithSelectionKey(ServerSocketChannel server, SelectionKey sk)
            throws IOException {
        /** deal with the key
         * @param: server    the socket channel
         * @param: sk SelectionKey
         */
        StringBuilder content_ = new StringBuilder();

        StringBuilder type= new StringBuilder();
        StringBuilder sender = new StringBuilder();
        StringBuilder receiver = new StringBuilder();
        if (sk.isAcceptable()) {

            SocketChannel sc = server.accept();

            sc.configureBlocking(false);// non-blocking mode

            sc.register(selector, SelectionKey.OP_READ);

            sk.interestOps(SelectionKey.OP_ACCEPT);

            System.out.println("Server is listening from client :"
                    + sc.socket().getRemoteSocketAddress());



            //sc.write(charset.encode("Please input your name："));
        }
        // deal with the request from client
        if (sk.isReadable()) {


            SocketChannel sc = (SocketChannel) sk.channel();


            StringBuilder content = new StringBuilder();

            content_.setLength(0);
            type.setLength(0);
            sender.setLength(0);
            receiver.setLength(0);




            int capacity = 1024;

            int account_state;

            ByteBuffer buff = ByteBuffer.allocate(capacity);

            ByteBuffer intBuff = ByteBuffer.allocate(1024);

            // deal with the package with problems
            try {
                sc.read(intBuff);// the head info read from client,include the name length.

                intBuff.flip();

                String str = charset.decode(intBuff).toString();
                System.out.println("str: "+str);

//
                if(str.length()>0)
                {
//
                    utils.pers.zpl.HeartBridge.decode_message.decode_message(str,content_,type,sender,receiver);
                    System.out.println("receiver:"+receiver);
                    System.out.println("sender:"+sender);
                    System.out.println("content:"+content_);
                    System.out.println("type:"+type);
                }

//

            } catch (IOException io) {
                sk.cancel();
                if (sk.channel() != null) {
                    sk.channel().close();
                }
            }


            if(type.toString().equals("check")&&content_.length()>0)
            {
                String user = null;
                String password = null;
                System.out.println("content: "+content.toString());
                user = content_.toString().split(" ")[0];
                password = content_.toString().split(" ")[1];

                account_SQL login = new account_SQL();

                account_state = login.login_check(user,password);
                System.out.println("account :"+String.valueOf(account_state));

               sc.write(charset.encode(String.valueOf(account_state)));
            }
            else if(type.toString().equals("people_send")&&content_.length()>0)
            {
                System.out.println("the type is people_send");

                if(cheackOut(receiver.toString())){
                    if(sender.toString().equals(receiver.toString()))//send self
                        SendToSpecificClient(selector,sender.toString(),sender.toString(),
                                "你不能向自己发消息",type.toString());
                    else{
                        SendToSpecificClient(selector,receiver.toString(),sender.toString(),content_.toString(),type.toString());
                    }

                }
                else//not oneline or don't exist
                {
                    account_SQL a = new account_SQL();
                    if(a.user_check(receiver.toString())==1)
                    {
                        SendToSpecificClient(selector,sender.toString(),sender.toString(),
                                "对方没有上线或不存在",type.toString());
                    }
                    else{
                        SendToSpecificClient(selector,sender.toString(),sender.toString(),
                                "对方没有上线或不存在",type.toString());
                    }
                }



            }
            else if(type.toString().equals("register")&&content_.length()>0)
            {
                map.put(sender.toString(), sc);// put in the Hashmap
                maps.add(map);
                System.out.println(sender+" has registered sucessfully");

            }
            else if(type.toString().equals("get_friend"))
            {
                friend_SQL f = new friend_SQL();
                String list = f.list_friend(sender.toString());
                System.out.println("friend list is "+list);
                String list1[] = list.split(" ");
                String online = "@";



                for(int i = 0;i<list1.length;i++)
                {
                    if(i!=0)
                        online+=" ";
                    int m = 0;
                    for (int j = 0; j < maps.size(); j++) {
                        if (maps.get(j).get(list1[i]) != null) {
                            online +="1";
                            m = 1;
                            break;
                        }

                        }
                    if(m==0)
                        online+="0";
                    }

                SendToSpecificClient(selector,receiver.toString(),sender.toString(),list+online,type.toString());
                    }






//
//            if (content.length() > 0) {
//
//                if (content.indexOf(":") == -1) {// the content is not message
//
//                    String name = content.toString();
//
//                    if (cheackOut(name)) {// the user has existed
//                        sc.write(charset.encode(USER_EXIST));
//                        sc.write(charset.encode("\nPlease input your name："));
//                    } else {
//                        map.put(name, sc);// put in the Hashmap
//                        maps.add(map);
//
//                        int num = OnlineNum(selector);// get the online num
//
//                        socketChannelOnline.add(sc);
//
//                        String message = "welcome " + name
//                                + " to chat room! Online numbers:" + num;
//                        BroadcastToAllClient(selector, message);
//                    }
//
//                } else if (content.indexOf(":") == 0) {// the group message
//                    // send message to others
//                    sendToOthersClient(selector, sc, content.substring(1));
//
//                } else {// the message to someone
//                    String[] arrayContent = content.toString().split(":");
//                    String[] arrayName = arrayContent[0].toString().split("to");
//                    String oneself = arrayName[0];
//                    String target = arrayName[1];
//                    // 发送消息给特定用户
//                    if (arrayContent != null && arrayContent[0] != null) {
//                        String message = arrayContent[1];
//                        message = oneself + " say: " + message;
//                        if (cheackOut(target)) {// whether the user exists
//                            if (!oneself.equals(target)) {
//                                SendToSpecificClient(selector, target,target, message);
//                            } else {// send message to your self
//                                sc.write(charset.encode(MESSAGE_FORMAT_ERROR));
//                            }
//                        } else {
//                            sc.write(charset.encode(USER_NOTEXIST));
//                        }
//                    }
//                }
//            }
        }
    }

    /**
     * check the user name if exists
     *
     * @param name
     * @return Boolen
     */
    public boolean cheackOut(String name) {
        boolean isExit = false;
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).containsKey(name)) {
                isExit = true;
                break;
            }
        }
        return isExit;
    }

    // get the num of people online
    public static int OnlineNum(Selector selector) {
        int res = 0;
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel) {
                res++;
            }
        }
        return res;
    }

    /**
     * send message to specific people
     *
     * @param selector
     * @param name
     * @param content
     * @throws IOException
     */
    public void SendToSpecificClient(Selector selector, String name,String sender,
                                     String content,String type) throws IOException {

        SocketChannel desChannel = null;
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).get(name) != null) {
                desChannel = maps.get(i).get(name);
                break;
            }

        }
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel) {
                if (desChannel == null || desChannel.equals(targetchannel)) {
                    SocketChannel dest = (SocketChannel) targetchannel;
                    dest.write(charset.encode(type+"&"+sender+"&"+name+"#"+content));
                    System.out.println("the message has sended successfully");
                }
            }

        }

    }

    /**
     * Broadcast To All Clients
     *
     * @param selector
     * @param content
     * @throws IOException
     */
    public void BroadcastToAllClient(Selector selector, String content)
            throws IOException {

        for (SelectionKey key : selector.keys()) {

            Channel targetchannel = key.channel();

            if (targetchannel instanceof SocketChannel) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(content));
            }

        }

    }

    /**
     * send message to others
     *
     * @param selector
     * @param content
     * @throws IOException
     */
    public void sendToOthersClient(Selector selector, SocketChannel oneself,
                                   String content) throws IOException {

        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel
                    && targetchannel != oneself) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(content));
            }

        }

    }

    /**
     * Broadcast To All Clients
     *
     * @param selector
     * @param content
     * @throws IOException
     */
    public void update_friend_list(Selector selector, SocketChannel oneself,String content)
            throws IOException {

        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel
                    && targetchannel != oneself) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(content));
            }

        }

    }

    public static void main(String[] args) throws IOException {
        new server_ZhangPL().init();
    }
}