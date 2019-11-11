package View;

import Database.DBConnection;
import Main.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WelcomeController
{
    @FXML
    private Label welcomeField;

    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Employee employee;

    public void getEmployeeData(Employee e)
    {
        employee = e;
        welcomeField.setText("Welcome to Vic's, " + employee.getFirstName() + " " + employee.getLastName());
    }

    /**
     * Clock in
     */
    @FXML
    public void bringUpClockInGui() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClockIn.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        ClockInController controller = fxmlLoader.getController();
        controller.setEmployeeID(employee.getId());
        Stage stage = new Stage();
        stage.setTitle("Clock In");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * Clock out
     */
    @FXML
    public void bringUpClockOutGui() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClockOut.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        ClockOutController controller = fxmlLoader.getController();
        controller.setEmployeeID(employee.getId());
        Stage stage = new Stage();
        stage.setTitle("Clock Out");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * Schedule
     */
    @FXML
    public void bringUpScheduleGui() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Schedule.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Employee Schedule");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * Hours Worked
     */
    @FXML
    public void bringUpHoursWorkedGui() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HoursWorked.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("View Hours Worked");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * Request off
     */
    @FXML
    public void bringUpRequestOffGui() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RequestOff.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Request Off");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    Button logoutButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }




}
