package fourteam.fantastic.btl.model;

public class Address {
    String receiver_name;
    String receiver_phone;
    String city;
    String town;
    String street;
    String address;
    Integer user;

    public Address(String receiver_name, String receiver_phone, String city, String town, String street, String address, Integer user) {
        this.receiver_name = receiver_name;
        this.receiver_phone = receiver_phone;
        this.city = city;
        this.town = town;
        this.street = street;
        this.address = address;
        this.user = user;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
