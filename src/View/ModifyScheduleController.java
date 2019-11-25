package View;

import Database.DBConnection;
import Main.Employee;
import Main.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
import java.util.ArrayList;

public class ModifyScheduleController
{
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @FXML
    Button doneButton;
    @FXML
    Label weekLabel;
    @FXML
    TableView scheduleTable;
    @FXML
    TableColumn<Employee, String> employeeCol;
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

        scheduleTable.getSelectionModel().setCellSelectionEnabled(true);
        //Add double click listener to the table, double click will pop up an edit window in theory
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
            resultSet = statement.executeQuery("SELECT * FROM Employee");

            ArrayList<Integer> EmployeeIDs = new ArrayList<>();
            ArrayList<String> EmployeeNames = new ArrayList<>();

            while(resultSet.next())
            {
                EmployeeIDs.add(resultSet.getInt("Employee_ID"));
                EmployeeNames.add(resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
            }

            for(int i = 0; i < EmployeeIDs.size(); i++)
            {
                resultSet = statement.executeQuery("SELECT * from Schedule where Employee_Id = " + EmployeeIDs.get(i));
                mondayShift = "";
                tuesdayShift = "";
                wednesdayShift = "";
                thursdayShift = "";
                fridayShift = "";
                saturdayShift = "";
                sundayShift = "";

                while(resultSet.next())
                {
                    String resultDate = resultSet.getString("ShiftDate");
                    String ShiftStartTime = resultSet.getString("ShiftStartTime");
                    String ShiftEndTime = resultSet.getString("ShiftEndTime");
                    int startFirstColon = ShiftStartTime.indexOf(":");
                    int endFirstColon = ShiftEndTime.indexOf(":");
                    int startLength = ShiftStartTime.length();
                    int endLength = ShiftEndTime.length();

                    ShiftStartTime = ShiftStartTime.substring(0, startFirstColon + 3) + ShiftStartTime.substring(startLength - 3, startLength);
                    ShiftEndTime = ShiftEndTime.substring(0, endFirstColon + 3) + ShiftEndTime.substring(endLength - 3, endLength);

                    if (resultDate.equals(week[0]))
                        mondayShift = ShiftStartTime + " - " + ShiftEndTime;

                    else if (resultDate.equals(week[1]))
                        tuesdayShift = ShiftStartTime + " - " + ShiftEndTime;

                    else if (resultDate.equals(week[2]))
                        wednesdayShift = ShiftStartTime + " - " + ShiftEndTime;

                    else if (resultDate.equals(week[3]))
                        thursdayShift = ShiftStartTime + " - " + ShiftEndTime;

                    else if (resultDate.equals(week[4]))
                        fridayShift = ShiftStartTime + " - " + ShiftEndTime;

                    else if (resultDate.equals(week[5]))
                        saturdayShift = ShiftStartTime + " - " + ShiftEndTime;

                    else if (resultDate.equals(week[6]))
                        sundayShift = ShiftStartTime + " - " + ShiftEndTime;
                }
                scheduleData.add(new Schedule(mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayShift, saturdayShift, sundayShift));
            }
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
