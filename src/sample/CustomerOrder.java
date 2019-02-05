package sample;

import java.io.Serializable;

public class CustomerOrder implements Serializable {
    static final long serialVersionUID = 42L;
    int id;
    String name;
    String status;

    public CustomerOrder(String name,int id) {
        this.id = id;
        this.name=name;
    }



    public CustomerOrder(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public CustomerOrder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
