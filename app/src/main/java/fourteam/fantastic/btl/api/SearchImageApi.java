package fourteam.fantastic.btl.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SearchImageApi {
    Gson gson = new GsonBuilder().create();
    SearchImageApi retrofit = new Retrofit.Builder()
            .baseUrl("http://10.20.8.119:9111")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SearchImageApi.class);
    @Multipart
    @POST("/upload_img")
    Call<String> searchImage(@Part MultipartBody.Part file);
}
