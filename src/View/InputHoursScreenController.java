package View;

import Database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class InputHoursScreenController
{
    private DBConnection database = new DBConnection();
    private Connection connection = database.getConnection();
    private Statement statement = connection.createStatement();

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField UsernameField;
    @FXML
    private ChoiceBox TimePicker;
    @FXML
    private Button CancelButton;

    public InputHoursScreenController() throws SQLException
    {
        //TODO
    }

    @FXML
    public void bringUpClockIn()
    {
        if(doTheseHoursAndUsernameExist())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hours inputted successfully");
            alert.setHeaderText("You have inputted hours for " + UsernameField.getText() + "successfully");
            alert.setContentText("Good Job homie");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Username");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("Please type in a correct username");
            alert.showAndWait();
        }
    }

    @FXML
    public void bringUpClockOut()
    {
        if(doTheseHoursAndUsernameExist())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Clock Out successful");
            alert.setHeaderText("You have updated clocked out hours for " + UsernameField.getText() + "successfully");
            alert.setContentText("Good Job homie");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ClockIn on this date does not exist");
            alert.setContentText("Please type in a correct date or enter a clock in on this date");
            alert.showAndWait();
        }
    }

    @FXML
    public void bringUpDeleteHours()
    {
        if(doTheseHoursAndUsernameExist())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hours deleted successfully");
            alert.setHeaderText("You have deleted hours for " + UsernameField.getText() + "successfully");
            alert.setContentText("Good Job homie");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hours or username do not exist");
            alert.setHeaderText("Invalid Hours or username");
            alert.setContentText("Please type in a correct username or hours on the correct date");
            alert.showAndWait();
        }

    }

    @FXML
    public boolean doTheseHoursAndUsernameExist()
    {
        boolean doTheseHoursExist = false;
        boolean doesTheUsernameExist = false;
        boolean doBothExist = false;

        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String username = UsernameField.getText();
        String Time = "" + TimePicker.getValue(); //Ill come back to this

        if(doesTheUsernameExist && doTheseHoursExist)
        {
            doBothExist = true;
        }

        return doBothExist;

    }

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
