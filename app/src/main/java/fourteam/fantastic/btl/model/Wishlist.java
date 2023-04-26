package fourteam.fantastic.btl.model;

public class Wishlist {
    private int id;

    private int product_id;
    private String img;
    private String name;
    private String price;

    public Wishlist(int id, int product_id, String img, String name, String price) {
        this.id = id;
        this.product_id = product_id;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString(){
        return id + "," + product_id + "," + name + "," + price;
    }
}
