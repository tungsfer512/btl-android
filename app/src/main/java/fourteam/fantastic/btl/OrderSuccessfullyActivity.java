package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class OrderSuccessfullyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successfully);
        GifImageView gifImageView = findViewById(R.id.order_successfully);
        GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
        gifDrawable.setLoopCount(1);
        Button gotoOrdesSuccessfullyButton = findViewById(R.id.gotoOrdesSuccessfullyButton);
        gotoOrdesSuccessfullyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent writeReview = new Intent(OrderSuccessfullyActivity.this, WriteReviewActivity.class);
//                writeReview.putExtra("orderList","true");
                String token = getIntent().getStringExtra("token");
                writeReview.putExtra("token",token);
                writeReview.putExtra("user_id",getIntent().getStringExtra("user_id"));
                writeReview.putExtra("order_id",getIntent().getStringExtra("order_id"));

                startActivity(writeReview);
            }
        });
        Button continueShopping = findViewById(R.id.continueShopping);
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(OrderSuccessfullyActivity.this,HomeProductListActivity.class);
                String token = getIntent().getStringExtra("token");
                mainActivity.putExtra("token",token);
                startActivity(mainActivity);
            }
        });
    }
}