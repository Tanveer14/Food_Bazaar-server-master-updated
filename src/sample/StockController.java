package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    public static String Currentpage=null;
    @FXML Label Label1,Label2,Label3,Label4,Label5,caption;
    @FXML Button button1,button2,button3,button4,button5;
    @FXML VBox vbox1,vbox2,vbox3,vbox4,vbox5;
    @FXML Button nextButton;
    @FXML Button previousButton;

    ArrayList<String> types;
    @FXML TreeView<String> FoodTree;
    private int k,count;
    ArrayList<product> temp=new ArrayList<>();

    public void button1Clicked()
    {

    }

    public void button2Clicked()
    {

    }

    public void button3Clicked()
    {

    }

    public void button4Clicked()
    {

    }

    public void button5Clicked()
    {

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

        try {
            String strTemp= FoodTree.getSelectionModel().getSelectedItem().getValue();
            nextButton.setVisible(false);
            previousButton.setVisible(false);
            k=0;
            if(!strTemp.equals("Food Items"))setScene(strTemp);
        }catch (Exception ne){

        }
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
        temp=Common.setSceneforNetworking(strtemp);

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

        types=Common.OwnerFile(new File("type list"));
        int i=0;
        while (true){
            String itemtext=types.get(i);
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

        try {
            setTreeview(FoodTree);
        }catch(Exception e){
            System.out.println(e);
        }
        String strtemp=types.get(0);
        setScene(strtemp);
    }
}
