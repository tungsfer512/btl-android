package fourteam.fantastic.btl.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fourteam.fantastic.btl.R;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>{
    public List<OrderItem> mList;

    public OrderItemAdapter(List<OrderItem> mList) {
        this.mList = mList;
        setHasStableIds(false);
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        int rowPos = holder.getAdapterPosition();
        if(rowPos == 0){
            holder.shipmentStatus.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.products.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.address.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.total.setBackgroundResource(R.drawable.table_header_cell_bg);

            holder.shipmentStatus.setText("Status");
            holder.products.setText("Products");
            holder.address.setText("Address");
            holder.total.setText("Total");
        } else {
            OrderItem orderItem = mList.get(position - 1);
            if(orderItem == null) return;
            holder.shipmentStatus.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.products.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.address.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.total.setBackgroundResource(R.drawable.table_content_cell_bg);

            holder.shipmentStatus.setText(orderItem.getShipmentStatus());
            holder.products.setText(orderItem.getProducts());
            holder.address.setText(orderItem.getAddress());
            holder.total.setText(orderItem.getTotal());
        }
    }

    @Override
    public int getItemCount() {
        if(mList != null) return mList.size() + 1;
        return 0;
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{
        private TextView products;
        private TextView shipmentStatus;
        private TextView address;
        private TextView total;
        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            products = itemView.findViewById(R.id.orderProducts);
            shipmentStatus = itemView.findViewById(R.id.orderStatus);
            address = itemView.findViewById(R.id.orderAddress);
            total = itemView.findViewById(R.id.orderTotal);
        }
    }
}
