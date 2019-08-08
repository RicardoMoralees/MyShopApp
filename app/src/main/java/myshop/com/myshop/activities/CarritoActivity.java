package myshop.com.myshop.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import myshop.com.myshop.R;
import myshop.com.myshop.fragments.CarritoFragment;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.services.GetCartService;
import myshop.com.myshop.utils.Session;

public class CarritoActivity extends AppCompatActivity implements GetCartService.GetCarritoInterface {

    private ProgressBar progressBar;
    private Carrito carrito;
    private CarritoFragment carritoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        progressBar = findViewById(R.id.pb_carrito);
        GetCartService.startService(Session.getInstance().getEmail(), this);
    }

    @Override
    public void onSuccessCarrito(Carrito carrito) {
        this.carrito = carrito;
        openCarritoFragment();
    }

    @Override
    public void onFailCarrito(String message) {

    }

    private void openCarritoFragment(){
        progressBar.setVisibility(View.GONE);
        carritoFragment = CarritoFragment.newInstance(this,carrito);
        getSupportFragmentManager().beginTransaction().add(R.id.carrito_container, carritoFragment).commit();
    }
}
