package sample;

/*import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;*/


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;


public class TableViewControl {


    public static ObservableList<product> ItemList= FXCollections.observableArrayList();

    public static void addItem(TableView foodTable, product product,ObservableList<product> productList)
    {
        int temp=0;
        productList=ItemList;
        //itemList.add(item);
        try {
            for (product i: ItemList){
                if(i.getName()==product.getName()){
                    i.setUnit(i.getUnit()+product.getUnit());
                    i.setPrice(i.getPrice()+product.getPrice());
                    temp=1;
                    //System.out.println(1);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        if(temp==0)foodTable.getItems().add(product);
    }

    public static void InitTable(TableView<product> table, ObservableList<product> productList)
    {
        productList=ItemList;
        table.setItems(productList);

    }



}
