package sample;
import java.util.Arrays;

public class Employee {
    private String name;
    private String MailAddress;
    private String location;
    public String [] SupplyItemList;

    public Employee()
    {
        this.name = null;
        MailAddress = null;
        this.location = null;
    }

    public Employee(String name, String mailAddress, String location) {
        this.name = name;
        MailAddress = mailAddress;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", MailAddress='" + MailAddress + '\'' +
                ", location='" + location + '\'' +
                ", SupplyItemList=" + Arrays.toString(SupplyItemList) +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getLocation() {return location;}

    public String getMailAddress() {return MailAddress;}

    public void setInfo(String name,String MailAddress,String location) {
        this.name = name;
        this.MailAddress=MailAddress;
        this.location=location;
    }
}
