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

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    public static String Currentpage=null;
    @FXML Label Label1,Label2,Label3,Label4,Label5,Label6,caption;
    @FXML Button button1,button2,button3,button4,button5,button6;
    @FXML VBox vbox1,vbox2,vbox3,vbox4,vbox5,vbox6;
    @FXML Button nextButton;
    @FXML private Button previousButton,goBackButton,DeleteTypeButton;

    ArrayList<String> types;
    @FXML TreeView<String> FoodTree;
    private int k,count;
    ArrayList<product> temp=new ArrayList<>();

    public void setAlert(String s){
        String typeofDeleteItem=null;
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove "+s+" from your stock?");
        Optional<ButtonType> action=alert.showAndWait();
        if(action.get()==ButtonType.OK){
            for(int i=0;i<temp.size();i++){
                if(temp.get(i).getName().equals(s)){
                    typeofDeleteItem=temp.get(i).getType();
                    temp.remove(i);
                    break;
                }
            }
            System.out.println(typeofDeleteItem.toLowerCase() +
                    " available ones\n"+temp);
            Common.fileupdate(new File(typeofDeleteItem.toLowerCase()+".txt"),temp);
            setScene(typeofDeleteItem);

        }
    }

    public void DeleteTypeButtonClicked(){
        String typeofDeleteType=caption.getText();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("You are attempting to remove "+typeofDeleteType+" food type along with its food items."+
                "\nAre you sure you want to delete it?\nOnce deleted , no items of this foodtype will be available.");
        Optional<ButtonType> action=alert.showAndWait();
        if(action.get()==ButtonType.OK){
            for(int i=0;i<types.size();i++){
                if(types.get(i).equalsIgnoreCase(typeofDeleteType)){
                    types.remove(i);
                    break;
                }
            }
            File typefile=new File("type list");
            try {
                FileOutputStream fo = new FileOutputStream(typefile);
                ObjectOutputStream oo = new ObjectOutputStream(fo);
                oo.writeObject(types);
                oo.close();
                fo.close();
            }catch(FileNotFoundException ff){
                System.out.println( ff);
            }catch(IOException io){
                System.out.println(io);
            }
            File deleteFile=new File(typeofDeleteType.toLowerCase()+".txt");
            File deleteStockout=new File(typeofDeleteType.toLowerCase()+"outOfStock.txt");
            deleteStockout.delete();
            deleteFile.delete();
            System.out.println("file deleted");
            String strtemp=types.get(0);
            setScene(strtemp);

        }
    }

    public void button1Clicked()
    {
        String item=Label1.getText();

        String[] neededItem=item.split("\n");
        setAlert(neededItem[0]);
    }

    public void button2Clicked()
    {
        String item=Label2.getText();
        String[] neededItem=item.split("\n");
        setAlert(neededItem[0]);
    }

    public void button3Clicked()
    {
        String item=Label3.getText();
        String[] neededItem=item.split("\n");
        setAlert(neededItem[0]);
    }

    public void button4Clicked()
    {
        String item=Label4.getText();
        String[] neededItem=item.split("\n");
        setAlert(neededItem[0]);
    }

    public void button5Clicked()
    {
        String item=Label5.getText();
        String[] neededItem=item.split("\n");
        setAlert(neededItem[0]);
    }

    public void button6Clicked()
    {
        String item=Label6.getText();
        String[] neededItem=item.split("\n");
        setAlert(neededItem[0]);
    }

    public void goBackButtonClicked(ActionEvent e) throws Exception{
        Parent page=FXMLLoader.load(getClass().getResource("FirstPage.fxml"));
        Common.ButtonClicked(e,page);
    }

    public void nextButtonClicked() throws Exception{
        previousButton.setVisible(true);
        int l=setLabelText();
        if(l==0) nextButton.setVisible(false);
    }

    public void previousButtonClicked() throws Exception{
        k=k-count-6;
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
        vbox6.setVisible(false);
        Label1.setText("");
        Label2.setText("");
        Label3.setText("");
        Label4.setText("");
        Label5.setText("");
        Label6.setText("");
        count=0;
        if(k==temp.size()) return 0;
         Label1.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\tper "+temp.get(k).getUnit_type()+"\nAvailable: "+temp.get(k).getAvailable_units()+" "+temp.get(k).getUnit_type());
        vbox1.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label2.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\tper "+temp.get(k).getUnit_type()+"\nAvailable: "+temp.get(k).getAvailable_units()+" "+temp.get(k).getUnit_type());
        vbox2.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label3.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\tper "+temp.get(k).getUnit_type()+"\nAvailable: "+temp.get(k).getAvailable_units()+" "+temp.get(k).getUnit_type());
        vbox3.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label4.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\tper "+temp.get(k).getUnit_type()+"\nAvailable: "+temp.get(k).getAvailable_units()+" "+temp.get(k).getUnit_type());
        vbox4.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;
        Label5.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\tper "+temp.get(k).getUnit_type()+"\nAvailable: "+temp.get(k).getAvailable_units()+" "+temp.get(k).getUnit_type());
        vbox5.setVisible(true);
        k++;
        count++;
        if(k==temp.size()) return 0;

        Label6.setText(temp.get(k).getName()+"\n"+temp.get(k).getPrice()+" tk\tper "+temp.get(k).getUnit_type()+"\nAvailable: "+temp.get(k).getAvailable_units()+" "+temp.get(k).getUnit_type());
        vbox6.setVisible(true);
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
            try{
                String itemtext=types.get(i);
                treeItems.add(new TreeItem<>(itemtext));
                i++;
            }catch (Exception e)
            {
                break;
            }


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

        FoodTree.setStyle("-fx-text-fill: ORANGE;"+"-fx-background-color: #232020;"+"-fx-border-color: ORANGE;");
        String strtemp=types.get(0);
        setScene(strtemp);
    }
}
