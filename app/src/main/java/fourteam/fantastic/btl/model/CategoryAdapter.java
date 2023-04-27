package fourteam.fantastic.btl.model;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

import fourteam.fantastic.btl.ProductDetailActivity;
import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.SearchActivity;
import fourteam.fantastic.btl.api.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> mList;

    public CategoryAdapter(List<Category> mList) {
        this.mList = mList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Category category = mList.get(position);

        if(category == null) return;
//        holder.img.setImageResource(product.getImg());
        holder.name.setText(category.getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Nhận Intent from login
                Activity activity = (Activity) holder.itemView.getContext();
                Intent intent = activity.getIntent();
                final String token = "token " + intent.getStringExtra("token");
//                Gửi
                CallGetSearch(view, token);
            }

            private void CallGetSearch(View view, String token){
                UserApi.retrofitUser.getMe(token).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Gson gson = new Gson();
                        String userJson = gson.toJson(response.body());
                        JsonElement user = new JsonParser().parse(userJson);
                        System.out.println("userJson " + userJson);
                        String user_id = user.getAsJsonObject().get("id").getAsString();

                        Intent SearchIntent = new Intent(view.getContext(), SearchActivity.class);
                        SearchIntent.putExtra("query_category_name", String.valueOf(category.getName()));
                        SearchIntent.putExtra("query_category_id", String.valueOf(category.getId()));
                        SearchIntent.putExtra("user_id", user_id);
                        SearchIntent.putExtra("token", token.substring(6));
                        view.getContext().startActivity(SearchIntent);
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
        if(mList!=null) return mList.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtCategoryName);
        }
    }
}
