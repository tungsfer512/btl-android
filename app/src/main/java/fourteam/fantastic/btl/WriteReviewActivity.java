package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;

import fourteam.fantastic.btl.RequestBody.ReviewRequestBody;
import fourteam.fantastic.btl.api.CommentApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Button writeReviewButton = findViewById(R.id.writeReviewButton);
        writeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText descriptionEditText = findViewById(R.id.descriptionEditText);
                String description = descriptionEditText.getText().toString();
                Slider slide = findViewById(R.id.starReviewSlider);
                float valueSlider =  slide.getValue();
                if(description.equalsIgnoreCase("")){
                    Toast.makeText(WriteReviewActivity.this, "Invalid describe", Toast.LENGTH_SHORT).show();
                    return;
                }
                String user_idStr = getIntent().getStringExtra("user_id");
                Integer user_id = (int) Double.parseDouble(user_idStr);
                String order_idStr = getIntent().getStringExtra("order_id");
                Integer order_id = (int) Double.parseDouble(order_idStr);
                CommentApi.retrofit.addComment(new ReviewRequestBody(user_id,order_id,1,valueSlider,description)).enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if(response.isSuccessful()){

                            System.out.println("review add oke");
                            Intent intent = new Intent(WriteReviewActivity.this,HomeProductListActivity.class);
                            intent.putExtra("token",getIntent().getStringExtra("token"));
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        });
    }
}
