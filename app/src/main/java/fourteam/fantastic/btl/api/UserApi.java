package fourteam.fantastic.btl.api;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.RequestBody.AddressRequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserApi {
    Resources resources = Resources.getSystem();
    String ip_config = "http://192.168.1.14";
    String port_gateway = "9999";

    Gson gson = new GsonBuilder().create();
    UserApi retrofitUser = new Retrofit.Builder()
            .baseUrl(ip_config + ":" + port_gateway)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserApi.class);

    @Headers({
            "Accept: application/json"
    })
    @GET("users/auth/me")
    Call<Object> getMe(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("/users/auth/login")
    Call<String> postLogin(@Field("username") String username, @Field("password") String password);

    @Headers({
            "Accept: application/json"
    })
    @POST("/users/auth/logout")
    Call<String> postLogout(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("/users/auth/register")
    Call<Object> postRegister(@Field("username") String username, @Field("password") String password, @Field("first_name") String firstname, @Field("last_name") String lastname, @Field("email") String email);

    @POST("/addresses")
    Call<Object> addAddress(@Body AddressRequestBody addressRequestBody);

    @GET("/addresses")
    Call<Object> getAllAddresses(@Query("user") Integer user);

    @GET("/addresses/{id}")
    Call<Object> getAddressById(@Path("id") Integer id);

    @PUT("/addresses/{id}")
    Call<Object> updateAddress(@Path("id") Integer id, @Body AddressRequestBody addressRequestBody);
}
