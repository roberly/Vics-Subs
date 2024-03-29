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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockOutController {
    private int employeeID;
    @FXML
    Button okButton;
    @FXML
    Label timeLabel;
    @FXML
    Label successText;

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public static String getCurrentTime()
    {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(date);
    }

    public static String getCurrentDate()
    {
        Date date = new Date();
        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);

        return formattedDate;
    }

    public void onInit(int id) throws SQLException
    {
        employeeID = id;
        logClockOut();
    }


    public void logClockOut() throws SQLException
    {
        System.out.println(getCurrentDate() + " " + getCurrentTime());
        String date = getCurrentDate();
        String time = getCurrentTime();
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        if(!isClockedIn())
        {
            successText.setText(" ");
            timeLabel.setText("You have not clocked in today");
        }
        else if(isClockedOut())
        {
            successText.setText(" ");
            timeLabel.setText("You have already clocked out \n                for the day");
        }
        else
        {
            String str = "update TimePunches set ClockOutTime = '" + time + "' where Employee_ID =" + employeeID + " and CurrentDate = '" + date + "'";
            statement.executeUpdate(str);
            timeLabel.setText(time);
        }

    }

    public boolean isClockedIn() throws SQLException
    {
        boolean clockedIn = true;
        String currentDate = getCurrentDate();
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "SELECT * FROM TimePunches WHERE Employee_ID = '" + employeeID + "' AND CurrentDate = '"+currentDate+"';";
        ResultSet resultSet = statement.executeQuery(str);

        if(resultSet.first() == false)
        {
            clockedIn = false;
        }
        return clockedIn;
    }

    public boolean isClockedOut() throws SQLException
    {
        boolean alreadyClockedOut = false;
        String currentDate = getCurrentDate();
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "SELECT * FROM TimePunches WHERE Employee_ID = '" + employeeID + "' AND CurrentDate = '"+currentDate+"'";
        ResultSet resultSet = statement.executeQuery(str);
        resultSet.next();

        if(resultSet.getString("ClockOutTime") != null)
        {
            alreadyClockedOut = true;
        }
        return alreadyClockedOut;

    }
}


