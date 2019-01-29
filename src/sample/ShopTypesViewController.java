package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;


import javafx.event.ActionEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ShopTypesViewController implements Initializable {


    @FXML
    ComboBox<String> FoodType;
    @FXML
    Button SellerButton;
    @FXML
    Label CommentLabel;
    public static String SelectedType=null;

    public void SellerButtonClicked(ActionEvent event) throws Exception{
        Parent subPage= FXMLLoader.load(getClass().getResource("OwnerIn.fxml"));
        Common.ButtonClicked(event,subPage);
    }

    public void ReadLabel() throws Exception{
        CommentLabel.setDisable(false);
    }

   /* @FXML
    TreeView<String> FoodTree;

    @FXML public void FruitsButtonClicked(ActionEvent event) throws IOException{
        Parent FruitPage= FXMLLoader.load(getClass().getResource("FruitsView.fxml"));
        Common.ButtonClicked(event,FruitPage);
    }

    @FXML public void VegetablesButtonClicked(ActionEvent event) throws Exception{
        Parent VegPage= FXMLLoader.load(getClass().getResource("VegetablesView.fxml"));
        Common.ButtonClicked(event,VegPage);
    }

    @FXML public void StaplesButtonClicked(ActionEvent event) throws Exception{
        Parent StapPage= FXMLLoader.load(getClass().getResource("StaplesView.fxml"));
        Common.ButtonClicked(event,StapPage);
    }

    @FXML public void MFButtonClicked(ActionEvent event) throws Exception{
        Parent MFPage= FXMLLoader.load(getClass().getResource("Meat&Fish.fxml"));
        Common.ButtonClicked(event,MFPage);
    }*/


    public void FoodTypeSelected(ActionEvent event) throws Exception{

       // SelectedType=FoodType.getValue();
        Parent Page= FXMLLoader.load(getClass().getResource("CommonTypeView.fxml"));
        Common.ButtonClicked(event,Page);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Image icon=new Image(getClass().getResourceAsStream("icon.png"));
        //treeViewControl.setTreeview(FoodTree);
        ArrayList<String> types = new ArrayList<>();
        types=Common.OwnerFile(types, new File("type list"));
        FoodType.getItems().addAll(types);


    }
}
