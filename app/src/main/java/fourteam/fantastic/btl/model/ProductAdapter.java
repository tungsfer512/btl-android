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

import fourteam.fantastic.btl.CartActivity;
import fourteam.fantastic.btl.HomeProductListActivity;
import fourteam.fantastic.btl.ProductDetailActivity;
import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.api.WishlistApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> mList;

    public ProductAdapter(List<Product> mList) {
        this.mList = mList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Product product = mList.get(position);

        if(product == null) return;
//        holder.img.setImageResource(product.getImg());
        Glide.with(holder.itemView).load(product.getImg()).into(holder.img);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());

        holder.btnAddWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) holder.itemView.getContext();
                Intent intent = activity.getIntent();
                final String token = "token " + intent.getStringExtra("token");
//                Gửi
                AddToWishlist(view, token);
            }

            private void AddToWishlist(View view, String token){
                UserApi.retrofitUser.getMe(token).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Gson gson = new Gson();
                        String userJson = gson.toJson(response.body());
                        JsonElement user = new JsonParser().parse(userJson);
                        System.out.println("userJson " + userJson);
                        String user_id = user.getAsJsonObject().get("id").getAsString();
                        int userId = (int) Double.parseDouble(user_id);
                        WishlistApi.retrofit.addWishlistItem(userId, product.getId()).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(view.getContext(), "Add product to wishlist success", Toast.LENGTH_SHORT).show();
                                    Activity activity = (Activity) holder.itemView.getContext();
                                    activity.recreate();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
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
                        productDetailIntent.putExtra("product_id", String.valueOf(product.getId()));
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
        if(mList != null) return mList.size();
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name;
        private TextView price;
        private FloatingActionButton btnAddWishlist;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgProduct);
            name = itemView.findViewById(R.id.nameProduct);
            price = itemView.findViewById(R.id.priceProduct);
            btnAddWishlist = itemView.findViewById(R.id.btnAddWishlist);
        }
    }
}
