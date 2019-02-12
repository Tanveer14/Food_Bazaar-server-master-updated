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
    @FXML private ComboBox<String> OutOfStockBox;
    @FXML public TextField quantity,unit_price;
    public Button updateButton,HomeButton;
    public Label FootLabel,ItemshowLabel,CaptionLabel;

    public static ArrayList<product> item=new ArrayList<>();
    private ArrayList<String> types=new ArrayList<>();
    private ArrayList<String>  unit_types=new ArrayList<>();
    private ArrayList<product> outOfStockProduct=new ArrayList<>();


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

                File file2=new File(s.toLowerCase()+"outOfStock.txt");
                outOfStockProduct=Common.ownerFileInput(file2);
                OutOfStockBox.getItems().clear();
                for(product i:outOfStockProduct){
                    OutOfStockBox.getItems().add(i.getName());
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        name.setDisable(false);
        OutOfStockBox.setDisable(false);
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
        boolean validQ=true,validQ2=true;
        try{
            String s=type.getSelectionModel().getSelectedItem();
            String t=name.getSelectionModel().getSelectedItem();
            Double unit=Double.parseDouble(quantity.getText());
            Double price=Double.parseDouble(unit_price.getText());
            String type=unit_type.getSelectionModel().getSelectedItem();
            if(s.equals(null)||t.equals(null)||type.equals(null)||price<=0) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Input !");
                alert.showAndWait();
                validQ2=false;
                valid=0;
            }

        } catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
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
                        int itemQ = 0;
                        try {
                            itemQ=Integer.parseInt(quantity.getText());
                        }catch (Exception e){
                            itemQ= (int) Double.parseDouble(quantity.getText());
                        }
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
             if (validQ)
             {
                 if(k==0&&Integer.parseInt(quantity.getText())<=0){
                     validQ2=false;
                     Alert alert=new Alert(Alert.AlertType.ERROR);
                     alert.setHeaderText(null);
                     alert.setContentText("Item quantity cannot be zero or negative .");
                     alert.showAndWait();
                 }
                 if (k == 0&&Integer.parseInt(quantity.getText())>0) {
                     item.add(new product(t, s, Double.parseDouble(unit_price.getText()),
                             Integer.parseInt(quantity.getText()), unit_type.getSelectionModel().getSelectedItem()));
                     ArrayList<product> endproducts=new ArrayList<>();
                     endproducts=Common.ownerFileInput(new File(s.toLowerCase()+"outOfStock.txt"));
                     for(int i=0;i<endproducts.size();i++){
                         if(endproducts.get(i).getName().equalsIgnoreCase(t)){
                             endproducts.remove(i);
                             Common.fileupdate(new File(s.toLowerCase()+"outOfStock.txt"),endproducts);
                             break;
                         }
                     }


                 }
                 Common.fileupdate(new File(s.toLowerCase() + ".txt"), item);
                 int check = 0;
                 for (String temp : types) {
                     if (temp.equals(s)) {
                         check = 1;
                         break;
                     }
                 }
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
                 int unitcheck = 0;
                 for (String i : unit_types) {
                     if (i.equals(unit_type.getSelectionModel().getSelectedItem())) {
                         unitcheck = 1;
                         break;
                     }
                 }
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
                 //FootLabel.setText("Updated ! ! !");
                 if(validQ2)
                 {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setHeaderText(null);
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
                     name.setDisable(true);
                     type.getItems().addAll(types);
                     type.setValue(null);
                     name.setValue(null);
                     unit_type.setValue(null);
                     OutOfStockBox.getItems().clear();
                     OutOfStockBox.setDisable(true);
                 }
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


        CaptionLabel.setStyle("-fx-background-color: #232020;"+"-fx-border-color: ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
        updateButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color: ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
        HomeButton.setStyle("-fx-background-color: #232020;"+"-fx-border-color: ORANGE;"+"-fx-background-radius: 3;"+"-fx-border-radius: 3;");
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
