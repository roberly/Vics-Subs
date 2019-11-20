package View;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private String username;
    private String date;
    private String time;

    public InputHoursScreenController() throws SQLException
    {
        //TODO
    }
    public void initialize()
    {
        date = datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        time = "" + TimePicker.getValue(); //Ill come back to this
        username = UsernameField.getText();
        ObservableList<String> choices;
        choices = FXCollections.observableArrayList();
        choices.addAll("9:30", "10:00","10:30","11:00","11:30","12:00","12:30","1:00","1:30","2:00","2:30","3:00","3:30","4:00","4:30","5:00","5:30","6:00","6:30","7:00","7:30","8:00","8:30","9:00","9:30","10:00");
        TimePicker.setItems(choices);
    }

    @FXML
    public void bringUpClockIn() throws SQLException {
        if(doesThisUsernameExist())
        {
            String inputHours = "Insert into TimePunches ( Employee_ID, ClockInTime, ClockOutTime, CurrentDate) Values (" + getEmployeeID() + ",'" + time + "'," + "null" + ",'" + date + "')";
            statement.executeQuery(inputHours);
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
        if(!doesTheClockInExist() && doesThisUsernameExist())
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
    public boolean doesTheClockInExist() throws SQLException
    {
        boolean doesTheClockInExist = true;
        String checkClockIn = "Select * from TimePunches where CurrentDate = '" + date + "'" + " and username = '" + username + "'";
        ResultSet resultSet = statement.executeQuery(checkClockIn);
        if(resultSet.first()) {
            doesTheClockInExist = false;
        }

        return doesTheClockInExist;
    }
    @FXML
    public boolean doTheseHoursExist() throws SQLException
    {
        boolean doTheseHoursExist = true;
        String hoursCheck = "Select * from TimePunches where EmployeeID = "  + "and CurrentDate = '" + date + "'";
        ResultSet newResultSet = statement.executeQuery(hoursCheck);

        if(!newResultSet.first())
        {
            doTheseHoursExist = false;
        }
        return doTheseHoursExist;
    }

    public boolean doesThisUsernameExist() throws SQLException
    {
        boolean doesTheUsernameExist = true;
        String usernameCheck = "Select Employee_ID from Employee where Username = '" + username + "'";
        ResultSet resultSet = statement.executeQuery(usernameCheck);
        if(!resultSet.first())
        {
            doesTheUsernameExist = false;
        }
        return doesTheUsernameExist;
    }
    public int getEmployeeID() throws SQLException {
        int employeeID = -1;
        if(doesThisUsernameExist())
        {
            String getEmployeeID = "Select Employee_ID from Employee where Username = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(getEmployeeID);
            employeeID = resultSet.getInt("Employee_ID");
        }
        return employeeID;
    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
