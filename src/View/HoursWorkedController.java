package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HoursWorkedController {

    @FXML
    Button doneButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
