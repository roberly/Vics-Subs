package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class InputHoursScreenController
{
    private DBConnection database = new DBConnection();
    private Connection connection = database.getConnection();
    private Statement statement = connection.createStatement();

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField UsernameField;
    @FXML
    private ChoiceBox TimePicker;
    @FXML
    private Button CancelButton;

    public InputHoursScreenController() throws SQLException
    {
        //TODO
    }

    @FXML
    public void bringUpClockIn() throws SQLException {
        if(doesThisUsernameExist())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hours inputted successfully");
            alert.setHeaderText("You have inputted hours for " + UsernameField.getText() + "successfully");
            alert.setContentText("Good Job homie");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Username");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("Please type in a correct username");
            alert.showAndWait();
        }
    }

    @FXML
    public void bringUpClockOut() throws SQLException {
        if(!doTheseHoursExist() && doesThisUsernameExist())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Clock Out successful");
            alert.setHeaderText("You have updated clocked out hours for " + UsernameField.getText() + "successfully");
            alert.setContentText("Good Job homie");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ClockIn on this date does not exist");
            alert.setContentText("Please type in a correct date or enter a clock in on this date");
            alert.showAndWait();
        }
    }

    @FXML
    public void bringUpDeleteHours() throws SQLException {
        if(!doTheseHoursExist() && doesThisUsernameExist())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hours deleted successfully");
            alert.setHeaderText("You have deleted hours for " + UsernameField.getText() + "successfully");
            alert.setContentText("Good Job homie");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hours or username do not exist");
            alert.setHeaderText("Invalid Hours or username");
            alert.setContentText("Please type in a correct username or hours on the correct date");
            alert.showAndWait();
        }

    }

    @FXML
    public boolean doTheseHoursExist() throws SQLException {
        boolean doTheseHoursExist = true;
        boolean doBothExist = false;

        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String Time = "" + TimePicker.getValue(); //Ill come back to this


            String hoursCheck = "Select from TimePunches where EmployeeID = "  + "and CurrentDate = '" + date + "'";
            ResultSet newResultSet = statement.executeQuery(hoursCheck);

            if(newResultSet.first())
            {
                doTheseHoursExist = false;
            }


        return doTheseHoursExist;

    }
    public boolean doesThisUsernameExist() throws SQLException
    {
        boolean doesTheUsernameExist = true;
        String username = UsernameField.getText();
        String usernameCheck = "Select from Employee where Username = '" + username + "'";
        ResultSet resultSet = statement.executeQuery(usernameCheck);
        if(resultSet.first())
        {
            doesTheUsernameExist = false;
        }
        return doesTheUsernameExist;
    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
