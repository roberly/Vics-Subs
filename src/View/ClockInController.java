package View;

import Database.DBConnection;
import Main.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClockInController
{
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private ObservableList<Employee> getDataFromEmployeeAndAddToObservableList(){
        ObservableList<Employee> EmployeeData = FXCollections.observableArrayList();
        try {

            connection = (Connection) database.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Employee");
            while(resultSet.next()){
                EmployeeData.add(new Employee(
                        resultSet.getString("Employee_ID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName")
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
        return EmployeeData;
    }

    @FXML
    Button okButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void logClockIn()
    {

    }
}
