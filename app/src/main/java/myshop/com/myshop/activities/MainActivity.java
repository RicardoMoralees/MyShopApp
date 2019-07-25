package myshop.com.myshop.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import myshop.com.myshop.R;
import myshop.com.myshop.adapters.ProductAdapter;
import myshop.com.myshop.dialogs.CerrarSesionDialog;
import myshop.com.myshop.fragments.MainFragment;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.services.GetCartService;
import myshop.com.myshop.services.GetProductsService;
import myshop.com.myshop.utils.Constants;
import myshop.com.myshop.utils.Session;

public class MainActivity extends AppCompatActivity
        implements GetProductsService.ProductoInterface,
        ProductAdapter.ItemInterface,
        GetCartService.GetCarritoInterface,
        View.OnClickListener{

    private TextView tvCartItemCounter;
    private MainFragment mainFragment;
    private ProgressBar progressBar;
    private CerrarSesionDialog cerrarSesionDialog;
    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_main);


        GetProductsService.startService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart_actionbar_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        tvCartItemCounter = notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tvCartItemCounter.setText("0");

        // Inflate the menu; this adds items to the action bar if it is present.


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            showLogoutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog(){
        if (cerrarSesionDialog != null){
            cerrarSesionDialog.show(getSupportFragmentManager(),"LogoutDialog");
        }else {
            cerrarSesionDialog = CerrarSesionDialog.create(this);
            showLogoutDialog();
        }
    }

    //getProducts
    @Override
    public void onSuccess(List<Producto> productos) {
        progressBar.setVisibility(View.GONE);
        mainFragment = MainFragment.newInstance(this,productos, this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,mainFragment).commit();
        GetCartService.startService(Session.getInstance().getEmail(),this);
    }

    @Override
    public void onFail(String message) {

    }

    //getCarrito
    @Override
    public void onSuccessCarrito(Carrito carrito) {
        Log.e("MainActivity", "onSuccesscarrito");
        this.carrito = carrito;
        setTvCartItemCounter(carrito.getProductoList().size() + "");
    }

    @Override
    public void onFailCarrito(String message) {

    }

    @Override
    public void onItemClicked(Producto producto) {
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra(Constants.EXTRA_ID_PRODUCTO, producto.getIdProducto());
        intent.putExtra(Constants.EXTRA_ID_USUARIO, Session.getInstance().getEmail());
        startActivityForResult(intent,Constants.REQUEST_CARRITO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CARRITO){
            GetCartService.startService(Session.getInstance().getEmail(),this);
        }
    }

    private void setTvCartItemCounter(String items){
        tvCartItemCounter.setText(items);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.shopping_cart){
            Intent intent = new Intent(this, CarritoActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_dialog_cerrar_sesion){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Session.getInstance().cerrarSesion();
            startActivity(intent);
            finish();
        }
    }
}
