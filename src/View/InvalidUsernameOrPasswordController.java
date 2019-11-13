package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InvalidUsernameOrPasswordController
{
    @FXML
    Button OKButton;
    @FXML
    Label InvalidLabel;
    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }


}
