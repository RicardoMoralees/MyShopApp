package myshop.com.myshop.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import myshop.com.myshop.R;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.utils.Utils;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ContentViewHolder> {

    private List<Producto> productos;
    private Context context;
    private ItemInterface itemInterface;
    private boolean isCart;

    public ProductAdapter(
            Context context,
            List<Producto> productos,
            ItemInterface itemInterface,
            boolean isCart) {
        this.productos = productos;
        this.context = context;
        this.itemInterface = itemInterface;
        this.isCart = isCart;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        ContentViewHolder viewHolder = new ContentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        final Producto producto = productos.get(position);

        if (isCart){
            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onItemClicked(producto);
                }
            });
        } else {
            holder.ivRemove.setVisibility(View.GONE);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onItemClicked(producto);
                }
            });
        }

        Glide.with(context).load(producto.getImagen()).into(holder.ivImage);
        holder.tvName.setText(producto.getNombre());
        holder.tvPrize.setText(Utils.getFormatPrize(context,producto.getPrecio()));
        holder.tvDesc.setText(producto.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvDesc, tvPrize;
        public ImageView ivImage, ivRemove;
        public CardView container;

        public ContentViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.cv_product_container);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvDesc = itemView.findViewById(R.id.tv_product_desc);
            tvPrize = itemView.findViewById(R.id.tv_product_prize);
            ivImage = itemView.findViewById(R.id.iv_product_image);
            ivRemove = itemView.findViewById(R.id.iv_remove);
        }
    }

    public interface ItemInterface{
        void onItemClicked(Producto producto);
    }
}
