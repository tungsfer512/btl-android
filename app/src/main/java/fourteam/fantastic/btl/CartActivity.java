package fourteam.fantastic.btl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final String token = getIntent().getStringExtra("token");
        final String user_id = getIntent().getStringExtra("user_id");

        System.out.println("check token: " + token);
        System.out.println("check user_id: " + user_id);
    }
}