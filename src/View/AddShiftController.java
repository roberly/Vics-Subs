package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddShiftController
{
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @FXML
    Label lblemployee;
    @FXML
    TextField tfstarttime;
    @FXML
    TextField tfendtime;
    @FXML
    Button btnok;
    @FXML
    Button btncancel;

    private String ShiftDate = "";
    private int EmployeeID = 0;

    public void onInit(String date, int employeeID)
    {
        ShiftDate = date;
        EmployeeID = employeeID;
    }

    @FXML
    private void onAddShift() throws SQLException
    {
        //Add method to verify the time is valid
        String startTime = tfstarttime.getText();
        String endTime = tfendtime.getText();

        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String str = "INSERT INTO Schedule (Employee_ID, ShiftDate, ShiftStartTime, ShiftEndTime) VALUES ("
                + EmployeeID + ", '" + ShiftDate + "', '" + startTime + "', '" + endTime + "');";
        System.out.println(str);
        statement.executeUpdate(str);

    }

    @FXML
    private void onCancel()
    {

    }
}
