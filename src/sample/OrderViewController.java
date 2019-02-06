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
    Button DoneButton,NextOrderButton,PreviousOrderButton;
    @FXML Label OrderLabel;
    ArrayList<Customer> OrderList;
    private int count;
    File file=new File("orderInfo.txt");

    public OrderViewController() throws Exception {
    }

    @FXML public void DoneButtonClicked() throws Exception {
        String labelText = OrderLabel.getText();
        String[] textParts = labelText.split("\n");
        System.out.println(textParts[0]);
        String type = textParts[0];

        if (type.startsWith("Customer Id:")) {
            String[] parts = type.split("\t");
            File file1 = new File("Done Orders.txt");
            FileWriter doneOrderWriter = new FileWriter(file1, true);
            doneOrderWriter.write(parts[1]+"\n");
            doneOrderWriter.close();

            System.out.println("Order No " + parts[1]);
            int orderId = Integer.parseInt(parts[1]);
            ArrayList<Customer> CustomerArrayList = new ArrayList<>();
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true) {
                try {
                    Customer temp = (Customer) oi.readObject();
                    if (temp.getId() != orderId) CustomerArrayList.add(temp);
                } catch (EOFException e) {
                    break;
                }
            }
            oi.close();

            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream Oo = new ObjectOutputStream(fo);

            for (Customer c : CustomerArrayList) {
                Oo.writeObject(c);
            }

            Oo.close();

            OrderList = CustomerArrayList;
        }
        count--;
        if(OrderList.size()!=0)NextOrderButtonClicked();
        else
        {
            DoneButton.setDisable(true);
            NextOrderButton.setDisable(true);
            PreviousOrderButton.setDisable(true);
            OrderLabel.setText("No Order Pending ! ! !");
        }



    }


    @FXML public void NextOrderButtonClicked()
    {
        if(count+1<OrderList.size()){
            count++;
            OrderLabel.setText(OrderList.get(count).toMessage());
        }
        if(count+1==OrderList.size())NextOrderButton.setDisable(true);

    }
    @FXML public void PreviousOrderButtonClicked(){
        if(count-1>=0)
        {
            count--;
            OrderLabel.setText(OrderList.get(count).toMessage());
        }
        if(count-1<0)PreviousOrderButton.setDisable(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //networking needed to read from the arraylist of orders

        try {

            ArrayList<Customer> CustomerArrayList=new ArrayList<>();
            FileInputStream fi=new FileInputStream(file);
            ObjectInputStream oi=new ObjectInputStream(fi);
            while (true)
            {
                try {
                    Customer temp= (Customer) oi.readObject();
                    CustomerArrayList.add(temp);
                }catch (EOFException e){
                    break;
                }
            }
            System.out.println(CustomerArrayList);
            oi.close();
            OrderList=CustomerArrayList;
            if(OrderList.size()!=0){
                OrderLabel.setText(OrderList.get(count).toMessage());
                if(count+1==OrderList.size())NextOrderButton.setDisable(true);
                if(count-1<0)PreviousOrderButton.setDisable(true);
            }
            else{
                OrderLabel.setText("No Order Pending ! ! !");
                NextOrderButton.setDisable(true);
                PreviousOrderButton.setDisable(true);
                DoneButton.setDisable(true);
            }


            /*File file=new File("orderInfo.txt");
            FileInputStream fi=new FileInputStream(file);
            ObjectInputStream oi=new ObjectInputStream(fi);
            OrderList= (ArrayList<Customer>) oi.readObject();
            for (Customer c:OrderList) {
                System.out.println(c);
            }
            oi.close();*/
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

   /* public void PreviousOrderButtonClicked(ActionEvent event) {
    }*/

    public void GoBackButtonClicked(ActionEvent event) throws IOException {
        Parent newsceneparent= FXMLLoader.load(getClass().getResource("OwnerIn.fxml"));
        Common.ButtonClicked(event,newsceneparent);
    }
}
