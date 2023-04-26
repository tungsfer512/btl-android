package fourteam.fantastic.btl.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fourteam.fantastic.btl.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private List<Product> mlist;

    public WishlistAdapter(List<Product> mlist) {
        this.mlist = mlist;
        setHasStableIds(true);
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
        Product product = mlist.get(position);

        if (product == null) return;

        Glide.with(holder.itemView).load(product.getImg()).into(holder.img);
        holder.nameProduct.setText(product.getName());
        holder.priceProduct.setText(product.getPrice());

        holder.btnDeleteWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Delete WishListItem");
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
