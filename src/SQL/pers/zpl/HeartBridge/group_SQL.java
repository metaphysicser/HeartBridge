package SQL.pers.zpl.HeartBridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class group_SQL {
    /**
     * @Description: list the friend
     * @author: zpl
     * @Date: 2020.12.13 13:48
     * */
    //driver
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //the database url
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/group?useSSL=false&serverTimezone=Hongkong";
    //user name
    static final String USER = "root";
    //password
    static final String PASSWORD = "zpl010720";

    public String list_member(String user_name) {
        /**
         * get the friend list
         * @param user_name the user input
         */
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("connect the database...");
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            statement = connection.createStatement();
            String sql;
            sql = "SELECT member from " +user_name;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            int n = 0;

            String friend ="";
            n = 0;

            while (resultSet.next()){
                String name = resultSet.getString("member");
                if(n!=0)
                    friend+=" ";
                friend+=name;
                n++;

            }
            resultSet.close();
            statement.close();
            connection.close();

            return friend;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("the database maybe shut down");
        return null;
    }
    public static void main(String[] args) {
        group_SQL f = new group_SQL();
        System.out.println(f.list_member("zpl1"));
    }
}
