package fourteam.fantastic.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import fourteam.fantastic.btl.api.ProductApi;
import fourteam.fantastic.btl.api.ReadWriteToken;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.model.Product;
import fourteam.fantastic.btl.model.ProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button loginButton = findViewById(R.id.loginButton);
        EditText usernameText = findViewById(R.id.usernameText);
        EditText passwordText = findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                postUserLoginApi(usernameText.getText().toString(), passwordText.getText().toString(), v);

            }
        });
    }

    protected void postUserLoginApi(String username, String password, View view) {
        UserApi.retrofitUser.postLogin(username, password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                I/System.out
                Gson gson = new Gson();
                System.out.println("check status: " + response.code());
                if (response.code() == 200) {
                    Intent homeProductListIntent = new Intent(view.getContext(), HomeProductListActivity.class);

                    String token = "";
                    token = gson.toJson(response.body()).toString();
                    token = token.substring(1, token.length() - 1);
                    System.out.println("check token: " + token);
                    System.out.println(token.length());
                    if (!token.equalsIgnoreCase("Unable to log in with provided credentials!")) {
//                        ReadWriteToken.writeToken(token);
                        homeProductListIntent.putExtra("token", token);
                        startActivity(homeProductListIntent);
                    } else {
                        Toast.makeText(view.getContext(), "Wrong username or password",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    System.out.println("Wrong username or password");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }
}