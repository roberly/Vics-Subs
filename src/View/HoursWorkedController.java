package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class HoursWorkedController {

    private int employeeID;
    @FXML
    Button doneButton;


    @FXML
    public void handleCloseButtonAction() throws SQLException
    {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
        System.out.println(getHoursWorked(getWeek()));
    }

    public void setEmployeeID(int id) throws SQLException
    {
        employeeID = id;
    }

    public String[] getWeek()
    {
        LocalDate mostRecentMonday =
                LocalDate.now( ZoneId.of( "America/Montreal" ) )
                        .with( TemporalAdjusters.previous( DayOfWeek.MONDAY ) ) ;
        String week[] = new String[7];
        LocalDate current = mostRecentMonday;
        DateTimeFormatter mmddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for(int i = 0; i < 7; i++)
        {

            if(i != 0)
            {
                current = current.plusDays(1);
            }
            week[i] = current.format(mmddyyyy);
        }
//        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        String weekStart = mostRecentMonday.format(formatters);
//        System.out.println(mostRecentMonday);
//        System.out.println(weekStart);
        return week;
    }

    public long getHoursWorked(String week[]) throws SQLException
    {
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String current;
        long hoursWorked = 0;

        for(int i = 0; i < 7; i++)
        {
            current = week[i];
            String str = "SELECT * FROM TimePunches WHERE Employee_ID = '" + employeeID + "' AND CurrentDate = '"+current+"';";
            ResultSet resultSet = statement.executeQuery(str);
            if(resultSet.first())
            {
                Date clockInTime = Date.valueOf(resultSet.getString("ClockInTime"));
                Date clockOutTime = Date.valueOf(resultSet.getString("ClockOutTime"));
                hoursWorked = hoursWorked + (clockOutTime.getTime() - clockInTime.getTime());

            }
        }

        return hoursWorked;
    }
}
