package fourteam.fantastic.btl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}