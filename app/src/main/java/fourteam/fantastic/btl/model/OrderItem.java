package fourteam.fantastic.btl.model;

public class OrderItem {
    private int id;
    private String products;
    private String shipmentStatus;
    private String address;
    private String total;

    public OrderItem(int id, String products, String shipmentStatus, String address, String total) {
        this.id = id;
        this.products = products;
        this.shipmentStatus = shipmentStatus;
        this.address = address;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
