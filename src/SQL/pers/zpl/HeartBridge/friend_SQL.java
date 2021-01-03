package SQL.pers.zpl.HeartBridge;

import javax.swing.*;
import java.sql.*;

public class friend_SQL {
    /**
     * @Description: list the friend
     * @author: zpl
     * @Date: 2020.12.13 13:48
     * */
    //driver
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //the database url
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/friend?useSSL=false&serverTimezone=Hongkong";
    //user name
    static final String USER = "root";
    //password
    static final String PASSWORD = "zpl010720";

    public String list_friend(String user_name) {
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
            sql = "SELECT user_friend from " +user_name;
            ResultSet resultSet = statement.executeQuery(sql);
            int n = 0;

            String friend ="";
            n = 0;

            while (resultSet.next()){
                String name = resultSet.getString("user_friend");
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

    public boolean delete_friend(String user_name,String friend_delete) {
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

            String delete_sql = "delete from "+user_name+" where user_friend="+"'"+friend_delete+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(delete_sql);
            preparedStatement.executeUpdate();

//            statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery(delete_sql);
            System.out.println(delete_sql);
            System.out.println(friend_delete+" is deleted sucessfully");

            preparedStatement.close();
            connection.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("the database maybe shut down");
        return false;
    }

    public boolean insert_friend(String user_name,String friend_delete) {
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

            String delete_sql = "insert into "+user_name+" (user_friend) value "+"('"+friend_delete+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(delete_sql);
            preparedStatement.executeUpdate();

//            statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery(delete_sql);
            System.out.println(delete_sql);
            System.out.println(friend_delete+" is deleted sucessfully");

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
        friend_SQL f = new friend_SQL();
        System.out.println(f.insert_friend("zpl1","zpl3"));
    }

}
