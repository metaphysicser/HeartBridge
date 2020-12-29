package SQL.pers.zpl.HeartBridge;


import java.sql.*;
public class account_SQL {
    /**
     * @Description: conduct the sql work
     * @author: zpl
     * @Date: 2020.12.13 13:48
     * */
    //driver
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //the database url
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/user?useSSL=false&serverTimezone=Hongkong";
    //user name
    static final String USER = "root";
    //password
    static final String PASSWORD = "zpl010720";

    public int login_check(String user_name,String user_password) {
        /**
         * check the user name and password
         * @param user_name the user input
         * @param user_password the user input
         * @retuen: 1 right
         * @return: -1 password is wrong
         * @return: 0 account don't exist
         */
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("connect the database...");
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            statement = connection.createStatement();
            String sql;
            sql = "SELECT name,password from account";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                if (name.equals(user_name)) {
                    System.out.println("the user "+user_name+" exists");

                    if(password.equals(user_password))
                    {
                        System.out.println("the user "+user_name+" password is right");
                        resultSet.close();
                        statement.close();
                        connection.close();
                        return 1;
                    }
                    else
                    {
                        System.out.println("the user "+user_name+" password is wrong");
                        resultSet.close();
                        statement.close();
                        connection.close();
                        return -1;
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("the user "+user_name+" doesn't exits");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("the database maybe shut down");
        return -2;
    }

    public static void main(String[] args){
        account_SQL a = new account_SQL();
        System.out.println(a.login_check("zpl1","123456"));

    }

}
