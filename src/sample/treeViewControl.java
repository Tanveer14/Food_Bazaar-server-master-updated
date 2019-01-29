package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class treeViewControl {
    @FXML
    //TreeView<String> treeview;



    public static void setTreeview(TreeView<String> treeview) {

        TreeItem<String> foods=new TreeItem<>("Food Items");
        treeview.setRoot(foods);
        TreeItem<String> Vegitables=new TreeItem<>("Vegetables");
        TreeItem<String> Onion=new TreeItem<>("Onion");
        TreeItem<String> Tomato=new TreeItem<>("Red Tomato");
        TreeItem<String> Potato=new TreeItem<>("Potato");
        TreeItem<String> Beans=new TreeItem<>("Flat Beans");
        TreeItem<String> Chili=new TreeItem<>("Green Chili");
        //Vegitables.getChildren().addAll(Onion,Tomato,Potato,Chili,Beans);


        TreeItem<String> FishMeat=new TreeItem<>("Meat & Fish");
        TreeItem<String> Rui=new TreeItem<>("Rui Fish");
        TreeItem<String> Prawn=new TreeItem<>("Prawn(Medium)");
        TreeItem<String> Chicken=new TreeItem<>("Broiler Chicken");
        TreeItem<String> Beef=new TreeItem<>("Beef(Boneless)");
        TreeItem<String> Mutton=new TreeItem<>("Mutton");
       // FishMeat.getChildren().addAll(Rui,Prawn,Chicken,Beef,Mutton);

        TreeItem<String> Fruits=new TreeItem<>("Fruits");
        TreeItem<String> Banana=new TreeItem<>("Chompa Banana");
        TreeItem<String> Orange=new TreeItem<>("Orange");
        TreeItem<String> Coconut=new TreeItem<>("Coconut");
        TreeItem<String> Apple=new TreeItem<>("Green Apples");
        TreeItem<String> Grapes=new TreeItem<>("Green Grapes");
       // Fruits.getChildren().addAll(Banana,Apple,Orange,Grapes,Coconut);

        TreeItem<String> Staples=new TreeItem<>("Staples");
        TreeItem<String> Rice=new TreeItem<>("Miniket Rice");
        TreeItem<String> Salt=new TreeItem<>("ACI Pure Salt");
        TreeItem<String> Dal=new TreeItem<>("Moshur Dal");
        TreeItem<String> SoyaBeanOil=new TreeItem<>("Soyabean Oil");
        TreeItem<String> TurmericPowder=new TreeItem<>("Turmeric Powder");
        //Staples.getChildren().addAll(Rice,Dal,Salt,SoyaBeanOil,TurmericPowder);

        foods.getChildren().addAll(Vegitables,FishMeat,Fruits,Staples);
        foods.setExpanded(true);

       // tree=treeview;


    }
}
