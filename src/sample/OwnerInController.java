package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OwnerInController implements Initializable {


    @FXML public ComboBox<String> type,unit_type;
    @FXML public ComboBox<String> name;
    @FXML public TextField quantity,unit_price;
    public Button updateButton,OrderCheckButton,StockButton;
    public Label FootLabel,ItemshowLabel,CaptionLabel;

    public static ArrayList<product> item=new ArrayList<>();
    private ArrayList<String> types=new ArrayList<>();
    private ArrayList<String>  unit_types=new ArrayList<>();


   /* @FXML public void nextpagebuttonClicked(ActionEvent event) throws Exception{
        //Parent shoppage= FXMLLoader.load(getClass().getResource("ShopTypesView.fxml"));
        //Common.ButtonClicked(event,shoppage);
    }*/

   /* public void MoreItemButtonClicked(){
        try{
            ArrayList<product> temp=null;
            String s=type.getSelectionModel().getSelectedItem();
            String t=name.getSelectionModel().getSelectedItem();
            if(s.equalsIgnoreCase("Fruits")) {
                temp = fruit_items;
            }
            else if(s.equalsIgnoreCase("Vegetables")){
                temp=veg_items;
            }
            else if(s.equalsIgnoreCase("Meat and Fish")){
                temp=mf_items;
            }
            else if(s.equalsIgnoreCase("Staples")){
                temp=staple_items;
            }
            for(int i=0;i<temp.size();i++){
                if(temp.get(i).getName().equalsIgnoreCase(t)){
                    temp.get(i).setPrice(Integer.parseInt(unit_price.getText()));
                    temp.get(i).setUnit_type(unit_type.getSelectionModel().getSelectedItem());
                    temp.get(i).add_available_units(Integer.parseInt(quantity.getText()));
                    System.out.println(temp.get(i));
                    break;
                }
            }
        type.getSelectionModel().clearSelection();
        unit_type.getSelectionModel().clearSelection();
        name.getSelectionModel().clearSelection();
            unit_price.setText("");
            quantity.setText("");
        }catch (Exception ex)
        {
            FootLabel.setText("You've left an option empty!");
        }

    }*/




    public void productTypeSelected(){
        try{
            String s=type.getSelectionModel().getSelectedItem();
            if(s!=null) {
                File file = new File(s.toLowerCase() + ".txt");
                item = Common.ownerFileInput(file);
                name.getItems().clear();

                for (product i : item) {
                    name.getItems().add(i.getName());
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        name.setDisable(false);
        ItemshowLabel.setText("");
        name.setValue(null);


    }

    public void ItemTypeSelected() throws Exception{
        String s=name.getSelectionModel().getSelectedItem();
        if(s!=null){
            for(product i:item){
                if(i.getName().equals(s)){
                    String text="Product name: "+s+"\nAvailable Units: "+i.getAvailable_units()+"\nPrice: "+i.getPrice()+" tk per "+i.getUnit_type();
                    ItemshowLabel.setText(text);
                    break;
                }
            }
        }

    }

    public void updateButtonClicked()  {
        int valid=1;
        boolean validQ=false;
        try{
            String s=type.getSelectionModel().getSelectedItem();
            String t=name.getSelectionModel().getSelectedItem();
            Double unit=Double.parseDouble(quantity.getText());
            Double price=Double.parseDouble(unit_price.getText());
            String type=unit_type.getSelectionModel().getSelectedItem();
            System.out.println(s+" "+t+" "+unit+" "+price+" "+" "+type);
            if(s.equals(null)||t.equals(null)||type.equals(null)) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No option can be empty !");
                alert.showAndWait();
                valid=0;
            }

        } catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No option can be empty !");
            alert.showAndWait();
            valid=0;
        }
        if (valid == 1) {

            try {
                String s = type.getSelectionModel().getSelectedItem();
                String t = name.getSelectionModel().getSelectedItem();
                int k = 0;
                for (int i = 0; i < item.size(); i++) {
                    if (item.get(i).getName().equalsIgnoreCase(t)) {
                        k = 1;
                        item.get(i).setPrice(Double.parseDouble(unit_price.getText()));
                        item.get(i).setUnit_type(unit_type.getSelectionModel().getSelectedItem());
                        int itemQ=Integer.parseInt(quantity.getText());
                        if(item.get(i).getAvailable_units()+itemQ>=1){
                            item.get(i).add_available_units(itemQ);
                            System.out.println("Available  "+itemQ);
                            validQ=true;
                        }else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText(null);
                            alert.setContentText("You cannot make the unit Zero or Lower. To remove an item, you can go to the  stock and delete it.");
                            alert.showAndWait();
                            validQ=false;
                        }
                        break;
                    }
                }
                System.out.println("1");
             if (validQ)
             {
                 if (k == 0) {
                     item.add(new product(t, s, Double.parseDouble(unit_price.getText()),
                             Integer.parseInt(quantity.getText()), unit_type.getSelectionModel().getSelectedItem()));
                 }
                 Common.fileupdate(new File(s.toLowerCase() + ".txt"), item);
                 int check = 0;
                 for (String temp : types) {
                     if (temp.equals(s)) {
                         check = 1;
                         break;
                     }
                 }
                 System.out.println("11111");
                 if (check == 0) {
                     types.add(s);
                     File typefile = new File("type list");
                     try {
                         FileOutputStream fo = new FileOutputStream(typefile);
                         ObjectOutputStream oo = new ObjectOutputStream(fo);
                         oo.writeObject(types);
                         oo.close();
                         fo.close();
                     } catch (FileNotFoundException ff) {
                         System.out.println(ff);
                     } catch (IOException io) {
                         System.out.println(io);
                     }
                 }
                 System.out.println("111");
                 int unitcheck = 0;
                 for (String i : unit_types) {
                     if (i.equals(unit_type.getSelectionModel().getSelectedItem())) {
                         unitcheck = 1;
                         break;
                     }
                 }
                 System.out.println("11");
                 if (unitcheck == 0 && !(unit_type.getSelectionModel().getSelectedItem().equals(null))) {
                     unit_types.add(unit_type.getSelectionModel().getSelectedItem());
                     File typefile = new File("Unit type list");
                     try {
                         FileOutputStream fo = new FileOutputStream(typefile);
                         ObjectOutputStream oo = new ObjectOutputStream(fo);
                         oo.writeObject(unit_types);
                         oo.close();
                         fo.close();
                     } catch (FileNotFoundException ff) {
                         System.out.println(ff);
                     } catch (IOException io) {
                         System.out.println(io);
                     }
                     unit_type.getItems().clear();
                     unit_type.getItems().addAll(unit_types);
                 }
                 System.out.println("5");
                 //FootLabel.setText("Updated ! ! !");

                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setContentText("Stock Updated !");
                 alert.showAndWait();

                 type.getSelectionModel().clearSelection();
                 unit_type.getSelectionModel().clearSelection();
                 name.getSelectionModel().clearSelection();
                 unit_price.setText("");
                 quantity.setText("");
                 ItemshowLabel.setText("");
                 type.getItems().clear();
                 name.getItems().clear();
                 type.getItems().addAll(types);
                 type.setValue(null);
                 name.setValue(null);
                 unit_type.setValue(null);
             }else {

             }
            } catch (Exception ex) {

           /* Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No option can be empty !");
            alert.showAndWait();*/
                // FootLabel.setText("You've left an option empty!");
            }
        }
    }






    public void HomeButtonClicked(ActionEvent e) throws IOException {
        Parent page= FXMLLoader.load(getClass().getResource("FirstPage.fxml"));
        Common.ButtonClicked(e,page);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        types =Common.OwnerFile(new File("type list"));
        type.getItems().addAll(types);

        unit_types=Common.OwnerFile(new File("Unit type list"));
        unit_type.getItems().addAll(unit_types);


        CaptionLabel.setStyle("-fx-background-color: fce28c;"+"-fx-border-color: ORANGE");
        // ArrayList<product> MF=new ArrayList<>();
        /*MF.add(new product("Onion","Vegetable"));
        MF.add(new product("Tomato","Vegetable"));
        MF.add(new product("Potato","Vegetable"));
        MF.add(new product("Chilli", "Vegetable"));
        MF.add(new product("Beans","Vegetable"));*/



        /*MF.add(new product("Chompa Banana","Fruit"));
        MF.add(new product("Green Apples","Fruit"));
        MF.add(new product("Orange","Fruit"));
        MF.add(new product("Green Grapes", "Fruit"));
        MF.add(new product("Coconut","Fruit"));*/


        /*MF.add(new product("Rice","Staple"));
        MF.add(new product("Dal","Staple"));
        MF.add(new product("Salt","Staple"));
        MF.add(new product("Oil", "Staple"));
        MF.add(new product("Turmeric Powder","Staple"));*/


        /*MF.add(new product("Rui Fish","Fish"));
        MF.add(new product("Prawn","Fish"));*/
       /*MF.add(new product("Broiler Chicken","Meat"));
        MF.add(new product("Beef", "Meat"));
        MF.add(new product("Mutton","Meat"));*/


       /*try {
            FileOutputStream fo = new FileOutputStream(new File("meat.txt"));
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            for (product i : MF) {
                oo.writeObject(i);
            }
            oo.close();
            fo.close();
        }catch(FileNotFoundException ff){
            System.out.println( ff);
        }catch(IOException io){
            System.out.println(io);
        }*/






        //////unit type list file created

       /* ArrayList<String> unit_types=new ArrayList<>();
        unit_types.add("pcs");
        unit_types.add(" kg");
        unit_types.add("litre");

       try {
            FileOutputStream fo=new FileOutputStream("Unit type list",true);
            ObjectOutputStream Oo=new ObjectOutputStream(fo);
            Oo.writeObject(unit_types);

            //FootLabel.setText("Successfully Updated");

            Oo.close();
            fo.close();
        }catch (Exception e){
           System.out.println(e);
        }*/

        /////unit type list file creation closed


        ////type list file created

       /* ArrayList<String> types=new ArrayList<>();

       types.add("Staple");
        types.add("Vegetable");
        types.add("Meat");
        types.add("Fruit");
        types.add("Fish");
        type.getItems().addAll(types);

       try {
            FileOutputStream fo=new FileOutputStream("type list");
            ObjectOutputStream Oo=new ObjectOutputStream(fo);
            Oo.writeObject(types);

            //FootLabel.setText("Successfully Updated");

            Oo.close();
            fo.close();
        }catch (Exception e){
           System.out.println(e);
        }*/


    }
}
