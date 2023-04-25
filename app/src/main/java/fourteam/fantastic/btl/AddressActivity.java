package fourteam.fantastic.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        final String token = getIntent().getStringExtra("token");
        final String user_id = getIntent().getStringExtra("user_id");
        final String checkAddress = getIntent().getStringExtra("checkAddress");
        Integer user_id_int = (int) Double.parseDouble(user_id);
        System.out.println("check token: " + token);
        System.out.println("check user_id: " + user_id);


        Button saveAddressButton = findViewById(R.id.saveAddressButton);
        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullname = findViewById(R.id.fullnameText);
                EditText country = findViewById(R.id.countryText);
                EditText city = findViewById(R.id.cityText);
                EditText address = findViewById(R.id.addressText);

                String fullnameStr = fullname.getText().toString();
                String countryStr = country.getText().toString();
                String cityStr = city.getText().toString();
                String addressStr = address.getText().toString();

            }
        });
    }
}