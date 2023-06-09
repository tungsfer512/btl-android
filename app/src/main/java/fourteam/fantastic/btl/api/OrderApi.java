package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.RequestBody.OrderRequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApi {
    Gson gson = new GsonBuilder().create();

    OrderApi retrofit = new Retrofit.Builder()
            .baseUrl("http://10.20.8.119:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderApi.class);

    @GET("/orders")
    Call<Object> getAllOrders(@Query("user_id") int user_id);

    @POST("/orders")
    Call<Object> addOrder(@Body OrderRequestBody orderRequestBody);
}
