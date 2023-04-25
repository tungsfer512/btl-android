package fourteam.fantastic.btl.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fourteam.fantastic.btl.R;
import fourteam.fantastic.btl.api.CartApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Cart> cartList;

    public CartAdapter(Context context, int layout, List<Cart> cartList) {
        this.context = context;
        this.layout = layout;
        this.cartList = cartList;
    }

    private class CartViewHolder{
        ImageView imageProductView;
        TextView title;
        TextView price;
        TextView quantity;

        ImageButton up;
        ImageButton down;
        ImageButton delete;
    }

    @Override
    public int getCount() {
        return cartList.size();
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
        CartViewHolder cartViewHolder;
        if (view == null){
            cartViewHolder = new CartViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            cartViewHolder.imageProductView = (ImageView) view.findViewById(R.id.imageViewCartItem);
            cartViewHolder.title = (TextView) view.findViewById(R.id.titleCartItem);
            cartViewHolder.price = (TextView) view.findViewById(R.id.priceCartItem);
            cartViewHolder.quantity = (TextView) view.findViewById(R.id.quantityCartItem);
            cartViewHolder.up = (ImageButton) view.findViewById(R.id.upQuantity);
            cartViewHolder.down = (ImageButton) view.findViewById(R.id.downQuantity);
            cartViewHolder.delete = (ImageButton) view.findViewById(R.id.deleteCart);
            view.setTag(cartViewHolder);
        } else {
            cartViewHolder = (CartViewHolder) view.getTag();

        }

        Cart cart = cartList.get(i);
        Glide.with(view).load(cart.getImage()).into(cartViewHolder.imageProductView);
        cartViewHolder.title.setText(cart.getTitle());
        cartViewHolder.price.setText(String.valueOf(cart.getPrice()));
        cartViewHolder.quantity.setText(String.valueOf(cart.getQuantity()));
        cartViewHolder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("check onclick");
                cartViewHolder.quantity.setText(String.valueOf(cartList.get(i).getQuantity()+1));
                int quantity = cartList.get(i).getQuantity()+1;
                cartList.get(i).setQuantity(quantity);
                System.out.println(cartViewHolder.quantity.getText().toString());
                notifyDataSetChanged();
                Integer id_cart =  (Integer) cartList.get(i).getId();
                CartApi.retrofit.updateCart(id_cart,"increase").enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()){
                            System.out.println("increase quantity success, id_cart =  " + id_cart);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        });
        cartViewHolder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("check onclick");
                if(cartList.get(i).getQuantity() <=1)
                    return;

                cartViewHolder.quantity.setText(String.valueOf(cartList.get(i).getQuantity()-1));
                int quantity = cartList.get(i).getQuantity()-1;
                cartList.get(i).setQuantity(quantity);
                System.out.println(cartViewHolder.quantity.getText().toString());
                notifyDataSetChanged();
                Integer id_cart =  (Integer) cartList.get(i).getId();
                CartApi.retrofit.updateCart(id_cart,"decrease").enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()){
                            System.out.println("decrease quantity success, id_cart = " + id_cart);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        });
        cartViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("check onclick delete button");
                Cart cart_remove = cartList.get(i);
                cartList.remove(i);
                notifyDataSetChanged();
                Integer id_cart =  (Integer) cart_remove.getId();
                CartApi.retrofit.deleteCart(id_cart).enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code()==204){
                            System.out.println("delete id_cart = " + id_cart);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
            }
        });
        return view;
    }
}
