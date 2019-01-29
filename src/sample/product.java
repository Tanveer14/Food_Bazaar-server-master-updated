package sample;

import java.io.Serializable;

public class product implements Serializable {
    private String name;
    private String type;
    private double price;
    private double unit;
    private double available_units;
    private String unit_type;


    public product(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public product(String name, String type, double price, double available_units, String unit_type) {
        this.name=name;
        this.type = type;
        this.price = price;
        this.available_units = available_units;
        this.unit_type=unit_type;
        unit=0;
    }

    public product(String name, double unit, double price) {
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public product() {

    }

    public void setUnit(double unit) {
            this.unit=unit;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable_units(double available_units) {
        this.available_units = available_units;
    }

    public double getUnit() {
        return unit;
    }

    public double getAvailable_units() {
        return available_units;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }

    public void add_available_units(int n){
        available_units+=n;
    }

    public void exclude_available_units(int n){available_units-=n;}

    @Override
    public String toString() {
        return "product{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                ", available_units=" + available_units +
                ", unit_type='" + unit_type + '\'' +
                '}';
    }
}

