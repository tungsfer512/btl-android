package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ProductApi {

    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    ProductApi retrofitCategory = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13:9116")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);
    @GET("/products")
    Call<Object> getAllProducts();

    @GET("/categories")
    Call<Object> getAllCategories();

}