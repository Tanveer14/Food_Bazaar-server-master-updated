package sample;

public class Order {
    int id;
    String name;
    String status;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Order(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public Order(int id, String name, String status) {
        this.id = id;
        this.status = status;
        this.name=name;
    }

    public Order() {

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
