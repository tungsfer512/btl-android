package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.RequestBody.ReviewRequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentApi {
    Gson gson = new GsonBuilder().create();
    CommentApi retrofit = new Retrofit.Builder()
            .baseUrl("http://10.20.8.119:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CommentApi.class);

    CommentApi retrofitComment= new Retrofit.Builder()
            .baseUrl("http://10.20.8.119:9113")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CommentApi.class);
    @GET("/comments")
    Call<Object> getAllComments(@Query("product_id") int product_id);


    @POST("/comments")
    Call<Object> addComment(@Body ReviewRequestBody reviewRequestBody);
}
