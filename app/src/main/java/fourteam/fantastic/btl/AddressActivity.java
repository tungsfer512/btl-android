package fourteam.fantastic.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fourteam.fantastic.btl.RequestBody.AddressRequestBody;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        System.out.println("Check address: "+ checkAddress);

        if(checkAddress.equalsIgnoreCase("true")){
            UserApi.retrofitUser.getAllAddresses(user_id_int).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Gson gson = new Gson();
                    String data = gson.toJson(response.body());
                    System.out.println("Address " + data);
                    JsonElement addressElement = new JsonParser().parse(data);

                    JsonObject addressObject = addressElement.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    String receiver_nameStr  = addressObject.get("receiver_name").getAsString();
                    String phoneStr = addressObject.get("receiver_phone").getAsString();
                    String addressStr = addressObject.get("address").getAsString();
                    String townStr = addressObject.get("town").getAsString();
//                    String street = addressObject.get("street").getAsString();
                    String cityStr = addressObject.get("city").getAsString();

                    System.out.println("Check true " + receiver_nameStr);

                    EditText fullname = findViewById(R.id.fullnameText);
                    EditText phone = findViewById(R.id.phoneText);
                    EditText town = findViewById(R.id.townText);
                    EditText city = findViewById(R.id.cityText);
                    EditText address = findViewById(R.id.addressText);

                    fullname.setText(receiver_nameStr);
                    phone.setText(phoneStr);
                    town.setText(townStr);
                    city.setText(cityStr);
                    address.setText(addressStr);
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }

        Button saveAddressButton = findViewById(R.id.saveAddressButton);
        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullname = findViewById(R.id.fullnameText);
                EditText phone = findViewById(R.id.phoneText);
                EditText town = findViewById(R.id.townText);
                EditText city = findViewById(R.id.cityText);
                EditText address = findViewById(R.id.addressText);

                String fullnameStr = fullname.getText().toString().trim();
                String phoneStr = phone.getText().toString().trim();
                String townStr = town.getText().toString().trim();
                String cityStr = city.getText().toString().trim();
                String addressStr = address.getText().toString().trim();

                System.out.println("fullname: " + fullnameStr);
                System.out.println("phone: " +phoneStr);
                System.out.println("town: " + townStr);
                System.out.println("city: " + cityStr);
                System.out.println("address: " + addressStr);
                if(fullnameStr.equalsIgnoreCase("")) {
                    Toast.makeText(AddressActivity.this, "fullname invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneStr.equalsIgnoreCase("")) {
                    Toast.makeText(AddressActivity.this, "phone number invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(townStr.equalsIgnoreCase("")) {
                    Toast.makeText(AddressActivity.this, "district invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cityStr.equalsIgnoreCase("")) {
                    Toast.makeText(AddressActivity.this, "city invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(addressStr.equalsIgnoreCase("")) {
                    Toast.makeText(AddressActivity.this, "address invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(checkAddress.equalsIgnoreCase("true")){
                    final String addressId = getIntent().getStringExtra("addressId");
                    Integer addressIdint = (int) Double.parseDouble(addressId);
                    UserApi.retrofitUser.updateAddress(addressIdint,new AddressRequestBody(fullnameStr,phoneStr,cityStr,townStr,addressStr,user_id_int)).enqueue(new Callback<Object>() {

                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if(response.code()==200){
                                System.out.println("update address ok");
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {

                        }
                    });
                } else {
                    UserApi.retrofitUser.addAddress(new AddressRequestBody(fullnameStr,phoneStr,cityStr,townStr,addressStr,user_id_int)).enqueue(new Callback<Object>() {

                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if(response.isSuccessful()){
                                System.out.println("add address ok!");
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {

                        }
                    });
                }

                Intent orderIntent = new Intent(AddressActivity.this,OrderActivity.class);
                orderIntent.putExtra("token",token);
                orderIntent.putExtra("user_id",user_id);
                orderIntent.putExtra("checkAddress","true");

                String checkPaymentForAddress = getIntent().getStringExtra("checkPayment");
                System.out.println("check payment: " + checkPaymentForAddress);
                if (checkPaymentForAddress.equalsIgnoreCase("true")){
                    orderIntent.putExtra("checkPayment","true");
                } else {
                    orderIntent.putExtra("checkPayment","false");
                }
                startActivity(orderIntent);
            }
        });
    }
}