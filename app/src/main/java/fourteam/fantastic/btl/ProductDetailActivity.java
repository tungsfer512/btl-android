package fourteam.fantastic.btl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import fourteam.fantastic.btl.RequestBody.CartRequestBody;
import fourteam.fantastic.btl.api.CartApi;
import fourteam.fantastic.btl.api.ProductApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView imageProduct1, imageProduct2, imageProduct3, imageProduct4, imageProduct5;
    TextView txtProductCategory, txtProductName, txtProductPrice, txtProductSize, txtProductDescription, txtProductVat;
    Button btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

//        Find id
        imageProduct1 = (ImageView) findViewById(R.id.imageProduct1);
        imageProduct2 =  (ImageView) findViewById(R.id.imageProduct2);
        imageProduct3 =  (ImageView) findViewById(R.id.imageProduct3);
        imageProduct4 =  (ImageView) findViewById(R.id.imageProduct4);
        imageProduct5 =  (ImageView) findViewById(R.id.imageProduct5);

        txtProductCategory = (TextView) findViewById(R.id.txtProductCategory);
        txtProductName = (TextView) findViewById(R.id.txtProductName);
        txtProductPrice = (TextView) findViewById(R.id.txtProductPrice);
        txtProductSize = (TextView) findViewById(R.id.txtProductSize);
        txtProductDescription = (TextView) findViewById(R.id.txtProductDescription);
        txtProductVat = (TextView) findViewById(R.id.txtProductVat);

        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);

//        Get product_id by Intent
        final Integer product_id = Integer.parseInt(getIntent().getStringExtra("product_id"));
        final Integer user_id = (int) Double.parseDouble(getIntent().getStringExtra("user_id"));



//        Xử lí
        if(product_id != null){
            CallProductDetailApi(product_id);
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallAddToCartApi(user_id, product_id);
            }
        });
    }

    public void CallProductDetailApi(int id){
        ProductApi.retrofit.getProductDetail(id).enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                String res = gson.toJson(response.body());

                JsonElement productJson = new JsonParser().parse(res);

                JsonObject product = productJson.getAsJsonObject().get("data").getAsJsonObject();

                Log.e("product_detail", gson.toJson(product));

//                set to id
                String product_image1_url = product.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                String product_image2_url = product.get("images").getAsJsonArray().get(1).getAsJsonObject().get("image").getAsString();
                String product_image3_url = product.get("images").getAsJsonArray().get(2).getAsJsonObject().get("image").getAsString();
                String replaceString =  getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product);
                Glide.with(ProductDetailActivity.this).load(product_image1_url.replace("product-service:9000", replaceString)).into(imageProduct1);
                Glide.with(ProductDetailActivity.this).load(product_image2_url.replace("product-service:9000", replaceString)).into(imageProduct2);
                Glide.with(ProductDetailActivity.this).load(product_image3_url.replace("product-service:9000", replaceString)).into(imageProduct3);
                Glide.with(ProductDetailActivity.this).load(product_image1_url.replace("product-service:9000", replaceString)).into(imageProduct4);
                Glide.with(ProductDetailActivity.this).load(product_image2_url.replace("product-service:9000", replaceString)).into(imageProduct5);

                txtProductName.setText(product.get("title").getAsString());
                txtProductPrice.setText("$" + product.get("price").getAsString());
                txtProductDescription.setText(product.get("description").getAsString());

                double price = Double.parseDouble(product.get("price").getAsString());
                double vat = price / 10 + price;
                txtProductVat.setText("$" + String.format("%.2f", vat));

                String categories = "";
                int categoriesSize = product.get("categories").getAsJsonArray().size();
                for (int i = 0; i < categoriesSize; i++){
                    if(i != (categoriesSize - 1)){
                        categories = categories + product.get("categories").getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString() + ", ";
                    } else {
                        categories = categories + product.get("categories").getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString();
                    }
                }
                txtProductCategory.setText(categories);
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public void CallAddToCartApi(int user_id, int product_id){
        CartApi.retrofit.addToCart(new CartRequestBody(user_id, product_id)).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println("product_id: " + product_id);
                System.out.println("user_id: " + user_id);
                if(response.isSuccessful()){
                    Toast.makeText(ProductDetailActivity.this, "Add to cart success", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(ProductDetailActivity.this, "Add to cart false", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}