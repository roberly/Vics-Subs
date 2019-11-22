package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteEmployeeConfirmationController
{
    @FXML
    Label ConfirmationQuestionLabel;
    @FXML
    Button YesButton;
    @FXML
    Button NoButton;

    String firstname;
    String lastname;

    public void onInit(String x, String y)
    {
        firstname = x;
        lastname = y;
    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) NoButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void deleteUser() throws SQLException
        {
            if(isNotEmployed())
            {
                ConfirmationQuestionLabel.setText("This user is not employed! Please close!");
            }
            else{
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "DELETE FROM Employee WHERE FirstName = '" + firstname + "' AND LastName = '"+lastname+"';";
        System.out.println(str);
        statement.executeUpdate(str);
        Stage stage = (Stage) YesButton.getScene().getWindow();
        stage.close();
        }}
    private boolean isNotEmployed() throws SQLException
    {
        boolean employed = false;
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "SELECT * FROM Employee WHERE FirstName = '" + firstname + "' AND LastName = '"+lastname+"';";
        System.out.println(str);
        ResultSet resultSet = statement.executeQuery(str);

        if(!resultSet.first())
        {
            employed = true;
        }
        return employed;
    }
}

