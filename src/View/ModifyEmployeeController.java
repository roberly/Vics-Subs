package View;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyEmployeeController
{

    @FXML
    Button CancelButton;

    @FXML
    public void bringUpAddEmployee() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Employee");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    public void bringUpEditEmployee() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
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
