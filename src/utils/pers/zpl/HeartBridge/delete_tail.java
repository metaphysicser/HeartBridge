package utils.pers.zpl.HeartBridge;

public class delete_tail {
    public static String delete_tail(String current_clicked){
        String name;
        if(current_clicked.contains("（未在线）"))
            name = current_clicked.split("（")[0];
        else
            name = current_clicked;
        return name;
    }
}
