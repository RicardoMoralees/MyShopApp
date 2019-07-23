package myshop.com.myshop.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import myshop.com.myshop.R;
import myshop.com.myshop.fragments.CarritoFragment;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.services.GetCartService;

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

        carrito = Carrito.getCarrito();
        if (carrito == null){
            GetCartService.startService(Usuario.getUsuario().getCorreo(), this);
        } else {
            carritoFragment = CarritoFragment.newInstance(this,carrito);
        }

    }

    @Override
    public void onSuccess(Carrito Carrito) {
        this.carrito = carrito;
        carritoFragment = CarritoFragment.newInstance(this,carrito);
        getSupportFragmentManager().beginTransaction().add(R.id.carrito_container, carritoFragment).commit();
    }

    @Override
    public void onFail(String message) {

    }
}
