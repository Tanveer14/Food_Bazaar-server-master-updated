package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationViewController implements Initializable {
   @FXML
   public Label ConfirmationMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfirmationMessage.setText(LogInPage.ConfirmationMessage);
    }
}

