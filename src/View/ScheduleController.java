package View;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

import Main.Schedule;

public class ScheduleController
{
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private int employeeID;
    private Schedule schedule;

    @FXML
    Button doneButton;
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

    public void onInit(int id) throws SQLException
    {
        employeeID = id;
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
            resultSet = statement.executeQuery("SELECT * FROM Schedule WHERE Employee_Id = " + employeeID);//"SELECT * FROM Schedule;"

            while(resultSet.next())
            {
                String resultDate = resultSet.getString("ShiftDate");
                if(resultDate == week[0])
                {
                    mondayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultDate.equals(week[1]))
                {
                    tuesdayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultDate.equals(week[2]))
                {
                    wednesdayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultDate.equals(week[3]))
                {
                    thursdayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultDate.equals(week[4]))
                {
                    fridayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultDate.equals(week[5]))
                {
                    saturdayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else
                {
                    sundayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
            }
            scheduleData.add(new Schedule(sundayShift, mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayShift, saturdayShift));
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
            week[i] = current.format(mmddyyyy);
        }
        return week;
    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
