package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClockInController {

    @FXML
    Button okButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
