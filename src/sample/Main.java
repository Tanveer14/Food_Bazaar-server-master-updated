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
        try {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Object ob=is.readObject();

                    if(ob.getClass().getName().equals("java.lang.String"))
                    {
                        type=(String)ob;
                        System.out.println(type);
                        ArrayList<product> senttoClient=Common.setSceneforNetworking(type);
                        os.writeObject(senttoClient);

                    }
                    else {
                        FileOutputStream fo=new FileOutputStream(file,true);
                        ObjectOutputStream Oo=new ObjectOutputStream(fo);
                        customer= (Customer) ob;
                        System.out.println(customer);
                        Oo.writeObject(customer);
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
