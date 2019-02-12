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
import java.util.*;

public class OrderViewController implements Initializable {
    @FXML
    Button DoneButton,NextOrderButton,PreviousOrderButton,GoBackButton,CancelButton;
    @FXML Label OrderLabel,Caption;
    ArrayList<Customer> OrderList;
    private int count;
    File file=new File("orderInfo.txt");

    public OrderViewController() throws Exception {
    }

    @FXML public void CancelButtonClicked() throws Exception{
        Customer customer1=OrderList.get(count);
        int customerId=customer1.getId();
        System.out.println(customer1.getProductList());

        Map<String,ArrayList<String>> mapFoodTypes=new HashMap<>();
        Map<String,Double> mapAvailableItems=new HashMap<>();
        for(int i=0;i<customer1.getProductList().size();i++){
            String foodtype=customer1.getProductList().get(i).getType();
            String foodname=customer1.getProductList().get(i).getName();
            Double unit=customer1.getProductList().get(i).getUnit();
            if(mapFoodTypes.get(foodtype)==null){
                mapFoodTypes.put(foodtype,new ArrayList<String>());
            }
            mapFoodTypes.get(foodtype).add(foodname);
            if(mapAvailableItems.get(foodname)==null){
                mapAvailableItems.put(foodname,unit);
            }
            else{
                unit+=mapAvailableItems.get(foodname);
                mapAvailableItems.put(foodname,unit);
            }
        }

        int found;

        for(Map.Entry<String,ArrayList<String>> ee:mapFoodTypes.entrySet()){
            ArrayList<product> temp=Common.ownerFileInput(new File(ee.getKey().toLowerCase()+".txt"));
            for(int i=0;i<ee.getValue().size();i++){
                found=0;
                if(!(mapAvailableItems.containsKey(ee.getValue().get(i)))) continue;
                for(int j=0;j<temp.size();j++){
                    if(ee.getValue().get(i).equals(temp.get(j).getName())){
                        Double Unit=mapAvailableItems.get(temp.get(j).getName());
                        mapAvailableItems.remove(temp.get(j).getName());
                        int unit=Unit.intValue();
                        temp.get(j).add_available_units(unit);
                        found=1;
                        break;
                    }
                }
                if(found==0){
                    for(product ii:customer1.getProductList()){
                        if(ii.getName().equals(ee.getValue().get(i))){
                            System.out.println(ii);
                            ii.setAvailable_units(mapAvailableItems.get(ee.getValue().get(i)));
                            temp.add(ii);
                            mapAvailableItems.remove(ii.getName());

                            ArrayList<product> endproducts=new ArrayList<>();
                            endproducts=Common.ownerFileInput(new File(ii.getType().toLowerCase()+"outOfStock.txt"));
                            for(int k=0;k<endproducts.size();k++){
                                if(endproducts.get(k).getName().equalsIgnoreCase(ii.getName())){
                                    endproducts.remove(k);
                                    Common.fileupdate(new File(ii.getType().toLowerCase()+"outOfStock.txt"),endproducts);
                                    break;
                                }
                            }

                            break;
                        }
                    }
                }
            }
            Common.fileupdate(new File(ee.getKey().toLowerCase() + ".txt"),temp);
            File fileCancel=new File("cancelled orders.txt");


            FileWriter fw=new FileWriter(fileCancel,true);
            fw.write(customerId+"\n");
            fw.close();


            ArrayList<Customer> CustomerArrayList = new ArrayList<>();
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true) {
                try {
                    Customer temp1 = (Customer) oi.readObject();
                    if (temp1.getId() != customerId) CustomerArrayList.add(temp1);
                } catch (EOFException e) {
                    break;
                }
            }
            oi.close();
            fi.close();

            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream Oo = new ObjectOutputStream(fo);

            for (Customer c : CustomerArrayList) {
                Oo.writeObject(c);
            }

            Oo.close();
            fo.close();

            OrderList = CustomerArrayList;
        }
        count--;
        if(OrderList.size()!=0)
        {
            NextOrderButtonClicked();
        }
        else
        {
            DoneButton.setDisable(true);
            NextOrderButton.setDisable(true);
            PreviousOrderButton.setDisable(true);
            OrderLabel.setText("No Order Pending ! ! !");
        }
           /* int i=0;
            while (true){
                try {
                    fw.write(CancelledOrders.get(i)+"\n");
                    i++;
                }catch (Exception e)
                {
                    System.out.println("my prob o  "+ e);

                    break;
                }
            }
            */

        }




    @FXML public void DoneButtonClicked() throws Exception {

        File countfile=new File("Earning.txt");
        try {

            Scanner scanner=new Scanner(countfile);
            int earningCount=scanner.nextInt();
            scanner.close();
            earningCount+=OrderList.get(count).getTotalPrice();
            FileWriter fw=new FileWriter(countfile);
            fw.write(String.valueOf(earningCount));
            fw.close();
        } catch (IOException e) {

            System.out.println("main333");
            System.out.println(e);
        }



        String labelText = OrderLabel.getText();
        String[] textParts = labelText.split("\n");
        System.out.println(textParts[0]);
        String type = textParts[0];

        if (type.startsWith("Customer Id:")) {
            String[] parts = type.split("\t");
            File file1 = new File("Done Orders.txt");
            FileWriter doneOrderWriter = new FileWriter(file1, true);
            String s=parts[1]+"\n";
            doneOrderWriter.write(s);
            doneOrderWriter.close();

            int orderId = 0;

            try{
                orderId = Integer.parseInt(parts[1]);
            }catch (Exception e){

            }
            System.out.println("Order No " + orderId);
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
            fi.close();

            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream Oo = new ObjectOutputStream(fo);

            for (Customer c : CustomerArrayList) {
                Oo.writeObject(c);
            }

            Oo.close();
            fo.close();

            OrderList = CustomerArrayList;
        }
        count--;
        if(OrderList.size()!=0)
        {
            NextOrderButtonClicked();
        }
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

        count=(count+1)%OrderList.size();
        OrderLabel.setText(OrderList.get(count).toMessage());
      /*  if(count+1<OrderList.size()){
            count++;
            OrderLabel.setText(OrderList.get(count).toMessage());
            PreviousOrderButton.setDisable(false);
        }
        if(count+1>=OrderList.size())NextOrderButton.setDisable(true);*/

    }
    @FXML public void PreviousOrderButtonClicked(){

        if(count-1<0)count+=OrderList.size();
        count=(count-1)%OrderList.size();
        OrderLabel.setText(OrderList.get(count).toMessage());
       /* if(count-1>=0)
        {
            count--;
            OrderLabel.setText(OrderList.get(count).toMessage());
            NextOrderButton.setDisable(false);
        }
        if(count-1<0)PreviousOrderButton.setDisable(true);*/
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //networking needed to read from the arraylist of orders
        GoBackButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
        NextOrderButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
        DoneButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
        PreviousOrderButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
        Caption.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE;");
        CancelButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color:ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");

        if(file.length()==0){
            OrderLabel.setText("No Order Pending ! ! !");
            NextOrderButton.setDisable(true);
            PreviousOrderButton.setDisable(true);
            DoneButton.setDisable(true);
        }else {

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
                oi.close();
                OrderList=CustomerArrayList;
                if(OrderList.size()!=0){
                    OrderLabel.setText(OrderList.get(count).toMessage());
                    /*if(count+1==OrderList.size())NextOrderButton.setDisable(true);
                    if(count-1<0)PreviousOrderButton.setDisable(true);*/
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

    }

   /* public void PreviousOrderButtonClicked(ActionEvent event) {
    }*/

    public void GoBackButtonClicked(ActionEvent event) throws IOException {
        Parent newsceneparent= FXMLLoader.load(getClass().getResource("FirstPage.fxml"));
        Common.ButtonClicked(event,newsceneparent);
    }
}
