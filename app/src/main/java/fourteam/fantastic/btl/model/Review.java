package fourteam.fantastic.btl.model;

public class Review {
    private int id;
//    private String image;
    private String name;
    private String date;
    private Double star;
    private String description;





    public Review(int id, String name, String date, Double star, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.star = star;
        this.description = description;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
