package fourteam.fantastic.btl.api;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApi {

    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl("http://10.20.8.119:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    ProductApi retrofitCategory = new Retrofit.Builder()
            .baseUrl("http://10.20.8.119:9116")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);
    @GET("/products")
    Call<Object> getAllProducts(@Nullable @Query("categories")ArrayList<Integer> list, @Nullable @Query("search")String search, @Nullable @Query("priceStart")Double priceStart, @Nullable @Query("priceEnd")Double priceEnd );

    @GET("/products/{id}")
    Call<Object> getProductDetail(@Path("id") int product_id);

    @GET("/categories")
    Call<Object> getAllCategories();

}