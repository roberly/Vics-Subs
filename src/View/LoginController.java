package View;

import Database.DBConnection;
import Main.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController
{
    @FXML
    private TextField tfusername;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField pfpassword;

    private Employee employee;

    @FXML
    public void loginButtonClicked() throws IOException
    {
        if (allFieldsEntered())
        {
            String userName = tfusername.getText().trim();
            String password = pfpassword.getText();

            try {
                if (isValidCredentials(userName,password))
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    WelcomeController controller = fxmlLoader.getController();
                    controller.getEmployeeData(employee);
                    Stage stage = new Stage();
                    stage.setTitle("Welcome To Vics!");
                    stage.setScene(new Scene(root1));
                    stage.show();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid Username or Password");
                    alert.setContentText("Please type in a correct username and password");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private boolean isValidCredentials(String userName, String password) throws SQLException
    {
        boolean userPassOk = false;

        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String usernameQueryReturnID = "";
        String passwordQueryReturnID = "";

        String str = "select Employee_ID from employee where username = '" + userName + "';";
        String str2 = "select Employee_ID from Employee where password = '" + password + "';";

        System.out.println(str);
        System.out.println(str2);

        ResultSet usernameQuery = statement.executeQuery(str);
        if(usernameQuery.next())
            usernameQueryReturnID = usernameQuery.getString("Employee_ID");
        ResultSet passwordQuery = statement.executeQuery(str2);
        if(passwordQuery.next())
            passwordQueryReturnID = passwordQuery.getString("Employee_ID");

        if(usernameQueryReturnID.equals(passwordQueryReturnID))
        {
            String str3 = "select * from Employee where Employee_ID = " + usernameQueryReturnID;

            ResultSet resultSet = statement.executeQuery(str3);

            while (resultSet.next())
            {
                if(resultSet.getString("username")!=null && resultSet.getString("password")!=null)
                {
                    userPassOk = true;
                    employee = new Employee(
                            resultSet.getString("Employee_ID"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"));
                }
            }
        }

        if(!userPassOk)
        {
            tfusername.clear();
            pfpassword.clear();

            userPassOk = false;

        }

        return userPassOk;
    }

    private boolean allFieldsEntered()
    {
        boolean filledOut;

        if(tfusername.getText().trim().isEmpty()||pfpassword.getText().isEmpty())
        {
            filledOut = false;
        }
        else filledOut = true;

        return filledOut;
    }

}
