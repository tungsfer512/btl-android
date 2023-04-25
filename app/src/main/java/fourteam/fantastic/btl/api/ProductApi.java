package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {

    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.10.221:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    ProductApi retrofitCategory = new Retrofit.Builder()
            .baseUrl("http://192.168.10.221:9116")
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