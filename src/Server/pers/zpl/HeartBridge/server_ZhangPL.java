package Server.pers.zpl.HeartBridge;

import SQL.pers.zpl.HeartBridge.account_SQL;
import SQL.pers.zpl.HeartBridge.friend_SQL;
import SQL.pers.zpl.HeartBridge.group_SQL;
import com.mysql.cj.jdbc.exceptions.SQLError;

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
                //System.out.println("str: "+str);

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

                if(utils.pers.zpl.HeartBridge.judge_group.judge_group(receiver.toString())==0)
                {
                    System.out.println(cheackOut(receiver.toString()));
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


                        SendToSpecificClient(selector,sender.toString(),receiver.toString(),
                                    "对方没有上线",type.toString());

                    }

                }
                else {
                    SQL.pers.zpl.HeartBridge.group_SQL g = new group_SQL();
                    String member = g.list_member(receiver.toString());
                    String list[] = member.split(" ");
                    for(int i = 0;i<list.length;i++){
                        System.out.println(list[i]);
                        if(!list[i].equals(sender.toString())) {
                            SocketChannel desChannel = null;
                            for (int j = 0; j < maps.size(); j++) {
                                if (maps.get(j).get(list[i]) != null) {
                                    desChannel = maps.get(j).get(list[i]);
                                    break;
                                }

                            }
                            if(desChannel!=null)
                            {
                                try{
                                    desChannel.write(charset.encode(type+"&"+receiver.toString()+"&"+list[i]+"#"+content_));
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }

//                            for (SelectionKey key : selector.keys()) {
//                                Channel targetchannel = key.channel();
//                                if (targetchannel instanceof SocketChannel) {
//                                    if (desChannel==null||desChannel.equals(targetchannel)) {
//                                        SocketChannel dest = (SocketChannel) targetchannel;
//                                        try{
//                                            dest.write(charset.encode(type+"&"+receiver.toString()+"&"+list[i]+"#"+content_));
//                                        }catch (IOException e){
//                                            e.printStackTrace();
//                                        }
//
//                                        System.out.println("the message has sended successfully");
//                                    }
//                                }
//
//                            }

                        }
                    }

                }




            }
            else if(type.toString().equals("register")&&content_.length()>0)
            {
                map.put(sender.toString(), sc);// put in the Hashmap
                maps.add(map);
                System.out.println(sender+" has registered sucessfully");

            }
            else if(type.toString().equals("bye")){
                for (int i = 0; i < maps.size(); i++) {
                    if (maps.get(i).containsKey(sender.toString())) {
                        maps.remove(maps.get(i));
                    }
                }

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
                        System.out.println(maps.get(j).get(list1[i]));
                        if (maps.get(j).get(list1[i]) != null&&maps.get(j).get(list1[i]).isConnected()) {
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
                System.out.println("check: "+name);
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
                    try{
                        dest.write(charset.encode(type+"&"+sender+"&"+name+"#"+content));
                    }catch (IOException e){
                        e.printStackTrace();
                    }

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