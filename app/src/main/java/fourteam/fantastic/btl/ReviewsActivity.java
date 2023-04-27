package fourteam.fantastic.btl;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import fourteam.fantastic.btl.api.CommentApi;
import fourteam.fantastic.btl.model.Review;
import fourteam.fantastic.btl.model.ReviewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Review> reviewArrayList = new ArrayList<>();
    ReviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        lv = findViewById(R.id.listViewReview);
        adapter = new ReviewAdapter(this,R.layout.review_item,reviewArrayList);
        lv.setAdapter(adapter);

        String product_idStr = getIntent().getStringExtra("product_id");
        Integer product_id = (int) Double.parseDouble(product_idStr);
        CommentApi.retrofit.getAllComments(product_id).enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                reviewArrayList.clear();
                String reviewList = gson.toJson(response.body());
                System.out.println(reviewList);
                JsonElement root = new JsonParser().parse(reviewList);
                int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                Double avgStar = 0.0;
                for (int i = 0;i < size; i++){
                    JsonObject reviewOb = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer idReview = (int) Double.parseDouble(reviewOb.get("id").getAsString());
                    String name = "tung";
                    Double rateting = Double.parseDouble(reviewOb.get("rating").getAsString());
                    avgStar += rateting;
                    String content = reviewOb.get("content").getAsString();
                    String dateReview = reviewOb.get("updated").getAsString();
                    reviewArrayList.add(new Review(idReview,name,dateReview,rateting,content));
                }
                avgStar /= size;
                String avgStarStr = String.valueOf(avgStar);
                TextView avgTextView = findViewById(R.id.avgRateReview);
                avgTextView.setText(avgStarStr + " rating");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
