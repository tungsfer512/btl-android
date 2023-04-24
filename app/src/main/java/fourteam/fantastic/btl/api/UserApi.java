package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    Gson gson = new GsonBuilder().create();
    ProductApi retrofitUser = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13:9999")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    @GET("/me")
    Call<Object> getMe();

    @POST("/auth/users/login")
    Call<Object> postLogin();

    @POST("/auth/users/logout")
    Call<Object> postLogout();

    @POST("/users/auth/register")
    Call<Object> postRegister();

}
