package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static sun.plugin2.main.client.LiveConnectSupport.shutdown;

public class Main extends Application {
    public static final int port= 4444;
    private ServerSocket ss;
    String type;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("OwnerIn.fxml"));
        primaryStage.setTitle("Food Bazaar");
         primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

         ss = new ServerSocket(port);
         Thread s=new Thread(new Runnable() {
             @Override
             public void run() {
                 while(true) {
                     try {

                         Socket connection = ss.accept();
                         WorkerThread wt = new WorkerThread(connection);
                         Thread t = new Thread(wt);
                         t.start();
        /*for(product i:sendtoClient){
            os.writeObject(i);
        }*/
                     } catch (Exception e) {
                         System.out.println(e);
                     }
                 }
             }
         });

         s.start();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
class WorkerThread implements Runnable{
    private Socket socket;
    WorkerThread(Socket s){
        socket=s;
    }

    public void run(){
        String type;
        Customer customer;


        try {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Object ob=is.readObject();
            File file=new File("orderInfo.txt");

                    if(ob.getClass().getName().equals("java.lang.String"))
                    {
                        type=(String)ob;
                        System.out.println(type);
                        if(type.equals("Show Orders"))
                        {
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
                            os.writeObject(CustomerArrayList);

                        }
                        else if(type.equals("type list")){
                            ArrayList<String> types=Common.OwnerFile(new File("type list"));
                            os.writeObject(types);

                        }
                        else if(type.equals("Unit type list")){
                            ArrayList<String> types=Common.OwnerFile(new File("Unit type list"));
                            os.writeObject(types);
                        }
                        else if(type.equals("Customer Count")){
                            File file1=new File("Idcount.txt");
                            try {

                                Scanner scanner=new Scanner(file1);
                                int customercount=scanner.nextInt();
                                scanner.close();
                                os.writeObject(customercount);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(type.startsWith("Customer Id:"))
                        {
                            String []parts=type.split("\t");
                            File file1=new File("Done Orders.txt");
                            /*FileWriter doneOrderWriter=new FileWriter(file1,true);
                            doneOrderWriter.write(parts[1]+"\n");
                            doneOrderWriter.close();*/

                            Order order=new Order();

                            System.out.println("Order No "+parts[1]);
                            int orderId=Integer.parseInt(parts[1]);
                            ArrayList<Customer> CustomerArrayList=new ArrayList<>();
                            FileInputStream fi=new FileInputStream(file);
                            ObjectInputStream oi=new ObjectInputStream(fi);
                            while (true)
                            {
                                try {
                                    Customer temp= (Customer) oi.readObject();
                                   if(temp.getId()!=orderId) CustomerArrayList.add(temp);
                                   else order=new Order(temp.getId(),temp.getName());
                                }catch (EOFException e){
                                    break;
                                }
                            }
                            oi.close();

                            FileOutputStream fo=new FileOutputStream(file);
                            ObjectOutputStream Oo=new ObjectOutputStream(fo);
                            for (Customer c:CustomerArrayList) {
                                Oo.writeObject(c);
                            }
                            Oo.close();

                            ArrayList<Order> DoneOrders=new ArrayList<>();
                            FileInputStream fi2=new FileInputStream("Done Orders.txt");
                            ObjectInputStream oi2=new ObjectInputStream(fi2);
                            while (true)
                            {
                                try {
                                    Order o= (Order) oi2.readObject();
                                    DoneOrders.add(o);
                                }catch (EOFException e){
                                    break;
                                }
                            }
                            oi2.close();

                            DoneOrders.add(order);

                            FileOutputStream fo2=new FileOutputStream("Done Orders.txt");
                            ObjectOutputStream Oo2=new ObjectOutputStream(fo2);
                            for (Order o:DoneOrders) {
                                Oo2.writeObject(o);
                                System.out.println(o);
                            }
                            Oo2.close();


                            os.writeObject(CustomerArrayList);




                        }
                        else {

                            System.out.println(type);
                            ArrayList<product> senttoClient=Common.setSceneforNetworking(type);
                            os.writeObject(senttoClient);
                        }

                    }
                    else if(ob.getClass().getName().equals("sample.Customer")){
                        File file2=new File("orderInfo.txt");
                        ArrayList<Customer> CustomerArrayList=new ArrayList<>();
                        FileInputStream fin=new FileInputStream(file2);
                        ObjectInputStream oin=new ObjectInputStream(fin);

                        Customer customer1=(Customer) ob;
                        System.out.println(customer1);
                        //CustomerArrayList= (ArrayList<Customer>) oin.readObject();

                        ///problem is the file is got
                       while (true)
                        {
                            try {
                                Customer temp= (Customer) oin.readObject();
                                System.out.println(temp);
                                CustomerArrayList.add(temp);
                            }catch (EOFException e){
                                break;
                            }
                        }

                        CustomerArrayList.add(customer1);
                        System.out.println();
                        System.out.println("it is    "+CustomerArrayList);
                        System.out.println();
                        oin.close();

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
                            /*else{
                                unit+=mapAvailableItems.get(foodname);
                            }*/
                        }
                        for(Map.Entry<String,ArrayList<String>> ee:mapFoodTypes.entrySet()){
                            System.out.println(ee.getKey()+" "+ee.getValue());
                            ArrayList<product> temp=Common.ownerFileInput(new File(ee.getKey().toLowerCase()+".txt"));
                            for(int i=0;i<ee.getValue().size();i++){
                                for(int j=0;j<temp.size();j++){
                                    if(ee.getValue().get(i).equals(temp.get(j).getName())){
                                        Double Unit=mapAvailableItems.get(temp.get(j).getName());
                                        int unit=Unit.intValue();
                                        temp.get(j).exclude_available_units(unit);
                                        if(temp.get(j).getAvailable_units()==0){
                                            temp.remove(j);
                                        }
                                        break;
                                    }
                                }
                            }
                            Common.fileupdate(new File(ee.getKey().toLowerCase() + ".txt"),temp);
                        }


////here the total customerlist will be added to the file
                        //it will be helpfull if somehow the file can be made blank
                        FileOutputStream fo=new FileOutputStream(file2);
                        ObjectOutputStream Oo=new ObjectOutputStream(fo);

                        for (Customer c:CustomerArrayList) {
                            Oo.writeObject(c);
                        }
                        oin.close();
                        Oo.close();

                        //when the customer confirms order, then  the id increases by 1 and writes into file
                        int customercount=customer1.getId()+1;
                        FileWriter fw=new FileWriter("Idcount.txt");
                        //fw.write(String.valueOf(customercount));
                        fw.write(String.valueOf(customercount));
                        fw.close();

                    }
                    else if(ob.getClass().getName().equals("java.util.ArrayList")){
                        ArrayList<product> pro=(ArrayList<product>)ob;
                        String s=pro.get(0).getType();
                        Common.fileupdate(new File(s.toLowerCase() + ".txt"),pro);
                    }


                   //


                os.flush();
                os.close();
            is.close();
            socket.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
