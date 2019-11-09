package View;

import Database.DBConnection;
import Main.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockInController
{

    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private int employeeID;

    public void initialize() throws SQLException
    {
        timeLabel.setText(getCurrentTime());
        logClockIn();
    }

    @FXML
    Button okButton;
    @FXML
    Label timeLabel;

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void logClockIn() throws SQLException
    {
        System.out.println(getCurrentDate() + " " + getCurrentTime());
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String currentTime = getCurrentTime();
        String currentDate = getCurrentDate();

        String str = "INSERT INTO TimePunches VALUES (" + employeeID + ", '" + currentTime + "', " +
                "NULL, '" + currentDate + "')";
        statement.executeUpdate(str);
    }

    public void setEmployeeID(int id)
    {
        employeeID = id;
    }

    public static String getCurrentTime()
    {
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
}
