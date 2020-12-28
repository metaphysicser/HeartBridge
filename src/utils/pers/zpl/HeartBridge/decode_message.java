package utils.pers.zpl.HeartBridge;

/**
 * @Description: define the methoto decode message header and content
 * @author: zpl
 * @Date: 2020.12.9 19:54
 * */
public class decode_message {
    public static void decode_message(String str,StringBuilder content_,StringBuilder type,StringBuilder
            sender,StringBuilder receiver)
    {
        String header= str.split("#")[0];
        content_.setLength(0);
        content_.append(str.split("#")[1]);
        //System.out.println("content_:"+content_);
        type.setLength(0);
        type.append(header.split("&")[0]);
        sender.setLength(0);
        sender.append(header.split("&")[1]);
        receiver.setLength(0);
        receiver.append(header.split("&")[2]);
        //System.out.println("receiver:"+receiver);
        //System.out.println("type:"+type);
    }
}
