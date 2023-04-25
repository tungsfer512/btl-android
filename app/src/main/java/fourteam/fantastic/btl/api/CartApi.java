package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartApi {
    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.10.221:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    @GET("/carts")
    Call<Object> getAllCarts();

    @POST("/carts")
    Call<Object> addToCart();

    @PUT("/carts/{id}")
    Call<Object> updateCart(@Path("id") Integer id);

    @DELETE("/carts/{id}")
    Call<Object> deleteCart(@Path("id") Integer id);


}