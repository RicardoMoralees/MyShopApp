package myshop.com.myshop.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import myshop.com.myshop.R;
import myshop.com.myshop.adapters.ProductAdapter;
import myshop.com.myshop.dialogs.CerrarSesionDialog;
import myshop.com.myshop.models.Producto;


public class MainFragment extends Fragment {

    private RecyclerView rvContenido;
    private ProductAdapter adapter;
    private List<Producto> productos;
    private CerrarSesionDialog cerrarSesionDialog;
    private Context context;
    private ProductAdapter.ItemInterface itemInterface;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Context context, List<Producto> productos, ProductAdapter.ItemInterface itemInterface) {
        MainFragment fragment = new MainFragment();
        fragment.setProductos(productos);
        fragment.setContext(context);
        fragment.setItemInterface(itemInterface);
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvContenido = view.findViewById(R.id.rv_products);

        rvContenido.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ProductAdapter(context, productos,itemInterface, false);
        rvContenido.setAdapter(adapter);
        rvContenido.setVisibility(View.VISIBLE);
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setItemInterface(ProductAdapter.ItemInterface itemInterface) {
        this.itemInterface = itemInterface;
    }
}
