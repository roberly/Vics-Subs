package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeController {


    /**
     * Clock in
     */
    @FXML
    public void bringUpClockInGui() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClockIn.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
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




}
