package fourteam.fantastic.btl.api;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.RequestBody.CartRequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartApi {
    Resources resources = Resources.getSystem();
    String ip_config = "http://" + resources.getString(R.string.ip_config);
    String port_gateway = resources.getString(R.string.port_gateway);
    Gson gson = new GsonBuilder().create();
    CartApi retrofit = new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_gateway)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CartApi.class);

    @GET("/carts")
    Call<Object> getAllCarts();

    @POST("/carts")
    Call<Object> addToCart(@Body CartRequestBody cartRequestBody);

    @FormUrlEncoded
    @PUT("/carts/{id}")
    Call<Object> updateCart(@Path("id") Integer id, @Field("update") String update);

    @DELETE("/carts/{id}")
    Call<Object> deleteCart(@Path("id") Integer id);


}
