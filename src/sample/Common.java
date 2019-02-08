package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;


//import java.awt.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Common {

    public static void ButtonClicked(ActionEvent event,Parent page){
        Scene View=new Scene(page);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(View);
        window.show();

    }
    public static void ButtonClicked(MouseEvent event, Parent page){
        Scene View=new Scene(page);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(View);
        window.show();

    }

    public static void fileupdate(File file,ArrayList<product> t){
        try {
            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            for (product i : t) {
                oo.writeObject(i);
            }
            oo.close();
            fo.close();
        }catch(FileNotFoundException ff){
            System.out.println(ff);
        }catch(IOException io){
            System.out.println(io);
        }
    }


    public static ArrayList<product> ownerFileInput(File file){
        FileInputStream fi;
        ObjectInputStream ob;
        ArrayList<product> type=new ArrayList<>();
        try{
            fi=new FileInputStream(file);
            ob=new ObjectInputStream(fi);
            try{
                while(true){
                    product f=(product)ob.readObject();
                    type.add(f);
                }
            }catch(EOFException ex){

            }
            ob.close();
            fi.close();
        }catch(FileNotFoundException ex){
            System.out.println("new type as file is not found");
        }catch(IOException el){
            System.out.println(el);
        }catch(ClassNotFoundException cl){
            System.out.println(cl);
        }


        return type;
    }


   /* public static ArrayList<Item> fileinput(File file){
        FileInputStream fi;
        ObjectInputStream ob;
        ArrayList<Item> type=new ArrayList<>();
        try{

            fi=new FileInputStream(file);
            ob=new ObjectInputStream(fi);
            try{
                while(true){
                    Item f=(Item)ob.readObject();
                    type.add(f);
                }
            }catch(EOFException ex){

            }
            ob.close();
            fi.close();
        }catch(FileNotFoundException ex){
            System.out.println(ex);
        }catch(IOException el){
            System.out.println(el);
        }catch(ClassNotFoundException cl){
            System.out.println(cl);
        }

        return type;
    }*/

    public static ArrayList<product> setSceneforNetworking(String strtemp){
        File file=new File(strtemp.toLowerCase()+".txt");
        ArrayList<product> temporary=Common.ownerFileInput(file);
        return temporary;
    }

    public static ArrayList<String> OwnerFile( File file)
    {
        ArrayList<String> ar=new ArrayList<>();
        try {
            FileInputStream fi=new FileInputStream(file);
            ObjectInputStream oi=new ObjectInputStream(fi);
            ar= (ArrayList<String>) oi.readObject();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ar;
    }


    public static ArrayList<String> choices(){
        ArrayList<String> choice=new ArrayList<>();
        choice.add("1");
        choice.add("2");
        choice.add("3");
        choice.add("4");
        choice.add("5");
        return choice;
    }

   /* public static void TreeViewSelect(TreeView FoodTree)
    {
        if(FoodTree.getSelectionModel().getSelectedItem()=="Vegetables") FoodTree.setOnMouseClicked(e->{
            Parent newsceneparent= FXMLLoader.load(getClass().getResource("LogInPage.fxml"));
            ButtonClicked(e,);
        });
            if(FoodTree.getSelectionModel().getSelectedItem()=="Fish N Meat")
                if(FoodTree.getSelectionModel().getSelectedItem()=="Fruits")
                    if(FoodTree.getSelectionModel().getSelectedItem()=="Staples")

    }*/


    /*public static void AddToTable(Label label,Label TypeLabel,Label totalPriceLabel, ComboBox unit, TableView FoodTable, ObservableList<product> productList)
    {


        String labeltext=label.getText();
        String []labelInfo=labeltext.split("\n");
        product product=new product();
        product.setType(TypeLabel.getText());
        // Item i=new Item();
        product.setName(labelInfo[0]);
        String []lastLabel=labelInfo[1].split(" ");
        String []lastlabel1=labelInfo[2].split(" ");
        String unitText=(String)unit.getValue();
        double unitVal=Double.parseDouble(unitText);
        System.out.println(unitVal+" "+Double.parseDouble(lastLabel[0]));
        product.setUnit(unitVal);
        product.setPrice(Double.parseDouble(lastLabel[0])*product.getUnit());
        product.setUnit_type(lastlabel1[1]);
        CommonTypeViewController.totalPrice+=product.getPrice();
        totalPriceLabel.setText(String.valueOf(CommonTypeViewController.totalPrice));
        TableViewControl.addItem(FoodTable,product,productList);
        unit.getSelectionModel().clearSelection();
    }*/

}
