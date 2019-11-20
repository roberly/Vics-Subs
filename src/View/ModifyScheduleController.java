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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class ModifyScheduleController
{
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private int employeeID;

    @FXML
    Button doneButton;
    @FXML
    Label weekLabel;
    @FXML
    TableView scheduleTable;
    @FXML
    TableColumn<Schedule, String> sundayCol;
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

    public void initialize() throws SQLException
    {
        showSchedule();
    }

    public void showSchedule() throws SQLException
    {
        sundayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("sunday"));
        mondayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("monday"));
        tuesdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("tuesday"));
        wednesdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("wednesday"));
        thursdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("thursday"));
        fridayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("friday"));
        saturdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("saturday"));

        ObservableList<Schedule> data = getDataFromScheduleAndAddToObservableList();
        scheduleTable.getItems().addAll(data);
    }

    private ObservableList<Schedule> getDataFromScheduleAndAddToObservableList(){
        ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
        try {
            String sundayShift = "";
            String mondayShift = "";
            String tuesdayShift = "";
            String wednesdayShift = "";
            String thursdayShift = "";
            String fridayShift = "";
            String saturdayShift = "";
            String[] week = getWeek();

            connection = (Connection) database.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Schedule");//"SELECT * FROM Schedule;"

            while(resultSet.next())
            {
                String resultDate = resultSet.getString("ShiftDate");
                String ShiftStartTime = resultSet.getString("ShiftStartTime");
                String ShiftEndTime = resultSet.getString("ShiftEndTime");
                int startFirstColon = ShiftStartTime.indexOf(":");
                int endFirstColon = ShiftEndTime.indexOf(":");
                int startLength = ShiftStartTime.length();
                int endLength = ShiftEndTime.length();

                ShiftStartTime = ShiftStartTime.substring(0,startFirstColon+3) + ShiftStartTime.substring(startLength-3,startLength);
                ShiftEndTime = ShiftEndTime.substring(0,endFirstColon+3) + ShiftEndTime.substring(endLength-3,endLength);

                if(resultDate.equals(week[0]))
                    mondayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    mondayShift = "";

                if(resultDate.equals(week[1]))
                    tuesdayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    tuesdayShift = "";

                if(resultDate.equals(week[2]))
                    wednesdayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    wednesdayShift = "";

                if(resultDate.equals(week[3]))
                    thursdayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    thursdayShift = "";

                if(resultDate.equals(week[4]))
                    fridayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    fridayShift = "";

                if(resultDate.equals(week[5]))
                    saturdayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    saturdayShift = "";

                if(resultDate.equals(week[6]))
                    sundayShift = ShiftStartTime + " - " + ShiftEndTime;
                else
                    sundayShift = "";
            }
            scheduleData.add(new Schedule(mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayShift, saturdayShift, sundayShift));
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
        return scheduleData;
    }

    public String[] getWeek()
    {
        LocalDate mostRecentMonday =
                LocalDate.now(ZoneId.of("America/Montreal"))
                        .with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        String week[] = new String[7];
        LocalDate current = mostRecentMonday;
        DateTimeFormatter mmddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int i = 0; i < 7; i++)
        {
            if (i != 0)
            {
                current = current.plusDays(1);
            }
            week[i] = current.minusDays(7).format(mmddyyyy);
        }
        weekLabel.setText("Week of " + week[0] + " - " + week[6]);
        return week;
    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
