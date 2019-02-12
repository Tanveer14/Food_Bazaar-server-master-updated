package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Main extends Application {
    public static final int port= 4444;
    private ServerSocket ss;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("FirstPage.fxml"));
        primaryStage.setTitle("Food Bazaar");
         primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

         ss = new ServerSocket(port);
        System.out.println(InetAddress.getLocalHost());
         Thread s=new Thread(new Runnable() {
             @Override
             public void run() {
                 while(true) {
                     try {
                         Socket connection = ss.accept();
                         WorkerThread wt = new WorkerThread(connection);
                         Thread t = new Thread(wt);
                         t.start();
                     } catch (Exception e) {
                         System.out.println(e+" main1");
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


        try {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Object ob=is.readObject();
            System.out.println(ob);
            File file=new File("orderInfo.txt");

                    if(ob.getClass().getName().equals("java.lang.String"))
                    {
                        type=(String)ob;
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
                            oi.close();
                            fi.close();
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

                                System.out.println("main333");
                                e.printStackTrace();
                            }
                        }
                        else if(type.startsWith("Customer Id:"))
                        {

                            String []parts=type.split("\t");

                            CustomerOrder order=new CustomerOrder();

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
                                   else order=new CustomerOrder(temp.getId(),temp.getName());
                                }catch (EOFException e){
                                    break;
                                }
                            }
                            oi.close();
                            fi.close();

                            FileOutputStream fo=new FileOutputStream(file);
                            ObjectOutputStream Oo=new ObjectOutputStream(fo);
                            for (Customer c:CustomerArrayList) {
                                Oo.writeObject(c);
                            }
                            Oo.close();
                            fo.close();

                            ArrayList<CustomerOrder> DoneOrders=new ArrayList<>();


                            FileInputStream fi2=new FileInputStream("Done Orders.txt");
                            ObjectInputStream oi2=new ObjectInputStream(fi2);
                            while (true)
                            {
                                try {
                                    CustomerOrder o= (CustomerOrder) oi2.readObject();
                                    DoneOrders.add(o);
                                }catch (EOFException e){
                                    break;
                                }
                            }
                            oi2.close();
                            fi2.close();

                            DoneOrders.add(order);

                            FileOutputStream fo2=new FileOutputStream("Done Orders.txt");
                            ObjectOutputStream Oo2=new ObjectOutputStream(fo2);
                            for (CustomerOrder o:DoneOrders) {
                                Oo2.writeObject(o);
                            }
                            Oo2.close();
                            fo2.close();

                            os.writeObject(CustomerArrayList);
                        }
                        else if(type.startsWith("CheckOrder"))
                        {
                            System.out.println("in check order");
                            CustomerOrder temp=new CustomerOrder();
                            int CustomerId = 0;
                            boolean OrderExists=false;
                            CustomerOrder temp1=new CustomerOrder();
                            String []parts=type.split(" ");
                           try {
                               CustomerId=Integer.parseInt(parts[1]);

                               temp.setId(CustomerId);
                               FileInputStream filein=new FileInputStream(new File("All Orders Info.txt"));
                               ObjectInputStream objin=new ObjectInputStream(filein);
                               while (true)
                               {
                                   try {
                                       CustomerOrder co= (CustomerOrder) objin.readObject();
                                       if(co.getId()==CustomerId){
                                           OrderExists=true;
                                           System.out.println("got "+co.toString());
                                           temp.setName(co.getName());
                                           break;
                                       }
                                   }catch (Exception e){
                                       System.out.println("IT "+e);
                                       break;
                                   }
                               }

                               objin.close();
                               filein.close();


                           }catch (Exception e)
                           {
                               
                           }

                            if(!OrderExists){
                                temp.setName("No Name");
                                temp.setStatus("Invalid ID");
                            }else {
                                Scanner scanner=new Scanner(new File("Done Orders.txt"));
                                int gotIt = 0;

                                String id=String.valueOf(CustomerId);
                                System.out.println(id);

                                while(scanner.hasNext()){
                                    String str=scanner.nextLine();
                                    if(str.equals(id)){
                                        gotIt=1;//got itin done
                                        break;
                                    }
                                }

                                scanner.close();

//my part
                                if(gotIt!=1)
                                {

                                    File fileCancel=new File("cancelled orders.txt");
                                    Scanner scannerc=new Scanner(fileCancel);
                                    while (scannerc.hasNextLine())
                                    {
                                        String string=scannerc.nextLine();
                                        if(string.equals(id)){
                                            gotIt=2;
                                            break;
                                        }

                                    }
                                    scannerc.close();

                                }



                                if (gotIt==1){
                                    temp.setStatus("Cleared!");
                                }
                                else {
                                    if(gotIt==0)temp.setStatus("Pending");
                                    if(gotIt==2)temp.setStatus("Cancelled");

                                }

                            }
                            os.writeObject(temp);
                        }
                        //another else if will be added for sending an order status
                        else {

                            ArrayList<product> senttoClient=Common.setSceneforNetworking(type);
                            os.writeObject(senttoClient);
                        }

                    }
                    else if(ob.getClass().getName().equals("sample.Customer")){

                        File file2=new File("orderInfo.txt");
                        File file3=new File("All Orders Info.txt");
                        ArrayList<Customer> CustomerArrayList=new ArrayList<>();
                        ArrayList<CustomerOrder> CustomerOrderArrayList=new ArrayList<>();


                        Customer customer1=(Customer) ob;
                        CustomerOrder customerOrder=new CustomerOrder(customer1.getName(),customer1.getId());
                        System.out.println(customer1);
                        //CustomerArrayList= (ArrayList<Customer>) oin.readObject();

                        ///problem is the file is got

                        try {
                            FileInputStream fin = new FileInputStream(file2);
                            ObjectInputStream oin  = new ObjectInputStream(fin);
                            while (true) {
                                try {
                                    Customer temp = (Customer) oin.readObject();
                                    CustomerArrayList.add(temp);
                                }catch (Exception e)
                                {
                                    oin.close();
                                    break;
                                }
                            }
                            fin.close();


                            FileInputStream fin2 = new FileInputStream(file3);
                            ObjectInputStream oin2  = new ObjectInputStream(fin2);
                            while (true) {
                                try {
                                    CustomerOrder temp = (CustomerOrder) oin2.readObject();
                                    CustomerOrderArrayList.add(temp);
                                }catch (Exception e)
                                {
                                    oin2.close();
                                    break;
                                }
                            }
                            fin2.close();



                        }catch (EOFException e)
                        {

                            System.out.println("here1");
                        }


                        CustomerArrayList.add(customer1);
                        CustomerOrderArrayList.add(customerOrder);



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

                        for(Map.Entry<String,ArrayList<String>> ee:mapFoodTypes.entrySet()){
                            ArrayList<product> temp=Common.ownerFileInput(new File(ee.getKey().toLowerCase()+".txt"));
                            for(int i=0;i<ee.getValue().size();i++){
                                if(!(mapAvailableItems.containsKey(ee.getValue().get(i)))) continue;
                                for(int j=0;j<temp.size();j++){
                                    if(ee.getValue().get(i).equals(temp.get(j).getName())){
                                        Double Unit=mapAvailableItems.get(temp.get(j).getName());
                                        mapAvailableItems.remove(temp.get(j).getName());
                                        int unit=Unit.intValue();
                                        temp.get(j).exclude_available_units(unit);
                                        if(temp.get(j).getAvailable_units()<=0){
                                            Common.outOfStockFileUpdate(temp.get(j));
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
                        try {
                            FileOutputStream fo = new FileOutputStream(file2);
                            ObjectOutputStream Oo = new ObjectOutputStream(fo);

                            for (Customer c : CustomerArrayList) {
                                Oo.writeObject(c);
                            }

                            Oo.close();
                            fo.close();

                            FileOutputStream fo2 = new FileOutputStream(file3);
                            ObjectOutputStream Oo2 = new ObjectOutputStream(fo2);

                            for (CustomerOrder c : CustomerOrderArrayList) {
                                Oo2.writeObject(c);
                            }

                            Oo2.close();
                            fo2.close();
                        }catch (Exception e){
                            System.out.println("in here");
                        }

                         //when the customer confirms order, then  the id increases by 1 and writes into file
                        int customercount=customer1.getId()+1;
                        FileWriter fw=new FileWriter("Idcount.txt");
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
            System.out.println(e+"\t main jayyga");
        }
    }
}
