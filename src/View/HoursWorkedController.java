package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HoursWorkedController
{
    private int employeeID;

    @FXML
    Button doneButton;

    @FXML
    public void handleCloseButtonAction() throws SQLException, ParseException {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
        System.out.println(getHoursWorked(getWeek()));
    }

    public void onInit(int id) throws SQLException
    {
        employeeID = id;
    }

    public String[] getWeek()
    {
        LocalDate mostRecentMonday = LocalDate.of(2019, 11, 11);
//                LocalDate.now( ZoneId.of( "America/Montreal" ) )
//                        .with( TemporalAdjusters.previous( DayOfWeek.MONDAY ) ) ;
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

    public long getHoursWorked(String week[]) throws SQLException, ParseException {
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        DateFormat hhmmss = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String current;
        long hoursWorked = 0;

        for(int i = 0; i < 7; i++)
        {
            current = week[i];
            String str = "SELECT * FROM TimePunches WHERE Employee_ID = '" + employeeID + "' AND CurrentDate = '" + current + "';";
            System.out.println(str);
            ResultSet resultSet = statement.executeQuery(str);
            System.out.println(week[i]);
            if(resultSet.first())
            {
                Calendar clockInTime = Calendar.getInstance();
                Calendar clockOutTime = Calendar.getInstance();
                clockInTime.setTime(sdf.parse(hhmmss.format(df.parse( resultSet.getString("ClockInTime")))));
                clockOutTime.setTime(sdf.parse(hhmmss.format(df.parse( resultSet.getString("ClockOutTime")))));
                hoursWorked = hoursWorked + (clockOutTime.getTimeInMillis() - clockInTime.getTimeInMillis());
            }
        }

        current = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(hoursWorked),
                TimeUnit.MILLISECONDS.toSeconds(hoursWorked) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hoursWorked))
        );

        System.out.println(current);
        return hoursWorked;
    }
}
