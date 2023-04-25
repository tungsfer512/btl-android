package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import fourteam.fantastic.btl.api.CartApi;
import fourteam.fantastic.btl.model.Cart;
import fourteam.fantastic.btl.model.OrderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Cart> arrayList = new ArrayList<>();
    OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final String token = getIntent().getStringExtra("token");
        final String user_id = getIntent().getStringExtra("user_id");
        Integer user_id_int = (int) Double.parseDouble(user_id);
        System.out.println("check token: " + token);
        System.out.println("check user_id: " + user_id);

        lv = (ListView) findViewById(R.id.orderItem);
        orderAdapter = new OrderAdapter(this,R.layout.order_item,arrayList);
        lv.setAdapter(orderAdapter);

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
        ImageButton addressButton = findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent address = new Intent(OrderActivity.this, AddressActivity.class);
                startActivity(address);
            }
        });
        ImageButton paymentButton = findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payment = new Intent(OrderActivity.this,PaymentActivity.class);
                startActivity(payment);
            }
        });
    }
}