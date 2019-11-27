package View;

import Database.DBConnection;
import Main.Employee;
import Main.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
    TableColumn<Schedule, String> employeeCol;
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

    ArrayList<Integer> EmployeeIDs = new ArrayList<>();
    ArrayList<String> EmployeeNames = new ArrayList<>();
    String week[] = new String[7];

    public void initialize() throws SQLException
    {
        showSchedule();
    }

    public void showSchedule() throws SQLException
    {
        employeeCol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("employee"));
        sundayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("sunday"));
        mondayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("monday"));
        tuesdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("tuesday"));
        wednesdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("wednesday"));
        thursdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("thursday"));
        fridayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("friday"));
        saturdayCol.setCellValueFactory(new PropertyValueFactory<Schedule,String>("saturday"));

        ObservableList<Schedule> data = getDataFromScheduleAndAddToObservableList();
        scheduleTable.getItems().addAll(data);

        scheduleTable.setEditable(true);
        scheduleTable.getSelectionModel().setCellSelectionEnabled(true);

        scheduleTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    @SuppressWarnings("rawtypes")
                    TablePosition pos = (TablePosition) scheduleTable.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();
                    int col = pos.getColumn();
                    @SuppressWarnings("rawtypes")
                    TableColumn column = pos.getTableColumn();
                    String val = column.getCellData(row).toString();
                    System.out.println("Selected Value, " + val + ", Column: " + col + ", Row: " + row);

                    if(val.equals(""))
                    {
                        try
                        {
                            addShift(col, EmployeeIDs.get(row));
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try
                        {
                            changeShift(val, col, EmployeeIDs.get(row));
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    //Do thing
                    //If its empty, we will want to do an insert of a new shift
                    //If it exists, we will want to do an update of the shift. Maybe find a way to bind the shift ID to
                    //it? Probably not worth trying and I'll just select by shift date. This will all be handled in the
                    //Shift edit controller obviously but it doesn't exist yet.
                }
            }
        });
    }

    private void addShift(int weekColumn, int employeeID) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddShift.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        AddShiftController controller = fxmlLoader.getController();
        controller.onInit(week[weekColumn], employeeID);
        Stage stage = new Stage();
        stage.setTitle("Add New Shift");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    private void changeShift(String currentShift, int weekColumn, int employeeID) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditShift.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Alter Shift");
        stage.setScene(new Scene(root1));
        stage.show();
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
            String employee = "";
            String[] week = getWeek();

            connection = (Connection) database.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Employee WHERE IsAdmin = 0");

            while(resultSet.next())
            {
                EmployeeIDs.add(resultSet.getInt("Employee_Id"));
                EmployeeNames.add(resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
            }

            for(int i = 0; i < EmployeeIDs.size(); i++)
            {
                resultSet = statement.executeQuery("SELECT * from Schedule where Employee_Id = " + EmployeeIDs.get(i));
                employee = EmployeeNames.get(i);
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
                scheduleData.add(new Schedule(employee, mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayShift, saturdayShift, sundayShift));
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
