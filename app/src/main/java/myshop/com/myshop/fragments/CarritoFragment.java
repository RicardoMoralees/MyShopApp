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
import android.widget.TextView;

import io.realm.Realm;
import myshop.com.myshop.R;
import myshop.com.myshop.adapters.ProductAdapter;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.services.GetCartService;
import myshop.com.myshop.utils.MyShopApp;
import myshop.com.myshop.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends Fragment implements ProductAdapter.ItemInterface {

    private Carrito carrito;
    private RecyclerView rvCarritoProducts;
    private ImageView ivEmptyCart;
    private ProductAdapter adapter;
    private Context context;
    private TextView tvCarritoTotal;

    public CarritoFragment() {
        // Required empty public constructor
    }

    public static CarritoFragment newInstance(Context context, Carrito carrito) {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();

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

        if (carrito != null){
            setAdapter();
        }

        return view;
    }

    @Override
    public void onItemClicked(Producto producto) {
        Realm realm = MyShopApp.getRealmInstance();
        realm.beginTransaction();
        carrito.getProductoList().remove(producto);
        realm.delete(Carrito.class);
        realm.copyToRealm(carrito);
        realm.commitTransaction();
        if (carrito.getProductoList().size() == 0){
            ivEmptyCart.setVisibility(View.VISIBLE);
            rvCarritoProducts.setVisibility(View.GONE);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setAdapter(){
        if (carrito.getProductoList().size() == 0){
            ivEmptyCart.setVisibility(View.VISIBLE);
            rvCarritoProducts.setVisibility(View.GONE);
            tvCarritoTotal.setVisibility(View.GONE);
        }else {
            rvCarritoProducts.setLayoutManager(new LinearLayoutManager(context));
            adapter = new ProductAdapter(context, carrito.getProductoList(), this, true);
            rvCarritoProducts.setAdapter(adapter);
            rvCarritoProducts.setVisibility(View.VISIBLE);
            tvCarritoTotal.setVisibility(View.VISIBLE);
            tvCarritoTotal.setText(Utils.getFormatPrize(context,carrito.getTotal()));
        }

    }
}
