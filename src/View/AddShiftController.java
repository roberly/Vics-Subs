package View;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddShiftController
{
    private ResultSet resultSet;

    @FXML
    Label lblemployee;
    @FXML
    ChoiceBox cbstarttime;
    @FXML
    ChoiceBox cbendtime;
    @FXML
    Button btnok;
    @FXML
    Button btncancel;
    @FXML
    CheckBox LunchStart;
    @FXML
    CheckBox DinnerStart;
    @FXML
    CheckBox LunchEnd;
    @FXML
    CheckBox WkDyClose;
    @FXML
    CheckBox WkEdClose;





    private String ShiftDate = "";
    private int EmployeeID = 0;
    private ModifyScheduleController Controller;

    public void onInit(String date, int employeeID, ModifyScheduleController controller) throws SQLException
    {
        ShiftDate = date;
        EmployeeID = employeeID;
        Controller = controller;

        ObservableList<String> choices;
        choices = FXCollections.observableArrayList();
        choices.addAll("9:30:00 AM", "10:00:00 AM","10:30:00 AM","11:00:00 AM","11:30:00 AM","12:00:00 PM","12:30:00 PM","1:00:00 PM","1:30:00 PM","2:00:00 PM","2:30:00 PM","3:00:00 PM","3:30:00 PM","4:00:00 PM","4:30:00 PM","5:00:00 PM","5:30:00 PM","6:00:00 PM","6:30:00 PM","7:00:00 PM","7:30:00 PM","8:00:00 PM","8:30:00 PM","9:00:00 PM","9:30:00 PM","10:00:00 PM");
        cbstarttime.setItems(choices);
        cbendtime.setItems(choices);

        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT FirstName, LastName FROM Employee WHERE Employee_ID = " + EmployeeID);
        resultSet.next();

        lblemployee.setText("Add " + ShiftDate + " Shift for " + resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
    }

    @FXML
    private void onAddShift() throws SQLException, ParseException
    {
        String startTime = (String)cbstarttime.getValue();
        String endTime = (String)cbendtime.getValue();

        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String str = "INSERT INTO Schedule (Employee_ID, ShiftDate, ShiftStartTime, ShiftEndTime) VALUES ("
                + EmployeeID + ", '" + ShiftDate + "', '" + startTime + "', '" + endTime + "');";
        System.out.println(str);

        if(!validateHours())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Shift Add Failed");
            alert.setHeaderText("Shift Add Failed");
            alert.setContentText("Please Verify Times are Valid and Try Again");
            alert.showAndWait();

            Stage stage = (Stage) btnok.getScene().getWindow();
            stage.close();

            return;
        }

        statement.executeUpdate(str);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Shift Added");
        alert.setHeaderText("Shift Added Successfully");
        alert.showAndWait();

        Controller.showSchedule();

        Stage stage = (Stage) btnok.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel()
    {
        Stage stage = (Stage) btncancel.getScene().getWindow();
        stage.close();
    }

    private boolean validateHours() throws ParseException
    {
        String startTime = (String)cbstarttime.getValue();
        String endTime = (String)cbendtime.getValue();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date start = sdf.parse(startTime.substring(0, startTime.length()-3));
        Date end = sdf.parse(endTime.substring(0, endTime.length()-3));

        if(end.before(start))
            return false;
        else
            return true;

    }
}
