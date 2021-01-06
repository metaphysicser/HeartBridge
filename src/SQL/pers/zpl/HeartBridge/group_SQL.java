package SQL.pers.zpl.HeartBridge;

import java.sql.*;

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

    public void create_table(String name){
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("connect the database...");
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            statement = connection.createStatement();
            String sql;
            sql = "create table " + name+" (member varchar(256) not null)charset=utf8;";
            System.out.println(statement.executeLargeUpdate(sql));



            statement.close();
            connection.close();

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("the database maybe shut down");
        return;
    }

    public boolean insert_friend(String table,String name) {
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

            String delete_sql = "insert into "+table+" (member) value "+"('"+name+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(delete_sql);
            preparedStatement.executeUpdate();

//            statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery(delete_sql);
            System.out.println(delete_sql);


            preparedStatement.close();
            connection.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("the database maybe shut down");
        return false;
    }
    public static void main(String[] args) {
        group_SQL f = new group_SQL();
        f.create_table("test");
    }
}
