package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import fourteam.fantastic.btl.api.OrderApi;
import fourteam.fantastic.btl.api.UserApi;
import fourteam.fantastic.btl.api.WishlistApi;
import fourteam.fantastic.btl.model.OrderItem;
import fourteam.fantastic.btl.model.OrderItemAdapter;
import fourteam.fantastic.btl.model.Wishlist;
import fourteam.fantastic.btl.model.WishlistAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderItemAdapter adapter;

    private TextView txtSizeOrderlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        txtSizeOrderlist = (TextView) view.findViewById(R.id.txtSizeOrderlist);

        List<OrderItem> list = new ArrayList<>();
        //  get Intent nhận
        Intent intent = getActivity().getIntent();
        System.out.println("Orderlist check");
        final String token = "token " + intent.getStringExtra("token");
        System.out.println("token: " + token);

        CallGetOrderList(view, list, token);
        return view;
    }

    private void CallGetOrderList(View view, List<OrderItem> list, String token){
        UserApi.retrofitUser.getMe(token).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                String userJson = gson.toJson(response.body());
                JsonElement user = new JsonParser().parse(userJson);
                System.out.println("userJson " + userJson);
                String user_id = user.getAsJsonObject().get("id").getAsString();
                int userId = (int) Double.parseDouble(user_id);
                OrderApi.retrofit.getAllOrders(userId).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                        Gson gson = new Gson();
                        list.clear();
                        String productList = gson.toJson(response.body());
                        System.out.println("OrderApi: " + productList);
                        JsonElement root = new JsonParser().parse(productList);
                        int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                        for (int i=0;i<size;i++){
                            JsonObject orderItem = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                            System.out.println("Order Item " + i + ":" + orderItem.toString());
                            Integer id = (int) Double.parseDouble(orderItem.get("id").getAsString());
                            String status = orderItem.get("shipment").getAsJsonObject().get("shipment_status").getAsString();
                            String products = orderItem.get("products").getAsJsonArray().get(0).getAsJsonObject().get("info").getAsJsonObject().get("title").getAsString();
                            int sizeProducts = orderItem.get("products").getAsJsonArray().size();
                            for(int j=1;j<sizeProducts;j++){
                                JsonObject product = orderItem.get("products").getAsJsonArray().get(j).getAsJsonObject().get("info").getAsJsonObject();
                                products = products + ", " + product.get("title").getAsString();
                            }
                            String address = orderItem.get("address").getAsJsonObject().get("address").getAsString() + ", " +
                                    orderItem.get("address").getAsJsonObject().get("town").getAsString() + ", " +
                                    orderItem.get("address").getAsJsonObject().get("city").getAsString();
                            String total = "" + orderItem.get("payment").getAsJsonObject().get("amount").getAsString() + ".000 đ";
                            list.add(new OrderItem(id, products, status, address, total));
                        }
                        System.out.println("a "+ list.toString());
                        txtSizeOrderlist.setText(String.valueOf(size) + " orders");
                        // Find recycle view
                        recyclerView = view.findViewById(R.id.rOrderList);

                        // Create a new layout manager for the RecyclerView
                        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        // Create a new adapter for the RecyclerView
                        adapter = new OrderItemAdapter(list);
                        recyclerView.setAdapter(adapter);
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