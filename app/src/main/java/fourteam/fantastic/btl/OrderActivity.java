package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import fourteam.fantastic.btl.api.CartApi;
import fourteam.fantastic.btl.api.OrderApi;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.model.Cart;
import fourteam.fantastic.btl.model.OrderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Cart> arrayList = new ArrayList<>();
    OrderAdapter orderAdapter;
    public void getAllCartAndAddListId(ArrayList<Integer> cartIdList,Integer user_id_int){
        CartApi.retrofit.getAllCarts().enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                arrayList.clear();
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(response.body());
                System.out.println(jsonResponse);
                JsonElement carts = new JsonParser().parse(jsonResponse);
                int size = carts.getAsJsonObject().get("data").getAsJsonArray().size();
                Double priceSubtotal = 0.0;
                int cnt = 0;
                for (int i = 0;i < size; i++){
                    JsonObject cart = carts.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer user_id_c = (int) Double.parseDouble(cart.get("user_id").getAsString());
                    if (user_id_int == user_id_c){
                        Integer cart_id = (int) Double.parseDouble(cart.get("id").getAsString());
                        Integer quantity = (int) Double.parseDouble(cart.get("quantity").getAsString());

                        JsonObject product = cart.get("product").getAsJsonObject();
                        String title = product.get("title").getAsString();
                        Double price = Double.parseDouble(product.get("price").getAsString());
                        String image = product.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                        String old = "product-service:9000";
                        String newS = image.replace(old,getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product));

                        priceSubtotal += (quantity*price);
                        arrayList.add(new Cart(cart_id,newS,title,quantity,price));
                        cartIdList.add(cart_id);
                        cnt+=1;
                    }
                }

                Double priceShipment = 10.0;
                Double priceTotal = priceSubtotal + priceShipment;

                TextView subtotal = findViewById(R.id.priceSubtotal);
                subtotal.setText("$" + String.valueOf(priceSubtotal));
                TextView ship = findViewById(R.id.priceShipment);
                ship.setText("$"+String.valueOf(priceShipment));
                TextView total = findViewById(R.id.priceTotal);
                total.setText("$"+String.valueOf(priceTotal));
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final String token = getIntent().getStringExtra("token");
        final String user_id = getIntent().getStringExtra("user_id");
        Integer user_id_int = (int) Double.parseDouble(user_id);
        System.out.println("check token: " + token);
        System.out.println("check user_id: " + user_id);
//        set listAdapter
        lv = (ListView) findViewById(R.id.orderItem);
        orderAdapter = new OrderAdapter(this,R.layout.order_item,arrayList);
        lv.setAdapter(orderAdapter);

//        intent address
        Intent addressIntent = new Intent(OrderActivity.this, AddressActivity.class);
        addressIntent.putExtra("token",token);
        addressIntent.putExtra("user_id",user_id);
        ArrayList<Integer> cartIdList = new ArrayList<>();
//        get api cart
        getAllCartAndAddListId(cartIdList,user_id_int);


//      check address
        UserApi.retrofitUser.getAllAddresses(user_id_int).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                String data = gson.toJson(response.body());
                System.out.println("Address " + data);
                JsonElement addressElement = new JsonParser().parse(data);
                int size = addressElement.getAsJsonObject().get("data").getAsJsonArray().size();
                if(size == 0){
                    TextView addressDisplayOrder = findViewById(R.id.addresDisplayOrder);
                    addressDisplayOrder.setText("Please enter your address");
                    addressIntent.putExtra("checkAddress","false");
                    return;
                }
                addressIntent.putExtra("checkAddress","true");

                String checkPaymentForAddress = getIntent().getStringExtra("checkPayment");
                System.out.println("check payment: " + checkPaymentForAddress);
                if (checkPaymentForAddress.equalsIgnoreCase("true")){
                    addressIntent.putExtra("checkPayment","true");
                } else {
                    addressIntent.putExtra("checkPayment","false");
                }
                JsonObject addressObject = addressElement.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                String addressId = addressObject.get("id").getAsString();
                addressIntent.putExtra("addressId",addressId);
                String addressStr = addressObject.get("address").getAsString();
                String town = addressObject.get("town").getAsString();
//                    String street = addressObject.get("street").getAsString();
                String city = addressObject.get("city").getAsString();

                String addressDisplay = addressStr + ", "+ town  + ", " + city + ", Viet Nam";

                TextView addressDisplayOrder = findViewById(R.id.addresDisplayOrder);
                addressDisplayOrder.setText(addressDisplay);
                ImageView checkDisplayAddressImageView = findViewById(R.id.checkDisplayAddressImageView);
                checkDisplayAddressImageView.setImageResource(R.drawable.icons8_tiktok_verified_account_48_green_tick);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

//        address
        LinearLayout addressButton = findViewById(R.id.addressLayout);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(addressIntent);
            }
        });
//        payment

        String checkPayment = getIntent().getStringExtra("checkPayment");
        System.out.println("check payment: " + checkPayment);

        if (checkPayment.equalsIgnoreCase("true")) {

            String cardOwner = getIntent().getStringExtra("cardOwner");
            String cardNumber = getIntent().getStringExtra("cardNumber");
            String exp = getIntent().getStringExtra("exp");
            String cvv = getIntent().getStringExtra("cvv");
            System.out.println(cardOwner);
            System.out.println(cardNumber);
            System.out.println(exp);
            System.out.println(cvv);

            TextView paymentOwnerTextView = findViewById(R.id.paymentOwnerTextView);
            paymentOwnerTextView.setText(cardOwner);
            TextView cardNumberTextView = findViewById(R.id.cardNumberTextView);
            StringBuilder te_cardNumberBuilder = new StringBuilder(cardNumber);
            te_cardNumberBuilder.reverse();
            String te_cardNumber = te_cardNumberBuilder.substring(0, 4);
            StringBuilder x_cardNumberBuilder = new StringBuilder(te_cardNumber);
            x_cardNumberBuilder.reverse();
            cardNumberTextView.setText("**** " + x_cardNumberBuilder.toString());

            ImageView checkDisplayPaymentImageView = findViewById(R.id.checkDisplayPaymentImageView);
            checkDisplayPaymentImageView.setImageResource(R.drawable.icons8_tiktok_verified_account_48_green_tick);
        }

        LinearLayout paymentButton = findViewById(R.id.paymentLayout);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(OrderActivity.this,PaymentActivity.class);
                paymentIntent.putExtra("token",token);
                paymentIntent.putExtra("user_id",user_id);
                startActivity(paymentIntent);
            }
        });
//        confirm
        Button confirmOrderButton = findViewById(R.id.confirmOrderButton);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView addressDisplayOrder = findViewById(R.id.addresDisplayOrder);
                String checkFillAddress = addressDisplayOrder.getText().toString();
                if (checkFillAddress.equalsIgnoreCase("Please enter your address")){
                    Toast.makeText(OrderActivity.this, "Please enter you address", Toast.LENGTH_SHORT).show();
                    return;
                }
                String checkPayment = getIntent().getStringExtra("checkPayment");
                System.out.println("check payment: " + checkPayment);
                if (checkPayment.equalsIgnoreCase("false")){
                    Toast.makeText(OrderActivity.this, "Please enter you card number", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(cartIdList);
//                OrderApi.retrofit.addOrder()
            }
        });
    }
}