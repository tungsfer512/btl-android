package fourteam.fantastic.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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
import fourteam.fantastic.btl.model.MyCartAdapter;
import fourteam.fantastic.btl.model.OrderItem;
import fourteam.fantastic.btl.model.OrderItemAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCardFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyCartAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_card, container, false);
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
                            String time = orderItem.get("shipment").getAsJsonObject().get("created").getAsString();
                            String total = "- " + orderItem.get("payment").getAsJsonObject().get("amount").getAsString() + ".000 đ";
                            list.add(new OrderItem(id, time, total, "", ""));
                        }
                        System.out.println("a "+ list.toString());
                        // Find recycle view
                        recyclerView = view.findViewById(R.id.rMyCard);

                        // Create a new layout manager for the RecyclerView
                        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        // Create a new adapter for the RecyclerView
                        adapter = new MyCartAdapter(list);
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