package myshop.com.myshop.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import myshop.com.myshop.R;
import myshop.com.myshop.adapters.ProductAdapter;
import myshop.com.myshop.fragments.MainFragment;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.services.GetCartService;
import myshop.com.myshop.services.GetProductsService;
import myshop.com.myshop.utils.Constants;

public class MainActivity extends AppCompatActivity implements GetProductsService.ProductoInterface, ProductAdapter.ItemInterface, GetCartService.GetCarritoInterface {

    TextView tvCartItemCounter;
    MainFragment mainFragment;
    private ProgressBar progressBar;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_main);

        id = getIntent().getStringExtra(Constants.EXTRA_ID_USUARIO);

        GetProductsService.startService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart_actionbar_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        tvCartItemCounter = notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tvCartItemCounter.setText("12");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.badge) {
            Log.e("MenuOption","Carrito");
            return true;
        }
        if (id == R.id.logout) {
            Log.e("MenuOption","Logout");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //getProducts
    @Override
    public void onSuccess(List<Producto> productos) {
        progressBar.setVisibility(View.GONE);
        mainFragment = MainFragment.newInstance(this,productos, this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,mainFragment).commit();
    }

    //getCarrito
    @Override
    public void onSuccess(Carrito carrito) {
        tvCartItemCounter.setText(carrito.getProductoList().size());
    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void onItemClicked(Producto producto) {
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra(Constants.EXTRA_ID_PRODUCTO, producto.getIdProducto());
        intent.putExtra(Constants.EXTRA_ID_USUARIO, id);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESPONSECODE_OK){
            GetCartService.startService(id, this);
        }
    }
}
