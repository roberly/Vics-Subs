package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockOutController {

    public void initialize(){
        timeLabel.setText(getCurrentTime());
    }

    @FXML
    Button okButton;

    @FXML
    Label timeLabel;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public static String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

}
