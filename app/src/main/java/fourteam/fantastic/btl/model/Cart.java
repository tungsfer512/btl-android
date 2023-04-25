package fourteam.fantastic.btl.model;

public class Cart {
    private int id;
    private String image,title;
    private int quantity;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Cart(int id, String image, String title, int quantity, Double price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    public Cart(int id, String image, String title, int quantity) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
