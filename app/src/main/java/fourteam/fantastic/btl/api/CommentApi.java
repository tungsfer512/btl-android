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

public interface CommentApi {
    Resources resources = Resources.getSystem();
    String ip_config = "http://" + resources.getString(R.string.ip_config);
    String port_gateway = resources.getString(R.string.port_gateway);
    String port_comment = resources.getString(R.string.port_comment);
    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_gateway)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    ProductApi retrofitComment= new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_comment)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);
    @GET("/comments")
    Call<Object> getAllComments();

    @POST("/comments")
    Call<Object> addComment();
}
