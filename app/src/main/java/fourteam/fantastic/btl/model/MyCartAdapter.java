package fourteam.fantastic.btl.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fourteam.fantastic.btl.R;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.OrderItemViewHolder>{
    public List<OrderItem> mList;

    public MyCartAdapter(List<OrderItem> mList) {
        this.mList = mList;
        setHasStableIds(false);
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_item, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        int rowPos = holder.getAdapterPosition();
        if(rowPos == 0){
            holder.payMonney.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.timePayMonney.setBackgroundResource(R.drawable.table_header_cell_bg);

            holder.payMonney.setText("Details");
            holder.timePayMonney.setText("Time");
        } else {
            OrderItem orderItem = mList.get(position - 1);
            if(orderItem == null) return;
            holder.payMonney.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.timePayMonney.setBackgroundResource(R.drawable.table_content_cell_bg);

            holder.payMonney.setText(orderItem.getShipmentStatus());
            holder.timePayMonney.setText(orderItem.getProducts());
        }
    }

    @Override
    public int getItemCount() {
        if(mList != null) return mList.size() + 1;
        return 0;
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{
        private TextView payMonney;
        private TextView timePayMonney;
        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            payMonney = itemView.findViewById(R.id.payMonney);
            timePayMonney = itemView.findViewById(R.id.timePayMonney);
        }
    }
}
