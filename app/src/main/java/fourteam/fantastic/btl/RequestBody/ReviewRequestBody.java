package fourteam.fantastic.btl.RequestBody;

public class ReviewRequestBody {
    Integer user_id;
    Integer order_id;
    Integer product_id;
    float rating;
    String content;

    public ReviewRequestBody(Integer user_id, Integer order_id, Integer product_id, float rating, String content) {
        this.user_id = user_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.rating = rating;
        this.content = content;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
