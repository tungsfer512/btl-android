package fourteam.fantastic.btl;

import android.os.Bundle;
import android.widget.ListView;

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

//        final String token = getIntent().getStringExtra("token");
//        final String user_id = getIntent().getStringExtra("user_id");
//
//        System.out.println("check token: " + token);
//        System.out.println("check user_id: " + user_id);
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
                    Integer cart_id =(int) Double.parseDouble(cart.get("id").getAsString());
                    Integer quantity =(int) Double.parseDouble(cart.get("quantity").getAsString());

                    System.out.println(cart_id);
                    JsonObject product = cart.get("product").getAsJsonObject();
                    String title = product.get("title").getAsString();
                    System.out.println(title);
                    Double price = Double.parseDouble(product.get("price").getAsString());
                    System.out.println(price);
                    String image = product.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                    System.out.println(image);
                    String old = "product-service:9000";
                    String newS = image.replace(old,"192.168.10.221:9116");
                    System.out.println(newS);
                    arrayList.add(new Cart(cart_id,newS,title,quantity,price));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}