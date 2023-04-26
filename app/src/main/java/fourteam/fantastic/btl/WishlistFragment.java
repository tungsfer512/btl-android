package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import fourteam.fantastic.btl.api.ProductApi;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.api.WishlistApi;
import fourteam.fantastic.btl.model.Product;
import fourteam.fantastic.btl.model.ProductAdapter;
import fourteam.fantastic.btl.model.WishlistAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    private WishlistAdapter adapter;
    private TextView txtSizeWishlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        txtSizeWishlist = (TextView) view.findViewById(R.id.txtSizeWishlist);
        //  get Intent nhận
        Intent intent = getActivity().getIntent();
        System.out.println("Wishlist check");
        final String token = "token " + intent.getStringExtra("token");
        System.out.println("token: " + token);

        List<Product> list = new ArrayList<>();
        getWishlistApi(view, list, token);
        return view;
    }

    private void getWishlistApi(View view, List<Product> list, String token){
        UserApi.retrofitUser.getMe(token).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                String userJson = gson.toJson(response.body());
                JsonElement user = new JsonParser().parse(userJson);
                System.out.println("userJson " + userJson);
                String user_id = user.getAsJsonObject().get("id").getAsString();
                int userId = (int) Double.parseDouble(user_id);
                WishlistApi.retrofit.getAllWishlist(userId).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                        Gson gson = new Gson();
                        list.clear();
                        String productList = gson.toJson(response.body());
                        JsonElement root = new JsonParser().parse(productList);
                        int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                        for (int i=0;i<size;i++){
                            JsonObject value1 = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject().get("product").getAsJsonObject();
                            Integer id = (int) Double.parseDouble(value1.get("id").getAsString());
                            String image = value1.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                            String replaceString = getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product);
                            image = image.replace("product-service:9000", replaceString);
                            Log.e("Product List " + id + ": ", image);
                            String title = value1.get("title").getAsString();
                            String price = "$" + value1.get("price").toString();
                            list.add(new Product(id, image, title, price));

                        }
                        System.out.println("a "+ list.toString());
                        txtSizeWishlist.setText(String.valueOf(size) + " items");
                        // Find recycle view
                        recyclerView = view.findViewById(R.id.rWishList);

                        // Create a new Adapter class that extends RecyclerView.Adapter.
                        adapter = new WishlistAdapter(list);

                        // Set the Adapter class on the RecyclerView.
                        recyclerView.setAdapter(adapter);

                        // Set the RecyclerView's layout manager.
                        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
                        recyclerView.setLayoutManager(layoutManager);

//                System.out.println("b "+ lists.toString());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println("err: " +t);
                    }
                });
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }
}