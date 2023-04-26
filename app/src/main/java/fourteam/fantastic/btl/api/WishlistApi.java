package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WishlistApi {

    Gson gson = new GsonBuilder().create();
    WishlistApi retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.10.221:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WishlistApi.class);

    @GET("/wishlists")
    Call<Object> getAllWishlist(@Query("user_id") int user_id);

    @FormUrlEncoded
    @POST("/wishlists")
    Call<Object> addWishlistItem(@Field("user_id") int user_id, @Field("product_id") int product_id);

    @DELETE("/wishlists/{id}")
    Call<Object> deleteWishlistItem(@Path("id") int product_id);

}