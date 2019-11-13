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
    TableColumn sundayCol;
    @FXML
    TableColumn mondayCol;
    @FXML
    TableColumn tuesdayCol;
    @FXML
    TableColumn wednesdayCol;
    @FXML
    TableColumn thursdayCol;
    @FXML
    TableColumn fridayCol;
    @FXML
    TableColumn saturdayCol;

    public void onInit(int id) throws SQLException
    {
        employeeID = id;
        showSchedule();
    }

    public void showSchedule() throws SQLException
    {
        ObservableList<Schedule> data = getDataFromScheduleAndAddToObservableList();
        scheduleTable.getItems().addAll(data);
    }

    public void loadSchedule() throws SQLException
    {
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String str = "select * from schedule where Employee_ID = '"+employeeID+"';";
        System.out.println(str);
        ResultSet resultSet = statement.executeQuery(str);

        while(resultSet.next())
        {
            if(resultSet.getString("DayOfWeek") == "Sunday")
            {
                schedule.setSunday(resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime"));
            }
        }
    }

    private ObservableList<Schedule> getDataFromScheduleAndAddToObservableList(){
        ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
        try {
            String sundayShift = "";
            String mondayShift = "";
            String tuesdayShift = "";
            String wednesdayShift = "";
            String thursdayShift = "";
            String fridayString = "";
            String saturdayShift = "";

            connection = (Connection) database.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Schedule WHERE Employee_Id = " + employeeID);//"SELECT * FROM Schedule;"

            if(resultSet.first() == false)
            {
                return scheduleData;
            }
            while(resultSet.next())
            {
                if(resultSet.getString("DayOfWeek") == "Sunday")
                {
                    sundayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultSet.getString("DayOfWeek") == "Monday")
                {

                }
                else if(resultSet.getString("DayOfWeek") == "Tuesday")
                {
                    tuesdayShift = resultSet.getString("ShiftStartTime") + "-" + resultSet.getString("ShiftEndTime");
                }
                else if(resultSet.getString("DayOfWeek") == "Wednesday")
                {

                }
                else if(resultSet.getString("DayOfWeek") == "Thursday")
                {

                }
                else if(resultSet.getString("DayOfWeek") == "Friday")
                {

                }
                else //if(resultSet.getString("DayOfWeek") == "Saturday")
                {

                }
                scheduleData.add(
                        new Schedule(sundayShift, mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayString, saturdayShift
                ));
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

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
