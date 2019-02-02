package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {
    @FXML
    Button DoneButton,NextOrderButton;
    @FXML Label OrderLabel;
    ArrayList<Customer> OrderList;

    public OrderViewController() throws IOException {
    }

    @FXML public void DoneButtonClicked()
   {

   }
    @FXML public void NextOrderButtonClicked()
    {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //networking needed to read from the arraylist of orders
        try {
            File file=new File("orderInfo.txt");
            FileInputStream fi=new FileInputStream(file);
            ObjectInputStream oi=new ObjectInputStream(fi);
            OrderList= (ArrayList<Customer>) oi.readObject();
            for (Customer c:OrderList) {
                System.out.println(c);
            }
            oi.close();
            /*socket=new Socket("localhost",4444);
            ObjectOutputStream outtoServer=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oinfromServer=new ObjectInputStream(socket.getInputStream());
            outtoServer.writeObject("Show Orders");
            OrderList= (ArrayList<Customer>) oinfromServer.readObject();
            System.out.println(OrderList.get(0).toString());
            OrderLabel.setText(OrderList.get(0).toString());
            socket.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void PreviousOrderButtonClicked(ActionEvent event) {
    }

    public void GoBackButtonClicked(ActionEvent event) throws IOException {
        Parent newsceneparent= FXMLLoader.load(getClass().getResource("OwnerIn.fxml"));
        Common.ButtonClicked(event,newsceneparent);
    }
}
