package fourteam.fantastic.btl.RequestBody;

import java.util.ArrayList;

public class OrderRequestBody {
    int user_id;
    ArrayList<Integer> cart_ids;
    String shipment_method;
    int address_id;
    Double cost;
    String note;
    String payment_method;
    String card_number;
    String cvv;

    public OrderRequestBody(int user_id, ArrayList<Integer> cart_ids, String shipment_method, int address_id, Double cost, String note, String payment_method, String card_number, String cvv) {
        this.user_id = user_id;
        this.cart_ids = cart_ids;
        this.shipment_method = shipment_method;
        this.address_id = address_id;
        this.cost = cost;
        this.note = note;
        this.payment_method = payment_method;
        this.card_number = card_number;
        this.cvv = cvv;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ArrayList<Integer> getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(ArrayList<Integer> cart_ids) {
        this.cart_ids = cart_ids;
    }

    public String getShipment_method() {
        return shipment_method;
    }

    public void setShipment_method(String shipment_method) {
        this.shipment_method = shipment_method;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
