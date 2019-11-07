package Database;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

 

public class DBConnection {

    private Connection connection;

    private String url = "jdbc:mysql://localhost:8080/VicsSubs?useSSL=false";
    //private String url = "jdbc:mysql://localhost:3306/addressbook?useSSL=false";

    private String user = "test";

    private String pass = "test";

    public Connection getConnection() throws SQLException {

        connection = DriverManager.getConnection(url,user,pass);

        return connection;

    } 

}
