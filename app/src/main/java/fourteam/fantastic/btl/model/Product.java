package fourteam.fantastic.btl.model;

public class Product {
    private int id;
    private String img;
    private String name;
    private String price;

    public Product(int id, String img, String name, String price) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return id + "," + name + "," + price;
    }
}
