package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FirstPageController implements Initializable {


    @FXML public Button StockButton,OrderCheckButton,AddProductButton;
    @FXML private Label EarnLabel;

    @FXML public void StockButtonClicked(ActionEvent e) throws Exception{

        Parent page= FXMLLoader.load(getClass().getResource("Stock2.fxml"));
        Common.ButtonClicked(e,page);
    }

    public void OrderCheckButtonClicked(ActionEvent e) throws Exception{
        Parent page= FXMLLoader.load(getClass().getResource("OrderView.fxml"));
        Common.ButtonClicked(e,page);
    }

    public void AddProductButtonClicked(ActionEvent e) throws IOException {
        Parent page= FXMLLoader.load(getClass().getResource("OwnerIn.fxml"));
        Common.ButtonClicked(e,page);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StockButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE");
        OrderCheckButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE");
        AddProductButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE");
        int earningCount=0;
        File countfile=new File("Earning.txt");
        try {

            Scanner scanner = new Scanner(countfile);
             earningCount = scanner.nextInt();
            scanner.close();
        }catch (Exception e){
            System.out.println(e);
        }

        EarnLabel.setText(earningCount+" tk only");

    }
}
