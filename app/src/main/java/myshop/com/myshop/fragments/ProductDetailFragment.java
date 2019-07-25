package myshop.com.myshop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import myshop.com.myshop.R;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.services.AddToCartService;
import myshop.com.myshop.utils.Utils;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private Producto producto;
    private Button btnAddCart;
    private TextView tvProductName, tvProductDesc, tvProductPrice;
    private ImageView ivProductImage;
    private Context context;
    private String id;
    private AddToCartService.AddToCartInterface cartInterface;


    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(
            Context context,
            Producto producto,
            String id,
            AddToCartService.AddToCartInterface cartInterface) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setProducto(producto);
        fragment.setContext(context);
        fragment.setId(id);
        fragment.setCartInterface(cartInterface);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        btnAddCart = view.findViewById(R.id.btn_add_cart);
        btnAddCart.setOnClickListener(this);

        tvProductDesc = view.findViewById(R.id.tv_detail_product_desc);
        tvProductName = view.findViewById(R.id.tv_detail_product_name);
        tvProductPrice = view.findViewById(R.id.tv_detail_product_price);
        ivProductImage = view.findViewById(R.id.iv_product_detail_image);

        Glide.with(context).load(producto.getImagen()).into(ivProductImage);
        tvProductPrice.setText(Utils.getFormatPrize(context, producto.getPrecio()));
        tvProductName.setText(producto.getNombre());
        tvProductDesc.setText(producto.getDescripcion());
        return view;
    }


    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public void onClick(View view) {
        AddToCartService.startService(producto, id, cartInterface);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCartInterface(AddToCartService.AddToCartInterface cartInterface) {
        this.cartInterface = cartInterface;
    }
}
