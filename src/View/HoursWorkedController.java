package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class HoursWorkedController {
    private int employeeID;
    @FXML
    Button doneButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
    public void setEmployee_ID(int id) throws SQLException {
        employeeID = id;
    }

    public static String getCurrentDate()
    {
        Date date = new Date();


        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        System.out.println(dateFormat.format(date.before(date)));

        return formattedDate;
    }
    /*
    public double computeHoursWorked() throws SQLException
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.getFirstDayOfWeek();
        String currentDate = getCurrentDate();

        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "SELECT * FROM TimePunches WHERE Employee_ID = '" + employeeID + "' AND CurrentDate = '"+currentDate+"';";
        ResultSet resultSet = statement.executeQuery(str);
        String ClockInTime = resultSet.getString("ClockInTime");
        String ClockOutTime = resultSet.getString("ClockOutTime");



    }
     */
}
