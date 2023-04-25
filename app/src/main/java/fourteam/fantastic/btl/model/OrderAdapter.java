package fourteam.fantastic.btl.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import fourteam.fantastic.btl.R;

public class OrderAdapter extends BaseAdapter {
    Context context;
    private int layout;
    private List<Cart> list;

    public OrderAdapter(Context context, int layout, List<Cart> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }
    private class OrderViewHolder{
        ImageView image;
        TextView title;
        TextView price;
        TextView quantity;
    }
    @Override
    public int getCount() {
        return list.size();
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
        OrderViewHolder orderViewHolder;
        if(view == null){
            orderViewHolder = new OrderViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            orderViewHolder.image = (ImageView) view.findViewById(R.id.imageOrderItem);
            orderViewHolder.title = (TextView) view.findViewById(R.id.titleOrderItem);
            orderViewHolder.price = (TextView) view.findViewById(R.id.priceOrderItem);
            orderViewHolder.quantity = (TextView) view.findViewById(R.id.quantityOrderItem);
            view.setTag(orderViewHolder);
        } else{
            orderViewHolder = (OrderViewHolder) view.getTag();
        }
        Cart cart = list.get(i);
        Glide.with(view).load(cart.getImage()).into(orderViewHolder.image);
        orderViewHolder.title.setText(cart.getTitle());
        orderViewHolder.price.setText(String.valueOf(cart.getPrice()));
        orderViewHolder.quantity.setText("x"+String.valueOf(cart.getQuantity()));

        return view;
    }
}
