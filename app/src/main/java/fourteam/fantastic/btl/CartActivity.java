package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import fourteam.fantastic.btl.api.CartApi;
import fourteam.fantastic.btl.model.Cart;
import fourteam.fantastic.btl.model.CartAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Cart> arrayList = new ArrayList<>();
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final String token = getIntent().getStringExtra("token");
        final String user_id = getIntent().getStringExtra("user_id");

        System.out.println("check token: " + token);
        System.out.println("check user_id: " + user_id);
        Integer user_id_int = (int) Double.parseDouble(user_id);
        lv = (ListView) findViewById(R.id.listCart);
        adapter = new CartAdapter(this,R.layout.cart_item,arrayList);
        lv.setAdapter(adapter);
        CartApi.retrofit.getAllCarts().enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                arrayList.clear();
                String cartList = gson.toJson(response.body());
                JsonElement carts = new JsonParser().parse(cartList);
                int size = carts.getAsJsonObject().get("data").getAsJsonArray().size();

                for (int i = 0; i < size; i++){
                    JsonObject cart = carts.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer user_id_c = (int) Double.parseDouble(cart.get("user_id").getAsString());

                    if (user_id_int == user_id_c){
                        Integer cart_id =(int) Double.parseDouble(cart.get("id").getAsString());
                        Integer quantity =(int) Double.parseDouble(cart.get("quantity").getAsString());


                        JsonObject product = cart.get("product").getAsJsonObject();
                        String title = product.get("title").getAsString();

                        Double price = Double.parseDouble(product.get("price").getAsString());

                        String image = product.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();

                        String old = "product-service:9000";
                        String newS = image.replace(old,getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product));

                        arrayList.add(new Cart(cart_id,newS,title,quantity,price));
                    }
                }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,OrderActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("user_id",user_id);
                intent.putExtra("checkPayment", "false");
                startActivity(intent);
            }
        });
    }
}