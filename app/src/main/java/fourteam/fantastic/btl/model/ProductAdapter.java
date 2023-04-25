package fourteam.fantastic.btl.model;

import android.content.Intent;
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

import fourteam.fantastic.btl.HomeProductListActivity;
import fourteam.fantastic.btl.ProductDetailActivity;
import fourteam.fantastic.btl.R;

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
                System.out.println("Add to WishList");
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productDetailIntent = new Intent(view.getContext(), ProductDetailActivity.class);

                productDetailIntent.putExtra("product_id", String.valueOf(product.getId()));

                view.getContext().startActivity(productDetailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mList!=null) return mList.size();
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
