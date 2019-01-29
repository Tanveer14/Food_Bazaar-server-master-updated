package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CommonTypeViewController implements Initializable {
    public static String Currentpage=null;
    @FXML ComboBox Unit1,Unit2,Unit3,Unit4,Unit5;
    @FXML Label Label1,Label2,Label3,Label4,Label5,caption;
    @FXML Label TotalPriceValue;
    @FXML Button button1,button2,button3,button4,button5;
    @FXML VBox vbox1,vbox2,vbox3,vbox4,vbox5;
    ObservableList<product> productList= FXCollections.observableArrayList();
    TextFieldTableCell TextFieldTableCell=new TextFieldTableCell();
    TextFieldTableCell TextFieldTableCell2=new TextFieldTableCell();
    @FXML Button nextButton;
    @FXML Button previousButton,DeleteItemButton;
    @FXML TreeView<String> FoodTree;
    @FXML TableView<product> FoodTable;
    @FXML TableColumn<product,String> NameColumn;
    @FXML TableColumn<product,Double> UnitColumn;
    @FXML TableColumn<product,Double> PriceColumn;
    @FXML TableColumn<product,String> TypeColumn;
    @FXML TableColumn<product,String> UnitTypeColumn;

    public static double totalPrice=0;
    private int k,count;
    ArrayList<product> temp=new ArrayList<>();
    public static ArrayList<product> TableProductList=new ArrayList<>();

    public void ConfirmButtonClicked(ActionEvent e) throws IOException {
        Currentpage=caption.getText();

        product demoproduct;
        for(int i=0;i<FoodTable.getItems().size();i++){
            demoproduct=FoodTable.getItems().get(i);
            TableProductList.add(demoproduct);
            System.out.println(TableProductList.get(i));
        }

        Parent newsceneparent= FXMLLoader.load(getClass().getResource("LogInPage.fxml"));
        Common.ButtonClicked(e,newsceneparent);
    }


    public void getbackButtonClicked(ActionEvent event) throws Exception{
        Parent page= FXMLLoader.load(getClass().getResource("ShopTypesView.fxml"));
        Common.ButtonClicked(event,page);
    }

    public void AddToCartButton1Clicked()
    {
        if(Unit1.getValue()==null) return;
        if((temp.get(k-count).getAvailable_units()-Double.parseDouble(String.valueOf(Unit1.getValue())))>=0){
            try {
                Common.AddToTable(Label1,caption,TotalPriceValue, Unit1, FoodTable, productList);
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
        Unit1.getSelectionModel().clearSelection();

    }
    public void AddToCartButton2Clicked() {
        if(Unit2.getValue()==null) return;
        if ((temp.get(k - count+1).getAvailable_units() - Double.parseDouble(String.valueOf(Unit2.getValue()))) >= 0) {
            try {
                Common.AddToTable(Label2,caption,TotalPriceValue, Unit2, FoodTable, productList);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Unit2.getSelectionModel().clearSelection();
    }
    public void AddToCartButton3Clicked()
    {
        if(Unit3.getValue()==null) return;
        if((temp.get(k-count+2).getAvailable_units()-Double.parseDouble(String.valueOf(Unit3.getValue())))>=0){
            try {
                Common.AddToTable(Label3,caption,TotalPriceValue, Unit3, FoodTable, productList);
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
        Unit3.getSelectionModel().clearSelection();
    }

    public void AddToCartButton4Clicked() {
        if(Unit4.getValue()==null) return;
        if ((temp.get(k -count+3).getAvailable_units() - Double.parseDouble(String.valueOf(Unit4.getValue()))) >= 0) {
            try {

                Common.AddToTable(Label4,caption,TotalPriceValue, Unit4, FoodTable, productList);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Unit4.getSelectionModel().clearSelection();
    }
    public void AddToCartButton5Clicked() {
        if(Unit5.getValue()==null) return;
        if ((temp.get(k -count+ 4).getAvailable_units() - Double.parseDouble(String.valueOf(Unit5.getValue()))) >= 0) {
            try {
                Common.AddToTable(Label5,caption,TotalPriceValue, Unit5, FoodTable, productList);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Unit5.getSelectionModel().clearSelection();
    }

    @FXML
    public void TableUnitEdit(TableColumn.CellEditEvent<product,Double> e){

        productList.addAll(FoodTable.getItems());
        double oldUnit=e.getOldValue();
        double newUnit=e.getNewValue();
        double oldPrice=FoodTable.getSelectionModel().getSelectedItem().getPrice();
        double newPrice=oldPrice/oldUnit*newUnit;
        FoodTable.getItems().get(e.getTablePosition().getRow()).setUnit(newUnit);
        //FoodTable.getItems().get(FoodTable.getSelectionModel().getFocusedIndex()).setUnit(newUnit);
        int index=FoodTable.getSelectionModel().getSelectedIndex();
        /*for (product i:productList
             ) {
            System.out.println(i);
        }*/
        productList.get(index).setPrice(newPrice);
        FoodTable.getItems().clear();
        FoodTable.getItems().addAll(productList);





    }


    public void DeleteButtonClicked()
    {
        ObservableList<product> allProducts,productSelected;
        allProducts = FoodTable.getItems();
        productSelected = FoodTable.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
        totalPrice=0;
        for (product p:FoodTable.getItems()
        ) {

            totalPrice+=p.getPrice();
        }
        TotalPriceValue.setText(String.valueOf(totalPrice));
    }

    public void nextButtonClicked() throws Exception{
        previousButton.setVisible(true);
        int l=setLabelText();
        if(l==0) nextButton.setVisible(false);
    }

    public void previousButtonClicked() throws Exception{
        k=k-count-5;
        nextButton.setVisible(true);
        if(k==0) previousButton.setVisible(false);
        int l=setLabelText();

    }

    public void TreeViewClicked()
    {

        String strTemp= FoodTree.getSelectionModel().getSelectedItem().getValue();
        nextButton.setVisible(false);
        previousButton.setVisible(false);
        k=0;
        setScene(strTemp);
    }



    private int setLabelText(){
        vbox1.setVisible(false);
        vbox2.setVisible(false);
        vbox3.setVisible(false);
        vbox4.setVisible(false);
        vbox5.setVisible(false);
        Label1.setText("");
        Label2.setText("");
        Label3.setText("");
        Label4.setText("");
        Label5.setText("");
        count=0;
        Label1.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\nper "+temp.get(k).getUnit_type());
        vbox1.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label2.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\nper "+temp.get(k).getUnit_type());
        vbox2.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label3.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\nper "+temp.get(k).getUnit_type());
        vbox3.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label4.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\nper "+temp.get(k).getUnit_type());
        vbox4.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label5.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\nper "+temp.get(k).getUnit_type());
        vbox5.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        return 1;
    }



    public void setScene(String strtemp)
    {
        File file=new File(strtemp.toLowerCase()+".txt");
        temp=Common.ownerFileInput(file);
        caption.setText(strtemp);
        k=0;
        int l=setLabelText();
        if(l==1){
            nextButton.setVisible(true);
        }
        try {
            setTreeview(FoodTree);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setTreeview(TreeView<String> treeview) throws IOException, ClassNotFoundException {

        TreeItem<String> foods=new TreeItem<>("Food Items");
        treeview.setRoot(foods);
        ArrayList<TreeItem<String>>treeItems=new ArrayList<>();

        FileInputStream fin=new FileInputStream("type list");
        ObjectInputStream oin=new ObjectInputStream(fin);
        ArrayList<String> temp=new ArrayList<>();
        temp= (ArrayList<String>) oin.readObject();
        int i=0;
        while (true){
            String itemtext=temp.get(i);
            treeItems.add(new TreeItem<>(itemtext));
            i++;
            if(i>itemtext.length())break;

        }
        foods.getChildren().addAll(treeItems);
        foods.setExpanded(true);

        // tree=treeview;


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        UnitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        UnitTypeColumn.setCellValueFactory(new PropertyValueFactory<>("unit_type"));
        TableViewControl.InitTable(FoodTable,productList);
        FoodTable.setEditable(true);
        UnitColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        PriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));


        ArrayList<String> choice=Common.choices();
        Unit1.getItems().addAll(choice);
        Unit2.getItems().addAll(choice);
        Unit3.getItems().addAll(choice);
        Unit4.getItems().addAll(choice);
        Unit5.getItems().addAll(choice);

        String strtemp;
        try{
            strtemp=ShopTypesViewController.SelectedType;
        }catch(Exception ex){
            strtemp=LogInPage.gobackPage;
        }
        setScene(strtemp);

        try {
            setTreeview(FoodTree);
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
