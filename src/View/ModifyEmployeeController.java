package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyEmployeeController
{
    /*private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet; **/

    private int employeeID;
    @FXML
    public TextField FirstNameField;
    @FXML
    public TextField LastNameField;
    @FXML
    Label LastNameLabel;
    @FXML
    Label FirstNameLabel;
    @FXML
    Button CancelButton;
    @FXML
    Button AddEmployeeButton;
    @FXML
    Button EditEmployeeButton;
    @FXML
    Button DeleteEmployeeButton;

    @FXML
    public void bringUpAddEmployee() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNewEmployee.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Employee");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    public void bringUpEditEmployee() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditEmployee.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Existing Employee");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    public void bringUpDeleteConfirmation() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteEmployeeConfirmation.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        DeleteEmployeeConfirmationController control = fxmlLoader.getController();
        control.onInit(FirstNameField.getText(), LastNameField.getText());
        Stage stage = new Stage();
        stage.setTitle("Delete an Employee");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
