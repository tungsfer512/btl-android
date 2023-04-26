package fourteam.fantastic.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        EditText cardOwnerText = findViewById(R.id.cardOwnerText);
        EditText cardNumberText = findViewById(R.id.cardNumberText);
        EditText expText = findViewById(R.id.expText);
        EditText cvvText = findViewById(R.id.cvvText);
        final String token = getIntent().getStringExtra("token");
        final String user_id = getIntent().getStringExtra("user_id");
        Integer user_id_int = (int) Double.parseDouble(user_id);
        Button confirmPaymentButton = findViewById(R.id.confirmPaymentButton);
        confirmPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderIntent = new Intent(view.getContext(), OrderActivity.class);
                String cardOwner = cardOwnerText.getText().toString();
                String cardNumber = cardNumberText.getText().toString();
                String exp = expText.getText().toString();
                String cvv = cvvText.getText().toString();

                orderIntent.putExtra("checkPayment", "true");
                orderIntent.putExtra("cardOwner", cardOwner);
                orderIntent.putExtra("cardNumber", cardNumber);
                orderIntent.putExtra("exp", exp);
                orderIntent.putExtra("cvv", cvv);
                orderIntent.putExtra("token",token);
                orderIntent.putExtra("user_id",user_id);

                startActivity(orderIntent);
            }
        });

    }
}