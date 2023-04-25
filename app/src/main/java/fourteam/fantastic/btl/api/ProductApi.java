package fourteam.fantastic.btl.api;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.R;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {

    Resources resources = Resources.getSystem();
    String ip_config = "http://" + resources.getString(R.string.ip_config);
    String port_gateway = resources.getString(R.string.port_gateway);
    String port_product = resources.getString(R.string.port_product);

    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_gateway)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    ProductApi retrofitCategory = new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_product)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);
    @GET("/products")
    Call<Object> getAllProducts();

    @GET("/products/{id}")
    Call<Object> getProductDetail(@Path("id") int product_id);

    @GET("/categories")
    Call<Object> getAllCategories();

}