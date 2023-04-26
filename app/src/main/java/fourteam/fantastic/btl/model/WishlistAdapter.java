package fourteam.fantastic.btl.model;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

import fourteam.fantastic.btl.ProductDetailActivity;
import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.api.WishlistApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private List<Wishlist> mlist;

    public WishlistAdapter(List<Wishlist> mlist) {
        this.mlist = mlist;
        setHasStableIds(true);
    }

    public List<Wishlist> getMlist() {
        return mlist;
    }

    public void setMlist(List<Wishlist> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Wishlist product = mlist.get(position);

        if (product == null) return;

        Glide.with(holder.itemView).load(product.getImg()).into(holder.img);
        holder.nameProduct.setText(product.getName());
        holder.priceProduct.setText(product.getPrice());

        holder.btnDeleteWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) holder.itemView.getContext();
                TextView txtSizeWishlist = activity.findViewById(R.id.txtSizeWishlist);
                WishlistApi.retrofit.deleteWishlistItem(product.getId()).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        System.out.println(response.code());
                        Toast.makeText(view.getContext(), "Delete wishlist item success", Toast.LENGTH_SHORT).show();
                        mlist.remove(position);
                        if(mlist.size() > 0){
                            txtSizeWishlist.setText(String.valueOf(mlist.size()) + " items");
                        }else{
                            txtSizeWishlist.setText("0 items");
                        }

                        setMlist(mlist);
                        Activity activity = (Activity) holder.itemView.getContext();
                        activity.recreate();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Nhận Intent from login
                Activity activity = (Activity) holder.itemView.getContext();
                Intent intent = activity.getIntent();
                final String token = "token " + intent.getStringExtra("token");
//                Gửi
                CallGetUserInfor(view, token);
            }

            private void CallGetUserInfor(View view, String token){
                UserApi.retrofitUser.getMe(token).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Gson gson = new Gson();
                        String userJson = gson.toJson(response.body());
                        JsonElement user = new JsonParser().parse(userJson);
                        System.out.println("userJson " + userJson);
                        String user_id = user.getAsJsonObject().get("id").getAsString();

                        Intent productDetailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                        productDetailIntent.putExtra("product_id", String.valueOf(product.getProduct_id()));
                        productDetailIntent.putExtra("user_id", user_id);
                        view.getContext().startActivity(productDetailIntent);
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlist != null) return mlist.size();
        return 0;
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView nameProduct;
        private TextView priceProduct;
        private FloatingActionButton btnDeleteWishlistItem;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgProductWishlist);
            nameProduct = (TextView) itemView.findViewById(R.id.nameProductWishlist);
            priceProduct = (TextView) itemView.findViewById(R.id.priceProductWishlist);
            btnDeleteWishlistItem = (FloatingActionButton) itemView.findViewById(R.id.btnDeleteWishlistItem);
        }
    }
}
