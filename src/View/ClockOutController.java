package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockOutController {
    private int employeeID;
    public void initialize() throws SQLException {
        timeLabel.setText(getCurrentTimeUsingCalendar());
        logClockOut();
    }

    @FXML
    Button okButton;
    @FXML
    Label timeLabel;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public static String getCurrentTimeUsingCalendar() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    public static String getCurrentDate()
    {
        Date date = new Date();
        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }
    public void setEmployeeID(int id)
    {
        employeeID = id;
    }


    public void logClockOut() throws SQLException {
        System.out.println(getCurrentDate() + " " + getCurrentTimeUsingCalendar());
        String date = getCurrentDate();
        String time = getCurrentTimeUsingCalendar();
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "update TimePunches set ClockOutTime = '" + time + "' where Employee_ID =" + employeeID + " and CurrentDate = '" + date + "'";
        statement.executeUpdate(str);
    }
}


