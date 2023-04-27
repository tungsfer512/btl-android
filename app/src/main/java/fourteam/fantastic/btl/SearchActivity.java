package fourteam.fantastic.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import fourteam.fantastic.btl.api.ProductApi;
import fourteam.fantastic.btl.model.Product;
import fourteam.fantastic.btl.model.ProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ProductAdapter adapterProduct;

    private EditText priceStart;
    private EditText priceEnd;
    private Button btnFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        Find R id
        priceStart = (EditText) findViewById(R.id.txtpriceStart);
        priceEnd = (EditText) findViewById(R.id.txtpriceEnd);
        btnFilter = (Button) findViewById(R.id.btnFilter);

        String token = getIntent().getStringExtra("token");
        final String query_category_name = getIntent().getStringExtra("query_category_name");
        if(getIntent().getStringExtra("query_category_id") != null){
            Integer query_category_id = (int) Double.parseDouble(getIntent().getStringExtra("query_category_id"));
            System.out.println("query_category_id: " + query_category_id);
            List<Product> list = new ArrayList<>();
            ArrayList<Integer> listCategories = new ArrayList<>();
            listCategories.add(query_category_id);
            getProductListByCategoryApi(list, listCategories);
        }
        if(getIntent().getStringExtra("user_id") != null) {
            Integer user_id = (int) Double.parseDouble(getIntent().getStringExtra("user_id"));
            System.out.println("user_id: " + user_id);
        }
        String query_from_home = getIntent().getStringExtra("query_from_home");
        String token_from_home = getIntent().getStringExtra("token");
        if (query_from_home != null){
            List<Product> list = new ArrayList<>();
            getProductListByQueryApi(list, query_from_home);
        }

        System.out.println("token: " + token);
        System.out.println("query_category_name: " + query_category_name);

        System.out.println("query_from_home: " + query_from_home);
        System.out.println("token_from_home: " + token_from_home);


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double a = Double.parseDouble(priceStart.getText().toString());
                Double b = Double.parseDouble(priceEnd.getText().toString());

                if(a > b){
                    Toast.makeText(SearchActivity.this, "Price start must be greater than price end", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    List<Product> list = new ArrayList<>();
                    getProductListByPriceApi(list, a, b);
                }
            }
        });
    }

    private void getProductListByPriceApi(List<Product> list, Double priceStart, Double priceEnd){
        ProductApi.retrofit.getAllProducts(null, null, priceStart, priceEnd).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                Gson gson = new Gson();
                list.clear();
                String productList = gson.toJson(response.body());
                JsonElement root = new JsonParser().parse(productList);
                int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                System.out.println("size" + size);
                for (int i=0;i<size;i++){
                    JsonObject value1 = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer id = (int) Double.parseDouble(value1.get("id").getAsString());
                    String image = value1.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                    String replaceString = getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product);
                    image = image.replace("product-service:9000", replaceString);
//                    Log.e("Product List " + id + ": ", image);
                    String title = value1.get("title").getAsString();
                    String price = "" + value1.get("price").toString() + ".000 đ";
                    list.add(new Product(id, image, title, price));

                }
                System.out.println("a "+ list.toString());

                // Find recycle view
                recyclerView = findViewById(R.id.rSearch);

                // Create a new Adapter class that extends RecyclerView.Adapter.
                adapterProduct = new ProductAdapter(list);

                // Set the Adapter class on the RecyclerView.
                recyclerView.setAdapter(adapterProduct);

                // Set the RecyclerView's layout manager.
                GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);

//                System.out.println("b "+ lists.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }

    private void getProductListByCategoryApi(List<Product> list, ArrayList<Integer> listCategories){
        ProductApi.retrofit.getAllProducts(listCategories, null, null, null).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                Gson gson = new Gson();
                list.clear();
                String productList = gson.toJson(response.body());
                JsonElement root = new JsonParser().parse(productList);
                int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                System.out.println("size" + size);
                for (int i=0;i<size;i++){
                    JsonObject value1 = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer id = (int) Double.parseDouble(value1.get("id").getAsString());
                    String image = value1.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                    String replaceString = getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product);
                    image = image.replace("product-service:9000", replaceString);
//                    Log.e("Product List " + id + ": ", image);
                    String title = value1.get("title").getAsString();
                    String price = "" + value1.get("price").toString() + ".000 đ";
                    list.add(new Product(id, image, title, price));

                }
                System.out.println("a "+ list.toString());

                // Find recycle view
                recyclerView = findViewById(R.id.rSearch);

                // Create a new Adapter class that extends RecyclerView.Adapter.
                adapterProduct = new ProductAdapter(list);

                // Set the Adapter class on the RecyclerView.
                recyclerView.setAdapter(adapterProduct);

                // Set the RecyclerView's layout manager.
                GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);

//                System.out.println("b "+ lists.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }

    private void getProductListByQueryApi(List<Product> list, String querySearch){
        ProductApi.retrofit.getAllProducts(null, querySearch, null, null).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                Gson gson = new Gson();
                list.clear();
                String productList = gson.toJson(response.body());
                JsonElement root = new JsonParser().parse(productList);
                int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                System.out.println("size" + size);
                for (int i=0;i<size;i++){
                    JsonObject value1 = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer id = (int) Double.parseDouble(value1.get("id").getAsString());
                    String image = value1.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                    String replaceString = getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product);
                    image = image.replace("product-service:9000", replaceString);
//                    Log.e("Product List " + id + ": ", image);
                    String title = value1.get("title").getAsString();
                    String price = "" + value1.get("price").toString() + ".000 đ";
                    list.add(new Product(id, image, title, price));

                }
                System.out.println("a "+ list.toString());

                // Find recycle view
                recyclerView = findViewById(R.id.rSearch);

                // Create a new Adapter class that extends RecyclerView.Adapter.
                adapterProduct = new ProductAdapter(list);

                // Set the Adapter class on the RecyclerView.
                recyclerView.setAdapter(adapterProduct);

                // Set the RecyclerView's layout manager.
                GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);

//                System.out.println("b "+ lists.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }
}