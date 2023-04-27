package fourteam.fantastic.btl.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import fourteam.fantastic.btl.R;
public class ReviewAdapter extends BaseAdapter {
    Context context;

    private int layout;
    private List<Review> reviewList;

    public ReviewAdapter(Context context, int layout, List<Review> reviewList) {
        this.context = context;
        this.layout = layout;
        this.reviewList = reviewList;
    }
    private class ReviewHolder{
//        ImageView imageView;
        TextView nameView;
        TextView dateView;
        TextView starView;
        TextView descriptionView;
    }
    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ReviewHolder reviewHolder;
        if (view == null){
            reviewHolder = new ReviewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
//            reviewHolder.imageView = (ImageView) view.findViewById(R.id.userReviewImageView);
            reviewHolder.nameView = (TextView) view.findViewById(R.id.nameReviewTextView);
            reviewHolder.dateView = (TextView) view.findViewById(R.id.dateReviewTextView);
            reviewHolder.starView = (TextView) view.findViewById(R.id.starReviewTextView);
            reviewHolder.descriptionView  = (TextView) view.findViewById(R.id.descriptionReviewTextView);
            view.setTag(reviewHolder);
        } else {
            reviewHolder = (ReviewHolder) view.getTag();
        }
        Review review = reviewList.get(i);
//        Glide.with(view).load(review.getImage()).into(reviewHolder.imageView);
        reviewHolder.nameView.setText(review.getName());

        reviewHolder.dateView.setText(review.getDate());
        reviewHolder.descriptionView.setText(review.getDescription());
        reviewHolder.starView.setText(String.valueOf(review.getStar()));

        return view;
    }
}
