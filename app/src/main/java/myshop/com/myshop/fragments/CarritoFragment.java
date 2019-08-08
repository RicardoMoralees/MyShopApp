package myshop.com.myshop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import myshop.com.myshop.R;
import myshop.com.myshop.adapters.ProductAdapter;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.services.RemoveFromCartService;
import myshop.com.myshop.utils.Session;
import myshop.com.myshop.utils.Utils;

public class CarritoFragment extends Fragment implements ProductAdapter.ItemInterface, RemoveFromCartService.RemoveFromCartInterface {

    private Carrito carrito;
    private RecyclerView rvCarritoProducts;
    private ImageView ivEmptyCart;
    private ProductAdapter adapter;
    private Context context;
    private TextView tvCarritoTotal;
    private LinearLayout totalContainer;

    public CarritoFragment() {
        // Required empty public constructor
    }

    public static CarritoFragment newInstance(Context context, Carrito carrito) {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();
        fragment.carrito = carrito;
        fragment.context = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        rvCarritoProducts = view.findViewById(R.id.rv_cart_products);
        ivEmptyCart = view.findViewById(R.id.iv_cart_empty);
        tvCarritoTotal = view.findViewById(R.id.tv_carrito_total);
        totalContainer = view.findViewById(R.id.carrito_total_container);

        if (carrito != null){
            setAdapter();
        }

        return view;
    }

    @Override
    public void onItemClicked(Producto producto) {
        RemoveFromCartService.startService(producto, Session.getInstance().getEmail(),this);
    }

    private void setAdapter(){
        if (carrito.getProductoList().size() == 0){
            ivEmptyCart.setVisibility(View.VISIBLE);
            rvCarritoProducts.setVisibility(View.GONE);
            totalContainer.setVisibility(View.GONE);
        }else {
            rvCarritoProducts.setLayoutManager(new LinearLayoutManager(context));
            adapter = new ProductAdapter(context, carrito.getProductoList(), this, true);
            rvCarritoProducts.setAdapter(adapter);
            ivEmptyCart.setVisibility(View.GONE);
            rvCarritoProducts.setVisibility(View.VISIBLE);
            totalContainer.setVisibility(View.VISIBLE);
            tvCarritoTotal.setText(Utils.getFormatPrize(context,carrito.getTotal()));
        }

    }

    @Override
    public void onSuccessRemoveCarrito(Carrito newCarrito) {
        this.carrito = newCarrito;
        if (carrito.getProductoList().size() == 0){
            ivEmptyCart.setVisibility(View.VISIBLE);
            rvCarritoProducts.setVisibility(View.GONE);
            totalContainer.setVisibility(View.GONE);
        } else {
            tvCarritoTotal.setText(Utils.getFormatPrize(context, carrito.getTotal()));
            adapter.setProductos(carrito.getProductoList());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailRemove(String message) {

    }
}
