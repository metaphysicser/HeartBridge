package utils.pers.zpl.HeartBridge;

import Client.pers.zpl.HeartBridge.Controller;

/**
 * @Description: decode the history record
 * @author: zpl
 * @Date: 2020.12.29 22:32
 * */
public class decode_history {
    public static void decode_history(String content, Controller controller,String receiver){
        String setence[] = content.split("#");
        try{
            for(int i = 0;setence[i]!=null;i++){
                String object = setence[i].split("&")[0];
                String word = setence[i].split("&")[1];
                if(object.equals("1")){
                    controller.mainWindow.chatBoard.addTextMessage(word,1,receiver);
                }
                else{
                    controller.mainWindow.chatBoard.addTextMessage(word,0,receiver);
                }
        }


    }catch (Exception e){
            e.printStackTrace();
        }
}
}
