package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LogInPage implements Initializable{
    @FXML private TextField CustomerName;
    @FXML private TextField CustomerMail;
    @FXML private TextField CustomerPhone;
    @FXML private TextField CustomerAddress;
    @FXML private Button OrderButton;
    @FXML private Button goBackButton;
    @FXML private TextArea CommentBox;
    @FXML private Label Labelcheck;


    public static String ConfirmationMessage=new String();
    public static String gobackPage;
    public static int customercount=0;

    public Customer customer=new Customer();
    public void setCustomerName() {}
    public void setCustomerMail() {}
    public void setCustomerPhone() {}
    public void setCustomerAddress() {}

    public void goBackButtonClicked(ActionEvent event) throws Exception{
        Parent subPage= FXMLLoader.load(getClass().getResource("CommonTypeView.fxml"));
        Common.ButtonClicked(event,subPage);
    }

    public void OrderButtonClicked (ActionEvent e) throws Exception {
        customer.setName(CustomerName.getText());
        customer.setMail(CustomerMail.getText());
        customer.setContactNo(CustomerPhone.getText());
        customer.setAddress(CustomerAddress.getText());
        customer.setProductList(CommonTypeViewController.TableProductList);
        customer.setTotalPrice(CommonTypeViewController.totalPrice);
        //customer.setArea((String) CustomerArea.getValue());
        System.out.println(customer);

        int temp=0;
        if(customer.getName().equals("")){
            temp=1;
        }
        if(customer.getMail().equals("")){
            temp=1;
        }
        if(customer.getContactNo().equals("")){
            temp=1;
        }
        if(customer.getAddress().equals("")){
            temp=1;
        }
        if(customer.getProductList().size()==0){
            temp=1;
        }

        //modifying file available units
        if(temp==0) {
            Labelcheck.setText("");
            String temptype;
            for (int i = 0; i < customer.ProductList.size(); i++) {
                temptype = customer.getProductList().get(i).getType();
            }
            customercount++;

            ConfirmationMessage = "Customer Id:\t" + customercount + "\n";
            for (product p : customer.ProductList
            ) {
                ConfirmationMessage = ConfirmationMessage + "\n" + p.getName() + "\t" + p.getUnit() + " " + p.getUnit_type() + "\t" + p.getPrice() + "taka";
            }
            ConfirmationMessage = ConfirmationMessage + "\n\nTotal Price:" + "\t" + customer.getTotalPrice() + "taka";

            ConfirmationMessage += "\n\n\n";
            ConfirmationMessage = ConfirmationMessage + "Customer Details:\n******************" +
                    "\nName:\t" + customer.getName() + "\nMail Address:\t" + customer.getMail()
                    + "\nContact No:\t" + customer.getContactNo() + "\nAddress Details:\t" + customer.getAddress();

            //before it... everything must be stored into binary file

            File customerFile = new File("Customer Details" + customercount + ".txt");
            FileOutputStream fOutput = new FileOutputStream(customerFile);
            ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);
            oOutput.writeObject(customer);
            fOutput.close();
            oOutput.close();
            FileWriter fw=new FileWriter("Idcount.txt");
            fw.write(String.valueOf(customercount));

            Parent newsceneparent= FXMLLoader.load(getClass().getResource("ConfirmationView.fxml"));
            Common.ButtonClicked(e,newsceneparent);
        }else{
            Labelcheck.setText("you have left some options empty");
        }


        //to read from the file and get value
       /* FileInputStream fileInputStream=new FileInputStream(customerFile);
        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
        Customer c=new Customer();
        c=(Customer)objectInputStream.readObject();
        System.out.println(c);
        */

        CustomerName.setText("");
        CustomerMail.setText("");
        CustomerPhone.setText("");
        CustomerAddress.setText("");


        ///table items must be removed
        ///data must be written in a file
        ///send mails
        ///after selection and confirmation, the items must decrease.



        //scene change

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Image icon=new Image(getClass().getResourceAsStream("icon.png"));
        gobackPage=CommonTypeViewController.Currentpage;
        File file=new File("Idcount.txt");
        try {

            Scanner scanner=new Scanner(file);
            customercount=scanner.nextInt();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

