package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private Connection connection;
    private String url = "jdbc:mysql://127.0.0.1/VicsSubs";
    //private String url = "jdbc:mysql://localhost:3306/addressbook?useSSL=false";
    private String user = "root";
    private String pass = "";

    public Connection getConnection() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return connection;

    }

}
