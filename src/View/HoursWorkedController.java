package View;

import Database.DBConnection;
import Main.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class HoursWorkedController
{
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int employeeID;

    @FXML
    Button doneButton;
    @FXML
    Label hoursWorked;
    @FXML
    Label currentWeek;
    @FXML
    TableView hoursWorkedTable;
    @FXML
    TableColumn<Schedule, String> mondayCol;
    @FXML
    TableColumn<Schedule, String> tuesdayCol;
    @FXML
    TableColumn<Schedule, String> wednesdayCol;
    @FXML
    TableColumn<Schedule, String> thursdayCol;
    @FXML
    TableColumn<Schedule, String> fridayCol;
    @FXML
    TableColumn<Schedule, String> saturdayCol;
    @FXML
    TableColumn<Schedule, String> sundayCol;


    @FXML
    public void handleCloseButtonAction() throws SQLException, ParseException {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    public void onInit(int id) throws SQLException, ParseException {
        employeeID = id;
        hoursWorked.setText(getHoursWorked(getWeek()));
    }

    public String[] getWeek() {
        LocalDate mostRecentMonday = LocalDate.of(2019, 11, 11);
//                LocalDate.now( ZoneId.of( "America/Montreal" ) )
//                        .with( TemporalAdjusters.previous( DayOfWeek.MONDAY ) ) ;
        String week[] = new String[7];
        DateTimeFormatter mmddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate current = mostRecentMonday;

        for(int i = 0; i < 7; i++) {
            if(i != 0) {
                current = current.plusDays(1);
            }

            week[i] = current.format(mmddyyyy);
        }

        currentWeek.setText(week[0] + " - " + week[6]);

        return week;
    }

    public String getHoursWorked(String week[]) throws SQLException, ParseException {
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        mondayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("monday"));
        tuesdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("tuesday"));
        wednesdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("wednesday"));
        thursdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("thursday"));
        fridayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("friday"));
        saturdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("saturday"));
        sundayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("sunday"));

        ObservableList<Schedule> hoursWorkedData = FXCollections.observableArrayList();
        String sundayShift = "";
        String mondayShift = "";
        String tuesdayShift = "";
        String wednesdayShift = "";
        String thursdayShift = "";
        String fridayShift = "";
        String saturdayShift = "";

        DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        DateFormat hhmmss = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String current;
        long hoursWorked = 0;

        for(int i = 0; i < 7; i++) {
            current = week[i];
            String str = "SELECT * FROM TimePunches WHERE Employee_ID = '" + employeeID + "' AND CurrentDate = '" + current + "';";
            ResultSet resultSet = statement.executeQuery(str);
            if(resultSet.first()) {
                String cIn = hhmmss.format(df.parse(resultSet.getString("ClockInTime")));
                String cOut = hhmmss.format(df.parse(resultSet.getString("ClockOutTime")));
                Calendar clockInTime = Calendar.getInstance();
                Calendar clockOutTime = Calendar.getInstance();
                clockInTime.setTime(sdf.parse(cIn));
                clockOutTime.setTime(sdf.parse(cOut));
                String day = cIn + " - " + cOut;
                hoursWorked = hoursWorked + (clockOutTime.getTimeInMillis() - clockInTime.getTimeInMillis());

                if(i == 0)
                    mondayShift = day;

                else if(i == 1)
                    tuesdayShift = day;

                else if(i == 2)
                    wednesdayShift = day;

                else if(i == 3)
                    thursdayShift = day;

                else if(i == 4)
                    fridayShift = day;

                else if(i == 5)
                    saturdayShift = day;

                else if(i == 6)
                    sundayShift = day;
            }
        }

        hoursWorkedData.add(new Schedule(mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayShift, saturdayShift, sundayShift));
        hoursWorkedTable.getItems().addAll(hoursWorkedData);
        long totalSecs = hoursWorked/1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        String hrsworked = hours + " hours and " + mins + " minutes";

        connection.close();
        statement.close();

        return hrsworked;
    }
}
