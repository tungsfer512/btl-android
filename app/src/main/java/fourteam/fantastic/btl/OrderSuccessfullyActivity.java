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
                Intent homProductL = new Intent(OrderSuccessfullyActivity.this, HomeProductListActivity.class);
                homProductL.putExtra("orderList","true");
                startActivity(homProductL);
            }
        });

    }
}