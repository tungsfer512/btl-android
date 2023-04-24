package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CommentApi {
    Gson gson = new GsonBuilder().create();
    ProductApi retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    ProductApi retrofitComment= new Retrofit.Builder()
            .baseUrl("http://192.168.1.13:9113")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);
    @GET("/comments")
    Call<Object> getAllComments();

    @POST("/comments")
    Call<Object> addComment();
}
