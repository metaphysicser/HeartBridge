package utils.pers.zpl.HeartBridge;

public class judge_group {
    public static int  judge_group(String current_clicked){
        String name;
        if(current_clicked.contains("（群聊）")){
            name = current_clicked.split("（")[0];
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public static String delete_group(String current_clicked){
        String name;
        if(current_clicked.contains("（群聊）")){
            name = current_clicked.split("（")[0];
            return name;
        }
        else
        {
            return current_clicked;
        }


    }

    public static void main(String[] args) {

        System.out.println(judge_group("项目开发组（群聊）"));
    }
}
