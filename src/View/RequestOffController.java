package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RequestOffController {

    @FXML
    Button cancelButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
