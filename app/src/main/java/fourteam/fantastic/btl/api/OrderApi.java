package fourteam.fantastic.btl.api;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.R;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrderApi {
    Resources resources = Resources.getSystem();
    String ip_config = "http://" + resources.getString(R.string.ip_config);
    String port_gateway = resources.getString(R.string.port_gateway);

    Gson gson = new GsonBuilder().create();

    OrderApi retrofit = new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_gateway)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderApi.class);

    @GET("/orders")
    Call<Object> getAllOrders();

    @POST("/orders")
    Call<Object> addOrder();
}
