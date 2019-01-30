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

import static sun.plugin2.main.client.LiveConnectSupport.shutdown;

public class Main extends Application {
    public static final int port= 4444;
    private ServerSocket ss;
    String type;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ShopTypesView.fxml"));
        primaryStage.setTitle("Food Bazaar");

        ss = new ServerSocket(port);
       // primaryStage.setScene(new Scene(root, 800, 600));
        //primaryStage.show();
        while(true) {
            try {

                Socket connection = ss.accept();
                System.out.println("accepted");
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
        File file=new File("orderInfo.txt");
        ArrayList<Customer> CustomerArrayList=new ArrayList<>();
        try {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Object ob=is.readObject();


                    if(ob.getClass().getName().equals("java.lang.String"))
                    {
                        type=(String)ob;
                        if(type.equals("Show Orders"))
                        {
                            FileInputStream fi=new FileInputStream(file);
                            ObjectInputStream oi=new ObjectInputStream(fi);
                             CustomerArrayList= (ArrayList<Customer>) oi.readObject();
                            for (Customer c:CustomerArrayList
                                 ) {
                                System.out.println(c);
                            }
                            oi.close();
                            os.writeObject(CustomerArrayList);

                        }
                        else {

                            System.out.println(type);
                            ArrayList<product> senttoClient=Common.setSceneforNetworking(type);
                            os.writeObject(senttoClient);
                        }

                    }
                    else if(ob.getClass().getName().equals("sample.Customer")){
                        FileOutputStream fo=new FileOutputStream(file);
                        ObjectOutputStream Oo=new ObjectOutputStream(fo);
                        FileInputStream fin=new FileInputStream(file);
                        ObjectInputStream oin=new ObjectInputStream(fin);
                        CustomerArrayList.add((Customer) ob);
                        oin.close();
                       System.out.println();
                        Oo.writeObject(CustomerArrayList);
                        Oo.close();

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
