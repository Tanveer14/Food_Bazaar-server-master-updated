package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private String name;
    private String address;
    public double TotalPrice;
    private String mail;
    ArrayList<product> ProductList=new ArrayList<>();
    private String contactNo;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }
    public void setTotalPrice(double totalPrice){this.TotalPrice=totalPrice;}

    public ArrayList<product> getProductList() {
        return ProductList;
    }




    public Customer(String name, String area, String address, String mail, String contactNo) {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.contactNo = contactNo;
    }

    public Customer() {

    }

    public void SetInfo(String name, String area, String address, String mail, String contactNo)
    {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.contactNo = contactNo;
    }
    public String getName() {
        return name;
    }



    public String getAddress() {
        return address;
    }

    public String getMail() {
        return mail;
    }
    public String getContactNo() {
        return contactNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setProductList(ArrayList<product> productList) {
        ProductList = productList;
    }

    public String toMessage()
    {
        String temp=new String();
        temp = "Customer Id:\t" + id + "\n";
        for (product p : ProductList
        ) {
            temp = temp + "\n" + p.getName() + "\t" + p.getUnit() + " " + p.getUnit_type() + "\t" + p.getPrice() + "taka";
        }
        temp = temp + "\n\nTotal Price:" + "\t" +TotalPrice + " taka";

        temp += "\n\n\n";
        temp = temp + "Customer Details:\n******************" +
                "\nName:\t" + name + "\nMail Address:\t" + getMail()
                + "\nContact No:\t" + contactNo + "\nAddress Details:\t" +address;
        return temp;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", TotalPrice=" + TotalPrice +
                ", mail='" + mail + '\'' +
                ", ProductList=" + ProductList +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
