package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fourteam.fantastic.btl.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface UserApi {
    Gson gson = new GsonBuilder().create();
    UserApi retrofitUser = new Retrofit.Builder()
            .baseUrl("http://192.168.1.14:9999")
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

}
