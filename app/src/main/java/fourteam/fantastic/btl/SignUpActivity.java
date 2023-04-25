package fourteam.fantastic.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import fourteam.fantastic.btl.api.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Button registerButton = findViewById(R.id.registerButton);
        EditText usernameText = findViewById(R.id.usernameText);
        EditText passwordText = findViewById(R.id.passwordText);
        EditText firstnameText = findViewById(R.id.firstnameText);
        EditText lastnameText = findViewById(R.id.lastnameText);
        EditText emailText = findViewById(R.id.emailText);


        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postUserSignUpApi(usernameText.getText().toString(), passwordText.getText().toString(), firstnameText.getText().toString(), lastnameText.getText().toString(), emailText.getText().toString(), v);

            }
        });
    }

    protected void postUserSignUpApi(String username, String password, String firstname, String lastname, String email, View view) {

        UserApi.retrofitUser.postRegister(username, password, firstname, lastname, email).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                Gson gson = new Gson();
                System.out.println("check status: " + response.code());
                if (response.code() == 200) {
                    Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(loginIntent);

                } else {
                    System.out.println("Wrong username or password");
                    Toast.makeText(view.getContext(), "Invalid information",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }
}